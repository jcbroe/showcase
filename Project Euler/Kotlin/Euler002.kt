class Euler002 : Solution {
    override fun solve() {
        val fib = generateSequence(0 to 1) { it.second to it.first + it.second }.map { it.first }
        var result = fib.takeWhile { it < 4000000 }.filter{x -> x % 2 == 0}.sum()
        println("Euler002 $result")
    }
}