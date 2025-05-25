package Tema2

import scala.annotation.tailrec

object Ej21 {
  def reverse[T](list: List[T]): List[T] = {
    if list.isEmpty then List()
    else reverse(list.tail) :+ list.head
  }

  @tailrec
  def reverseTailRec[T](list: List[T], acc: List[T]): List[T] = {
    if list.isEmpty then acc
    else reverseTailRec(list.tail, list.head :: acc)
  }

  def reverseFold[T](list: List[T]): List[T] = {
    list.foldLeft(List[T]())((acc: List[T], e: T) => e :: acc)
  }

  @main
  def main21(): Unit = {
    println(reverse(List(1, 2, 3, 4)))
    println(reverseTailRec(List(1, 2, 3, 4), List()))
    println(reverseFold(List(1, 2, 3, 4)))
  }

}
