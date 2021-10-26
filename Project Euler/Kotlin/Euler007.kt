import kotlin.math.pow

class Euler007 : Solution {
    //basic isPrimeNumber but is less efficient
    /*
    fun isPrimeNumber(number: Int): Boolean {

    for (i in 2..number / 2) {
        if (number % i == 0) {
            return false
        }
    }
    return true
}
     */

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

    override fun solve () {
        var num = 1
        var numPrimes = 1
        while (numPrimes < 10001) {
            num += 2
            if (isPrime(num)) {
                numPrimes++
            }
        }

        println("Euler007 $num")
    }
}