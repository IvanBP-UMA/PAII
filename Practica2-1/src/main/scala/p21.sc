import scala.annotation.tailrec
import scala.collection.mutable.ListBuffer

@tailrec
def primeFactorsRec(n: Int, current: Int, divisors: ListBuffer[Int]): ListBuffer[Int] = {
  if (n != 1){
    if (n % current == 0){
      primeFactorsRec(n/current, current, divisors.addOne(current))
    }else{
      primeFactorsRec(n, current+1, divisors)
    }
  }
  divisors
}

def primeFactors(n: Int): List[Int] = {
  primeFactorsRec(n, 2, ListBuffer[Int]()).toList
}

primeFactors(97)

def zip[T, U](a: List[T], b: List[U]): List[(T, U)] = {
  if a.isEmpty || b.isEmpty then List()
  else (a.head, b.head) :: zip(a.tail, b.tail)
}
zip(List(1, 2, 3, 4), List("s", "t", "b"))

def unzip[T, U](list: List[(T, U)]): (List[T], List[U]) = {
  @tailrec
  def unzipRec(list: List[(T, U)], a: List[T], b: List[U]): (List[T], List[U]) = {
    if list.isEmpty then (a, b)
    else unzipRec(list.tail, a :+ list.head._1, b :+ list.head._2)
  }
  unzipRec(list, List[T](), List[U]())
}
unzip(List[(Int, String)]((1, "s"), (2, "t"), (3, "b")))

def groupBy[T, U](list: List[T], f: T => U): Map[U, List[T]] = {
  val result: Map[U, List[T]] = Map()
  for (e <- list){
    if result.contains(f.apply(e)) then result(f.apply(e)) = e :: result(f.apply(e))
    else {
      result(f.apply(e)) = List(e)
    }
  }
  result
}

val a = List[Int](1, 2, 3)
val test = List[Int](4, 5, 6)
test(1) :: a
