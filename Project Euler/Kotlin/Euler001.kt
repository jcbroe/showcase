class Euler001 : Solution {
    override fun solve() {
        var sum = 0
        for (i in 1..999) {
            if (i % 3 == 0 || i % 5 == 0) {
                sum += i
            }
        }
        println("Euler001 $sum")
    }

    fun solveFunctional() {
        val x = (1..999).filter{ x -> x % 3 == 0 || x % 5 == 0 }.sum()
        println("Euler001 functional $x")
    }
}