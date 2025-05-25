package Practica6
import concurrencia.*
import java.util.concurrent.*
import scala.util.Random

object medicionesAlt {
  // Solucion con menos sentido semántico, pero que lo resuelve con menos semáforos y que además podría escalarse a n sensores sin aumentar el coste
  // No podría escalarse en cuanto que se usa un object y no una instancia de una clase en la que se le pase n como parámetro, pero la idea sigue siendo válida

  // CS-Sensor-i: sensor i no puede volver a medir hasta que el trabajador no ha
  // terminado de procesar las medidas anteriores
  // CS-Trabajador: no puede realizar su tarea hasta que no están las
  // tres mediciones

  private val esperaSensor = new Semaphore(1)
  private val leerDatos = new Semaphore(0)
  private val finLectura = new Semaphore(0)
  private var datos = 0


  def nuevaMedicion(id: Int): Unit = {
    esperaSensor.acquire()
    datos += 1
    log(s"Sensor $id almacena su medicion")
    if (datos == 3) leerDatos.release() else esperaSensor.release()

    finLectura.acquire()
    datos -= 1
    //log(s"Fin sensor $id. Datos: $datos")
    if datos == 0 then esperaSensor.release() else finLectura.release()
  }

  def leerMediciones(): Unit = {
    leerDatos.acquire()
    log(s"El trabajador recoge las mediciones")
  }

  def finTarea(): Unit = {
    log(s"El trabajador ha terminado sus tareas")
    finLectura.release()
  }
}

object Ejercicio1Alt {
  def main(args: Array[String]): Unit =
    val sensor = new Array[Thread](3)

    for (i <- sensor.indices)
      sensor(i) = thread {
        while (true)
          Thread.sleep(Random.nextInt(100)) // midiendo
          medicionesAlt.nuevaMedicion(i)
      }

    val trabajador = thread {
      while (true)
        medicionesAlt.leerMediciones()
        Thread.sleep(Random.nextInt(100)) // realizando la tarea
        medicionesAlt.finTarea()
    }
}
