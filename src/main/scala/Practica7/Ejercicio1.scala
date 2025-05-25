package Practica7

import concurrencia.*
import scala.util.Random

class Buffer(ncons:Int,tam:Int){
  //ncons-número de consumidores
  //tam-tamaño del buffer
  private val buffer = new Array[Int](tam)
  private var numElems = 0
  private var iProd = 0
  private var iCons = 0
  private var lecturas = Array.fill(ncons)(0)
  
  def nuevoDato(dato:Int): Unit = synchronized {
    while (numElems == tam) wait()

    buffer(iProd) = dato
    numElems += 1
    notifyAll()
    iProd = (iProd + 1) % tam
    log(s"Productor almacena $dato: buffer=${buffer.mkString("[",",","]")}}")
  }

  def extraerDato(id:Int):Int = synchronized {
    while (numElems == 0 || lecturas(id) == 1) wait()

    val res = buffer(iCons)
    lecturas(id) = 1
    if (lecturas.sum == ncons){
      lecturas = Array.fill(ncons)(0)
      buffer(iCons) = 0
      iCons = (iCons + 1) % tam
      numElems -= 1
    }
    notify()
    log(s"Consumidor $id lee $res : buffer=${buffer.mkString("[",",","]")}")
    res
  }
}
object Ejercicio1 {

  def main(args:Array[String]):Unit = {
    val ncons = 4
    val tam = 3
    val nIter = 10
    val buffer  = new Buffer(ncons,tam)
    val consumidor = new Array[Thread](ncons)
    for (i<-consumidor.indices)
      consumidor(i) = thread{
        for (j<-0 until nIter)
          val dato = buffer.extraerDato(i)
          Thread.sleep(Random.nextInt(200))
      }
    val productor = thread{
      for (i<-0 until nIter)
        Thread.sleep(Random.nextInt(50))
        buffer.nuevoDato(i+1)
    }
  }

}
