#include <iostream>
#include <cstring>
#include <stdlib.h>
#include <stdio.h>
using namespace std;

typedef struct trie_node
{
	int		count;
	struct trie_node* next[26];
}trie_node_;

void trie_insert( trie_node_* root, char* p )
{
	while ( *p )
	{
		int id = *p - 'a';
		if ( root->next[id] == NULL )
		{
			root->next[id] = new trie_node_();
		}
		root = root->next[id];
		root->count++;
		++p;
	}
}


int trie_search( trie_node_* root, char* p )
{
	while ( *p )
	{
		int id = *p - 'a';
		root = root->next[id];

		if ( root == NULL )
			return(0);
		++p;
	}

	return(root->count);
}


int main( void )
{
	trie_node_* root = new trie_node_();


	char	tmp[11];
	char	strs[100001][11];
	int	n = 0;
	scanf( "%d", &n );
	for ( int i = 0; i != n; ++i )
	{
		scanf( "%s", tmp );
		trie_insert( root, tmp );
	}
	int m = 0;
	scanf( "%d", &m );
	for ( int i = 0; i != m; ++i )
	{
		scanf( "%s", &strs[i] );
	}
	for ( int i = 0; i != m; ++i )
	{
		printf( "%d\n", trie_search( root, strs[i] ) );
	}
	return(0);
}



