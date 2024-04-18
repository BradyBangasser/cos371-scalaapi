package controllers

import javax.inject.Inject

import org.apache.pekko.actor.ActorSystem
import org.apache.pekko.stream.Materializer
import play.api.libs.streams.ActorFlow
import play.api.mvc._
import java.util.ArrayList
import actors.RoomDirector
import org.apache.pekko.actor.ActorContext
import auth._

class TestController @Inject() (cc: ControllerComponents)() extends AbstractController(cc) {
    def GET = Action {
        Ok(JWT(Map(

        ), Map(

        )).toString)
    }
}