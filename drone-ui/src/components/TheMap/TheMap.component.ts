import { Options, Vue } from "vue-class-component";
import { DroneType, Position } from "@/types/types";
import { useStore } from "vuex";
import { key } from "@/store/store";

import Drone from '@/components/Drone.vue'
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

@Options({
    components: {
        Drone,
        TheTruck,
        Line
    }
})
export default class TheMap extends Vue {

    map: any = undefined;
    mapLoaded = false;
    droneList: Array<DroneType> = [];
    truckPosition: Position = { latitude:0, longitude:0 };

    store = useStore(key);

    updateElements() {
        this.droneList = [];
        const trucPositionPixels = this.getPixelFromCoordinate(this.store.getters.truck);
        this.truckPosition = { latitude:trucPositionPixels[0], longitude:trucPositionPixels[1] };
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
                new Graticule({
                    strokeStyle: new Stroke({
                        color: '#013b01',
                        width: 1,
                    })
                })
            ],
            view: new View({
                zoom: 0,
                center: [0, 0],
                constrainResolution: true
            })
        });
        this.map.addControl(new Zoom({
            className: 'custom-zoom'
        }));
        this.map.once('postrender', () => {
            console.log('>>> Map Loaded !')
            this.mapLoaded = true;
            this.updateElements();
        });
        this.map.on(['moveend', 'pointerdrag'], () => {
            // console.log('Map moved (end) / dragged');
            this.updateElements();
        });
    }
}