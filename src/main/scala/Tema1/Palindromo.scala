package Tema1

object Palindromo extends App {
  def esPalindromo(s: String): Boolean = {
    var head: Int = 0
    var tail: Int = s.length - 1;
    var palindromo: Boolean = true

    while (palindromo && head < tail){
      if (s.charAt(head).compareTo(s.charAt(tail)) != 0){
        palindromo = false
      }
      head+=1
      tail-=1
    }

    palindromo
  }
  println(esPalindromo("abca"))
}
