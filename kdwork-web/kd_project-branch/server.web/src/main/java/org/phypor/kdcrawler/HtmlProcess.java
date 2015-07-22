package org.phypor.kdcrawler;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.kd.server.beans.entity.InfoPlatformType;

/**
 * 
 * @author bojiehuang@163.com
 * 
 */
public class HtmlProcess {
	final static String str_begin = "<!DOCTYPE html><html><head><style type='text/css'>.f_center{text-align:center;}table{font-size:1px;border-collapse:collapse;border-spacing:0;background:#efefef;}th,td{font-size:1px;line-height:2em;}th{font-weight:bold;background:#ccc;font-size:1px;line-height:2em;}</style><meta http-equiv='Content-Type' content='text/html; charset=UTF-8'><meta name='viewport' content='width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no'><meta name='apple-mobile-web-app-capable' content='yes'><meta name='apple-mobile-web-app-status-bar-style' content='black'><meta http-equiv='X-UA-Compatible' content='IE=edge,chrome=1'><link rel='stylesheet' type='text/css' href='http://120.25.159.27:8888/static/n_style.css'></head><body class='from_day' id='content' style='background-color:#f6f6f6'><div id='container'><!--正文--><div><article><div class='font16' id='newsArticle'>";
	final static String str_end = "</div><span class='end_source' id='source'><a href='http://www.kdwork.com/'><img src='http://www.kdwork.com/resources/images/logo.png'  width='13' height='13'></a></span></div></article></div></body></html>";

	/**
	 * app
	 * 
	 * @param html
	 * @return
	 */
	public static String packet(String html) {
		return str_begin + html + str_end;
	}

	/**
	 * web
	 * 
	 * @return
	 * @throws IOException
	 */
	public static Document getHtmlModelDoc(String path) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(
				new FileInputStream(path), "UTF-8"));
		String str = null;
		String text = "";
		while ((str = br.readLine()) != null) {
			text += str;
		}
		br.close();
		if (text.equals(""))
			return null;
		return Jsoup.parse(text);
	}

}
