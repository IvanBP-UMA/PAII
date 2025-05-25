package Tema2

object Ej6 {
  class Student(name: String, age: Int, grades: List[Float]){
    override def toString: String = getClass.getSimpleName + "(Name: " + name + "; Age: "+age+")"
    def mean(): Float = {
      grades.reduce(_ + _)/grades.size
    }
  }

  @main
  def main6(): Unit = {
    val st: Student = Student("Pepe", 18, List(5, 7, 9, 4))
    println(st)
    println(st.mean())
  }
}
