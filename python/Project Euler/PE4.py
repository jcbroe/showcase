# A palindromic number reads the same both ways.
# The largest palindrome made from the product of two 2-digit numbers is 9009 = 91 × 99.
# Find the largest palindrome made from the product of two 3-digit numbers.


def make_palindrome():
    palin = max(i * j   # creates the product of
                for i in range(100, 1000)   # first 3 digit number
                for j in range(100, 1000)   # second 3 digit number
                if str(i * j) == str(i * j)[:: -1])     # string of the product sliced and reversed
    return palin


if __name__ == '__main__':
    print(make_palindrome())
