package concurrencia

import java.util.concurrent.*
import scala.util.Random

//noinspection LanguageDetectionInspection
class Coche(C: Int) extends Thread {
  // CS-pasajero1: si el coche está lleno, un pasajero no puede subir al coche hasta que haya terminado
  // el viaje y se hayan bajado los pasajeros de la vuelta actual
  // CS-pasajero2: un pasajero que está en el coche no puede bajarse hasta que haya terminado el viaje
  // CS-coche: el coche espera a que se hayan subido C pasajeros para dar una vuelta
  private var numPas = 0
  private val esperaCoche = Semaphore(0)
  private val entrarCoche = Semaphore(1)
  private val esperaViaje = Semaphore(0)


  def nuevoPaseo(id: Int): Unit = {
    entrarCoche.acquire()
    numPas += 1
    log(s"El pasajero $id se sube al coche. Hay $numPas pasajeros.")
    if numPas == C then esperaCoche.release() else entrarCoche.release()

    esperaViaje.acquire()
    numPas -= 1
    log(s"El pasajero $id se baja del coche. Hay $numPas pasajeros.")
    if numPas == 0 then
      entrarCoche.release()
      print("--------------------------------------------------------------------\n")
    else
      esperaViaje.release()
  }

  def esperaLleno(): Unit = {
    esperaCoche.acquire()
    log(s"        Coche lleno!!! empieza el viaje....")
  }

  def finViaje(): Unit = {
    // el coche indica que se ha terminado el viaje
    log(s"        Fin del viaje... :-(")
    esperaViaje.release()
  }

  override def run(): Unit = {
    while (true) {
      esperaLleno()
      Thread.sleep(Random.nextInt(Random.nextInt(500))) // el coche da una vuelta
      finViaje()
    }
  }
}

object Ejercicio4 {
  def main(args: Array[String]): Unit =
    val coche = new Coche(5)
    val pasajero = new Array[Thread](12)
    coche.start()
    for (i <- pasajero.indices)
      pasajero(i) = thread {
        while (true)
          Thread.sleep(Random.nextInt(500)) // el pasajero se da una vuelta por el parque
          coche.nuevoPaseo(i)
      }
}
