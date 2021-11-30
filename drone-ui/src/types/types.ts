export enum DroneStatus {
    READY = 'READY',
    FLYINGTODELIVERY = 'FLYINGTODELIVERY',
    FLYINTOTRUCK = 'FLYINTOTRUCK',
    LOST = 'LOST'
}

export enum DeliveryStatus {
    PENDING = 'PENDING'
}

export interface Position {
    latitude: number,
    longitude: number
}

export interface Deliveries {
    id: number,
    position: Position,
    deliveryStatus: DeliveryStatus
}

export interface DroneType {
    id: number,
    name: string,
    status: DroneStatus,
    deliveries: Array<Deliveries>,
    capacity: number,
    position: Position
}