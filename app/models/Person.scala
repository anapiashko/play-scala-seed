package models

import play.api.libs.json._
case class Person(id: Int, name: String, age: Int)

object Person {

  implicit object PersonFormat extends Format[Person] {
    // convert from Person object to JSON (serializing to JSON)
    def writes(person: Person): JsValue = {
      val personSeq = Seq(
        "id" -> JsNumber(person.id),
        "name" -> JsString(person.name),
        "age" -> JsNumber(person.age)
      )
      JsObject(personSeq)
    }
    // convert from JSON string to a Person object (de-serializing from JSON)
    def reads(json: JsValue): JsResult[Person] = {
      val personId = (json \ "id").as[String].toInt
      val personName = (json \ "name").as[String]
      val personAge = (json \ "age").as[String].toInt
      JsSuccess(Person(personId, personName, personAge))
    }
  }

  def tupled = (this.apply _).tupled
}