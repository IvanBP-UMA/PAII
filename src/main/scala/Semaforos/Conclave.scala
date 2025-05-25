package Semaforos
import concurrencia.*
import java.util.concurrent._
import scala.util.Random

object Conclave {
  val numCardenales = 9
  var fumata_blanca = false
  var ganador: Option[Int] = None
  private val votos = scala.collection.mutable.Map[Int, Int]()

  private var numVotacion = 0
  private var numVotos = 0
  private var roundWinner: (Int, Int) = (0,0)
  private val mutex = Semaphore(1)
  private val esperaVotacion = Semaphore(0)

  def vota(i: Int) = {
    mutex.acquire()
    numVotos += 1
    votos += (i -> (votos.getOrElse(i, 0) + 1))

    if (numVotos < numCardenales)
      mutex.release()
      esperaVotacion.acquire()
      numVotos -= 1
      if numVotos == 0 then mutex.release() else esperaVotacion.release()
    else {
      roundWinner = votos.maxBy(_._2)
      if (roundWinner._2 > numCardenales/2){
        fumata_blanca = true
        ganador = Option(roundWinner._1)
      }
      log(s"Votacion $numVotacion, gana el candidato ${roundWinner._1} con ${roundWinner._2} votos")
      votos.clear()
      numVotacion += 1
      numVotos -= 1
      esperaVotacion.release()
    }
  }
}

@main
def mainConclave(): Unit = {
  val N = Conclave.numCardenales
  val cardenales = new Array[Thread](N)
  for (i <- 0 until N){
    cardenales(i) = thread{
      while (!Conclave.fumata_blanca){
        Conclave.vota(Random.nextInt(N))
        Thread.sleep(Random.nextInt(200))
      }
    }
  }

  for (i <- 0 until N){
    cardenales(i).join()
  }
  log(s"El nuevo papa es el cardenal  ${Conclave.ganador.get}")
}