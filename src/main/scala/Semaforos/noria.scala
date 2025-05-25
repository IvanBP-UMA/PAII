/*
Tenemos una nueva noria en el parque de atracciones. La noria tiene N coches y cada coche puede llevar C pasajeros
(N y C son pasados como argumentos del constructor de la clase Noria). Los pasajeros esperan para subir a la noria,
se dan una vuelta y bajan. Sin embargo, el movimiento no es continuo, la noria irá parando cada uno de sus coches
en la puerta de salida para renovar sus pasajeros. En concreto, cuando el coche en la puerta está lleno, la noria
se mueve hasta colocar el siguiente coche en la puerta de salida, bajándose entonces los pasajeros en el coche
situado en ese momento en la salida. Cuando terminan de bajar los pasajeros del coche en la puerta de salida, el
coche vuelve a llenarse hasta completar su capacidad. El proceso se repite indefinidamente. Por ejemplo, para 3
coches de capacidad 2 la siguiente sería una traza válida.
*/
package Semaforos
import concurrencia.{log, thread}
import java.util.concurrent.*
import scala.util.Random

class Noria(N: Int, C: Int) extends Thread {
  private val numPas = Array.fill(N)(0) // núm. de pasajeros en cada coche
  private var i = 0 // coche en la puerta
  private val mutex = new Semaphore(1)
  private val coches = Array.fill(N)(Semaphore(0))
  private val esperaCoches = Array.fill(N)(Semaphore(0))

  private var vuelta = false

  def paseo(id: Int): Unit = {
    val current = i
    mutex.acquire()
    numPas(current) += 1
    log(s"Se sube pasajero $id. Hay ${numPas(i)} pasajeros en el coche $i.")

    if (numPas(current) == C) {
      coches(current).release()
    } else {
      mutex.release()
    }

    esperaCoches(current).acquire()
    numPas(current) -= 1
    log(s"Se baja pasajero $id. Hay ${numPas(current)} pasajeros en el coche $i.")
    if numPas(current) == 0 then mutex.release() else esperaCoches(current).release()

  }

  private def mueve(): Unit = {
    coches(i).acquire()

    log(s"Coche $i lleno, la noria se mueve....")
    if i == (N - 1) then vuelta = true
    i = (i + 1) % N
    log(s"Coche $i en la puerta")
    if (vuelta) {
      esperaCoches(i).release()
    } else {
      mutex.release()
    }

  }

  override def run(): Unit = {
    setName("Noria")
    while (true)
      mueve() // cuando la noria está lista (se vacía el coche en la salida y se vuelva a llenar) el noria avanza un paso
  }
}

@main def mainNoria(): Unit =
  val noria = new Noria(3, 2)
  val pasajero = new Array[Thread](50)
  noria.start()
  for (i <- pasajero.indices)
    pasajero(i) = thread {
      while (true)
        noria.paseo(i) // el pasajero i se da un paseo en la noria
        Thread.sleep(Random.nextInt(500)) // el pasajero i se da una vuelta por el parque
    }
