/*
* 2520 is the smallest number that can be divided by each of the numbers from 1 to 10 without any remainder.
* What is the smallest positive number that is evenly divisible by all of the numbers from 1 to 20?
*/

#include <iostream>

using namespace std;

//function to find the greatest common denominator - I believe this is available as part of the standard library in a later version of C++, include numeric or algorithm?
int gcd(int a, int b)
{
	if (b == 0)
	{
		return a;
	}
	//recursive gcd, this is the way
	return gcd(b, a % b);
}

int main()
{
	//start at 1 so that multiplication works
	int total = 1;
	for (int i = 1; i < 21; i++) //1 to 20
	{
		total *= i / gcd(i, total);
	}

	cout << total;
}
