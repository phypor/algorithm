

import java.util.Arrays;

public class SortMaxHeapTest {
	
	  protected static int heapsize;

	  public static void swapElements(double[] arr, int index1, int index2) {  
	        double temp = arr[index1];  
	        arr[index1] = arr[index2];  
	        arr[index2] = temp;  
	    }  
	
	protected static int parent(int i) {
		return (i - 1) / 2;
	}

	protected static int left(int i) {
		return 2 * i + 1;
	}

	protected static int right(int i) {
		return 2 * i + 2;
	}

	public static void sortMaxHeap(double[] arr) {
		makeMaxHeap(arr);
		int step = 1;
		for (int i = arr.length - 1; i > 0; i--) {
			swapElements(arr, i, 0);
			System.out.println("Step: " + (step++) + Arrays.toString(arr));
			heapsize--;
			maxHeapKeeper(arr,0);
		}
	}

	protected static void maxHeapKeeper(double[] arr,int i) {
		int l = left(i);
		int r = right(i);
		int largest = i;
		if (l <= heapsize - 1 && arr[l] > arr[i])
			largest = l;
		if (r <= heapsize - 1 && arr[r] > arr[largest])
			largest = r;
		if (largest != i) {
			swapElements(arr, i, largest);
			maxHeapKeeper(arr,largest);
		}
	}

	public static void makeMaxHeap(double[] arr) {
		heapsize = arr.length;
		for (int i = parent(heapsize - 1); i >= 0; i--)
			maxHeapKeeper(arr,i);
	}
	
	public static void main(String[] args) {
		double arr[]={ 10,320,10,3,2,-1,-0.55,-100 };
		sortMaxHeap(arr);
		System.out.println("Result: " + Arrays.toString(arr));
	}
}