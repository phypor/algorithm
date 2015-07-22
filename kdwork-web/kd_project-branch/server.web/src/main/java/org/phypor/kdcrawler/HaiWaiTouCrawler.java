package org.phypor.kdcrawler;

/**
 * 
 * @author bojiehuang@163.com
 * 
 */

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.phypor.algorithm.da.BloomFilter;

import java.io.IOException;

public class HaiWaiTouCrawler implements ISimpleCrawler {

	public HaiWaiTouCrawler(String[] seeds, String Tag) {
		BloomFilter filter = null; // = new SimpleBloomFilter(Tag);
		try {
			Document doc = Jsoup
					.connect(seeds[0])
					.userAgent(
							"Mozilla/5.0 (Windows NT 5.2) AppleWebKit/534.30 (KHTML, like Gecko) Chrome/12.0.742.122 Safari/534.30")
					.timeout(10000).get();
			Elements all = doc.select("tbody.preach-tbody");
			if (all.size() != 0) {
				for (Element e : all.select("tr")) {
					handle(filter, e);
				}

			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void handle(BloomFilter filter, Element e) {
		// TODO Auto-generated method stub

	}

}