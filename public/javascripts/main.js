const wsLocation = "ws://localhost:9000/ws"

let sock = new WebSocket(wsLocation)

let closed = true;

function reconnectSock() {
    sock = new WebSocket(wsLocation)
}

const messages = document.getElementById("messages")
const form = document.getElementById("form")

form.onsubmit = (event) => {
    if (closed) reconnectSock();
    event.preventDefault()
    sock.send(event.target.message.value)
}

sock.onopen = (event) => {
    closed = false;
    sock.send("Connection opened")
}

sock.onmessage = (event) => {
    console.log(event, messages)
    messages.innerHTML += `<h3>${event.data}</h3>\n`
}

sock.onclose = (event) => {
    closed = true;
}