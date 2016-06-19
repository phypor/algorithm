public class TFIDF {

	/**
	 * 题目： 假设文档集里面有1028576篇文章。 某词汇w在其中1024篇文章出现。
	 * 词汇w所在的文章出现的频率为10，所在文章的最大的词汇频率为20。 则词汇w的TF*IDF的值为？
	 */

	public static double tf(double f, double maxf) {
		return f / maxf;
	}

	public static double idf(double n, double N) {
		return Math.log(N / n) / Math.log(2);
	}

	public static void main(String[] args) {
		System.out.println(idf(1024, 1048576) * tf(10, 20));
	}

}
