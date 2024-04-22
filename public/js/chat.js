const accessToken = localStorage.getItem("accessToken")
const query = new URLSearchParams(window.location.search)
const chat = query.get("id")

if (!accessToken | !chat) {
    document.location.assign("/")
    alert("here")
}

const messagesDiv = document.getElementById("messages")
const messageForm = document.getElementById("messageForm")

let connected = false;

function establishSock() {
    let chatSock = new WebSocket(`ws://localhost:9000/api/room/${chat}/ws?token=${accessToken}`)
    
    chatSock.onopen = () => {
        setConnected(true)
    }

    chatSock.onclose = () => {
        setConnected(false)
    }

    chatSock.onmessage = (event) => {
        const messageData = JSON.parse(event.data)
        const now = new Date()
        messagesDiv.innerText += `[${now.toLocaleDateString()} ${now.toLocaleTimeString()}] ${messageData.username}: ${messageData.message}\n`
    }

    chatSock.onerror = (event) => {
        console.log(event)
        throw event
    }

    messageForm.onsubmit = (event) => {
        event.preventDefault()
        chatSock.send(event.target.message.value)
        event.target.message.value = ""
    }
}

const logoutButton = document.getElementById("logout")

if (logoutButton) {
    logoutButton.onclick = (event) => {
        localStorage.clear()
        window.location.href = "/"
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

establishSock()