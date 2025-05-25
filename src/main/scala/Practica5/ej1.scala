package Practica5

import concurrencia.*
import scala.util.Random

val N = 20

object Buffer {
  @volatile var hayDato = false
  private val elem = new Array[Int](N)
  private var p = 0
  private var c = 0

  def escribe(d: Int): Unit = {
    while (hayDato) Thread.sleep(0)
    elem(p) = d
    p = (p+1) % N
    hayDato = true
  }

  def lee(): Int = {
    while (!hayDato) Thread.sleep(0)
    val result = elem(c)
    c = (c + 1) % N
    hayDato = false
    result
  }
}

@main
def main1(): Unit = {
  val prod = thread {
    for (i <- 0 until 200){
      Thread.sleep(Random.nextInt(100))
      Buffer.escribe(i)
    }
  }

  val cons = thread {
    for (i <- 0 until 200) {
      log(s"Dato extraido: ${Buffer.lee()}")
      Thread.sleep(Random.nextInt(100))
    }
  }
}