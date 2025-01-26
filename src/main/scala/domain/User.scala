package domain

final case class User(
                       id: UserId,
                       name: String,
                       queryCount: Int = 0
                     )
