package controllers

import javax.inject.Inject

import org.apache.pekko.actor.ActorSystem
import org.apache.pekko.stream.Materializer
import play.api.libs.streams.ActorFlow
import play.api.mvc._
import java.util.ArrayList
import actors.RoomDirector
import org.apache.pekko.actor.ActorContext

/**
  * This is the endpoint controller for the chat websocket
  *
  * @param cc This is a library required param, I believe it is just context for the routing
  * @param system This is the actor system for the server
  * @param mat I'm not quite sure what this is, other than it is required for the Actor system to interface with the Websocket
  */
class ChatController @Inject() (cc: ControllerComponents) (implicit system: ActorSystem, mat: Materializer) extends AbstractController(cc) {
  var roomDirector: RoomDirector = new RoomDirector(system)
  def chatSocketConnection(id: String) = WebSocket.accept[String, String] { request =>
    val token = auth.JWT.fromString
    ActorFlow.actorRef { out => actors.ClientActor.props(system, out, roomDirector.getRoomActor(id)) }
  }
}