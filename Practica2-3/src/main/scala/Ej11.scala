object Ej11 {
  def errors1(logs: List[String]): Map[String, Int] = {
    logs.map(_.split(":")(0)).groupBy(identity).map((k, v) => (k, v.length))
  }

  def errors2(logs: List[String]): List[String] = {
    logs.groupBy(_.split(":")(0))("ERROR")
  }

  @main
  def main11(): Unit = {
    val logs = List(
      "ERROR: Null pointer exception",
      "INFO: User logged in",
      "ERROR: Out of memory",
      "WARNING: Disk space low",
      "INFO: File uploaded",
      "ERROR: Database connection failed"
    )
    println(errors1(logs))
    println(errors2(logs))
  }
}
