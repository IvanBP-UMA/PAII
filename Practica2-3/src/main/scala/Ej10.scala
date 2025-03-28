import scala.annotation.tailrec

object Ej10 {
  def halfEvenTR(l1: List[Int], l2: List[Int]): List[Int] = {
    @tailrec
    def halfEvenTR(l1: List[Int], l2: List[Int], acc: List[Int]): List[Int] = {
      (l1, l2) match
        case (Nil, _) => acc
        case (_, Nil) => acc
        case (h1 :: t1, h2 :: t2) => if ((h1+h2)%2 == 0) then halfEvenTR(t1, t2, acc :+ (h1+h2)/2) else halfEvenTR(t1, t2, acc)
    }
    halfEvenTR(l1, l2, Nil)
  }

  def halfEven(l1: List[Int], l2: List[Int]): List[Int] = {
    l1.zip(l2).map((x, y) => x+y).filter(_ % 2 == 0).map(_/2)
  }

  @main
  def main10(): Unit = {
    println(halfEvenTR(List(1,2,3,4),List(3,2,4)))
    println(halfEven(List(1,2,3,4),List(3,2,4)))
  }
}
