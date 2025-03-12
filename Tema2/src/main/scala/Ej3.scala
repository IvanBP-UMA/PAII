object Ej3 {
  def maxIndex(list: List[Int]): Int = {
    var max = list.head
    var maxI = 0
    for (i <- 1 until list.size){
      if list(i) > max then {
        max = list(i)
        maxI = i
      }
    }
    maxI
  }

  @main
  def main3(): Unit = {
    val s: String = io.StdIn.readLine("Introduce una cadena: ")
    val (chars, n): (List[Char], List[Int]) = s.trim.groupBy(_.toInt).values.map(e => (e.head, e.length)).unzip
    print(chars(maxIndex(n)))
  }
}
