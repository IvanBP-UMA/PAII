package Practica6.soluciones

import java.util.concurrent._
import scala.util.Random

class Coche(C: Int) extends Thread {
  // CS-pasajero1: si el coche está lleno, un pasajero no puede subir al coche hasta que haya terminado
  // el viaje y se hayan bajado los pasajeros de la vuelta actual
  // CS-pasajero2: un pasajero que está en el coche no se puede bajarse hasta que haya terminado el viaje
  // CS-coche: el coche espera a que se hayan subido C pasajeros para dar una vuelta
  private var numPas = 0
  private val puertaEntrada = new Semaphore(1) // CS-Pasajero1
  private val puertaSalida = new Semaphore(0) // CS-Pasajero2
  private val lleno = new Semaphore(0) // CS-Coche

  def nuevoPaseo(id: Int) = {
    // el pasajero id quiere dar un paseo en la montaña rusa
    puertaEntrada.acquire()
    numPas += 1  // los pasajeros se suben al coche
    log(s"El pasajero $id se sube al coche. Hay $numPas pasajeros.")
    if (numPas < C) puertaEntrada.release()
    else lleno.release()
    puertaSalida.acquire() // esperan el final del viaje (la puerta de salida se abre)
    numPas -= 1
    log(s"El pasajero $id se baja del coche. Hay $numPas pasajeros.")
    if (numPas > 0) puertaSalida.release()
    else puertaEntrada.release()
  }

  def esperaLleno = {
    // el coche espera a que se llene para dar un paseo
    lleno.acquire()
    log(s"        Coche lleno!!! empieza el viaje....")
  }

  def finViaje = {
    // el coche indica que se ha terminado el viaje
    log(s"        Fin del viaje... :-(")
    puertaSalida.release()
  }

  override def run = {
    while (true)
      esperaLleno
      Thread.sleep(Random.nextInt(Random.nextInt(500))) // el coche da una vuelta
      finViaje
  }
}

object Ejercicio4 {
  def main(args: Array[String]) =
    val coche = new Coche(5)
    val pasajero = new Array[Thread](12)
    coche.start()
    for (i <- 0 until pasajero.length)
      pasajero(i) = thread {
        while (true)
          Thread.sleep(Random.nextInt(500)) //el pasajero se da una vuelta por el parque
          coche.nuevoPaseo(i)
      }
}
