package actors

import org.apache.pekko.actor._
import java.util.Date
import spray.json.DefaultJsonProtocol._

type bool = Boolean

object ClientActor {
  def props(sys: ActorSystem, out: ActorRef, chat: ActorRef, token: auth.JWT) = Props(new ClientActor(sys, out, chat, token))
}

/**
  * The Client Session Interface
  *
  * @param sys The Actor System that this actor is running off
  * @param out The Websocket Actor
  * @param chat The Chat where messages will be sent and received
  * @param token The JWT provided by the client
  */
class ClientActor(sys: ActorSystem, out: ActorRef, chat: ActorRef, token: auth.JWT) extends Actor {
  val username = token.getBody("username").convertTo[String]
  chat ! models.SessionAccess(self, username)
  override def receive = {
    // We don't need to worry about checking the token here because it is checked when the session is established
    // however this will not enforce token expiration if they are connected when the token expires
    case msg: String => {
      chat.forward(new models.UserMessage(username, msg))
    }

    /**
      * This will forward the Message to the client in JSON format
      */
    case msg: models.SessionMessage => {
      out.forward(msg.message.toJson)
    }
  }

  /**
    * This will run when the Actor is stopped and it will attempt to clean up after itself
    */
  override def postStop(): Unit = {
    chat ! models.SessionDestroy(self)
  }
}