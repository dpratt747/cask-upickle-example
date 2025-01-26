
import domain.*
import ujson.*
import upickle.default.{write as uWrite, *}

import java.util.UUID

package object endpoints {
  private def convertToUserId(userId: ujson.Value): Either[String, UUID] = {
    userId match
      case Str(IsUUID(uuid)) => Right(uuid)
      case jsonfield =>
        Left(s"Failed to parse [$jsonfield] to a UUID")
  }

  private def convertToUserName(userId: ujson.Value): Either[String, String] = {
    userId match
      case Str(name) => Right(name)
      case jsonfield =>
        Left(s"Failed to parse [$jsonfield] to a User name")
  }

  private def jsonify[A: ReadWriter](caseClass: A): ujson.Obj = {
    ujson.read(uWrite(caseClass)).obj
  }
}
