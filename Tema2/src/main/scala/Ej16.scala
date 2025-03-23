object Ej16 {
  def longestWord(words: List[String]): List[String] = {
    words.groupBy(_.length).toList.foldLeft(List[String](""))((acc, current) => if current._1 > acc.head.length then current._2 else acc)
    //words.groupBy(_.length).toList.maxBy(_._1)._2         More understandable alternative
  }

  @main
  def main16(): Unit = {
    val source = io.Source.fromFile("testFiles/repeat")
    val lines = source.getLines().mkString(" ").split(" ").toList
    source.close()

    println(longestWord(lines))
  }
}
