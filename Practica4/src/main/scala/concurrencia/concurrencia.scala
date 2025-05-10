package object concurrencia {
  def log(msg: String): Unit =
    println(s"${Thread.currentThread().getName}: $msg")

  def thread(body: => Unit): Thread = {
    val t = new Thread {
      override def run(): Unit = body
    }
    t.start()
    t
  }

  def periodico(t: Long)(b: =>Unit): Thread = {
    thread {
      while (true) {
        b
        Thread.sleep(t)
      }
    }
  }

  def parallel[A,B](a: =>A, b: =>B):(A,B) = {
    var va: A = null.asInstanceOf[A]
    var vb: B = null.asInstanceOf[B]
    val vA = thread{va = a}
    val vB = thread{vb = b}
    vA.join()
    vB.join()
    (va, vb)
  }
}
