class Euler005 : Solution {
    private fun gcd(a : Int, b : Int) : Int {
        if (b == 0) {
            return a
        }
        return gcd(b, a % b)
    }

    override fun solve () {
        var total = 1
        for (i in 1..20) {
            total *= i / gcd(i, total)
        }

        println("Euler005 $total")
    }

}