package Semaforos
import concurrencia.*
import scala.util.Random
import java.util.concurrent.*

class Bowl(n: Int)  {
  private var bowl = n
  private val esperaBowl = Semaphore(1)
  private val turnoPalomitero = Semaphore(0)
  private val esperaLlenar = Semaphore(0)

  def coge(id: Int): Unit = {
    esperaBowl.acquire()
    if bowl == 0 then {turnoPalomitero.release(); esperaLlenar.acquire()}
    bowl -= 1
    log(s"Joven $id coge una palomita del bowl. Quedan $bowl palomitas")
    esperaBowl.release()
  }

  def dormir(): Unit = {
    turnoPalomitero.acquire()
  }

  def llenarBowl(): Unit = {
    bowl = 100
    log(s"El palomitero llena el bowl. Quedan $bowl palomitas")
    esperaLlenar.release()
  }
}


@main
def mainMaraton(): Unit = {
  val NJ = 20
  val bowl = new Bowl(100)
  val jovenes = new Array[Thread](NJ)
  for (i <- jovenes.indices){
    jovenes(i) = thread{
      while (true){
        Thread.sleep(Random.nextInt(100))
        bowl.coge(i)
      }
    }
  }
  val palomitero = thread{
    while (true){
      bowl.dormir()
      Thread.sleep(500)
      bowl.llenarBowl()
    }
  }
}