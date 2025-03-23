import scala.annotation.tailrec

object Ej23 {
  def toN(n: Int): List[Int] = {
    require(n >= 0)
    @tailrec
    def toN(n: Int, acc: List[Int]): List[Int] = {
      if n == 0 then 0 :: acc
      else toN(n - 1, n :: acc)
    }
    toN(n, List())
  }

  @main
  def main23(): Unit = {
    println(toN(5))
  }
}
