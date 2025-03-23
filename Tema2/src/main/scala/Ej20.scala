import scala.annotation.tailrec

object Ej20 {
  @tailrec
  def suma(list: List[Int], acc: Int): Int = {
    if list.isEmpty then acc
    else suma(list.tail, acc+list.head)
  }

  @main
  def main20(): Unit = {
    println(suma(List(1, 3, 5, 10, 2, -6), 0))
  }

}
