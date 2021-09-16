public class BenchmarkSorts {
    private final int arrayRuns = 50;
    private int[] testSizes;
    private int[][][] counts;
    private long[][][] times;
    HeapSort mySort;

    BenchmarkSorts(int[] sizes) {
        testSizes = sizes;
        counts = new int[testSizes.length][2][arrayRuns];
        times = new long[testSizes.length][2][arrayRuns];
        mySort = new HeapSort();
    }

    public void runSorts() {
        for(int i = 0; i < testSizes.length; i++) {
            for(int j = 0; j < arrayRuns; j++) {
                int[] test = makeIntArray(testSizes[i]);
                int[] recursTest = new int[testSizes[i] + 1];
                int[] iterTest = new int[testSizes[i]];
                try {
                    System.arraycopy(test, 0, recursTest, 1, test.length);
                    mySort.recursiveSort(recursTest);
                    counts[i][1][j] = mySort.getCount();
                    times[i][1][j] = mySort.getTime();

                    System.arraycopy(test, 0, iterTest, 0, test.length);
                    mySort.iterativeSort(iterTest);
                    counts[i][0][j] = mySort.getCount();
                    times[i][0][j] = mySort.getTime();

                } catch (UnsortedException e) {
                    System.out.println("\nSort failed!");
                    System.out.println(e.getMessage());
                }
            }
        }
    }

    public void displayReports() {
        double[] data;

        //hopefully I did this part right, using this: https://docs.oracle.com/javase/tutorial/java/data/numberformat.html
        System.out.print("+");
        for(int i = 0; i < 119; i++) System.out.print("-");
        System.out.println("+");
        System.out.format("|%-7s|%28s%27s|%28s%27s|%n", "Size N", "Iterative", "", "Recursive", "");
        System.out.print("+");
        for(int i = 0; i < 119; i++) System.out.print("-");
        System.out.println("+");
        System.out.format("|%7s|", "");
        for(int i = 0; i < 2; i++) {
            System.out.format("%13s|%13s|%13s|%13s|", "Average", "Standard", "Average", "Standard");
        }
        System.out.format("%n|%7s|", "");
        for(int i = 0; i < 2; i++) {
            System.out.format("%13s|%13s|%13s|%13s|", "Critical", "Deviation", "Execution", "Deviation");
        }
        System.out.format("%n|%7s|", "");
        for(int i = 0; i < 2; i++) {
            System.out.format("%13s|%13s|%13s|%13s|", "Operation", "of Count", "Time", "of Time");
        }
        System.out.format("%n|%7s|", "");
        for(int i = 0; i < 2; i++) {
            System.out.format("%13s|%13s|%13s|%13s|", "Count", "", "(Seconds)", "(Seconds)");
        }
        System.out.print("\n+");
        for(int i = 0; i < 119; i++) System.out.print("-");
        System.out.print("+");

        for(int i = 0; i < testSizes.length; i++) {
            System.out.format("%n|%7d|", testSizes[i]);
            for(int j = 0; j < 2; j++) {
                data = calcStats(counts[i][j], times[i][j]);
                System.out.format("%,13.2f|%13.4f|%13.6f|%13.6f|", data[0], data[1], data[2], data[3]);
            }
        }

        System.out.print("\n+");
        for(int i = 0; i < 119; i++) System.out.print("-");
        System.out.println("+");
    }

    private static int[] makeIntArray(int size) {

        int[] data = new int[size];

        for(int i = 0; i < size; i++)
            data[i] = (int) (Math.random() * 1000);

        return data;
    }

    private double[] calcStats(int[] counts, long[] times) {
        double avgCount = 0;
        double avgTime = 0;
        double varCount = 0;
        double devCount = 0;
        double varTime = 0;
        double devTime = 0;

        for(int i = 0; i < counts.length; i++) {
            avgCount += counts[i];
            avgTime += times[i] / 1000000000.0;
        }

        avgCount = avgCount / counts.length;
        avgTime = avgTime / times.length;

        for(int i = 0; i < counts.length; i++) {
            varCount += (avgCount - counts[i]) * (avgCount - counts[i]);
            varTime += (avgTime - (times[i]/1000000000.0)) * (avgTime - (times[i]/1000000000.0));
        }

        varCount = varCount / counts.length;
        varTime = varTime / times.length;

        devCount = Math.sqrt(varCount);
        devTime = Math.sqrt(varTime);



        double[] data = {avgCount, devCount, avgTime, devTime};
        return data;
    }
}
