package Practica7

import concurrencia.*

import scala.util.Random

class Coche(C:Int) extends Thread{
  //CS-pasajero1: si el coche está lleno, un pasajero no puede subir al coche hasta que haya terminado
  //el viaje y se hayan bajado los pasajeros de la vuelta actual
  //CS-pasajero2: un pasajero que está en el coche no se puede bajarse hasta que haya terminado el viaje
  //CS-coche: el coche espera a que se hayan subido C pasajeros para dar una vuelta
  private var numPas = 0
  private var subir = true
  private var bajar = false


  def nuevoPaseo(id:Int)= synchronized {
    //el pasajero id  quiere dar un paseo en la montaña rusa
    while (!subir || numPas == C) wait()
    numPas += 1
    log(s"El pasajero $id se sube al coche. Hay $numPas pasajeros.")
    if (numPas == C){
      subir = false
      notifyAll()
    }
    while (!bajar) wait()
    numPas -= 1
    if numPas == 0 then {subir = true; bajar = false}
    log(s"El pasajero $id se baja del coche. Hay $numPas pasajeros.")
    notifyAll()
  }

  def esperaLleno(): Unit = synchronized {
    while (numPas < C || bajar) wait()
    log(s"        Coche lleno!!! empieza el viaje....")
  }

  def finViaje(): Unit = synchronized {
    //el coche indica que se ha terminado el viaje
    log(s"        Fin del viaje... :-(")
    bajar = true
    notifyAll()
  }

  override def run(): Unit = {
    while (true){
      esperaLleno()
      Thread.sleep(Random.nextInt(Random.nextInt(500))) //el coche da una vuelta
      finViaje()
    }
  }
}
object Ejercicio4 {
  def main(args:Array[String]): Unit =
    val coche = new Coche(5)
    val pasajero = new Array[Thread](20)
    coche.start()
    for (i<- pasajero.indices)
      pasajero(i) = thread{
        while (true)
          Thread.sleep(Random.nextInt(500))
          coche.nuevoPaseo(i)
      }
      
}
