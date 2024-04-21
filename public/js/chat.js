const accessToken = localStorage.getItem("accessToken")
const query = new URLSearchParams(window.location.search)
const chat = query.get("chat")

if (!accessToken | !chat) {
    window.location.href = "/login"
}

const messagesDiv = document.getElementById("messages")
const messageForm = document.getElementById("messageForm")

let connected = false;

function establishSock() {
    let chatSock = new WebSocket(`/api/room/${chat}/ws}`)
    
    chatSock.onopen = () => {
        setConnected(true)
    }

    chatSock.onclose = () => {
        setConnected(false)
    }

    chatSock.onmessage = (event) => {
        const messageData = JSON.parse(event.data)
        messagesDiv.innerText += `${new Date(messageData.timestamp).toLocaleDateString()} ${messageData.username}: ${messageData.message}\n`
    }

    messageForm.onsubmit = (event) => {
        chatSock.send(event.target.message.value)
    }
}

/**
 * 
 * @param {boolean} connected 
 */
function setConnected(connected) {
    if (connected) {
        connected = true
    } else {
        connected = false
    }
}