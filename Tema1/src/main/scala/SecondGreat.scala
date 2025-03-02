import scala.collection.mutable.ListBuffer

object SecondGreat {

  def greatest(list: List[Int]): Int = {
    var index: Int = 0
    for (i <- 1 until(list.size)){
      if list(i) > list(index) then index = i
    }
    list(index)
  }

  @main
  def mainSecondGreat(): Unit = {
    val lista: List[Int] = List(1, 2, 7, 89, 0, -7, 100)
    println(greatest(lista.filterNot(e => e == greatest(lista))))
  }
}
