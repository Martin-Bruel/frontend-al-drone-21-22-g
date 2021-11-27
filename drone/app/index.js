const express = require('express')
const axios = require('axios');
const app = express();
const { getConfiguration} = require('./config')
app.use(express.json());
const api = require('./api')
const TruckService= require('./services/truck.service')
app.use('/drone-api', api);
app.listen(getConfiguration().context.port, function() {
   console.log('(env:'+getConfiguration().env+') Server ['+getConfiguration().service+'] started on port: ' + getConfiguration().context.port);
});

TruckService.connectToTruck()
