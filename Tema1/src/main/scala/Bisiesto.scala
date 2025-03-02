object Bisiesto extends App{
  def esBisiesto(y: Int): Boolean = {
    ((y % 4 == 0) && (y % 100 != 0)) || (y%400 == 0)
  }

  print("Introduce un año: ")
  print(esBisiesto(io.StdIn.readInt()))
}
