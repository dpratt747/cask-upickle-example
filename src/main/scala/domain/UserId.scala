package domain

import java.util.UUID

opaque type UserId = UUID

object UserId {
  def apply(value: UUID): UserId = value
}