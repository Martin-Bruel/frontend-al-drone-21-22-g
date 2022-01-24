import axios from "axios";

export class ApiService {

    readonly globalURL = 'localhost'
    readonly truckPort = '8085'
    readonly truckInfoPath = '/truck/info'

    getTruckInfo(){
        try{
            axios.get(`http://${this.globalURL}:${this.truckPort}${this.truckInfoPath}`)
                .then((response)=>{console.log('+++++++++++++'); return response})
                .catch((error)=>{console.log('-------------'); return undefined});
        } catch (error) {
            return undefined;
        }
    }
}