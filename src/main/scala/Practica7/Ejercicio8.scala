package Practica7

import concurrencia.*
import scala.util.Random

object gestorAgua {
  //CS-Hid1: El hidrógeno que quiere formar una molécula espera si ya hay dos hidrógenos
  //CS-Hid2: Un hidrógeno debe esperar a los otros dos átomos para formar la molécula
  //CS-Ox1: El oxígeno que quiere formar una molécula espera si ya hay un oxígeno
  //CS-Ox2: El oxígeno debe esperar a los otros dos átomos para formar la molécula
  private var hayO = false
  private var numH = 0


  def hidrogeno(id: Int): Unit = synchronized {
    //el oxigeno id quiere formar una molécula
    while(numH == 2) wait()
    numH += 1
    log(s"Hidrógeno $id quiere formar una molécula")
    if (numH == 2 && hayO) {
      log(s"      Molécula formada!!!")
      numH = 0
      hayO = false
      notifyAll()
    }
  }

  def oxigeno(id: Int): Unit = synchronized {
    //el oxigeno id quiere formar una molécula
    while (hayO) wait()
    hayO = true
    log(s"Oxígeno $id quiere formar una molécula")
    if (numH == 2){
      log(s"      Molécula formada!!!")
      hayO = false
      numH = 0
      notifyAll()
    }
  }

}
object Ejercicio8 {

  def main(args:Array[String]): Unit =
    val N = 5
    val hidrogeno = new Array[Thread](2*N)
    for (i <- hidrogeno.indices)
      hidrogeno(i) = thread{
        Thread.sleep(Random.nextInt(500))
        gestorAgua.hidrogeno(i)
      }
    val oxigeno = new Array[Thread](N)
    for(i <- oxigeno.indices)
      oxigeno(i) = thread {
        Thread.sleep(Random.nextInt(500))
        gestorAgua.oxigeno(i)
      }
    hidrogeno.foreach(_.join())
    oxigeno.foreach(_.join())
    log("Fin del programa")
}
