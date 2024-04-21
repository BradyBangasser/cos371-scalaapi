package controllers

import javax.inject._
import play.api._
import play.api.mvc._
import java.time._

/**
  * The ping endpoint controller
  *
  * @param controllerComponents
  */
@Singleton
class PingController @Inject()(val controllerComponents: ControllerComponents) extends BaseController {
  def GET() = Action {
    Ok(Clock.system(ZoneId.systemDefault()).millis().toString())
  }
}
