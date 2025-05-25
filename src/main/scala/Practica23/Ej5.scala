package Practica23

object Ej5 {
  def unzip[A, B](l: List[(A, B)]): (List[A], List[B]) = {
    l.foldRight((List[A](), List[B]()))((e, acc) => (e._1 :: acc._1, e._2 :: acc._2))
  }

  @main
  def main5(): Unit = {
    println(unzip(List((1,'a'),(2,'b'),(3,'c'))))
  }
}
