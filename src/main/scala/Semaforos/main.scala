package Semaforos

import concurrencia.*
import java.util.concurrent.Semaphore

def impresorasGeneral(): Unit =
  val Nimpresoras = 2
  val Ntrabajadores = 10
  val semaforo = new Semaphore(Nimpresoras)
  semaforo.tryAcquire()

  for (i <- 0 until Ntrabajadores) {
    thread {
      while (true)
        semaforo.acquire()
        println("Esta imprimiendo")
        semaforo.release()
    }
  }

def impresorasBinario(): Unit = {
  val Nimpresoras = 2
  val Ntrabajadores = 10
  val impresoras = Array.fill(Nimpresoras)(new Semaphore(1))

  for (i <- 0 until Ntrabajadores) {
    thread {
      var tieneImpresora = false
      while (true) {
        var j = 0
        for (j <- 0 until Nimpresoras) {
          tieneImpresora = impresoras(j).tryAcquire()
        }
        if !tieneImpresora then Thread.sleep(0)
        else impresoras(j).release()
      }
    }
  }
}