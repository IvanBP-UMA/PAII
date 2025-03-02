import scala.annotation.tailrec

object Common {

  @tailrec
  def gcd(a: Int, b: Int): Int = {
    if b == 0 then a else gcd(b, a%b)
  }

  def mcm(product: Int, gcd: Int): Int = {
    product/gcd
  }

  @main
  def mainCommon(): Unit = {
    print("Introduce el primer número: ")
    val a: Int = io.StdIn.readInt().ensuring(_ >= 0)
    print("Introduce el segundo número: ")
    val b: Int = io.StdIn.readInt().ensuring(_ >= 0)
    val mcd: Int = gcd(a,b)

    println("MCD " + a + " y " + b + ": " + mcd)
    println("mcm " + a + " y " + b + ": " + mcm(a*b, mcd))
  }
}
