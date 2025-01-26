package domain

opaque type QueryCount = Int

object QueryCount {
  def apply(value: Int): QueryCount = value
}

extension (qc: QueryCount)
  def incrementByOne(): QueryCount = QueryCount(qc + 1)