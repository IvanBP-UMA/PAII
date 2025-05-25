package Tema2

object Ej4 {
  class BankAccount(saldoInicial: Float){
    var saldo: Float = saldoInicial

    def depositar(n: Float): Unit = saldo += n
    def retirar(n: Float): Unit =
      require(n <= saldo, "No hay saldo suficiente")
      saldo -= n


    override def toString: String = "Saldo: " + saldo
  }

  @main
  def main4(): Unit = {
    val account: BankAccount = BankAccount(20)
    account.depositar(5.2)
    println(account)
    account.retirar(25)
    println(account)
    account.retirar(1)
  }
}
