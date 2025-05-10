import concurrencia.*

@main
def main2b(): Unit = {
  var h1: Thread = periodico(1000)(log("Hebra 1000 ms"))
  var h2: Thread = periodico(3000)(log("Hebra 3000 ms"))
}