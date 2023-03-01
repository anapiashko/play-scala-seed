package controllers

import dao.PersonDAO
import models.Person
import play.api.libs.json.{JsArray, Json}
import play.api.mvc.{Action, AnyContent, BaseController, ControllerComponents}

import javax.inject.Inject
import scala.concurrent.ExecutionContext.Implicits.global

class PersonController @Inject()(val controllerComponents: ControllerComponents,
                                 val personDAO: PersonDAO) extends BaseController {

  def getAll: Action[AnyContent] = Action {
    _ => Ok("Get All")
  }

  def getYoungestPeople = Action.async {
    personDAO.getYoungestPeople.map {
      data => Ok(Json.stringify(Json.toJson(data)))
    }
  }

  def savePerson: Action[AnyContent] = Action { request =>
    val json = request.body.asJson.get
    val person = json.as[Person]
    val savedPerson = personDAO.savePerson(person)
    Ok
  }

  def deletePerson(personId: Int): Action[AnyContent] = Action { request =>
    val deletedPerson = personDAO.deletePerson(personId)
    Ok
  }

}
