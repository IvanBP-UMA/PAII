package Practica7

import concurrencia.*
import scala.util.Random

object Parejas{
  private var hayHombre = false
  private var hayMujer = false
  private var num = 0

  def llegaHombre(id:Int) = synchronized {
    while (hayHombre) wait()
    hayHombre = true
    log(s"Hombre $id quiere formar pareja")

    if (hayMujer) {
      log("PAREJA")
      hayMujer = false
      hayHombre = false
      notifyAll()
    }
  }

  def llegaMujer(id: Int) =  synchronized {
    while (hayMujer) wait()
    hayMujer = true
    log(s"Mujer $id quiere formar pareja")

    if (hayHombre) {
      log("PAREJA")
      hayMujer = false
      hayHombre = false
      notifyAll()
    } else {

    }
  }

  def llegaHombreProf(id: Int) = synchronized {

    log(s"Hombre $id quiere formar pareja")
  }

  def llegaMujerProf(id: Int) = synchronized {
    while (hayMujer)
    log(s"Mujer $id quiere formar pareja")
  }
}
object Ejercicio3 {

  def main(args:Array[String]):Unit = {
    val NP = 10
    val mujer = new Array[Thread](NP)
    val hombre = new Array[Thread](NP)
    for (i<-mujer.indices)
      mujer(i) = thread{
        Parejas.llegaMujer(i)
      }
    for (i <- hombre.indices)
      hombre(i) = thread {
        Parejas.llegaHombre(i)
      }
  }

}
