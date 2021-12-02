import { Options, Vue } from 'vue-class-component'
import { DroneType } from '@/types/types'
import { droneColor, isFlyingToDelivery, isFlyingToTruck } from '@/helpers/drone-helper';

@Options({
    props: {
        drone: undefined
    }
})
export default class Drone extends Vue {
    drone!: DroneType

    droneWidth = 50;
    droneLat = this.drone.position.latitude - this.droneWidth/2;
    droneLon = this.drone.position.longitude - this.droneWidth/2;

    droneInfo(){
        console.log(this.drone);
    }

    droneColor(){
        return droneColor(this.drone);
    }

    delivery(){
        return isFlyingToDelivery(this.drone);
    }

    truck(){
        return isFlyingToTruck(this.drone);
    }

    ///// Component Hooks

    updated(){
        this.droneLat = this.drone.position.latitude - this.droneWidth/2;
        this.droneLon = this.drone.position.longitude - this.droneWidth/2;
        droneColor(this.drone);
    }
}