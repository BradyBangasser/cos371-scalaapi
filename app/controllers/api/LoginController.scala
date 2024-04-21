package controllers

import javax.inject._
import play.api._
import play.api.mvc._
import java.time._
import spray.json._
import spray.json.DefaultJsonProtocol._

/**
  * This is the endpoint controller for the auth endpoints
  * 
  * @note In most applications this would be more complex and secure but for this simple project we don't need most of that
  * @param controllerComponents
  */
@Singleton
class LoginController @Inject()(val controllerComponents: ControllerComponents) extends BaseController {
  def GET(roomName: String, username: String) = Action {
    val token = auth.JWT(Map(

    ), Map(
      "username" -> JsString(username),
      "chatId" -> JsString(roomName)
    ))

    val accessJson = Map(
      "accessToken" -> token.toString
    )

    Ok(accessJson.toJson.compactPrint)
  }
}
