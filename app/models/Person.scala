package models

import play.api.libs.json._
case class Person(id: Int, name: String, age: Int)

object Person {

  implicit object PersonFormat extends Format[Person] {
    // convert from Fruit object to JSON (serializing to JSON)
    def writes(person: Person): JsValue = {
      val personSeq = Seq(
        "name" -> JsString(person.name),
        "age" -> JsNumber(person.age)
      )
      JsObject(personSeq)
    }
    // convert from JSON string to a Fruit object (de-serializing from JSON)
    def reads(json: JsValue): JsResult[Person] = {
      JsSuccess(Person((json \ "id").as[Int], (json \ "name").as[String], (json \ "age").as[Int]))
    }
  }

  def tupled = (this.apply _).tupled
}