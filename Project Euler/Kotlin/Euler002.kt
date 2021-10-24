class Euler002 : Solution {
    private fun fib(): Sequence<Long> {
        var x = 0L
        var y = 1L

        fun next(): Long {
            val res = x + y
            x = y
            y = res

            return x
        }

        return generateSequence(::next)
    }

    override fun solve() {
        val x = fib().takeWhile { x -> x <= 4_000_000 }.filter { x -> x % 2 == 0L }.sum()
        println("Euler002 $x")
    }

    override fun solveFunctional() {
        //base solution has functional implementation
        println("Euler002 base solution has functional implementation")
    }

}