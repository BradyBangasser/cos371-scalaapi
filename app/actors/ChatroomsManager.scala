

object ChatroomsManager {
    
}

@Singleton
class ChatroomsManager extends Actor {
    private var rooms: ArrayList[ActorRef]
    def receive: Receive =  {
        case _ => {
            
        }
    }
}
