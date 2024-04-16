const wsLocation = "ws://192.168.0.11:9000/ws"

let sock = new WebSocket(wsLocation)

let closed = true;

function reconnectSock() {
    sock = new WebSocket(wsLocation)

    sock.onopen = (event) => {
        closed = false;
    }

    sock.onmessage = (event) => {
        messages.innerHTML += `<h3>${event.data}</h3>\n`
    }

    sock.onclose = (event) => {
        closed = true;
    }
}

const messages = document.getElementById("messages")
const form = document.getElementById("form")

form.onsubmit = (event) => {
    if (closed) reconnectSock();
    event.preventDefault()
    sock.send(event.target.message.value)
}

reconnectSock()