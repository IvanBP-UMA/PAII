package Practica6
import concurrencia.* 
import java.util.concurrent.*
import scala.util.Random

object gestorAgua {
  // CS-Hid1: El hidrógeno que quiere formar una molécula espera si ya hay dos hidrógenos
  // CS-Hid2: Un hidrógeno debe esperar a los otros dos átomos para formar la molécula
  // CS-Ox1: El oxígeno que quiere formar una molécula espera si ya hay un oxígeno
  // CS-Ox2: El oxígeno debe esperar a los otros dos átomos para formar la molécula
  private var numH = 0
  private var numO = 0
  private val mutex = Semaphore(1)
  private val esperaH = Semaphore(1)
  private val esperaO = Semaphore(1)


  def oxigeno(id: Int): Unit = {
    esperaO.acquire()
    mutex.acquire()
    numO += 1
    log(s"Oxígeno $id quiere formar una molécula")
    if numH == 2 then
      log(s"      Molécula formada!!!")
      numO = 0
      numH = 0
      esperaO.release()
      esperaH.release()
    mutex.release()
  }

  def hidrogeno(id: Int): Unit = {
    esperaH.acquire()
    mutex.acquire()
    numH += 1
    log(s"Hidrógeno $id quiere formar una molécula")
    if numH < 2 then
      esperaH.release()
    else if numO == 1 then
      log(s"      Molécula formada!!!")
      numH = 0
      numO = 0
      esperaO.release()
      esperaH.release()
    mutex.release()
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
