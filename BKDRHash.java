import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class BKDRHash {
	public static int seed = 31;

	public static int getHashCode(String str) {
		int hash = 0;
		for (int i = 0; i != str.length(); ++i) {
			hash = seed * hash + str.charAt(i);
		}
		return hash;
	}

	public static void main(String[] args) {
		int length = 1000000;
		String str1 = "2015-11-17 19:29:42";
		System.out.println("str1 :" + str1.hashCode() + " system");
		System.out.println("str1 :" + getHashCode(str1) + " custom");

		Map<String, String> map = new HashMap<String, String>();
		for (int i = 0; i != length; ++i) {
			String str = UUID.randomUUID().toString();
			map.put(getHashCode(str) + "", str);
		}
		System.out.println("Conflict number ： " + (length - map.size()));
	}

}
