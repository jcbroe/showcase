# A Pythagorean triplet is a set of three natural numbers, a < b < c, for which,
# a2 + b2 = c2
# For example, 32 + 42 = 9 + 16 = 25 = 52.
# There exists exactly one Pythagorean triplet for which a + b + c = 1000.
# Find the product abc.


SUM = 1000


def get_product():
    for a in range(1, SUM + 1):
        for b in range(a + 1, SUM + 1):     # b must be greater than a, so range a + 1
            c = SUM - a - b     # a + b + c = 1000, so 1000 - a - b = c
            if a**2 + b**2 == c**2:
                return a * b * c


if __name__ == '__main__':
    print(get_product())


