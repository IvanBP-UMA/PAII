object Primes {
  def isPrime(x: Int): Boolean = {
    var result = true
    var i: Int = 2
    while (result && i <= x/2){
      if (x%i == 0){
        result = false
      }
      i += 1
    }
    result
  }

  @main
  def mainPrimes(): Unit = {
    print("Introduce el número de primos: ")
    val n: Int = io.StdIn.readInt().ensuring(_ > 0)
    var primesCounter: Int = 0
    var i: Int = 2
    while (primesCounter < n){
      if (isPrime(i)){
        print(i + " ")
        primesCounter += 1
      }
      i += 1
    }
  }
}
