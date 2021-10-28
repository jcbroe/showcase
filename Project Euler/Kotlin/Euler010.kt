import kotlin.math.pow

class Euler010 : Solution {
    private fun isPrime (n: Int) : Boolean {
        if (n == 2 || n == 3) {
            return true
        }
        if (n % 2 == 0 || n < 2) {
            return false
        }
        for (i in (3..n.toDouble().pow(0.5).toInt() + 1) step 2) {
            if (n % i == 0) {
                return false
            }
        }
        return true
    }


    override fun solve() {
        //"traditional" solution
        /*var total = 0L
        for (i in 1..2000000) {
            if(isPrime(i)) {
                total += i
            }
        } */

        //fold because reduce only returns type int
        val result = (1..2_000_000).filter { x -> isPrime(x) }.fold(0L) { acc, i -> acc + i }
        println("Euler010 $result")
    }
}