import java.util.ArrayList;
import java.util.Scanner;

public class DivideAndConquerTest  {

	public static int MaxSum(ArrayList<Integer> array, int a, int b) {
		if (a == b) {
			return array.get(a);
		}
		int tmp = (a + b) / 2;
		int aa = MaxSum(array, a, tmp);
		int bb = MaxSum(array, tmp + 1, b);
		if (aa > bb)
			return aa;
		else
			return bb;
	}

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		int sum = sc.nextInt();
		ArrayList<Integer> array = new ArrayList<Integer>();
		for (int i = 0; i != sum; ++i) {
			array.add(sc.nextInt());
		}
		System.out.println(MaxSum(array, 0, sum - 1));
	}

}
