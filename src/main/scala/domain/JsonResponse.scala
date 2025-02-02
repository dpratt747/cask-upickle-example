package domain

import upickle.default.*

final case class JsonResponse(message: String)

object JsonResponse {
  given rw: ReadWriter[JsonResponse] = macroRW[JsonResponse]
}
