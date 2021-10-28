class Euler009 : Solution {
    override fun solve() {
        var result = 0
        for (a in 1..1000) {
            for (b in a + 1..1000) {
                var c = 1000 - a - b
                if (c * c == a * a + b * b) {
                    result = a * b * c
                }
            }
        }
        println("Euler009 $result")
    }
}