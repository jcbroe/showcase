class Euler001 : Solution {
    //this is the "traditional" solution
    /*    override fun solve() {
        var sum = 0
        for (i in 1..999) {
            if (i % 3 == 0 || i % 5 == 0) {
                sum += i
            }
        }
        println("Euler001 $sum")
    } */

    override fun solve() {
        val x = (1..999).filter{ x -> x % 3 == 0 || x % 5 == 0 }.sum()
        println("Euler001 $x")
    }
}