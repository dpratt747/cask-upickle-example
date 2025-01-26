package domain

import java.util.UUID
import scala.util.Try

object IsUUID {
  def unapply(string: String): Option[UUID] = {
    Try(UUID.fromString(string)).toOption
  }
}