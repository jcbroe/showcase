class Euler006 : Solution {
    override fun solve () {
        val sumOfSquares = (1..100).map { x -> x * x }.sum()
        val squareOfSum = (1..100).sum() * (1..100).sum()
        println(squareOfSum - sumOfSquares)
    }
}
