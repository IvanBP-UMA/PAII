import concurrencia.*

def mezclar(l1: List[Int], l2: List[Int]): List[Int] = {
  (l1, l2) match
    case (Nil, l) => l
    case (l, Nil) => l
    case (h1 :: t1, h2 :: t2) => if (h1 < h2) then h1 :: mezclar(t1, l2) else h2 :: mezclar(l1, t2)
}

def ordenar(l: List[Int]): List[Int] = {
  if l.size <= 1 then l
  else {
    val (l1, l2) = l.splitAt(l.size / 2)
    val (l1o, l2o) = parallel(ordenar(l1), ordenar(l2))
    mezclar(l1o, l2o)
  }
}

@main
def main5(): Unit = {
  println(ordenar(List(3, 5, 9, 1, 2, 10)))
}