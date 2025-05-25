package Practica4

var turno: Int = 0

class Hebra(c: Char, t: Int, id: Int) extends Thread {
  override def run(): Unit = {
     for (i <- 0 until t){
       while (id != turno) Thread.sleep(0)
        print(c)
        turno = (turno+1)%3
      }
    }
}

@main
def mainA(): Unit = {
  val hA: Hebra = new Hebra('A', 3, 0)
  val hB: Hebra = new Hebra('B', 3, 1)
  val hC: Hebra = new Hebra('C', 3, 2)
  hA.start(); hB.start(); hC.start();
}

