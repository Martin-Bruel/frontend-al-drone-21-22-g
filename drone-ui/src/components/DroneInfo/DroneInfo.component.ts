import { key } from "@/store/store";
import { Options, Vue } from "vue-class-component";
import { useStore } from "vuex";

@Options({
    props:{
        id: Number
    }
})
export default class DroneInfo extends Vue {
    
    id!:number;

    store = useStore(key);

    get drone() {
        return this.store.getters.droneById(this.id);
    }

    get droneJson(){
        return JSON.stringify(this.store.getters.droneById(this.id),null,4);
    }
}