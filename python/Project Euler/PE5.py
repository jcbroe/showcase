# 2520 is the smallest number that can be divided by each of the numbers from 1 to 10 without any remainder.
# What is the smallest positive number that is evenly divisible by all of the numbers from 1 to 20?


import math


def find_gcds():
    total = 1   # starting value of 1 so that multiplication below works
    for i in range(1, 21):  # 1 to 20
        total *= i // math.gcd(i, total)    # total = total * (1 to 20) divided by the greatest common denominator
    return total                            # of i and the total, with the result of the division floored


if __name__ == '__main__':
    print(find_gcds())
