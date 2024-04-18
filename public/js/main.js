const wsLocation = "ws://localhost:9000/ws/h"

let sock = new WebSocket(wsLocation)

let closed = true;

function reconnectSock() {
    sock = new WebSocket(wsLocation)

    sock.onopen = (event) => {
        closed = false;
    }

    sock.onmessage = (event) => {
        messages.innerText += `${event.data}\n`
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