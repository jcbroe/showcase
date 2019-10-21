# The sum of the primes below 10 is 2 + 3 + 5 + 7 = 17.
# Find the sum of all the primes below two million.


def is_prime(n):
    if n == 2 or n == 3:    # 2 and 3 are prime
        return True
    if n % 2 == 0 or n < 2:     # nothing less than 2 is prime
        return False
    for i in range(3, int(n**0.5) + 1, 2):  # check starting with 3 for primes
        if n % i == 0:                      # if it's divisible by something other than 1 or itself it isn't prime
            return False
    return True


def prime_sum():
    # pretty straightforward, if prime, add to total, return total
    total = 0
    for i in range(1, 2000000):
        if is_prime(i):
            total += i
    return total


if __name__ == '__main__':
    print(prime_sum())

