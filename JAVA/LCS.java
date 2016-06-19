import java.util.Scanner;

public class LCS {

	public static void main(String[] args) {

		Scanner sc = new Scanner(System.in);
		while (sc.hasNext()) {
			String n[] = new String[2];
			for (int i = 0; i != 2; ++i) {
				n[i] = sc.next();
			}
			int[][] reslut = new int[n[0].length() + 1][n[1].length() + 1];
			for (int i = 1; i <= n[0].length(); ++i) {
				for (int j = 1; j <= n[1].length(); ++j) {
					if (n[0].charAt(i - 1) == n[1].charAt(j - 1)) {
						reslut[i][j] = reslut[i - 1][j - 1] + 1;
					} else {
						reslut[i][j] = Math.max(reslut[i - 1][j],
								reslut[i][j - 1]);
					}
				}
			}

			System.out.println(reslut[n[0].length()][n[1].length()]);
		}
	}
}
