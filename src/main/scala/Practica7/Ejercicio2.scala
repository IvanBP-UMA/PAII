package Practica7

import concurrencia.*
import scala.collection.mutable.ListBuffer
import scala.util.Random

class Recursos(rec:Int) {

  private var numRec = rec
  private val queue: ListBuffer[Int] = new ListBuffer[Int]()
  
  def pidoRecursos(id:Int,num:Int) = synchronized {
    //proceso id solicita num recursos
    queue += id
    log(s"Proceso $id pide $num recursos. Esperando ${queue.size}")
    while (numRec < num || id != queue.head) wait()
    numRec -= num
    queue.remove(0)
    log(s"Proceso $id coge $num recursos. Quedan $numRec")
  }

  def libRecursos(id:Int,num:Int) = synchronized {
    //proceso id devuelve num recursos
    numRec += num
    notifyAll()
    log(s"Proceso $id devuelve $num recursos. Quedan $numRec")
    
  }
}
object Ejercicio2 {

  def main(args:Array[String]):Unit = {
    val rec = 5
    val numProc = 10
    val recursos = new Recursos(rec)
    val proceso = new Array[Thread](numProc)
    for (i<-proceso.indices)
      proceso(i) = thread{
      //  while (true){
          val r = Random.nextInt(rec)+1
          recursos.pidoRecursos(i,r)
          Thread.sleep(Random.nextInt(100))
          recursos.libRecursos(i,r)
     //   }
      }
  }
}
