package actors

import org.apache.pekko.actor._

object ChatActor {
  def props(out: ActorRef) = Props(new ChatActor(out))
}

class ChatActor(out: ActorRef) extends Actor {
  def receive = {
    case msg: String => {
      Console.println("here")
      out ! ("I received your message: " + msg)
    }
  }
}