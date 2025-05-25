package Tema1

object Cajero extends App {
  class  Caja {
    var saldo: Float = 0.0F
    def consultarSaldo(): Unit = println("Saldo actual: " + saldo)
    def ingresar(x: Float): Unit = {
      if (x > 0) {
        saldo += x
      }
    }
    def retirar(x: Float): Unit = {
      if (x > saldo){
        println("Saldo insuficiente")
      }
      else{
        saldo -= x
      }
    }
  }

  var cuenta: Caja = Caja()
  var option: Int = 1
  var saldo: Float = 0
  while (option > 0 && option < 4){
    println("1. Consultar saldo")
    println("2. Ingresar saldo")
    println("3. Retirar saldo")
    option = io.StdIn.readInt()

    option match
      case 1 => {cuenta.consultarSaldo()}
      case 2 => {saldo = io.StdIn.readFloat().ensuring( _ > 0)
                  cuenta.ingresar(saldo)}
      case 3 => {saldo = io.StdIn.readFloat().ensuring( _ > 0)
                  cuenta.retirar(saldo)}
      case _ => {}
  }
}
