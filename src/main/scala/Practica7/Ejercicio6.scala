package Practica7

import concurrencia.*
import scala.util.Random

class Bandeja(R:Int){

  /*He modificado el esqueleto. En el problema original, no hay tarta y el pastelero se despierta solo para ponerla
  * Para que en futuras iteraciones el niño que levante al pastelero sea el primero en coger una ración de la nueva tarta, mi implementación
  * asume que este es el único caso en el que se despierta al pastelero, el cual le cederá de vuelta el turno al niño que lo ha llamado.
  * Ahora mismo no sé cómo implementar el problema para que el pastelero se pueda despertar solo la primera vez y a demanda el resto del tiempo
  * */
  private var raciones = R
  private var hayTarta = true
  private var esperaPastelero = false
  

  def quieroRacion(id:Int): Unit = synchronized {
    while (!hayTarta) wait()
    if (raciones == 0){
      hayTarta = false
      notify()
      while (!esperaPastelero) wait()
      esperaPastelero = false
      hayTarta = true
      notifyAll()
    }
    raciones -= 1
    log(s"Niño $id ha cogido una ración. Quedan $raciones")
   
  }
  def tarta(): Unit = synchronized {
    while (hayTarta) wait()
    raciones = R
    esperaPastelero = true
    log("El pastelero pone una nueva tarta.")
    notifyAll()
  }
}
object Ejercicio6 {

  def main(args:Array[String]):Unit = {
    val R = 5
    val N = 10
    val bandeja = new Bandeja(R)
    var niño = new Array[Thread](N)
    for (i<-niño.indices)
      niño(i) = thread{
        while (true){
          Thread.sleep(Random.nextInt(500))
          bandeja.quieroRacion(i)
        }
      }
    val pastelero = thread{
      while (true){
        Thread.sleep(Random.nextInt(100))
        bandeja.tarta()
      }
    }
  }


}
