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
        drones: (state) => {
            return state.drones;
        },
        droneById: (state) => (id: number) => {
            return state.drones.find(drone => drone.id === id);
        }
    },
    actions:{}
})