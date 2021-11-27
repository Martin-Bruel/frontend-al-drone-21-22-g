const DEFAULT_ENV="dev";
var configuration = {
  server_name:"drone",
  info:{
    id:1,
    speed:1,
    connected:true
  },
  prod:{
    host: process.env.DRONE_HOST,
    port: process.env.DRONE_PORT,
    name: process.env.DRONE_NAME,
    capacity: process.env.DRONE_CAPACITY,
    database: '' ,
    external: {
      truck : {host:'truck',port:8085}
    }
  },
  dev:{
    host: 'localhost',
    port: 8087,
    name: 'alpha',
    capacity: 3,
    database:'',
    external: {
      truck : {host:'localhost',port:8085}
    }
  }
};
function getConfiguration(){
  const ENV= process.env.APP_ENV || DEFAULT_ENV;
  configuration[ENV].port = process.env.PORT || configuration[ENV].port;
  return {service:configuration.server_name,info:configuration.info,context :configuration[ENV], env:ENV};
}

module.exports = { getConfiguration};
