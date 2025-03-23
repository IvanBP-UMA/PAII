import scala.annotation.tailrec

object Ej27 {
  def compress[T](list: List[T]): List[T] = {
    @tailrec
    def compress[U](list: List[U], acc: List[U]): List[U] = {
      if list.isEmpty then acc
      else if acc.isEmpty || list.head != acc.last then compress(list.tail, acc :+ list.head)
      else compress(list.tail, acc)
    }
    compress(list, List())
  }

  @main
  def main27(): Unit = {
    println(compress(List("a","a","a","b","c","c","d","a","e","e","e")))
  }
}
