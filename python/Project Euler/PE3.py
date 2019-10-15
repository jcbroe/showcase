# The prime factors of 13195 are 5, 7, 13 and 29.
# What is the largest prime factor of the number 600851475143 ?

# output should be 6857

import math


def smallest_prime(n):
    # check n for minimum value, will eventually return false to end loop
    assert n >= 2
    # every integer n > 1 has a unique factorization as a product of prime numbers
    # sqrt of # + 1
    for i in range(2, int(math.sqrt(n)) + 1):
        if n % i == 0:
            return i
    return n


def get_factors():
    n = 600851475143
    # while n >= 2
    # take the number n and repeatedly divide out its smallest factor
    # the last factor that we divide out must be the largest prime factor
    while True:
        p = smallest_prime(n)
        if p < n:
            # set n = floor of n / p
            n //= p
        else:
            return n


if __name__ == '__main__':
    print(get_factors())

