package controllers

import javax.inject.Inject

import org.apache.pekko.actor.ActorSystem
import org.apache.pekko.stream.Materializer
import play.api.libs.streams.ActorFlow
import play.api.mvc._
import java.util.ArrayList

class ChatController @Inject() (cc: ControllerComponents) (implicit system: ActorSystem, mat: Materializer) extends AbstractController(cc) {
  val chatSock = system.actorOf(actors.ChatroomManager.props, "he")
  def chatSocketConnection(id: String) = WebSocket.accept[String, String] { request =>
    ActorFlow.actorRef { out => actors.ClientActor.props(system, out, chatSock) }
  }
}