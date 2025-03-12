object Ej10 {
  @main
  def main10(): Unit = {
    val source = io.Source.fromFile("test.txt")

    val lines= source.getLines.mkString("\n")
    val line = source.mkString


    print(line)
    println(lines)


    source.close()
  }
}
