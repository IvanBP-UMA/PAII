package Tema2

import scala.annotation.tailrec

object Ej29 {
  def replicate[T](list: List[T], n: Int): List[T] = {
    @tailrec
    def replicate[U](list: List[U], n: Int, acc: List[U]): List[U] = {
      if list.isEmpty then acc
      else replicate(list.tail, n, acc ::: (1 to n).map(_ => list.head).toList)
    }
    replicate(list, n, List())
  }

  @main
  def main29(): Unit = {
    println(replicate(List("a", "b", "c", "d"), 3))
  }
}
