import { Options, Vue } from 'vue-class-component'
import { DroneType } from '@/types/types'

@Options({
    props: {
        drone: undefined
    }
})
export default class Drone extends Vue {
    drone!: DroneType

    droneInfo(){
        console.log(this.drone);
    }
}