/*
 * Name: Jacob Roe
 * CMSC 451
 * Project: 1
 * This is the class for the heap sort. Thank you wikipedia and rosettacode for the inspiration.
 */

public class HeapSort implements SortInterface {
    private int count;
    private long time;

    public HeapSort() {
        count = 0;
        time = 0;
    }


    public void iterativeSort(int[] array) throws UnsortedException {
        System.out.println("\nBeginning iterative HeapSort");
        count = 0; 
        long start = System.nanoTime();

        for (int heapsize = 0; heapsize < array.length; heapsize++) {
            int n = heapsize; 
            while (n > 0) { 
                int p = (n-1)/2;

                if (array[n] > array[p]) { 
                    count++;
                    arraySwap(array, n, p); 
                    n = p; 
                } else { 
                    break;
                }
            }
        }

        for (int heapsize = array.length; heapsize > 0;) {
            arraySwap(array, 0, --heapsize);
            count++;

            int n = 0;

            while (true) {
                int left = n * 2 + 1;
                if (left >= heapsize) 
                    break;

                int right = left + 1;
                if (right >= heapsize) { 
                    if (array[left] > array[n]) 
                        arraySwap(array, left, n); 
                        count++;
                    break;
                }

                if (array[left] > array[n]) { 
                    if (array[left] > array[right]) {
                        arraySwap(array, left, n);
                        count++;
                        n = left; continue; 
                    } else { 
                        arraySwap(array, right, n);
                        count++;
                        n = right; continue; 
                    }
                } else { 
                    if (array[right] > array[n]) { 
                        arraySwap(array, right, n);
                        count++;
                        n = right;
                        continue; 
                    } else { 
                        break;
                    }
                }
            }
        }

        time = System.nanoTime() - start;

        System.out.println("Iterative HeapSort Check...");

        try {
            iterArrayChk(array);
        } catch (UnsortedException e) {
            throw new UnsortedException(e.getMessage());
        }

        System.out.println("Correctly Sorted.");
    }


    public void recursiveSort(int[] array) throws UnsortedException {
        System.out.println("\nBeginning recursive HeapSort");
        count = 0; // Reflects every time maxHeapify is called.
        long start = System.nanoTime();

        buildMaxHeap(array);

        for(int i = array.length - 1; i >= 1; i--) {
            arraySwap(array, 1, i);
            maxHeapify(array, 1, i - 1);
        }

        time = System.nanoTime() - start;

        System.out.println("Recursive HeapSort Check...");
                

        try {
            recursArrayChk(array);
        } catch (UnsortedException e) {
            throw new UnsortedException(e.getMessage());
        }

        System.out.println("Correctly Sorted. ");
                
    }

    private void buildMaxHeap(int[] a) {
        for(int i = (int) Math.floor((a.length - 1) / 2.0); i >= 0; i --) {
            maxHeapify(a, i, a.length - 1);
        }
    }

    private void maxHeapify(int[] a, int root, int heapArea) {
        count++;
        int left = 2 * root;
        int right = 2 * root + 1; 
        int max;

        if(left <= heapArea && a[left] > a[root]) {
            max = left;
        } else {
            max = root;
        }

        if(right <= heapArea && a[right] > a[max]) {
            max = right;
        }

        if(max != root) {
            arraySwap(a, root, max);
            maxHeapify(a, max, heapArea);
        }
    }
 
    public int getCount() { return count; }

    public long getTime() { return time; }

    private static void arraySwap(int[] a, int i, int j) {
        int temp = a[i];
        a[i] = a[j];
        a[j] = temp;
    }

    private static void iterArrayChk(int[] a) throws UnsortedException {
        for (int i = 0; i < a.length - 1; i++)
            if(a[i] > a[i + 1]) {
                for(int j = 0; j < a.length -1; j++)
                    System.out.print(" "  + a[j]);
                throw new UnsortedException("The array was not sorted " +
                        "correctly: \n" +
                        a[i] + " > " + a[i+1] + " at " + i +
                        " and " + (i+1) + "\n");
            }
    }

    private static void recursArrayChk(int[] a) throws UnsortedException {
        for (int i = 1; i < a.length - 1; i++)
            if(a[i] > a[i + 1]) {
                for(int j = 0; j < a.length -1; j++)
                    System.out.print(" "  + a[j]);
                throw new UnsortedException("The array was not sorted " +
                        "correctly: \n" +
                        a[i] + " > " + a[i+1] + " at " + i +
                        " and " + (i+1) + "\n");
            }
    }
}
