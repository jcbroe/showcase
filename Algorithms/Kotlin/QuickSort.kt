//3 different ways
//most familiar style to me personally
fun<T: Comparable<T>> List<T>.qsortOne(): List<T> {
  if (this.size < 2) return this

  val pivot = this[this.size / 2]
  val less = this.filter { it < pivot }
  val equal = this.filter { it == pivot }
  val greater = this.filter { it > pivot }
  return less.qsortOne() + equal + greater.qsortOne()
}

//same but different(?)
fun <E : Comparable<E>> List<E>.qsortTwo(): List<E> =
        if (size < 2) this
        else filter { it < first() }.qsortTwo() +
                filter { it == first() } +
                filter { it > first() }.qsortTwo()
                
//single comparison per element
fun <E : Comparable<E>> List<E>.qsortThree(): List<E> =
        if (size < 2) this
        else {
            val (less, high) = subList(1, size).partition { it < first() }
            less.qsortThree() + first() + high.qsortThree()
        }
