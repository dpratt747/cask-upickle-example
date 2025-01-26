package domain

final case class User(
                       id: UserId,
                       name: UserName,
                       queryCount: QueryCount = QueryCount(0)
                     )
