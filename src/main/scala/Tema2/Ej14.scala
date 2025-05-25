package Tema2

object Ej14 {
  def crescentSubSeq(list: List[Int]): List[Int] = {
    var best: List[Int] = List()
    var current: List[Int] = List()
    for (i <- list) {
      if current.isEmpty || i > current.last then current = current :+ i
      else current = List(i)

      if current.size > best.size then best = current
    }
    best
  }

  @main
  def main14(): Unit = {
    println(crescentSubSeq(List(2, 3, 4, 1, 2, 3, 4, 5, 8, 2, 4)))
  }
}
