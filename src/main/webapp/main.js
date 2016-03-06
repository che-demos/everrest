var wsServer = 'ws://localhost:8080/080-everrest/wss';
var webSocket = new WebSocket(wsServer);
var outputData = [];

webSocket.onopen = function (evt) { onOpen(evt) };
webSocket.onclose = function (evt) { onClose(evt) };
webSocket.onmessage = function (evt) { onMessage(evt) };
webSocket.onerror = function (evt) { onError(evt) };


function onOpen(evt) {
  console.log("Connected to WebSocket server.");
  webSocket.send(JSON.stringify({
    headers: [{
      "name":"x-everrest-websocket-message-type",
      "value": "subscribe-channel"
    }],
    body: JSON.stringify({
      channel: "test"
    })
  }));
}

function onClose(evt) {
  console.log("Disconnected");
}

function onMessage(evt) {
  console.log('Retrieved data from server: ');
  outputData.push(JSON.parse(evt.data).body);
  $("#output").val(outputData.join('\n'));
}

function onError(evt) {
  console.log('Error occured: ' + evt.data);
}

$("#exec").on('click', function() {
  $("#output").val("");
  $.get('./api/users/exec');
})