const {createClient} = require('redis');
const {WebSocketServer} = require('ws')

const wss = new WebSocketServer({ port: 3000 });
console.log("listening on 3000")
var sockets = [];

wss.on('connection', function connection(ws) {
    console.log("new connection")
    sockets.push(ws);
});

const client = createClient();
client.connect().then(() => {
    client.subscribe('droneTopic', (message) => {
        console.log("Receive: " + message); // 'message'
        sockets.forEach((s) => s.send(message));
    });
});
