package domain

opaque type UserName = String

object UserName {
  def apply(value: String): UserName = value
}