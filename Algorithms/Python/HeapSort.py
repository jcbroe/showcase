#this is the classic heapsort structure
#turn the array into a binary heap, then remove the max element to build sorted list
#can't have heapsort without heapify, heapify the array
def heapify(arr, n, i):
    # Initialize largest as root
    largest = i
    left = 2 * i + 1
    right = 2 * i + 2
    #check left child
    if left < n and arr[i] < arr[left]:
        largest = left
    #check right child
    if right < n and arr[largest] < arr[right]:
        largest = right
    #check if the largest value has been achieved
    if largest != i:
        arr[i], arr[largest] = arr[largest], arr[i]  # swap
        # Heapify the root.
        heapify(arr, n, largest)

def heap_sort (arr):
    n = len(arr)
    #first max-heap the array
    for i in range(n // 2 - 1, -1, -1):
        heapify(arr, n, i)
    #swap the root(maximum value) of the heap with the last element of the heap
    #size of the heap gets decremented and put back in max-heap order
    for i in range(n - 1, 0, -1):
        arr[i], arr[0] = arr[0], arr[i]  # swap
        heapify(arr, i, 0)

if __name__ == '__main__':
    arr = [2, 5, 3, 8, 6, 5, 4, 7]
    n = len(arr)
    print("Original: ")
    print(arr)
    heap_sort(arr)
    print("Sorted: ")
    print(arr)
