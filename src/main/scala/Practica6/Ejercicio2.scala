package Practica6
import concurrencia.*
import java.util.concurrent.*
import scala.util.Random

class Cadena(n: Int) {
  // CS-empaquetador-i: espera hasta que hay productos de tipo i
  // CS-colocador: espera si hay n productos en la cadena
  private val tipo = Array.fill(3)(0) // el buffer
  private val empaquetadores = Array.fill(3)(Semaphore(0))
  private var cuentaTotal = 0
  private val esperaCol = Semaphore(1) // CS- Colocalor
  private val mutex = Semaphore(1)


  def retirarProducto(p: Int): Unit = {
    empaquetadores(p).acquire()
    mutex.acquire()
    cuentaTotal += 1
    tipo(p) -= 1
    if tipo.sum == n - 1 then esperaCol.release()
    if tipo(p) > 0 then empaquetadores(p).release()
    log(s"Empaquetador $p retira un producto. Quedan ${tipo.mkString("[",",","]")}")
    mutex.release()
  }

  def nuevoProducto(p:Int): Unit = {
    esperaCol.acquire()
    mutex.acquire()
    tipo(p) += 1
    if tipo(p) == 1 then empaquetadores(p).release()
    if tipo.sum < n then esperaCol.release()
    log(s"Colocador pone un producto $p. Quedan ${tipo.mkString("[",",","]")}")
    log(s"Total de productos empaquetados $cuentaTotal")
    mutex.release()
  }
}

object Ejercicio2 {
  def main(args:Array[String]): Unit = {
    val cadena = new Cadena(6)
    val empaquetador = new Array[Thread](3)
    for (i <- empaquetador.indices)
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
