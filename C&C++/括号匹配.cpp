#include <vector>
#include <list>
#include <map>
#include <set>
#include <deque>
#include <queue>
#include <stack>
#include <bitset>
#include <algorithm>
#include <functional>
#include <cstdio>
#include <cstdlib>
#include <cstring>
#include <iostream>
using namespace std;

int main()
{
	int		n;
	stack<char>	st;
	char		tmp[10001];
	scanf( "%d", &n );
	for ( int i = 0; i != n; ++i )
	{
		scanf( "%s", tmp );
		int	index	= 0;
		int	flag	= 0;
		int	length	= strlen( tmp );
		while ( index <= length )
		{
			if ( tmp[index] == '[' || tmp[index] == '(' )
			{
				st.push( tmp[index] );
			}else if ( tmp[index] == ']' )
			{
				if ( !st.empty() && st.top() == '[' )
				{
					st.pop();
				}else{
					flag = 1;
					break;
				}
			}else if ( tmp[index] == ')' )
			{
				if ( !st.empty() && st.top() == '(' )
				{
					st.pop();
				} else{
					flag = 1;
					break;
				}
			}
			index++;
		}
		if ( flag == 0 && st.empty() )
			printf( "Yes\n" );
		else {
			printf( "No\n" );
			while ( !st.empty() )
			{
				st.pop();
			}
		}
	}
	return(0);
}
