object Ej18 {
  def lastElement[T](list: List[T]): Option[T] = {
    if list.isEmpty then None
    else Some(list.last)
  }

  @main
  def main18(): Unit = {
    println(lastElement(List(1, 2, 3)))
    println(lastElement(Nil))
  }
}
