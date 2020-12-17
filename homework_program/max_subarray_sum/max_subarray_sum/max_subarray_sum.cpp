#include<iostream>
using namespace std;

int sum(int a[], int length) {
	int sum = 0;
	for (int i = 0; i < length; i++) {
		sum += a[i];
	}
	return sum;
}

void copy(int a[], int b[], int start1, int start2, int length) {
	for (int i = 0; i < length; i++) {
		b[start2 + i] = a[start1 + i];
	}
}

int main() {
	int a[] = { -2, 1, -3, 4, -1, 2, 1, -5, 4 };
	int a_length = sizeof(a) / sizeof(a[0]);
	int b[5];
	copy(a, b, 0, 0, 5);
	cout << a_length<< endl;
	cout << sum(a, a_length) << endl;
	cout << sum(b, 5) << endl;

	return 0;
}