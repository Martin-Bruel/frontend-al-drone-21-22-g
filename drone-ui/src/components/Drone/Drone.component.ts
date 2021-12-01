import { Options, Vue } from 'vue-class-component'
import { DroneStatus, DroneType } from '@/types/types'

@Options({
    props: {
        drone: undefined
    }
})
export default class Drone extends Vue {
    drone!: DroneType

    droneColor = 'white';
    droneWidth = 50;
    droneLat = this.drone.position.latitude - this.droneWidth/2;
    droneLon = this.drone.position.longitude - this.droneWidth/2;

    droneInfo(){
        console.log(this.drone);
    }

    isReady(): boolean{
         if(this.drone.status === DroneStatus.READY){
            this.droneColor = '#000069';
            return true;
         }
         return false;
    }

    isFlyingToDelivery(): boolean{
        if(this.drone.status === DroneStatus.FLYINGTODELIVERY){
            this.droneColor = '#006900';
            return true;
        }
        return false;
    }

    isFlyingToTruck(): boolean{
        if(this.drone.status === DroneStatus.FLYINTOTRUCK){
            this.droneColor = '#006900';
            return true;
        }
        return false;
    }

    isLost(): boolean{
        if(this.drone.status === DroneStatus.LOST){
            this.droneColor = '#690000';
            return true;
        }
        return false;
    }

    ///// Component Hooks

    updated(){
        this.droneLat = this.drone.position.latitude - this.droneWidth/2;
        this.droneLon = this.drone.position.longitude - this.droneWidth/2;
    }
}