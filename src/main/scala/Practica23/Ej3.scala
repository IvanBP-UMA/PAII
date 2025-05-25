package Practica23

object Ej3 {
  def existe[T](l: List[T], f: T=>Boolean): Boolean = {
    l.foldLeft(false)((acc, e) => acc || f(e))
  }

  @main
  def main3(): Unit = {
    println(existe(List(1,2,3),_>2))
    println(existe(List("Hola","Mundo"),_.length<3))
  }
}
