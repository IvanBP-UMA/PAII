package Practica6.soluciones

import java.util.concurrent._
import scala.util.Random

class Olla(R: Int) {
  // CS-caníbal i: no coge una ración de la olla si esta está vacía
  // CS-cocinero: no cocina un nuevo explorador hasta que la olla está vacía
  private var olla = R // inicialmente llena
  private val mutex = new Semaphore(1) // para olla, CS-caníbal
  private val ollaVacia = new Semaphore(0) // CS-cocinero
  private val esperaLlena = new Semaphore(0) // CS-caníbal

  def racion(i: Int) = {
    // caníbal i coge una ración de la olla
    mutex.acquire()
    if (olla == 0) {
      ollaVacia.release()
      esperaLlena.acquire()
    }
    olla -= 1
    log(s"Caníbal $i coge una ración de la olla. Quedan $olla raciones.")
    mutex.release()
  }

  def dormir = {
    // cocinero espera a que la olla esté vacía
    ollaVacia.acquire()
  }
  def llenarOlla = {
    olla = R
    log(s"El cocinero llena la olla. Quedan $olla raciones.")
    esperaLlena.release()
  }
}

object Ejercicio8 {
  def main(args: Array[String]): Unit =
    val NCan = 20
    val olla = new Olla(5)
    val canibal = new Array[Thread](NCan)
    for (i <- canibal.indices)
      canibal(i) = thread {
        while (true)
          Thread.sleep(Random.nextInt(500))
          olla.racion(i)
      }
      val cocinero = thread {
        while (true)
          olla.dormir
          Thread.sleep(500) // cocinando
          olla.llenarOlla
      }
}
