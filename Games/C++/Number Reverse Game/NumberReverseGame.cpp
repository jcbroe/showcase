#include <iostream>
#include <algorithm>
#include <functional>
#include <iterator>
#include <cstdlib>
#include <ctime>

using namespace std;

template <typename T, int size>
	bool isSorted(T(&array)[size])
	{
		return adjacent_find(array, array + size, greater<T>()) == array + size;
	}

int main()
{
	srand(time(0));

	int list[] = { 1, 2, 3, 4, 5, 6, 7, 8, 9 };
	int score = 0;

	while (isSorted(list))
	{
		random_shuffle(list, list + 9);
	}

	while (!isSorted(list))
	{
		cout << "Current List: ";
		copy(list, list + 9, ostream_iterator<int>(cout, " "));
		int rev;
		while (true)
		{
			cout << "Digits to reverse? ";
			cin >> rev;
			if (rev > 1 && rev < 10)
			{
				break;
			}
			cout << "Please enter a value between 2 and 9.";
		}
		++score;
		reverse(list, list + rev);
	}
	cout << "Congratulations, you sorted the list.\n" << "You needed " << score << " reversals." << endl;
	return 0;
}
	
