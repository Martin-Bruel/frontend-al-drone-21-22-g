const DEFAULT_ENV="dev";
let configuration = {
  server_name:"warehouse",
  prod:{
    port: 8086,
    database: '' , 
    external: {
    }
  },
  dev:{
    port: 8086,
    database:'',
    external: {
    }
  }
};
function getConfiguration(){
  const ENV= process.env.APP_ENV || DEFAULT_ENV;
  configuration[ENV].port = process.env.PORT || configuration[ENV].port;
  return {service:configuration.server_name,context :configuration[ENV], env:ENV};
}

module.exports = { getConfiguration};