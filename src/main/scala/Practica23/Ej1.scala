package Practica23

object Ej1 {
  def Sum(l: List[Int]): Int = {
    l.foldLeft(0)((acc, x) => acc + x)
  }

  def product(l: List[Int]): Int = {
    l.foldRight(1)((e, acc) => e*acc)
  }

  def Length[T](l: List[T]): Int = {
    l.foldRight(0)((x, y)=> y+1)
  }
  
  @main
  def main1(): Unit = {
    val l = List(1, 2, 3, 4)
    println(Sum(l))
    println(product(l))
    println(Length(l))
  }
}
