import scala.annotation.tailrec
import scala.collection.mutable
import scala.collection.mutable.ListBuffer
import scala.collection.mutable.Map
import scala.collection.mutable.Set as mSet

/*EJERCICIO 1*/
def primeFactors(n: Int): List[Int] = {
  def primeFactorsRec(n: Int, current: Int, divisors: ListBuffer[Int]): ListBuffer[Int] = {
    if (n != 1) {
      if (n % current == 0) {
        primeFactorsRec(n / current, current, divisors.addOne(current))
      } else {
        primeFactorsRec(n, current + 1, divisors)
      }
    }
    divisors
  }
  primeFactorsRec(n, 2, ListBuffer[Int]()).toList
}
primeFactors(97)


/*EJERCICIO 2*/
@tailrec
def binarySearch(arr: Array[Int], elt: Int, offset: Int): Option[Int] = {
  if arr.isEmpty then None
  else{
    val i = arr.length/2
    val v = arr(i)
    if v == elt then Some(i + offset)
    else if v > elt then binarySearch(arr.slice(0, i), elt, 0)
    else binarySearch(arr.slice(i, arr.length), elt, i)
  }
}
binarySearch(Array(1, 3, 4, 5, 9), 5, 0)


/*EJERCICIO 3*/
def unzip[T, U](list: List[(T, U)]): (List[T], List[U]) = {
  @tailrec
  def unzipRec(list: List[(T, U)], a: List[T], b: List[U]): (List[T], List[U]) = {
    if list.isEmpty then (a, b)
    else unzipRec(list.tail, a :+ list.head._1, b :+ list.head._2)
  }
  unzipRec(list, List[T](), List[U]())
}
unzip(List[(Int, String)]((1, "s"), (2, "t"), (3, "b")))


/*EJERCICIO 4*/
def zip[T, U](a: List[T], b: List[U]): List[(T, U)] = {
  if a.isEmpty || b.isEmpty then List()
  else (a.head, b.head) :: zip(a.tail, b.tail)
}
zip(List(1, 2, 3, 4), List("s", "t", "b"))


/*EJERCICIO 5*/
def filter[T](list: List[T], f: T => Boolean): List[T] = {
  val res: ListBuffer[T] = ListBuffer()
  for (i <- list)
    if f.apply(i) then res.append(i)
  res.toList
}
filter(List(1, 5, 6, 7, 3, 2), e => e>=5)
/*Recursive version of filter. Is not an exercise*/
def filterRec[T](list: List[T], f: T => Boolean): List[T] = {
  if f.apply(list.head) then list.head :: filterRec(list.tail, f)
  else filter(list.tail, f)
}
filterRec(List(1, 5, 6, 7, 3, 2), e => e>=5)
/*Tail recursive version of filter. Is not an exercise*/
@tailrec
def filterTailRec[T](res: List[T], current: List[T], f: T => Boolean): List[T] = {
  if current.isEmpty then res
  else if (f.apply(current.head)) {
    filterTailRec(res :+ current.head, current.tail, f)
  }
  else{
    filterTailRec(res, current.tail, f)
  }
}
filterTailRec(List(), List(1, 5, 6, 7, 3, 2, 8), e => e>=5)


/*EJERCICIO 6*/
def map[T, U](list: List[T], f: T => U): List[U] = {
  val res: ListBuffer[U] = ListBuffer()
  for (e <- list){
    res.append(f.apply(e))
  }
  res.toList
}
map(List('a', 'b', 'd', 'z'), _.toInt)
/*Recursive version of map. Is not an exercise*/
def mapRec[T, U](list: List[T], f: T => U): List[U] = {
  if list.nonEmpty then f.apply(list.head) :: mapRec(list.tail, f)
  else List()
}
mapRec(List('a', 'b', 'd', 'z'), _.toInt)
/*Tail recursive version of map. Is not an exercise*/
@tailrec
def mapTailRec[T, U](res:List[U], current: List[T], f: T => U): List[U] = {
  if current.isEmpty then res
  else mapTailRec(res :+ f.apply(current.head), current.tail, f)
}
mapRec(List('a', 'b', 'd', 'z'), _.toInt)


/*EJERCICIO 7*/
def groupBy[T, U](list: List[T], f: T => U): mutable.Map[U, List[T]] = {
  val result: mutable.Map[U, List[T]] = mutable.Map()
  for (e <- list){
    val key: U = f.apply(e)
    if result.contains(key) then result(key) = e :: result(key)
    else {
      result += (key->List(e))
    }
  }
  result
}
groupBy(List('a', 'b', 'a', 'a', 'c'), _.toInt)


/*EJERCICIO 8*/
def reduce[T](list: List[T], f: (T, T) => T): T = {
  var previous: T = list.head
  for (i <- 1 until list.size){
    previous = f.apply(previous, list(i))
  }
  previous
}
reduce(List("He", "llo", " World"), _.appendedAll(_))


/*EJERCICIO 9*/
def subsets[T](set: Set[T]): Set[Set[T]] = {
  if set.isEmpty then Set(Set())
  else {
    var result: Set[Set[T]] = Set(set)
    for (e <- set){
      result = result ++ subsets(set - e)
    }
    result
  }
}
subsets(Set(1, 2, 3))
/*Alternate a probably more efficient method. Had to change signature a bit.
* Still not tail recursive*/
def subsets2[T](items: List[T], current: Set[T]): Set[Set[T]] = {
  val res: mSet[Set[T]] = mSet()
  if items.isEmpty then res.addOne(current)
  else{
    res.addAll(subsets2(items.drop(1), current))
    res.addAll(subsets2(items.drop(1), current + items.head))
  }
  res.toSet
}
subsets(Set(1, 2, 3))


/*EJERCICIO 10*/
def generateParentheses(n: Int): List[String] = {
  val result: ListBuffer[String] = ListBuffer()
  def parentheses(res: ListBuffer[String], len: Int, current: String, open: Int, closed: Int): Unit = {
    if ((open <= len) && (closed <= len)) {
      if ((open == len) && (open == closed)) {
        res.append(current)
      } else {
        parentheses(res, len, current.appendedAll("("), open+1, closed)
        if open>closed then parentheses(res, len, current.appendedAll(")"), open, closed+1)
      }
    }
  }
  parentheses(result, n, "", 0, 0)
  result.toList
}
generateParentheses(3)