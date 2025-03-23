import scala.annotation.tailrec

object Ej30 {
  def range(n: Int, m: Int): List[Int] = {
    @tailrec
    def range(n: Int, m: Int, acc: List[Int]): List[Int] = {
      if n == m then acc :+ n
      else if n > m then range(n-1, m, acc :+ n)
      else range(n+1, m, acc :+ n)
    }
    range(n, m, List())
  }

  @main
  def main30(): Unit = {
    println(range(4, 9))
    println(range(9, 4))
    println(range(5, 5))
  }
}
