package program

import db.DatabaseAlg
import domain.{User, UserId}

import scala.collection.mutable

trait UserProgramAlg {
  def createNewUser(user: User): Unit

  def getAllUsers: scala.collection.mutable.Map[UserId, User]

  def getUser(userId: UserId): Option[User]
}

private class UserProgram(database: DatabaseAlg) extends UserProgramAlg {
  override def createNewUser(user: User): Unit = database.insertUser(user)

  override def getAllUsers: mutable.Map[UserId, User] = database.getAllUsers

  override def getUser(userId: UserId): Option[User] = database.getUser(userId)
}

object UserProgram {
  def make(database: DatabaseAlg): UserProgramAlg = {
    new UserProgram(
      database
    )
  }
}

