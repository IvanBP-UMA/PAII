package Tema2

object Ej26 {

  def flatten[T](list: List[T]): List[T] = {
    def flatten[U](list: List[U], acc: List[U]): List[U] = {
      if list.isEmpty then acc
      else
        list.head match
          case l: List[U] => flatten(list.tail, acc ::: flatten(l, List()))
          case a: U => flatten(list.tail, acc :+ a)
    }
    flatten(list, List())
  }

  @main
  def main26(): Unit = {
    println(flatten(List("a", List("b","c"), List("d", "e"))))
    println(flatten(List("a", List("b","c",List("d", List("e"))))))
  }
}
