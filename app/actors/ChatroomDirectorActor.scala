package actors

import org.apache.pekko.actor._
import java.util.ArrayList

/**
  * Static methods for @ref ChatroomManager
  */
object ChatroomDirectorActor {
    def props(roomDirector: RoomDirector, roomname: String) = Props(new ChatroomManager(roomDirector, roomname))
}

/**
  * This Actor will manage all of the messages and clients within a specific room
  *
  * @param roomDirector The Room director
  * @param roomName The String name of the room, this is the unique identifier of it
  * @param clients the list of subscribed clients, in most cases using the fault value is fine
  */
class ChatroomDirectorActor(roomDirector: RoomDirector, roomName: String, clients: ArrayList[ActorRef] = new ArrayList()) extends Actor {
    override def receive: Receive = {
        // Every time a client sends a message, send it to all of the clients
        case msg: models.UserMessage => {
            clients.forEach(_ ! new models.SessionMessage(msg))
        }

        // Register a new listener 
        case reg: models.SessionAccess => {
            clients.add(reg.actor)

        }

        // Remove a listener from the 
        case destroy: models.SessionDestroy => {
            clients.remove(destroy.actor)
            // Interestingly you can't use ! on ints
            if (clients.size() == 0) {
                roomDirector.removeRoomActor(roomName)
                self ! PoisonPill
            }
        }
    }
}
