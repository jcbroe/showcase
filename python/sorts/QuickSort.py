# in-place quicksort with random pivot
# random pivot is used to improve average run times
# function quicksort(array)
#     if length(array) > 1
#         pivot := select any element of array
#         left := first index of array
#         right := last index of array
#         while left ≤ right
#             while array[left] < pivot
#                 left := left + 1
#             while array[right] > pivot
#                 right := right - 1
#             if left ≤ right
#                 swap array[left] with array[right]
#                 left := left + 1
#                 right := right - 1
#         quicksort(array from first index to right)
#         quicksort(array from left to last index)

import random


# pass in would look something like current, current[0], current[-1]
def qsort(current, first, last):
    if first >= last:
        return
    # see above pseudocode for implementation
    i, j = first, last
    pivot = current[random.randint(first, last)]
    
    while i <= j:
        while current[i] < pivot:
            i += 1
        while current[j] > pivot:
            j -= 1
        if i <= j:
            current[i], current[j] = current[j], current[i]
            i, j = i + 1, j - 1
    qsort(current, first, j)
    qsort(current, i, last)



