/*
* The prime factors of 13195 are 5, 7, 13 and 29.
* What is the largest prime factor of the number 600851475143 ?
*
* output should be 6857
*/

#include <iostream>
#include <assert.h>
#include <math.h>

using namespace std;

int main() 
{
	long long int i;
	long long int n = 600851475143;

	/*
	* this mimics the sqrt solution
	* every integer n > 1 has a unique factorization as a product of prime numbers
	* sqrt of # + 1
	* square each number less than n than factor it out of n
	*/
	for (i = 2; i * i <= n; i++)
	{
		while (n % i == 0)
		{
			n /= i;
		}
	}

	cout << n;
}
