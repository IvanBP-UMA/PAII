import scala.annotation.tailrec

trait ImmutableSet[T] {
  def add(elem: T): ImmutableSet[T]
  def remove(elem: T): ImmutableSet[T]
  def contains(elem: T): Boolean
  def size: Int
  def isEmpty: Boolean
}

class SimpleSet[T] private(private val elems: List[T]) extends ImmutableSet[T] {
  def this(elems: T*) = this(elems.toList)
  def toList: List[T] = elems

  def union(other: SimpleSet[T]): SimpleSet[T] = {
    @tailrec
    def union(other: List[T], acc: List[T]): SimpleSet[T] = {
      elems match
        case Nil => SimpleSet(acc)
        case head :: tail => if acc.contains(head) then union(tail, acc) else union(tail, acc :+ head)
    }
    union(other.elems, Nil)
  }

  def intersection(other: SimpleSet[T]): SimpleSet[T] = {
    SimpleSet(other.elems.filter(this.elems.contains(_)))
  }

  def difference(other: SimpleSet[T]): SimpleSet[T] = {
    SimpleSet(this.elems.foldLeft(List[T]())((acc: List[T], e: T) => if !other.elems.contains(e) then acc :+ e else acc))
  }

  override def add(elem: T): ImmutableSet[T] = this.union(SimpleSet(elem))
  override def remove(elem: T): ImmutableSet[T] = this.difference(SimpleSet(elem))
  override def contains(elem: T): Boolean = elems.contains(elem)
  override def size: Int = elems.size
  override def isEmpty: Boolean = elems.isEmpty
}

def ej2(words: List[String], sw: Set[String]) = {
  words.map(w => w.split(":")).filter(_(0) == "FINAL").map(_(1)).mkString(" ").split(" ").map(_.toLowerCase()).filter((s)=>{s != "" && !sw.contains(s)}).groupBy(identity).map((k, v) => (k, v.length))
}

@main def testSimpleSet(): Unit = {
  // ...
  assert(SimpleSet(1, 2, 3, 4, 5).union(SimpleSet(6, 7, 8, 9, 10)) == SimpleSet(1, 2, 3, 4, 5, 6, 7, 8, 9, 10), "The union of the sets should be Set(1,2,3,4,5,6,7,8,9,10)")
  assert(SimpleSet(1, 2, 3, 4, 5, 6).intersection(SimpleSet(8, 6, 4)) == SimpleSet(4, 6), "The intersection of the set should be Set(4,6)")
  assert(SimpleSet(1, 2, 3, 4, 5, 6).difference(SimpleSet(6, 5, 4)) == SimpleSet(1, 2, 3), "The difference of the set should be Set(1,2,3)")
}

val sentences = List(
  "FINAL: Scala is a functional language",
  "DRAFT: The power of functional programming is great",
  "DRAFT: Programming is elegant",
  "FINAL: Functional programming is elegant",
  "FINAL: Object-oriented programming is great")
val stopWords = Set("a", "the", "is", "of")
println(ej2(sentences, stopWords))
