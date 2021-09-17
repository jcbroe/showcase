/*
* A palindromic number reads the same both ways.
* The largest palindrome made from the product of two 2-digit numbers is 9009 = 91 × 99.
* Find the largest palindrome made from the product of two 3-digit numbers.
* Output should be 906609.
*/

#include <iostream>
#include <string>

using namespace std;

int main()
{
	int largestProduct = 0;
	//for loops go through all 3 digit numbers
	for (int i = 100; i < 1000; i++)
	{
		for (int j = 100; j < 1000; j++)
		{
			//product of two 3 digit numbers.
			int currentProduct = i * j;
			//cast to string to use cool stuff in the string library.
			string currentProductString = to_string(currentProduct);
			//The range between string::rbegin and string::rend contains all the characters of the string (in reverse order).
			//if string = reverse string and the current product is larger, set largest product to display at the end.
			if (currentProductString == string(currentProductString.rbegin(), currentProductString.rend()) &&
				currentProduct > largestProduct)
			{
				largestProduct = currentProduct;
			}
		}
	}
	cout << largestProduct;
}
