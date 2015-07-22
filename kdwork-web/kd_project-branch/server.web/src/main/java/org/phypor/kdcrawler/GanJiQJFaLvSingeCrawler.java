package org.phypor.kdcrawler;

import org.phypor.easycrawler.SingleCrawlerModel;

public class GanJiQJFaLvSingeCrawler extends SingleCrawlerModel {

	protected String JobDis;

	public GanJiQJFaLvSingeCrawler(String Regex, String[] seeds) {
		super(Regex, seeds);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void preHandle(String href) {
		if (href.contains("tianhe")) {
			JobDis = "天河";
		}
		if (href.contains("yuexiu")) {
			JobDis = "越秀";
		}
		if (href.contains("haizhu")) {
			JobDis = "海珠";
		}
		if (href.contains("baiyun")) {
			JobDis = "白云";
		}
		if (href.contains("liwan")) {
			JobDis = "荔湾";
		}
		if (href.contains("fanyu")) {
			JobDis = "番禺";
		}
		if (href.contains("huangpu")) {
			JobDis = "黄埔";
		}
		if (href.contains("huadu")) {
			JobDis = "花都";
		}
		if (href.contains("luogang")) {
			JobDis = "萝岗";
		}
		if (href.contains("nansha")) {
			JobDis = "南沙";
		}
		if (href.contains("zengcheng")) {
			JobDis = "增城 ";
		}
		if (href.contains("conghua")) {
			JobDis = "从化";
		}
		if (href.contains("nansha")) {
			JobDis = "南沙";
		}
	}

}
