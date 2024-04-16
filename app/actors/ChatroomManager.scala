package actors

// import org.apache.pekko.actor.typed._
import org.apache.pekko.actor._
import java.util.ArrayList

object ChatroomManager {
    def props = Props(new ChatroomManager(""))
}

class ChatroomManager(roomName: String, clients: ArrayList[ActorRef] = new ArrayList()) extends Actor {
    def receive: Receive = {
        case msg: models.UserMessage => {
            clients.forEach(_ ! new models.SessionMessage(msg))
        }

        case reg: models.SessionAccess => {
            clients.add(reg.actor)

        }

        case destroy: models.SessionDestroy => {
            clients.remove(destroy.actor)
        }
    }
}
