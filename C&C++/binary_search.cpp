#include <stdio.h>

/**
return index
*/
int binary_search(int *p,int n,int key){
	int low = 0;
	int hight = n - 1;
	if(hight < low) return -1;
	while(hight >= low){
		int mid = low +((hight-low)>>1);
		printf("step : %d\n",mid);
		if(*(p+mid) > key){
			hight=mid-1;
		}else if(*(p+mid) < key){
			low= mid+1; 
		}else if(*(p+mid) == key){
			return mid;
		}
	}
	return -1;
} 

int main(void){
	int a[]={1,3,10,100,200,1000,2550,213321,213213213,22323132133};
	printf("%d",binary_search(a,10,22323132133));
	return 0;
}
