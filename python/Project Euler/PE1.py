# If we list all the natural numbers below 10 that are multiples of 3 or 5,
# we get 3, 5, 6 and 9. The sum of these multiples is 23.
# Find the sum of all the multiples of 3 or 5 below 1000.


def sum_multiples():
    # long way - initialize variable, loop through calculating, return variable
    # total = 0
    # for x in range(10):
    #     if x % 3 == 0 or x % 5 == 0:
    #         total += x
    # return total
    # 23
    
    # short way - inline
    # total = sum(x for x in range(10) if (x % 3 == 0 or x % 5 == 0))
    # return total
    # 23

    total = sum(x for x in range(1000) if (x % 3 == 0 or x % 5 == 0))
    return total


if __name__ == '__main__':
    print(sum_multiples())

