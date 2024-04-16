package actors

import org.apache.pekko.actor._

object ClientActor {
  def props(sys: ActorSystem, out: ActorRef, chat: ActorRef) = Props(new ClientActor(sys, out, chat))
}

class ClientActor(sys: ActorSystem, out: ActorRef, chat: ActorRef) extends Actor {
  chat ! models.SessionAccess(self, "this")
  override def receive = {
    case msg: String => {
      chat.forward(new models.UserMessage("", msg))
    }

    case msg: models.SessionMessage => {
      out ! msg.message.getMsg()
    }
  }

  override def postStop(): Unit = {
    chat ! models.SessionDestroy(self)
  }
}