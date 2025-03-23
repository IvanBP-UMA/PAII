import scala.annotation.tailrec

object Ej28 {
  def pack[T](list: List[T]): List[List[T]] = {
    @tailrec
    def pack[T](list: List[T], acc: List[List[T]]): List[List[T]] = {
      if list.isEmpty then acc
      else if acc.isEmpty || list.head != acc.last.head then pack(list.tail, acc :+ List(list.head))
      else pack(list.tail, acc.dropRight(1) :+ (acc.last :+ list.head))
    }
    pack(list, List())
  }

  @main
  def main28(): Unit = {
    println(pack(List("a","a","a","b","c","c","d","e","e","e"))
    )
  }
}
