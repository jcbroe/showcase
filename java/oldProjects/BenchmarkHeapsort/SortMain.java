public class SortMain {
    public static void main(String[] args) {
        int[] testSizes = {10, 20, 50, 100, 200,
                500, 10000, 15000, 20000, 100000};

        BenchmarkSorts mySort = new BenchmarkSorts(testSizes);
        mySort.runSorts();
        mySort.displayReports();
    }
}
