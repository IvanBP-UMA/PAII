package Practica23

object Ej15 {
  def comb(m1: Map[String, Int], m2: Map[String, Int]): Map[String, Int] = {
    m1.foldLeft(m2)((acc: Map[String, Int], e: (String, Int)) => acc + (e._1 -> (e._2 + acc.getOrElse(e._1, 0))))
  }

  @main
  def main15(): Unit = {
    val warehouse1 = Map("laptop" -> 5, "mouse" -> 20, "keyboard" -> 10)
    val warehouse2 = Map("laptop" -> 3, "mouse" -> 15, "monitor" -> 8)
    println(comb(warehouse1, warehouse2))
  }
}
