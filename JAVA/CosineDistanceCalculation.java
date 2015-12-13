import org.apache.commons.lang.StringUtils;

/**
 * 
 * @author bojiehuang@163.com
 * 
 */
public class CosineDistanceCalculation {

	public static double getDistance(String str1, String str2) {
		if (StringUtils.isBlank(str1) || StringUtils.isBlank(str2)
				|| (str1.length() != str1.length()))
			return -1;
		double numerator = 0;
		double denominator = 0;
		double tmp1 = 0;
		double tmp2 = 0;
		for (int i = 0; i != str1.length(); ++i) {
			double a = str1.charAt(i) - '0';
			double b = str2.charAt(i) - '0';
			numerator += a * b;
			tmp1 += a * a;
			tmp2 += b * b;
		}
		denominator = Math.sqrt(tmp1) * Math.sqrt(tmp2);
		return numerator / denominator;
	}

	public static void main(String[] args) throws Exception {
		String str1 = "5030200200";
		String str2 = "3020110101";
		System.out.println(getDistance(str1, str2));
	}
}
