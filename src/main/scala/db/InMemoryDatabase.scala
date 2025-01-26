package db

import domain.*

trait DatabaseAlg {
  def insertUser(user: domain.User): Unit

  def getUser(userId: UserId): Option[User]

  def getAllUsers: scala.collection.mutable.Map[UserId, User]
}

private class InMemoryDatabase(inMemoryStore: scala.collection.mutable.Map[UserId, User]) extends DatabaseAlg {
  override def insertUser(user: User): Unit =
    inMemoryStore.get(user.id) match
      case Some(user) =>
        val newUser = user.copy(queryCount = user.queryCount + 1)
        inMemoryStore.update(user.id, newUser)
      case None =>
        inMemoryStore.addOne(user.id, user.copy(queryCount = 1))

  override def getAllUsers: scala.collection.mutable.Map[UserId, User] = inMemoryStore

  override def getUser(userId: UserId): Option[User] =
    inMemoryStore.get(userId)
}

object InMemoryDatabase {
  def make(state: scala.collection.mutable.Map[UserId, User]): DatabaseAlg = {
    new InMemoryDatabase(state)
  }
}
