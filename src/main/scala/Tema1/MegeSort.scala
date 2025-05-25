package Tema1

object MegeSort {
  def merge(a: List[Int], b: List[Int]): List[Int] = {
    val result: Array[Int] = new Array[Int](a.size + b.size)
    var i: Int = 0
    var j: Int = 0
    var k: Int = 0

    while(i < a.size && j < b.size){
      if (a(i) < b(j)){
        result(k) = a(i)
        i += 1
      }else if (a(i) > b(j)){
        result(k) = b(j)
        j += 1
      }else{
        result(k) = a(i)
        i += 1
        j += 1
      }
      k += 1
    }

    while (i < a.size){
      result(k) = a(i)
      i += 1
      k += 1
    }

    while (j < b.size) {
      result(k) = b(j)
      j += 1
      k += 1
    }

    result.toList
  }

  @main
  def mainMerge(): Unit = {
    val a: List[Int] = List(1, 7, 8, 9)
    val b: List[Int] = List(2, 3, 5)
    println(merge(a, b))
  }
}
