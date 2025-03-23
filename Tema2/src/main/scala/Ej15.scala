object Ej15 {
  def countWords(words: List[String]): Map[String, Int] = {
    words.groupBy(w => w).values.foldLeft(Map[String, Int]())((m: Map[String, Int], s: List[String]) => m + (s.head -> s.size))
  }

  @main
  def main15(): Unit = {
    val source = io.Source.fromFile("testFiles/repeat")
    val lines = source.getLines().mkString(" ").split(" ").toList
    source.close()

    println(countWords(lines))
  }
}
