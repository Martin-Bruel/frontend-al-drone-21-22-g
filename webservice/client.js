const {WebSocket} = require('ws');

const ws = new WebSocket('ws://localhost:3030');

ws.on('open', function open() {
  console.log("connected")
});

ws.on('message', function message(data) {
  console.log('received: %s', data);
});