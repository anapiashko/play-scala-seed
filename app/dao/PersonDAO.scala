package dao

import models.Person
import play.api.db.slick.DatabaseConfigProvider
import slick.jdbc.PostgresProfile.api._
import slick.jdbc.JdbcProfile
import slick.lifted.{TableQuery, Tag}

import javax.inject.{Inject, Singleton}
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

@Singleton
class PersonDAO @Inject()(protected val dbConfigProvider: DatabaseConfigProvider) {

  private val dbConfig = dbConfigProvider.get[JdbcProfile]

  private val people = TableQuery[People]

  def getYoungestPeople: Future[Seq[Person]] = {
    dbConfig.db.run(people.sortBy(_.value.age).take(5).result)
  }

  def savePerson (person: Person): Future[Person] = {
    dbConfig.db.run(people += person).map( _ => person)
  }

  def deletePerson(personId: Int): Future[Int] = {
    val query = people.filter(_.id === personId)
    dbConfig.db.run(query.delete)
  }

  def updatePersonName(personId: Int, personName: String): Future[Int] = {
    val query = people.filter(_.id === personId).map(_.name).update(personName)
    dbConfig.db.run(query)
  }

  private class People(tag: Tag) extends Table[Person](tag, "people") {
    def id = column[Int]("person_id", O.PrimaryKey, O.AutoInc)

    def name = column[String]("name")

    def age = column[Int]("age")

    override def * = (id, name, age) <> (Person.tupled, Person.unapply)
  }
}
