package domain

import upickle.default.*

final case class JsonResponse(message: String)

object JsonResponse {
  implicit val rw: ReadWriter[JsonResponse] = macroRW[JsonResponse]
}
