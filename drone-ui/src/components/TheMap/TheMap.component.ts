import { Options, Vue } from "vue-class-component";
import Drone from '@/components/Drone.vue'
import { DroneType } from "@/types/types";

import Map from 'ol/Map'
import TileLayer from 'ol/layer/Tile'
import OSM from 'ol/source/OSM'
import Graticule from 'ol/layer/Graticule'
import View from 'ol/View'
import Stroke from 'ol/style/Stroke'
import Feature from 'ol/Feature'
import Zoom from 'ol/control/Zoom'

import Point from 'ol/geom/Point'
import Geometry from "ol/geom/Geometry";
import { useStore } from "vuex";
import { key } from "@/store/store";

@Options({
    components: {
        Drone
    }
})
export default class TheMap extends Vue {

    map: any = undefined;
    mapLoaded = false;
    droneList: Array<DroneType> = [];

    store = useStore(key);

    computeDroneList() {
        this.droneList = [];
        let feature: Feature<Geometry>;
        (this.store.getters.drones as Array<DroneType>).forEach(drone => {
            feature = new Feature({ geometry: new Point([drone.position.longitude, drone.position.latitude]).transform('EPSG:4326', 'EPSG:3857') });
            const coordinates = this.map.getPixelFromCoordinate((feature.getGeometry() as Point).getCoordinates());
            this.droneList.push({
                ...drone,
                position: {
                    latitude: coordinates[0],
                    longitude: coordinates[1]
                }
            });
        });
    }

    ///// Component Hooks

    mounted() {
        this.map = new Map({
            target: this.$refs['map-root'] as HTMLElement,
            layers: [
                new TileLayer({
                    source: new OSM({
                        url: 'https://maps.geoapify.com/v1/tile/dark-matter/{z}/{x}/{y}.png?apiKey=2d9839952d2a4e5eaa3c680fb0ba5589'
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
            this.computeDroneList();
        });
        this.map.on(['moveend', 'pointerdrag'], () => {
            // console.log('Map moved (end) / dragged');
            this.computeDroneList();
        });
    }
}