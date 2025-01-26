
import domain.*
import ujson.*
import upickle.default.{write as uWrite, *}

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
}
