package org.phypor.easycrawler;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.regex.Pattern;

/**
 * 
 * @author bojiehuang@163.com
 * @last_modified 2015-6-25下午6:47:50
 */
public class SingleCrawlerModel {
	private String Regex = "";
	private int waitTime = 10000;
	private boolean isSetRegex = false;

	protected void throwError(String msg) {
		throw new IllegalArgumentException(msg);
	}

	protected boolean checkRegex(String href) {
		if (isSetRegex == false) {// 忽略正则表达式
			return true;
		}
		return Pattern.compile(Regex).matcher(href).matches();
	}

	/**
	 * 默认10秒 10000
	 * 
	 * @param waitTime
	 */
	protected void setTimeOut(int waitTime) {
		this.waitTime = waitTime;
	}

	/**
	 * 获取html document类
	 * 
	 * @param href
	 * @return
	 */
	protected Document getHtmlDocument(String href) {
		try {
			return Jsoup
					.connect(href)
					.userAgent(
							"Mozilla/5.0 (Windows NT 5.2) AppleWebKit/534.30 (KHTML, like Gecko) Chrome/12.0.742.122 Safari/534.30")
					.timeout(waitTime).get();
		} catch (IOException e) {
			System.out.println("超时连接!");
		}
		return null;
	}

	/**
	 * 获取这个页面所有连接
	 * 
	 * @param href
	 * @return
	 */
	protected Set<String> getHtmlLinks(String href) {
		Set<String> links = new HashSet<String>();
		Elements elems = getHtmlDocument(href).select("a[href]");
		for (int i = 0; i != elems.size(); ++i) {
			href = elems.get(i).absUrl("href");
			if (href.equals("javascript:;") || href.equals("")
					|| href.equals("#") || href.equals("javascript:void(0);"))
				continue;
			links.add(href);
		}
		return links;
	}

	/**
	 * 获取这个页面所有连接
	 * 
	 * @param href
	 * @return
	 */
	protected Set<String> getHtmlLinks(Document doc) {
		Set<String> links = new HashSet<String>();
		Elements elems = doc.select("a[href]");
		for (int i = 0; i != elems.size(); ++i) {
			String href = elems.get(i).absUrl("href");
			if (href.equals("javascript:;") || href.equals("")
					|| href.equals("#") || href.equals("javascript:void(0);"))
				continue;
			links.add(href);
		}
		return links;
	}

	protected Map<String, Elements> getElementsByCSS(Document doc,
			String titieCSS, String pubdateCSS, String sourceCSS,
			String contentCSS) {
		Map<String, Elements> map = new HashMap<String, Elements>();
		String[] values = new String[] { titieCSS, pubdateCSS, sourceCSS,
				contentCSS };
		String[] keys = new String[] { "title", "pubdate", "source", "content" };
		for (int i = 0; i != 4; ++i) {
			Elements elems = doc.select(values[i]);
			if (elems.size() == 0)
				return null;
			map.put(keys[i], elems);
		}
		return map;
	}

	/**
	 * Regex为""的时候不过滤连接
	 * 
	 * @param Regex
	 * @param seeds
	 */
	public SingleCrawlerModel(String Regex, String seeds[]) {
		if (seeds.length == 0)
			throwError("种子url为空！");
		if (!Regex.equals(""))
			isSetRegex = true;
		this.Regex = Regex;
		for (int a = 0; a != seeds.length; ++a) {
			preHandle(seeds[a]);
			Set<String> links = getHtmlLinks(seeds[a]);
			Iterator<String> iter = links.iterator();
			while (iter.hasNext()) {
				String href = iter.next();
				if (checkRegex(href)) {
					handle(href);
				}
			}
		}
	}

	/**
	 * url处理后处理函数
	 * 
	 * @param seeds
	 */
	public void handle(String href) {
	}

	 

	/**
	 * url处理前执行的函数
	 * 
	 * @param seeds
	 */
	public void preHandle(String href) {
	}
}
