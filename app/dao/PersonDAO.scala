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

  private class People(tag: Tag) extends Table[Person](tag, "people") {
    private def id = column[Int]("person_id", O.PrimaryKey, O.AutoInc)

    private def name = column[String]("name")

    def age = column[Int]("age")

    override def * = (id, name, age) <> (Person.tupled, Person.unapply)
  }
}
