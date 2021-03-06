import { InjectionKey } from 'vue';
import { DroneStatus, DroneType, Position } from '@/types/types';
import { createStore, Store } from 'vuex'

export interface State {
    truckPosition: Position,
    drones: Array<DroneType>
}

export const key: InjectionKey<Store<State>> = Symbol();

export const store = createStore<State>({
    state:{
        truckPosition: {
            latitude: 43.617226,
            longitude: 7.075738
        },
        drones: [
            {
                id: 0,
                name: 'a',
                status: DroneStatus.READY,
                deliveries: [],
                capacity: 1,
                position: {
                    latitude: 43.615627,
                    longitude: 7.072019
                },
            },
            {
                id: 1,
                name: 'b',
                status: DroneStatus.FLYINGTODELIVERY,
                deliveries: [],
                capacity: 1,
                position: {
                    latitude: 43.622352,
                    longitude: 7.060880
                }
            },
            {
                id: 2,
                name: 'c',
                status: DroneStatus.FLYINTOTRUCK,
                deliveries: [],
                capacity: 1,
                position: {
                    latitude: 43.622224,
                    longitude: 7.056913
                }
            },
            {
                id: 3,
                name: 'd',
                status: DroneStatus.LOST,
                deliveries: [],
                capacity: 1,
                position: {
                    latitude: 43.626964,
                    longitude: 7.041133
                }
            }
        ]
    },
    getters:{
        truck: (state) => {
            return state.truckPosition;
        },
        truckPositionAsArray: (state) => {
            return [state.truckPosition.longitude, state.truckPosition.latitude];
        },
        drones: (state) => {
            return state.drones;
        },
        droneById: (state) => (id: number) => {
            return state.drones.find(drone => drone.id === id);
        },
        dronePositionAsArray: (state, getters) => (id: number) => {
            const drone: DroneType = getters.droneById(id);
            return [drone.position.longitude, drone.position.latitude];
        }
    },
    mutations:{
        // TODO : Methode d'initialisation qui set les coords du camion
        // TODO : voir comment on fait pour apr??s 
        //  > on attend que les drones soient ajout??s dans l'??cran de load ? cool
        //  > on reste sur la map et on fait encore des requetes ? bof
        updateDrone(state, data: {position: Position, droneId: number}){
            state.drones = state.drones.map(drone => {
                if(drone.id === data.droneId){
                    return {...drone, position: data.position};
                }
                return drone;
            });
        }
    },
    actions:{}
})