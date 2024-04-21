if (localStorage.getItem("accessToken")) {
    window.location.href = `/chat?id=${localStorage.getItem("chatId")}`
}

const loginForm = document.getElementById("login")

loginForm.onsubmit = async (event) => {
    event.preventDefault();
    const chatId = event.target.chatId.value
    const username = event.target.username.value

    const res = await fetch(`/api/room/${chatId}/login?username=${username}`)

    if (res.status !== 200) {
        // cry
        throw new Error("Something failed")
    } else {
        localStorage.setItem("chatId", chatId)
        localStorage.setItem("accessToken", (await res.json()).accessToken)
        window.location.href = `/chat?id=${chatId}`
    }
}