/*
 * Name: Jacob Roe
 * CMSC 451
 * Project: 1
 * This is the interface class.
 */


public interface SortInterface {
    void recursiveSort(int[] list) throws UnsortedException;
    void iterativeSort(int[] list) throws UnsortedException;
    int getCount();
    long getTime();
}
