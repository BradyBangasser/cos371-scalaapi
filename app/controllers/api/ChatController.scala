package controllers

import javax.inject.Inject

import org.apache.pekko.actor.ActorSystem
import org.apache.pekko.stream.Materializer
import play.api.libs.streams.ActorFlow
import play.api.mvc._
import java.util.ArrayList
import actors.RoomDirector
import org.apache.pekko.actor.ActorContext

class ChatController @Inject() (cc: ControllerComponents) (implicit system: ActorSystem, mat: Materializer) extends AbstractController(cc) {
  var roomDirector: RoomDirector = new RoomDirector(system)
  def chatSocketConnection(id: String) = WebSocket.accept[String, String] { request =>
    ActorFlow.actorRef { out => actors.ClientActor.props(system, out, roomDirector.getRoomActor(id)) }
  }
}