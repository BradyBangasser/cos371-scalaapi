package controllers

import javax.inject._
import play.api._
import play.api.mvc._

/**
  * This is the controller for all frontend pages
  *
  * @param controllerComponents
  */
@Singleton
class HomeController @Inject()(val controllerComponents: ControllerComponents) extends BaseController {
  def index() = Action { implicit request: Request[AnyContent] =>
    Ok(views.html.index())
  }

  def chat() = Action { implicit request: Request[AnyContent] => Ok(views.html.chat()) }
}
