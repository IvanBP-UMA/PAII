import java.io.PrintWriter

object Ej17 {
  @main
  def main17(): Unit = {
    val source = io.Source.fromFile("testFiles/repeat")
    val lines = source.mkString
    source.close()

    new PrintWriter("testFiles/out.txt") { write(lines); close()}
  }
}
