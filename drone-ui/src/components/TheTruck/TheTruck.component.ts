import { Position } from "@/types/types";
import { Options, Vue } from "vue-class-component";

@Options({
    props: {
        position: undefined
    }
})
export default class TheTruck extends Vue {
    position!: Position

    truckWidth = 40;
    truckHeight = 60;
    truckLat = this.position.latitude - this.truckWidth/2;
    truckLon = this.position.longitude - this.truckHeight/2;

    ///// Component Hooks

    updated(){
        this.truckLat = this.position.latitude - this.truckWidth/2;
        this.truckLon = this.position.longitude - this.truckHeight/2;
    }
}