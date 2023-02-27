package controllers

import dao.PersonDAO
import play.api.libs.json.Json
import play.api.mvc.{BaseController, ControllerComponents}

import javax.inject.Inject
import scala.concurrent.ExecutionContext.Implicits.global
class PersonController @Inject()(val controllerComponents: ControllerComponents,
                                 val personDAO: PersonDAO) extends BaseController {

  def getAll = Action {
    _ => Ok("Get All")
  }

  def getOldestPeople = Action.async { implicit request => {
      personDAO.getOldestPeople.map {
        data => Ok(Json.stringify(Json.toJson(data)))
      }
    }
  }

  def addPerson = Action(parse.json) {
    implicit request =>
      Ok(Json.toJson(request.body))
  }

}
