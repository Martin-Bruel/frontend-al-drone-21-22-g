const axios = require('axios')
const { getConfiguration} = require('../config')
var connectionState = require('../utils/connection-state');
let context = getConfiguration().context;
let truckService=context.external.truck;
const states = ['STARTING_DELIVERY','PENDING_DELIVERY','FINISHED_DELIVERY','PACKAGE_DELIVERED']

sendDeliveryState=async function(droneId,statusCode, deliveryId, retrying){

  if(connectionState.ACCEPT_CONNECTION){
    let url = 'http://'+truckService.host+":"+truckService.port+'/delivery'
    let result= await axios.post(url, {
        droneId:droneId,
        deliveryState:statusCode,
        deliveryId:deliveryId
      })
      .then((response) => {
        if(deliveryId) console.log("Notify truck : " + states[statusCode - 1] + ' <' +deliveryId + '>');
        else console.log("Notify truck : " + states[statusCode - 1]);
      }, (error) => {
        console.log(error);
    });
  }
  else {
    if(!retrying) console.log("Cannot contact truck... retrying in background...")
    setTimeout(function() {
      sendDeliveryState(droneId, statusCode,deliveryId, true)
   }, 1000);
  }
}

connectToTruck=async function(){
  let url='http://'+truckService.host + ':'+ truckService.port +'/connect/drone/';
  let result = await axios.post(url, {
    name : context.name,
    host: context.host,
    port: context.port,
    capacity: context.capacity
  }).then((response) => {
    let id = response.data
    console.log("Connected to the truck - my id is : " + id)
    getConfiguration().info.id = id
  }, (error) => {
    setTimeout(function() {
      connectToTruck()
   }, 1000);
  });
  
}

module.exports = {
  sendDeliveryState,
  connectToTruck
}