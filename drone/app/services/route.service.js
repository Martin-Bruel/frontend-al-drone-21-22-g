var cron = require('node-cron');
const { Route } = require('../models')
const TruckService= require('../services/truck.service')
const {getConfiguration}=require('../config')
const {distance}=require('../utils/math')
var connectionState = require('../utils/connection-state');

let task=[]

exports.startDelivery= async function(itinary){
    let droneId= getConfiguration().info.id;
    Route.clean()
    Route.create({id:0,start:new Date().getTime() / 1000,itinary:itinary,step:0, lastdate:new Date().getTime() / 1000})
    console.log('Drone reiceve flight plan : ' + JSON.stringify(itinary))
    TruckService.sendDeliveryState(droneId,1);
    startTask(itinary) 
}

function startTask(itinary){
    
    let droneId= getConfiguration().info.id
    let droneSpeed= getConfiguration().info.speed
    let time = 0
    let precPos = itinary.start
    var route =Route.getById(0)
    for (let i = 0; i < itinary.steps.length; i++) {
        time = distance(precPos, itinary.steps[i]) / droneSpeed + time
        console.log(Math.round(time) + ' seconds before arriving to step ' + (i + 1))
        setTimeout(() => {
            console.log('Delivering package at ' + toString_Position(calculPosition()) + ' <' + itinary.steps[i].deliveryId + '>')
            route.lastdate = new Date().getTime() / 1000
            route.step = i + 1
            TruckService.sendDeliveryState(droneId,4, itinary.steps[i].deliveryId);//Sending delivery confirmation
        }, time * 1000)    
        precPos = itinary.steps[i] 
    }
    time = distance(precPos, itinary.start) / droneSpeed + time
    console.log(Math.round(time) + ' seconds before returning to truck')
    setTimeout(() => {
        connectionState.ACCEPT_CONNECTION
        TruckService.sendDeliveryState(droneId,3);//Sending delivery confirmation
        console.log('Im docked at ' + toString_Position(calculPosition()))
    }, time * 1000) 

    while(time > 0){
        setTimeout(() => {
            console.log('Position : ' + toString_Position(calculPosition()))
        }, time * 1000)
        time--
    }
    
}



exports.getPosition = async function(){
    let position = calculPosition();
    console.log('Sending position to truck '+ toString_Position(position))
    return position
}

function calculPosition() {
    
    var route =Route.getById(0)
    var precPosition = route.step > 0 ? route.itinary.steps[route.step-1] : route.itinary.start
    
    var nextPosition = route.step < route.itinary.steps.length ? route.itinary.steps[route.step] : route.itinary.start
    var dist = distance(precPosition, nextPosition)
    var datePrec = route.lastdate
    var droneSpeed= getConfiguration().info.speed

    Xa = precPosition.latitude
    Ya = precPosition.longitude

    Xc = nextPosition.latitude
    Yc = nextPosition.longitude

    Tstart = datePrec
    Tend = dist / droneSpeed + Tstart

    T = new Date().getTime() / 1000

    Xv = (droneSpeed / dist) * (Xc - Xa)
    Yv = (droneSpeed / dist) * (Yc - Ya)

    Xb = Xv * (T - Tstart) + Xa
    Yb = Yv * (T - Tstart) + Ya

    var position = {latitude : Math.round(Xb*100)/100, longitude : Math.round(Yb*100)/100}

    return position;
}

function toString_Position(position){
    return "("+Math.round(position.latitude * 10) / 10 +","+Math.round(position.longitude * 10) / 10+")"
}
