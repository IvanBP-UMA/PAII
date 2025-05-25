package Practica6.soluciones

import java.util.concurrent.*
import scala.util.Random

class Cadena(n: Int) {
  // CS-empaquetador-i: espera hasta que hay productos de tipo i
  // CS-colocador: espera si hay n productos en la cadena
  private val tipo = Array.fill(3)(0) // es el buffer, número de elementos de cada tipo
  private var cuentaTotal = 0
  private val mutex = new Semaphore(1) // sobre todos los enteros
  private val esperaEmp = new Array[Semaphore](3)
  for (i <- 0 until esperaEmp.length)
    esperaEmp(i) = new Semaphore(0) // CS-Empaquetador-i
  private val esperaCol = new Semaphore(1) // CS- Colocalor

  def retirarProducto(p: Int) = {
    esperaEmp(p).acquire()
    mutex.acquire()
    tipo(p) -= 1
    log(s"Empaquetador $p retira un producto. Quedan ${tipo.mkString("[",",","]")}")
    if (tipo(p) > 0) esperaEmp(p).release()
    if (tipo.sum == n - 1) esperaCol.release()
    mutex.release()
  }

  def nuevoProducto(p: Int) = {
    esperaCol.acquire()
    mutex.acquire()
    tipo(p) += 1
    cuentaTotal += 1
    log(s"Colocador pone un producto $p. Quedan ${tipo.mkString("[",",","]")}")
    log(s"Total de productos empaquetados $cuentaTotal")
    if (tipo(p) == 1) esperaEmp(p).release()
    if (tipo.sum < n) esperaCol.release()
    mutex.release()
  }
}

object Ejercicio2 {
  def main(args:Array[String]) = {
    val cadena = new Cadena(6)
    val empaquetador = new Array[Thread](3)
    for (i <- 0 until empaquetador.length)
      empaquetador(i) = thread {
        while (true)
          cadena.retirarProducto(i)
          Thread.sleep(Random.nextInt(500)) // empaquetando
      }

    val colocador = thread {
      while (true)
        Thread.sleep(Random.nextInt(100)) // recogiendo el producto
        cadena.nuevoProducto(Random.nextInt(3))
    }
  }
}
