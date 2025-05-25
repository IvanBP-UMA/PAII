package Practica23

object Ej7 {
  def copia[T](list: List[T]): List[T] = {
    list.foldRight(Nil: List[T])((e, acc) => e :: acc)
  }

  def remdups[T](lista: List[T]): List[T] = {
    lista.foldRight(Nil: List[T])((e, acc) => if (acc.isEmpty || e != acc.head) then e :: acc else acc)
  }

  @main
  def main7(): Unit = {
    println(copia(List(1, 2, 3, 4)))
    println(remdups(List(1,1,3,3,3,2,1,2,2,1,2)))
  }
}
