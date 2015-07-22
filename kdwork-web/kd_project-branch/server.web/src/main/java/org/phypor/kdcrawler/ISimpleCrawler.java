package org.phypor.kdcrawler;

/**
 * 
 * @author bojiehuang@163.com
 * 
 */
import org.jsoup.nodes.Element;
import org.phypor.algorithm.da.BloomFilter;

public interface ISimpleCrawler {
	public void handle(BloomFilter filter, Element e);
}
