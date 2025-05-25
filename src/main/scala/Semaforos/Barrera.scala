package Semaforos
import concurrencia.*
import scala.util.Random
import java.util.concurrent.*

class Barrera(N: Int) {
  private var finalizados = 0
  private val mutex = new Semaphore(1)
  private val esperaTrabajar = new Semaphore(1)
  private val esperaIteracion = new Semaphore(0)
  
  def finiter(id: Int, iter: Int): Unit = {
    esperaTrabajar.acquire()
    finalizados += 1
    log(s"Iteracion: $iter, Trabajador: $id")
    if (finalizados < N) esperaTrabajar.release() else esperaIteracion.release()
    
    esperaIteracion.acquire()
    finalizados -= 1
    if finalizados == 0 then esperaTrabajar.release() else esperaIteracion.release()
  }
}

@main
def barrera(): Unit = {
  val N = 5
  val barrera = new Barrera(N)
  val v = new Array[Thread](N)
  for (i <- 0 until N){
    v(i) = thread{
      for (j <- 0 until 1000) {
        Thread.sleep(Random.nextInt(100))
        barrera.finiter(i, j)
      }
    }
  }
}

