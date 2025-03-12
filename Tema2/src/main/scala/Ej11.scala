import scala.annotation.tailrec

object Ej11 {
  def fibonacci(n: Int): Int = {
    if n <= 2 then 1
    else fibonacci(n-1) + fibonacci(n-2)
  }

  @tailrec
  def fibTailRec(n: Int, prev1: Int, prev2: Int): Int = {
    if n < 2 then prev1 + prev2
    else fibTailRec(n-1, prev1 + prev2, prev1)
  }

  @main
  def main11(): Unit = {
    println(fibonacci(9))
    println(fibTailRec(9, 0, 1))
  }
}
