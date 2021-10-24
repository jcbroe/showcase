class Euler003 : Solution {
    override fun solve() {
        var i = 2
        var n = 600851475143

        //square each number less than n than factor it out of n
        while (i * i < n) {
            if (n % i == 0L) {
                n /= i
            }
            i++
        }

        println("Euler003 $n")
    }
}