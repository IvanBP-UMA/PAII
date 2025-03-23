object Ej2 {
  def cod(s: String): String = {
    s.map(_.toInt).groupBy(i => i).values.map(l => l.head.toChar.toString + l.size).mkString
  }

  @main
  def main2(): Unit = {
    println(cod("aaabbcccc"))
  }
}
