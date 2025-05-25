package Practica6.soluciones

import java.util.concurrent.*
import scala.util.Random

object mediciones {
  // CS-Sensor-i:  sensor i no puede volver a medir hasta que el trabajador no ha
  // terminado de procesar las medidas anteriores
  // CS-Trabajador: no puede realizar su tarea hasta que no están las
  // tres mediciones
  private var numMed = 0
  private val mutex = new Semaphore(1) // sobre numMed
  private val esperaSensor = new Array[Semaphore](3) // CS-Sensor-i
  private val esperaTrab = new Semaphore(0)
  for (i <- 0 until esperaSensor.length)
    esperaSensor(i) = new Semaphore(0)

  def nuevaMedicion(id: Int) = {
    mutex.acquire()
    numMed += 1
    log(s"Sensor $id almacena su medición" )
    if (numMed == 3) esperaTrab.release()
    mutex.release()
    esperaSensor(id).acquire()
  }

  def leerMediciones() = {
    esperaTrab.acquire()
    mutex.acquire() // en realidad no hace falta
    numMed = 0
    log(s"El trabajador recoge las mediciones")
    mutex.release()
  }

  def finTarea() = {
    log(s"El trabajador ha terminado sus tareas")
    esperaSensor.foreach(_.release())
  }
}

object Ejercicio1 {
  def main(args: Array[String]) =
    val sensor = new Array[Thread](3)
    for (i <- 0 until sensor.length)
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
