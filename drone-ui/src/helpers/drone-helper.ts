import { DroneStatus, DroneType } from "@/types/types";

// @ts-ignore
//
// Reading scss variables from scss file
// Ignore needed because typescript does not understand this import
import _export from '@/assets/styles/_export.scss';

export function droneColor(drone: DroneType): string{
    if(isReady(drone)) return _export.ready;
    else if(isFlyingToDelivery(drone) || isFlyingToTruck(drone)) return _export.flying;
    else if(isLost(drone)) return _export.lost;
    else return '';
}

export function isReady(drone: DroneType): boolean{
    return (drone.status === DroneStatus.READY);
}

export function isFlyingToDelivery(drone: DroneType): boolean{
   return (drone.status === DroneStatus.FLYINGTODELIVERY);
}

export function isFlyingToTruck(drone: DroneType): boolean{
   return (drone.status === DroneStatus.FLYINTOTRUCK);
}

export function isLost(drone: DroneType): boolean{
   return (drone.status === DroneStatus.LOST);
}