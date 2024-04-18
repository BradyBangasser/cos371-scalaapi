package actors

// import org.apache.pekko.actor.typed._
import org.apache.pekko.actor._
import java.util.ArrayList

object ChatroomManager {
    def props(roomDirector: RoomDirector, roomname: String) = Props(new ChatroomManager(roomDirector, roomname))
}

class ChatroomManager(roomDirector: RoomDirector, roomName: String, clients: ArrayList[ActorRef] = new ArrayList()) extends Actor {
    def receive: Receive = {
        case msg: models.UserMessage => {
            clients.forEach(_ ! new models.SessionMessage(msg))
        }

        case reg: models.SessionAccess => {
            clients.add(reg.actor)

        }

        case destroy: models.SessionDestroy => {
            clients.remove(destroy.actor)
            // Interestingly you can't use ! on ints
            if (clients.size() == 0) {
                roomDirector.removeRoomActor(roomName)
            }
        }
    }
}
