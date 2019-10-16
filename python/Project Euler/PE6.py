# The sum of the squares of the first ten natural numbers is,
# 12 + 22 + ... + 102 = 385
# The square of the sum of the first ten natural numbers is,
# (1 + 2 + ... + 10)2 = 552 = 3025
# Hence the difference between the sum of the squares of
# the first ten natural numbers and the square of the sum is 3025 − 385 = 2640.
# Find the difference between the sum of the squares of
# the first one hundred natural numbers and the square of the sum.


def sum_square_difference():
    n = 100     # could have hardcoded this but this way can be easily changed to test given case
    total1 = sum(i for i in range(1, n + 1))        # sum of the first 100 natural numbers
    total2 = sum(i**2 for i in range(1, n + 1))     # sum of the squares of the first 100 natural numbers
    return total1**2 - total2   # square of the sum - sum of the squares


if __name__ == '__main__':
    print(sum_square_difference())

