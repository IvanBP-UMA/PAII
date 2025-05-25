package Practica4

import concurrencia.*

import scala.annotation.tailrec
import scala.util.Random

def todosTrueIter(list: List[Boolean]): Boolean = {
  list.foldLeft(true)((acc, e) => acc && e)
}

@tailrec
def todosTrueRec(list: List[Boolean], acc: Boolean): Boolean = {
  if !acc || list.isEmpty then acc else todosTrueRec(list.tail, acc || list.head)
}

@main
def main3b(): Unit = {
  val lista = List.fill(Random.nextInt(10))(Random.nextBoolean())
  val (l1, l2) = lista.splitAt(lista.size / 2)
  println("" + l1 + " " + l2)
  val (b1, b2) = parallel(todosTrueIter(l1), todosTrueIter(l2))
  println(s"${b1 && b2}")
}