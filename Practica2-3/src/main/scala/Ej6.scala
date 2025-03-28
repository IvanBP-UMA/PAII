object Ej6 {
  def compose[T](lf: List[T=>T], v: T): T = {
    lf.foldRight(v)((f, acc) => f(acc))
  }

  @main
  def main6(): Unit = {
    println(compose(List[Int => Int](Math.pow(_,2).toInt, _+2), 5))
  }
}
