package program

import db.DatabaseAlg
import domain.{User, UserId}

import scala.collection.mutable
import scala.concurrent.Future

trait UserProgramAlg {
  def createNewUser(user: User): Future[Unit]

  def getAllUsers: Future[scala.collection.mutable.Map[UserId, User]]

  def getUser(userId: UserId): Future[Option[User]]
}

private class UserProgram(database: DatabaseAlg) extends UserProgramAlg {
  override def createNewUser(user: User): Future[Unit] = database.insertUser(user)

  override def getAllUsers: Future[mutable.Map[UserId, User]] = database.getAllUsers

  override def getUser(userId: UserId): Future[Option[User]] = database.getUser(userId)
}

object UserProgram {
  def make(database: DatabaseAlg): UserProgramAlg = {
    new UserProgram(
      database
    )
  }
}

