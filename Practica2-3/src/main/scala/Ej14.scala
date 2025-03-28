object Ej14 {
  def countWords(words: List[String]): Map[String, Int] = {
    words.groupBy(identity).map((k, v) => (k, v.length))
  }

  @main
  def main14(): Unit = {
    val words = List("scala", "is", "awesome", "scala", "functional", "scala", "is", "great")
    println(countWords(words))
  }
}
