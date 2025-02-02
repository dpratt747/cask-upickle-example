package endpoints

import cask.Response
import cask.util.Logger
import domain.{JsonResponse, User}
import program.UserProgramAlg
import ujson.Obj

import scala.concurrent.ExecutionContext

private class UserEndpoints(program: UserProgramAlg, logger: Logger)(using ec: ExecutionContext) extends cask.Routes {

  @cask.postJson("/user")
  def postUser(user_id: ujson.Value, name: ujson.Value): Response[Obj] = {
    logger.debug(s"[POST] /user request received")
    (for {
      userId <- convertToUserId(user_id)
      userName <- convertToUserName(name)
      user = User(userId, userName)
    } yield user).fold(
      error =>
        cask.Response(jsonify(JsonResponse(error)), 400),
      user =>
        program.createNewUser(user)
          .map(_ => cask.Response(jsonify(JsonResponse(s"No errors occurred. User stored successfully [$user]")), 200))
          .mapFutureFailure(returnInternalServerErrorOnFailure)
          .awaitResult()
    )
  }

  @cask.getJson("/user/:userId")
  def getUser(userId: String): Response[ujson.Obj] = {
    logger.debug(s"[GET] /user/$userId request received")
    convertToUserId(userId).fold(
      error => cask.Response(jsonify(JsonResponse(error)), 400),
      userId => program.getUser(userId).map {
          case Some(user) => cask.Response(jsonify(JsonResponse(s"No errors occurred. User found successfully [$user]")), 200)
          case None => cask.Response(jsonify(JsonResponse(s"No errors occurred but no User was found")), 404)
        }
        .mapFutureFailure(returnInternalServerErrorOnFailure)
        .awaitResult()
    )
  }

  @cask.getJson("/users")
  def getUsers(): Response[Obj] = {
    logger.debug(s"[GET] /users request received")
    program.getAllUsers
      .map(
        allUsers => cask.Response(jsonify(JsonResponse(allUsers.toString())), 200)
      )
      .mapFutureFailure(returnInternalServerErrorOnFailure)
      .awaitResult()
  }

  initialize()
}

object UserEndpoints {
  def make(programAlg: UserProgramAlg, logger: Logger)(using ec: ExecutionContext): UserEndpoints = {
    UserEndpoints(programAlg, logger)
  }
}