/*
* If we list all the natural numbers below 10 that are multiples of 3 or 5,
* we get 3, 5, 6 and 9. The sum of these multiples is 23.
* Find the sum of all the multiples of 3 or 5 below 1000.
*
* output should be 233168
*/
object Problem001 extends App {
  /*
  * result is declared as the range of numbers from 1 to 1000
  * view produces a lazy collection so that filter does not need to evaluate every element of the collection
  * this defers the creation of the new collection until after evaluation, for efficiency and reduced memory usage
  * filter by elements in list being divisible by either 3 or 5, and if they are, sum them into result
  */
  val result = (1 until 1000).view.filter(n => n % 3 == 0 || n % 5 == 0).sum
  println(result)
}
