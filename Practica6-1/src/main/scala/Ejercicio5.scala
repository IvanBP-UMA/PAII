package concurrencia

import java.util.concurrent.*
import scala.util.Random

object gestorAgua {
  // CS-Hid1: El hidrógeno que quiere formar una molécula espera si ya hay dos hidrógenos
  // CS-Hid2: Un hidrógeno debe esperar a los otros dos átomos para formar la molécula
  // CS-Ox1: El oxígeno que quiere formar una molécula espera si ya hay un oxígeno
  // CS-Ox2: El oxígeno debe esperar a los otros dos átomos para formar la molécula
  private var numH = 0
  private val esperaH = Semaphore(1)
  private val esperaO = Semaphore(1)


  def oxigeno(id: Int): Unit = {
    esperaO.acquire()
    log(s"Oxígeno $id quiere formar una molécula")
    if numH == 2 then
      log(s"      Molécula formada!!!")
      esperaO.release()
      numH = 0
      esperaH.release()
  }

  def hidrogeno(id: Int): Unit = {
    esperaH.acquire()
    numH += 1
    log(s"Hidrógeno $id quiere formar una molécula")
    if numH < 2 then
      esperaH.release()
    else if esperaO.availablePermits() == 0 then
      log(s"      Molécula formada!!!")
      numH = 0
      esperaO.release()
      esperaH.release()
  }
}
object Ejercicio5 {

  def main(args: Array[String]): Unit =
    val N = 5
    val hidrogeno = new Array[Thread](2 * N)
    for (i <- hidrogeno.indices)
      hidrogeno(i) = thread {
        Thread.sleep(Random.nextInt(500))
        gestorAgua.hidrogeno(i)
      }
    val oxigeno = new Array[Thread](N)
    for (i <- oxigeno.indices)
      oxigeno(i) = thread {
        Thread.sleep(Random.nextInt(500))
        gestorAgua.oxigeno(i)
      }
    hidrogeno.foreach(_.join())
    oxigeno.foreach(_.join())
    log("Fin del programa")
}
