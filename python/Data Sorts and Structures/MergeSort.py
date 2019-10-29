from heapq import merge


def merge_sort(somelist):
    # if size of list is 1 or smaller, return the list
    if len(somelist) <= 1:
        return somelist

    # find the middle, use floor in Python to return int
    middle = len(somelist) // 2
    # split the original into 2 lists/arrays, left from beginning to middle, right from middle to end
    left = somelist[:middle]
    right = somelist[middle:]

    # recursively merge sort on lists until 1-2 elements are left in each
    left = merge_sort(left)
    right = merge_sort(right)

    # use merge to sort and join the lists. Due to recursion the original list will eventually be returned sorted.
    return list(merge(left, right))


if __name__ == '__main__':
    arr = [12, 11, 13, 5, 6, 7]
    print("Original: ")
    print(arr)
    print("Sorted: ")
    print(merge_sort(arr))



