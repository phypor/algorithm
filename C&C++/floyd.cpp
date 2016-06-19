#include <stdio.h>
#include <math.h>

int main()
{
	int	n	= 0;
	int	m	= 0;
	scanf( "%d%d", &n, &m );
	int arr[n + 1][n + 1];

	for ( int i = 1; i <= n; ++i )
	{
		for ( int j = 1; j <= n; ++j )
		{
			if ( i == j )
				arr[i][j] = 0;
			else
				arr[i][j] = 9999999;
		}
	}


	for ( int i = 0; i != m; ++i )
	{
		int a, b, c;
		scanf( "%d%d%d", &a, &b, &c );
		if ( a != b )
		{
			if ( c < arr[a][b] )
			{
				arr[a][b] = arr[b][a] = c;
			}
		}
	}

	for ( int k = 1; k <= n; k++ )
		for ( int i = 1; i <= n; i++ )
			for ( int j = 1; j <= n; j++ )
			{
				int tmp = arr[i][k] + arr[k][j];
				if ( tmp < arr[i][j] )
				{
					arr[i][j] = tmp;
				}
			}
	for ( int i = 1; i <= n; ++i )
	{
		for ( int j = 1; j <= n; ++j )
		{
			printf( "%d ", arr[i][j] );
		}
		printf( "\n" );
	}
	return(0);
}
