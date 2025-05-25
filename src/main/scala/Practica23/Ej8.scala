package Practica23

object Ej8 {
  def fibonnaci(n: Int): Int = {
    (2 to n).foldRight((0, 1))((e, acc) => (acc._2, acc._1 + acc._2))._2
  }

  @main
  def main8(): Unit = {
    println(fibonnaci(3))
  }
}
