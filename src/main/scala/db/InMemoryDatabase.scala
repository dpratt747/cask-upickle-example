package db

import domain.*

import scala.concurrent.{ExecutionContext, Future}

trait DatabaseAlg {
  def insertUser(user: domain.User): Future[Unit]

  def getUser(userId: UserId): Future[Option[User]]

  def getAllUsers: Future[scala.collection.mutable.Map[UserId, User]]
}

private class InMemoryDatabase(inMemoryStore: scala.collection.mutable.Map[UserId, User])(implicit val ec: ExecutionContext) extends DatabaseAlg {
  override def insertUser(user: User): Future[Unit] =
    inMemoryStore.get(user.id) match
      case Some(user) =>
        val newUser = user.copy(queryCount = user.queryCount.incrementByOne())
        Future(inMemoryStore.update(user.id, newUser))
      case None =>
        Future(inMemoryStore.addOne(user.id, user.copy(queryCount = QueryCount(1))))

  override def getAllUsers: Future[scala.collection.mutable.Map[UserId, User]] = Future(inMemoryStore)

  override def getUser(userId: UserId): Future[Option[User]] =
    Future(inMemoryStore.get(userId))
}

object InMemoryDatabase {
  def make(state: scala.collection.mutable.Map[UserId, User])(implicit ec: ExecutionContext): DatabaseAlg = {
    new InMemoryDatabase(state)
  }
}
