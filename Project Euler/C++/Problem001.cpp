/*
* If we list all the natural numbers below 10 that are multiples of 3 or 5,
* we get 3, 5, 6 and 9. The sum of these multiples is 23.
* Find the sum of all the multiples of 3 or 5 below 1000.
*
* output should be 233168
*/

#include <iostream>

using namespace std;

int main()
{
	int total = 0;
	for (int i = 0; i < 1000; i++)
	{
		/*traditional
		if ((i % 3 == 0) or (i % 5 == 0))
		{
			total += i;
		}*/
		// now with more elvis
		total += ((i % 3 == 0) || (i % 5 == 0)) ? i : 0;
	}

	cout << total;
}
