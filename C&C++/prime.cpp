#include <bits/stdc++.h>
using namespace std;
#define rep( i, a, b )	for ( int i = a; i < b; i++ )
#define per( i, a, b )	for ( int i = b - 1; i >= a; i-- )
#define clr( arr, val ) memset( arr, val, sizeof(arr) )
#define scanf_d( x )	scanf( "%d", &x )
#define val3( a, b, c ) (a + b + c)
#define N 200001
int	table[N];
int	n = 0;

int main()
{
	scanf_d( n );
	rep( i, 2, N )
	{
		if ( !table[i] )
			for ( int j = 2 * i; j < N; j = j + i )
			{
				table[j] = 1;//标志非素数 
			}
	}
	rep( i, 2, n + 1 )
	{
		if ( i == 2 )
			printf( "2 " );
		else{
			if ( !table[i] )
				printf( "%d ", i );
		}
	}
	return(0);
}
