const express = require('express')
const app = express();
const { getConfiguration} = require('./config')
app.use(express.json());
const api = require('./api')
app.use('/warehouse-api', api);
app.listen(getConfiguration().context.port, function() {
   console.log('(env:'+getConfiguration().env+') Server ['+getConfiguration().service+'] started on port: ' + getConfiguration().context.port);
});