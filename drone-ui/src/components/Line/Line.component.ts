import { droneColor } from "@/helpers/drone-helper";
import { DroneType, Position } from "@/types/types";
import { Options, Vue } from "vue-class-component";

@Options({
    props: {
        drone: undefined,
        truckPosition: undefined
    }
})
export default class Line extends Vue {
    drone!: DroneType;
    truckPosition!: Position;

    color(){
        return droneColor(this.drone);
    }
}