import { Truck } from "@/types/types";
import axios from "axios";
import { Vue } from "vue-class-component";

export default class TheLoader extends Vue {

    truckIntervalId: number = 0;
    truckResponse: any = undefined;
    readonly globalURL = 'localhost'
    readonly truckPort = '8085'
    readonly truckInfoPath = '/truck/info'

    truckUp() {
        clearInterval(this.truckIntervalId);
        const truck: Truck = {
            available: true,
            drones: this.truckResponse.data.drones,
            deliveries: this.truckResponse.data.deliveries,
            position: this.truckResponse.data.truckPosition
        };
        this.$emit('backendStatus', truck);
    }

    truckDown() {
        const truck: Truck = {
            available: false,
            drones: [],
            deliveries: [],
            position: undefined
        }
        this.$emit('backendStatus', truck);
    }

    stopSendingRequest() {
        console.log('Stopped the Interval', this.truckIntervalId);
        clearInterval(this.truckIntervalId);
    }

    ///// Component Hooks

    async mounted() {
        this.truckIntervalId = setInterval(() => {
            axios.get(`http://${this.globalURL}:${this.truckPort}${this.truckInfoPath}`)
                .then((response) => {
                    this.truckResponse = response;
                })
                .catch((error) => {});
            if (this.truckResponse !== undefined && this.truckResponse.status === 200) {
                this.truckUp();
            }else{
                this.truckDown();
            }
        }, 2000);
    }
}