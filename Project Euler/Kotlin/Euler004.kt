class Euler004 : Solution {

    override fun solve() {
        var maxPalindrome = 0
        for (i in 100..1000) {
            for (j in 100..1000) {
                if ((i * j).toString() == (i * j).toString().reversed() && (i * j) > maxPalindrome) {
                    maxPalindrome = i * j
                }
            }
        }

        println("Euler004 $maxPalindrome")
    }
}