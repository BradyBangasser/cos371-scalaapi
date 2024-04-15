const socket = new WebSocket("ws://localhost:9000/ws")

socket.onopen = (event) => {
    socket.send("here")
}