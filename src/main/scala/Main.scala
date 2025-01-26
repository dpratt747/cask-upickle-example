import cask.main.Routes
import db.InMemoryDatabase
import domain.*
import endpoints.UserEndpoints
import program.UserProgram

object Main extends cask.Main {

  private val inMemoryDatabase = InMemoryDatabase.make(scala.collection.mutable.Map.empty[UserId, User])
  private val userProgram = UserProgram.make(inMemoryDatabase)
  private val userEndpoints = UserEndpoints.make(userProgram, log)

  override def allRoutes: Seq[Routes] = Seq(userEndpoints)

  log.debug(s"Server is up and running on host: $host and port: $port")
}
