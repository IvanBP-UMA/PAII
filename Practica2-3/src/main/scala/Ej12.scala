object Ej12 {
  def sales1(list: List[(String, Int, Double)]): Double = {
    list.foldLeft(0.0)((acc, e) => acc + e._2*e._3)
  }

  def sales2(list: List[(String, Int, Double)]): List[(String, Double)] = {
    list.map(e => (e._1, e._2 * e._3)).filter(_._2 >= 100.0).sortBy(-_._2)
  }

  @main
  def main12(): Unit = {
    val sales = List(
      ("Laptop", 2, 1000.0), ("Mouse", 10, 15.0),
      ("Keyboard", 5, 50.0),
      ("Monitor", 3, 200.0),
      ("USB Drive", 20, 5.0)
    )
    println(sales1(sales))
    println(sales2(sales))
  }
}
