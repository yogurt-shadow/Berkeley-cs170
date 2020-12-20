#include<iostream>
using namespace std;

int sum(int a[], int length) {
	int sum = 0;
	for (int i = 0; i < length; i++) {
		sum += a[i];
	}
	return sum;
}

int* sum2(int a[], int length) {
	static int p[10];
	for (int i = 0; i < length; i++) {
		p[i] = a[i] + 1;
	}
	return p;
}

void copy(int a[], int b[], int start1, int start2, int length) {
	for (int i = 0; i < length; i++) {
		b[start2 + i] = a[start1 + i];
	}
}

int main() {
	int a[] = { -2, 1, -3, 4, -1, 2, 1, -5, 4 };
	int a_length = sizeof(a) / sizeof(a[0]);
	int* p;
	p = sum2(a, a_length);
	for (int i = 0; i < a_length; i++) {
		cout << *(p + i) << " ";
	}

	return 0;
}