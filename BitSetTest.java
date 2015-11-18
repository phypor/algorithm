import java.util.BitSet;

public class BitSetTest {

	public static int getMax(int[] sum) {
		if(sum == null || sum.length == 0){
			throw new IllegalArgumentException("the array should be not null");
		}
		BitSet bs = new BitSet();
		System.out.println("default size: "+bs.size());
		for (int i = 0; i != sum.length; ++i) {
			bs.set(sum[i]);
		}
		System.out.println("real size: "+bs.size());
		for (int i = bs.size() - 1; i >= 0; --i) {
			if (bs.get(i) == true) {
				System.out.println("max num :" + i);
				return i;
			}
		}
		return -1;
	}

	public static void main(String[] args) {
		int[] sum = new int[] { 7, 18, 3, 123, 2118, 222 };
		getMax(sum);
	}

}
