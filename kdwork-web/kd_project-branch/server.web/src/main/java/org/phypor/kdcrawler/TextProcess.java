package org.phypor.kdcrawler;

/**
 * 
 * @author bojiehuang@163.com
 * 
 */
public class TextProcess {

	public static String preProcess(String html) {// 消去噪声
		html = html.replaceAll("(?is)<iframe.*?>.*?</iframe>", "");
		html = html.replaceAll("(?is)<object.*?>.*?</object>", "");
		html = html.replaceAll("(?is)<!--.*?-->", ""); // 去除注释
		html = html.replaceAll("(?is)<script.*?>.*?</script>", ""); // 去除JavaScript
		html = html.replaceAll("(?is)<style.*?>.*?</style>", ""); // 去除css属性
		// html = html.replaceAll("<(?!img|p|div|/p).*?>", ""); // (?is)<.*?>
		return html;
	}

}
