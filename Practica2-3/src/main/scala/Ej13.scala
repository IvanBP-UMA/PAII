object Ej13 {
  def remWords(words: Set[String], stop: Set[String]): Set[String] = {
    words.mkString(" ").split(" ").foldLeft(Set[String]())((acc: Set[String], e: String) => if stop.contains(e.toLowerCase()) then acc else acc + e.toLowerCase())
  }

  @main
  def main13(): Unit = {
    val sentences = Set(
      "Scala is a functional language",
      "The power of functional programming is great",
      "Functional programming is elegant"
    )
    val stopWords = Set("a", "the", "is", "of")
    println(remWords(sentences, stopWords))
  }
}
