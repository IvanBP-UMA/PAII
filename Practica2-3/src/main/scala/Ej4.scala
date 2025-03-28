import scala.annotation.tailrec

object Ej4 {
  def f(l: List[Int]): List[Int] = {
    l.filter(_<0).map(-_)
  }

  def fTR(l: List[Int]): List[Int] = {
    @tailrec
    def fTR(l: List[Int], acc: List[Int]): List[Int] = {
      l match
        case Nil => acc
        case head :: tail => if head < 0 then fTR(tail, acc :+ -head) else fTR(tail, acc)
    }
    fTR(l, Nil)
  }

  @main
  def main4(): Unit = {
    println(f(List(1,-2,3,-4,-5,6)))
    println(fTR(List(1,-2,3,-4,-5,6)))
  }
}
