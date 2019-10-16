# By listing the first six prime numbers: 2, 3, 5, 7, 11, and 13, we can see that the 6th prime is 13.
# What is the 10001st prime number?


def is_prime(n):
    if n == 2 or n == 3:    # 2 and 3 are prime
        return True
    if n % 2 == 0 or n < 2:     # nothing less than 2 is prime
        return False
    for i in range(3, int(n**0.5) + 1, 2):  # check starting with 3 for primes
        if n % i == 0:                      # if it's divisible by something other than 1 or itself it isn't prime
            return False
    return True


def gen_primes():
    num = 1     # initialize the starting point
    numprimes = 1   # initialize prime counter
    while numprimes < 10001:    
        num += 2    # starting with 1, check only odds
        if is_prime(num):
            numprimes += 1  # increment prime counter when prime is found
    return num


if __name__ == '__main__':
    print(gen_primes())

