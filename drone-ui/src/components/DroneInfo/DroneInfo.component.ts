import { Options, Vue } from "vue-class-component";

@Options({
    props:{
        id: Number
    }
})
export default class DroneInfo extends Vue {
    
    id!:number;

    

}