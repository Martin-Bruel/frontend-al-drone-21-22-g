import { Options, Vue } from "vue-class-component";
import { DroneType, Position } from "@/types/types";
import { useStore } from "vuex";
import { key } from "@/store/store";

import Drone from '@/components/Drone.vue'
import DroneInfo from "@/components/DroneInfo.vue";
import TheTruck from "@/components/TheTruck.vue";
import Line from "@/components/Line.vue";

import Map from 'ol/Map'
import TileLayer from 'ol/layer/Tile'
import OSM from 'ol/source/OSM'
import Graticule from 'ol/layer/Graticule'
import View from 'ol/View'
import Stroke from 'ol/style/Stroke'
import Feature from 'ol/Feature'
import Zoom from 'ol/control/Zoom'
import Point from 'ol/geom/Point'
import { transform } from 'ol/proj'

@Options({
    components: {
        Drone,
        DroneInfo,
        TheTruck,
        Line
    }
})
export default class TheMap extends Vue {

    map: any = undefined;
    mapLoaded = false;
    droneList: Array<DroneType> = [];
    truckPosition: Position = { latitude:0, longitude:0 };
    droneSelected = -1;

    store = useStore(key);

    updateElements() {
        this.droneList = [];
        const truckPositionPixels = this.getPixelFromCoordinate(this.store.getters.truck);
        this.truckPosition = { latitude:truckPositionPixels[0], longitude:truckPositionPixels[1] };
        (this.store.getters.drones as Array<DroneType>).forEach(drone => {
            const coordinates = this.getPixelFromCoordinate(drone.position);
            this.droneList.push({
                ...drone,
                position: {
                    latitude: coordinates[0],
                    longitude: coordinates[1]
                }
            });
        });
    }

    getPixelFromCoordinate(position: Position): number[]{
        const feature = new Feature(
            {
                geometry: new Point([position.longitude, position.latitude]).transform('EPSG:4326', 'EPSG:3857')
            });
        return this.map.getPixelFromCoordinate((feature.getGeometry() as Point).getCoordinates());
    }

    onDroneSelected(droneId: number){
        this.droneSelected = droneId;
        const mapView = this.map.getView();
        mapView.setCenter(
            transform(this.store.getters.dronePositionAsArray(droneId), 'EPSG:4326', 'EPSG:3857')
        );
        mapView.setZoom(17);
        console.log('Drone Selected',this.droneSelected);
    }

    ///// Component Hooks

    mounted() {
        this.map = new Map({
            target: this.$refs['map-root'] as HTMLElement,
            layers: [
                new TileLayer({
                    source: new OSM({
                        // url: 'https://maps.geoapify.com/v1/tile/dark-matter/{z}/{x}/{y}.png?apiKey=2d9839952d2a4e5eaa3c680fb0ba5589'
                    })
                }),
                // new Graticule({
                //     strokeStyle: new Stroke({
                //         color: '#013b01',
                //         width: 1,
                //     })
                // })
            ],
            view: new View({
                zoom: 14,
                center: [0,0],
                constrainResolution: true
            })
        });
        this.map.addControl(new Zoom({
            className: 'custom-zoom'
        }));
        this.map.once('postrender', () => {
            this.mapLoaded = true;
            this.updateElements();
            this.map.getView().setCenter(
                transform(this.store.getters.truckPositionAsArray, 'EPSG:4326', 'EPSG:3857')
            );
        });
        this.map.on(['moveend', 'pointerdrag'], () => {
            this.updateElements();
        });
    }
}