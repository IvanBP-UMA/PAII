import scala.annotation.tailrec

object Ej12 {
  def sumDig(n: Int): Int = {
    if n < 10 then n
    else n%10 + sumDig(n/10)
  }

  @tailrec
  def sumDigTailRec(n: Int, acc: Int): Int = {
    if n<10 then acc+n
    else sumDigTailRec(n/10, acc+(n%10))
  }

  @main
  def main12(): Unit = {
    val n: Int = 12345
    println(sumDig(n))
    println(sumDigTailRec(n, 0))
  }
}
