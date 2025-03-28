object Ej2 {
  def reverse[T](l: List[T]): List[T] = {
    l.foldRight(Nil)((e, acc) => acc :+ e)
  }

  def append[T](l1: List[T], l2: List[T]): List[T] = {
    l1.foldRight(l2)((e, acc) => e :: acc)
  }

  @main
  def main2(): Unit = {
    println(reverse(List(1, 2, 3, 4)))
    println(append(List(1, 2, 3), List(1, 2)))
  }
}
