
import cask.Response
import domain.*
import ujson.*
import upickle.default.{write as uWrite, *}

import scala.concurrent.duration.Duration
import scala.concurrent.{Await, ExecutionContext, Future}

package object endpoints {
  private def convertToUserId(userId: ujson.Value): Either[String, UserId] = {
    userId match
      case Str(IsUUID(uuid)) => Right(UserId(uuid))
      case jsonfield =>
        Left(s"Failed to parse [$jsonfield] to a UUID")
  }

  private def convertToUserName(userId: ujson.Value): Either[String, UserName] = {
    userId match
      case Str(name) => Right(UserName(name))
      case jsonfield =>
        Left(s"Failed to parse [$jsonfield] to a User name")
  }

  private def jsonify[A: ReadWriter](caseClass: A): ujson.Obj = {
    ujson.read(uWrite(caseClass)).obj
  }

  // can match on the type of the exception to return a different error message - status code
  private val returnInternalServerErrorOnFailure: PartialFunction[Throwable, Response[ujson.Obj]] = {
    case exception => cask.Response(jsonify(JsonResponse(s"Error occurred [$exception]")), 500)
  }

  extension (future: Future[Response[ujson.Obj]])
    def mapFutureFailure(handleFailure: PartialFunction[Throwable, Response[ujson.Obj]])(implicit ec: ExecutionContext): Future[Response[ujson.Obj]] = {
      future.recover(handleFailure)
    }
    def awaitResult(): Response[Obj] =
      Await.result(future, Duration.Inf)
}
