package Practica5

import concurrencia.*
import scala.util.Random

object Lago {
  @volatile private var n = 0;
  @volatile private var hayAgua = false
  private val cogiendoTurno = Array.fill(4)(false)
  private val turnos = Array.fill(4)(0)

  def meToca(i: Int, j: Int): Boolean = {
    if (turnos(j) > 0 && turnos(i) < turnos(j)) then false
    else if (turnos(i) == turnos(j) && j < i) then false
    else true
  }

  def subir(id: Int): Unit = {
    cogiendoTurno(id) = true
    turnos(id) = turnos.max + 1
    cogiendoTurno(id) = false

    for (i <- 0 until 4){
      while(cogiendoTurno(i)) Thread.sleep(0)
      while(!meToca(id, i)) Thread.sleep(0)
    }
    n+=1
    if (n == 1) then hayAgua = true
    log(s"Nivel del agua: $n")
    turnos(id) = 0
  }

  def bajar(id: Int): Unit = {
    while (!hayAgua) Thread.sleep(0)
    cogiendoTurno(id) = true
    turnos(id) = turnos.max + 1
    cogiendoTurno(id) = false

    for (i <- 0 until 4) {
      while (cogiendoTurno(i)) Thread.sleep(0)
      while (!meToca(id, i)) Thread.sleep(0)
    }
    n -= 1
    if (n == 0) then hayAgua = false
    log(s"Nivel del agua: $n")
    turnos(id) = 0
  }
}

@main
def main2(): Unit = {
  var rio1 = thread {
    for (i <- 0 until 1000){
      Lago.subir(0)
      Thread.sleep(Random.nextInt(10))
    }
  }
  var rio2 = thread {
    for (i <- 0 until 1000) {
      Lago.subir(1)
      Thread.sleep(Random.nextInt(10))
    }
  }

  var presa1 = thread{
    for (i <- 0 until 1000){
      Lago.bajar(2)
      Thread.sleep(Random.nextInt(10))
    }
  }
  var presa2 = thread {
    for (i <- 0 until 1000) {
      Lago.bajar(3)
      Thread.sleep(Random.nextInt(10))
    }
  }
}