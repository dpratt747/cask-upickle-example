package endpoints

import cask.util.Logger
import domain.{JsonResponse, User}
import endpoints.*
import program.UserProgramAlg

private class UserEndpoints(program: UserProgramAlg, logger: Logger) extends cask.Routes {

  @cask.postJson("/user")
  def postUser(user_id: ujson.Value, name: ujson.Value) = {
    logger.debug(s"[POST] /user request received")
    (for {
      userId <- convertToUserId(user_id)
      userName <- convertToUserName(name)
      user = User(userId, userName)
      _ = program.createNewUser(user)
    } yield user).fold(
      error =>
        cask.Response(jsonify(JsonResponse(error)), 400),
      success => cask.Response(jsonify(JsonResponse(s"No errors occurred. User stored successfully [$success]")), 200)
    )
  }

  @cask.getJson("/user/:userId")
  def getUsers(userId: String) = {
    logger.debug(s"[GET] /user/$userId request received")
    (for {
      userId <- convertToUserId(userId)
      user = program.getUser(userId)
    } yield user).fold(
      error => cask.Response(jsonify(JsonResponse(error)), 400),
      {
        case Some(user) => cask.Response(jsonify(JsonResponse(s"No errors occurred. User found successfully [$user]")), 200)
        case None => cask.Response(jsonify(JsonResponse(s"No errors occurred but no User was found")), 404)
      }
    )
  }

  @cask.getJson("/users")
  def getUser() =
    logger.debug(s"[GET] /users request received")
    cask.Response(jsonify(JsonResponse(program.getAllUsers.toString())), 200)

  initialize()
}

object UserEndpoints {
  def make(programAlg: UserProgramAlg, logger: Logger): UserEndpoints = {
    UserEndpoints(programAlg, logger)
  }
}