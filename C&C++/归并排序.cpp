#include <bits/stdc++.h>
using namespace std;
#define rep( i, a, b )	for ( int i = a; i < b; i++ )
#define per( i, a, b )	for ( int i = b - 1; i >= a; i-- )
#define clr( ar, v )	memset( ar, v, sizeof(ar) )
#define scanf_d( x )	scanf( "%d", &x )
#define val3( a, b, c ) (a + b + c)
int tmp[100];
int mergesort( int ar[], int low, int mid, int high )
{
	int	i	= low;
	int	j	= mid + 1;
	int	k	= 0;
	while ( i <= mid && j <= high )
	{
		if ( ar[i] > ar[j] )
			tmp[k++] = ar[i++];
		else
			tmp[k++] = ar[j++];
	}
	while ( i <= mid )
		tmp[k++] = ar[i++];

	while ( j <= high )
		tmp[k++] = ar[j++];

	rep( i, 0, k ) ar[low + i] = tmp[i];
}


int merge( int ar[], int low, int high )
{
	int mid = (low + high) >> 1;
	if ( high > low )
	{
		merge( ar, low, mid );
		merge( ar, mid + 1, high );
		mergesort( ar, low, mid, high );
	}
}


int main()
{
	int ar[] = { 123, 321, 3, 10, 3213, 1000, 12, 10 };
	merge( ar, 0, sizeof(ar) / sizeof(int) - 1 );
	rep( i, 0, sizeof(ar) / sizeof(int) )
	{
		printf( "%d ", ar[i] );
	}
	return(0);
}



