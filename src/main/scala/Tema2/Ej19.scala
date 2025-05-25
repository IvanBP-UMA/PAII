package Tema2

object Ej19 {
  def nth[T](list: List[T], i: Int): Option[T] = {
    if i<list.size then Some(list(i))
    else None
  }

  @main
  def main19(): Unit = {
    println(nth(List(1, 2, 3, 4), 2))
    println(nth(List(1, 2), 2))
    println(nth(Nil, 2))
  }
}
