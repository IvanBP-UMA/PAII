package concurrencia

import java.util.concurrent.*
import scala.util.Random

object mediciones {
  // CS-Sensor-i: sensor i no puede volver a medir hasta que el trabajador no ha
  // terminado de procesar las medidas anteriores
  // CS-Trabajador: no puede realizar su tarea hasta que no están las
  // tres mediciones

  val mutex = new Semaphore(1)
  private val esperaSensor = new Array[Semaphore](3)
  for (i <- esperaSensor.indices) {
    esperaSensor(i) = Semaphore(1)
  }
  private val leerDatos = new Semaphore(0)
  private var datos = 0


  def nuevaMedicion(id: Int): Unit = {
    esperaSensor(id).acquire()
    mutex.acquire()
    datos += 1
    log(s"Sensor $id almacena su medicion")
    if (datos == 3) leerDatos.release()
    mutex.release()
  }

  def leerMediciones(): Unit = {
    leerDatos.acquire()
    mutex.acquire()
    log(s"El trabajador recoge las mediciones")
    datos = 0
  }

  def finTarea(): Unit = {
    for (i <- esperaSensor.indices) {
      esperaSensor(i).release()
    }
    log(s"El trabajador ha terminado sus tareas")
    mutex.release()
  }
}

object Ejercicio1 {
  def main(args: Array[String]): Unit =
    val sensor = new Array[Thread](3)

    for (i <- sensor.indices)
      sensor(i) = thread {
        while (true)
          Thread.sleep(Random.nextInt(100)) // midiendo
          mediciones.nuevaMedicion(i)
      }

    val trabajador = thread {
      while (true)
        mediciones.leerMediciones()
        Thread.sleep(Random.nextInt(100)) // realizando la tarea
        mediciones.finTarea()
    }
}
