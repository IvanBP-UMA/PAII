import concurrencia.*

def fibonacci(n: Int): (Int, Int) = {
  if n <= 2 then (1, 0)
  else {
    var res = (0, 0)
    val h = thread{ res = fibonacci(n-1) }
    h.join()
    (res._1+res._2, res._1)
  }
}

@main
def main4(): Unit = {
  log(fibonacci(5)._1.toString)
}