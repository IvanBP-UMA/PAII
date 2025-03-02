object Rotate {

  def rotateList(list: List[Int], k: Int): List[Int] = {
    val aux: Array[Int] = new Array[Int](list.size)
    for (i <- list.indices){
      aux((i+k)%5) = list(i)
    }
    aux.toList
  }

  @main
  def mainRotate(): Unit = {
    var list: List[Int] = List(1, 2, 3, 4, 5)
    list = rotateList(list, 2)
    println(list)
  }
}
