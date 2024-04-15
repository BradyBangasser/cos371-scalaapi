package controllers

import javax.inject._
import play.api._
import play.api.mvc._
import java.time
import java.time.*

@Singleton
class LoginController @Inject()(val controllerComponents: ControllerComponents) extends BaseController {
  def GET(roomName: String, username: String) = Action {
    Ok("str")
  }
}
