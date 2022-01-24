export interface Truck {
    available: boolean,
    deliveries:Array<Delivery>,
    drones:Array<DroneType>,
    position: Position | undefined
}

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

export interface Delivery {
    id: number,
    position: Position,
    deliveryStatus: DeliveryStatus
}

export interface DroneType {
    id: number,
    name: string,
    status: DroneStatus,
    deliveries: Array<Delivery>,
    capacity: number,
    position: Position
}