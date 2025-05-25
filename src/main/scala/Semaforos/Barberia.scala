package Semaforos
import concurrencia.*
import java.util.concurrent.*
import scala.util.Random

class Barberia {
  private var n = 0
  private val mutex = new Semaphore(1)
  private val esperaBarbero = new Semaphore(0)

  def nuevoCliente():Unit = {
    mutex.acquire()
    n += 1
    if (n == 1) {esperaBarbero.release(); log("Despierto al peluquero")}
    log(s"Llega el cliente $n")

    mutex.release()
  }

  def pelar(): Unit = {
    esperaBarbero.acquire()
    mutex.acquire()
    n -= 1
    log(s"Pela a un cliente. Clientes por pelar: $n")
    if (n > 0) esperaBarbero.release() else log("Me voy a dormir")
    mutex.release()
  }

  def nuevoClienteAlt(): Unit = {
    mutex.acquire()
    n+=1
    log(s"Ha llegado un cliente $n")
    if n==0 then esperaBarbero.release()
    mutex.release()
  }

  def pelarAlt(): Unit = {
    mutex.acquire()
    n-=1
    if (n == -1) {
      mutex.release()
      esperaBarbero.acquire()
      mutex.acquire()
    }
    Thread.sleep(Random.nextInt(500))
    log(s"He pelado un cliente $n")
    mutex.release()
  }
}

@main
def mainBarberia(): Unit = {
  val barberia = new Barberia()
  val barbero = thread {
    while (true)
      barberia.pelar()
  }

  val entorno = thread {
    while (true)
      Thread.sleep(Random.nextInt(10))
      barberia.nuevoCliente()
  }
}