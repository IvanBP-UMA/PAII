package concurrencia

import java.util.concurrent.*
import scala.util.Random

class Nido(B: Int) {
  // CS-bebé i: no puede coger un bichito del plato si está vacío
  // CS-papá/mamá: no puede dejar un bichito en el plato si está lleno

  private var plato = 0
  private val esperaPadre = Semaphore(1)
  private val esperaHijos = Semaphore(0)
  private val mutex = Semaphore(1)

  def cojoBichito(i: Int): Any = {
    esperaHijos.acquire()
    mutex.acquire()
    plato -= 1
    log(s"Bebé $i coge un bichito. Quedan $plato bichitos")
    if plato > 0 then esperaHijos.release()
    if plato == B-1 then esperaPadre.release()
    mutex.release()
  }

  def pongoBichito(i: Int): Unit = {
    esperaPadre.acquire()
    mutex.acquire()
    plato += 1
    log(s"Papá $i pone un bichito. Quedan $plato bichitos")
    if plato == 1 then esperaHijos.release()
    if plato < B then esperaPadre.release()
    mutex.release()
  }
}

object Ejercicio7 {
  def main(args: Array[String]): Unit =
    val N = 10
    val nido = new Nido(5)
    val bebe = new Array[Thread](N)
    for (i <- bebe.indices)
      bebe(i) = thread {
        while (true)
          nido.cojoBichito(i)
          Thread.sleep(Random.nextInt(600))
      }
    val papa = new Array[Thread](2)
    for (i <- papa.indices)
      papa(i) = thread {
        while (true)
          Thread.sleep(Random.nextInt(100))
          nido.pongoBichito(i)
      }
}
