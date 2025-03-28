object Ej9 {
  def inits[T](l: List[T]): List[List[T]] = {
    l.reverse.foldRight(List[List[T]](List()))((e, acc)=> acc :+ (acc.last :+ e))
  }

  @main
  def main9(): Unit = {
    println(inits(List(1,2,3)))
  }
}
