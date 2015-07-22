package org.phypor.kdcrawler;

/**
 * @author bojiehuang@163.com
 */
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.phypor.algorithm.da.BloomFilter;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class GanJiCrawlerUtils {
	public String getCityName(Document doc) {
		try {
			String tmp = doc.getElementsByClass("location").get(0).ownText();
			tmp = tmp.trim().replaceAll(Jsoup.parse("&nbsp;").text(), " ");
			return tmp.substring(0, tmp.indexOf(" "));
		} catch (Exception e) {
			return "";
		}

	}

	public Date getCreateTime(Document doc) {
		try {
			Calendar c = Calendar.getInstance();
			int y = c.get(Calendar.YEAR);
			SimpleDateFormat df = new SimpleDateFormat("yyyy MM-dd hh:mm");
			String tmp = doc.getElementsByClass("detail-meta").select("span")
					.get(0).text();
			return df.parse(y + " " + tmp.substring(tmp.indexOf("：") + 1));
		} catch (Exception e) {
			return null;
		}
	}

	public long getMeteringMode(Document doc) {
		try {
			String tmp = doc.getElementsByClass("fc-orange").get(0).text();
			if (tmp.contains("元/月")) {
				return 4;
			}
			if (tmp.contains("元/小时")) {
				return 1;
			}
			if (tmp.contains("元/天") || tmp.contains("元/日")) {
				return 2;
			}
			if (tmp.contains("元/年")) {
				return 5;
			}
		} catch (Exception e) {
			return 0;
		}
		return 4;
	}

	public int getDistrictID(Document doc) {
		return 0;
	}

	public String getPositonName(Document doc) {
		try {
			return doc.getElementsByClass("detail-describe").get(1).select("p")
					.get(2).ownText();
		} catch (Exception e) {
			return "";
		}
	}

	public String getTitle(Document doc) {
		try {
			return doc.getElementsByClass("detail-title").get(0).text();
		} catch (Exception e) {
			return "";
		}
	}

	public String getRecruitInfoFrom(Document doc) {
		try {
			return doc.getElementsByClass("detail-describe").select("a").get(0)
					.text();
		} catch (Exception e) {
			return "";
		}
	}

	public boolean getAudited(Document doc) {
		try {
			String tmp = doc.getElementsByClass("fc-green").get(0).text();
			if (tmp.indexOf("营业执照已验证") != -1) {
				return true;
			}
		} catch (Exception e) {
			return false;
		}
		return false;
	}

	public String getPhone(Document doc) {
		try {
			return doc.getElementsByClass("detail-describe").get(2).select("p")
					.get(1).ownText();
		} catch (Exception e) {
			return "";
		}
	}

	public String getCompanyName(Document doc) {
		try {
			return doc.getElementsByClass("detail-describe").select("a").get(0)
					.text();
		} catch (Exception e) {
			return "";
		}
	}

	public Map<String, String> getCompanyMsg(Document doc) {
		try {
			String url = "http://wap.ganji.com"
					+ doc.getElementsByClass("detail-describe").select("a")
							.get(0).attr("href");
			System.out.println(url);
			doc = Jsoup
					.connect(url)
					.userAgent(
							"Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.31 (KHTML, like Gecko) Chrome/26.0.1410.64 Safari/537.31")
					.timeout(5000).get();
			String intruduction = doc.getElementsByClass("detail-describe")
					.get(0).select("p").get(5).text().trim()
					.replaceAll("公司介绍：", "");
			String name = doc.getElementsByClass("detail-describe").get(0)
					.select("p").get(0).text().trim();
			String size = doc.getElementsByClass("detail-describe").get(0)
					.select("p").get(2).text().trim().replaceAll("公司规模：", "");
			String type = doc.getElementsByClass("detail-describe").get(0)
					.select("p").get(4).text().trim().replaceAll("公司类型：", "");
			String industry = doc.getElementsByClass("detail-describe").get(0)
					.select("p").get(3).text().trim().replaceAll("所属行业：", "");
			Map<String, String> map = new HashMap<String, String>();
			map.put("int", intruduction);
			map.put("size", size);
			map.put("type", type);
			map.put("ind", industry);
			map.put("name", name);
			return map;
		} catch (Exception e) {
			return null;
		}
	}

	public String getContact(Document doc) {
		try {
			return doc.getElementsByClass("detail-describe").get(2).select("p")
					.get(0).ownText();
		} catch (Exception e) {
			return "";
		}
	}

	public String getWorkDesc(Document doc) {
		try {
			String tmp = doc.getElementsByClass("detail-describe").get(3)
					.html();
			tmp = tmp.replaceAll("&.{2,5};|&#.{2,5};", " ");
			tmp = tmp.replaceAll("<a[^>]+>[^<]*</a>", "");
			tmp = tmp.replaceAll("<b[^>]+>[^<]*</b>", "");
			tmp = tmp.replaceAll("(?is)<form.*?>.*?</form>", "");
			tmp = tmp.replaceAll("(?is)<div.*?>.*?</div>", "");
			doc = Jsoup.parse(tmp);
			Elements elem_p = doc.select("p");
			for (int j = 0; j != elem_p.size(); ++j) {
				if (elem_p.get(j).html().contains("更多信息")) {
					elem_p.get(j).remove();
				}
				if (elem_p.get(j).html().contains("公司行业")) {
					elem_p.get(j).remove();
				}
				if (elem_p.get(j).html().contains("职位描述")) {
					elem_p.get(j).remove();
				}
				if (elem_p.get(j).html().contains("赶集网")) {
					elem_p.get(j).remove();
				}

			}
			return doc.html();
		} catch (Exception e) {
			return "";
		}
	}

	public String getWorkType(Document doc) {
		try {
			String tmp = doc.getElementsByClass("detail-describe").get(0)
					.select("p").get(1).ownText();
			return tmp.replaceAll(Jsoup.parse("&nbsp;").text(), " ");
		} catch (Exception e) {
			return "";
		}
	}

	public int getProvinceID() {
		return 19;
	}

	public String getWorkReq(Document doc) {
		try {
			String tmp = doc.getElementsByClass("detail-describe").get(1)
					.select("p").get(3).ownText();
			return tmp.replaceAll(Jsoup.parse("&nbsp;").text(), " ");
		} catch (Exception e) {
			return "";
		}
	}

	public String getRequset(Document doc) {
		try {
			String tmp = doc.getElementsByClass("detail-describe").get(1)
					.select("p").get(3).ownText();
			return tmp;
		} catch (Exception e) {
			return "";
		}

	}

	public int getNeedNum(Document doc) {
		try {
			String tmp = doc.getElementsByClass("detail-describe").get(1)
					.select("p").get(3).ownText();
			return Integer.parseInt(tmp.substring(0, tmp.indexOf("人")));
		} catch (Exception e) {
			return 100;
		}
	}

	public String getDetailedAddress(Document doc) {
		try {
			String tmp = doc.getElementsByClass("detail-describe").get(1)
					.select("p").get(4).ownText();

			return tmp.replaceAll(Jsoup.parse("&nbsp;").text(), " ");
		} catch (Exception e) {
			return "";
		}
	}

	public String getSalaryDay(Document doc) {
		try {
			String tmp = doc.getElementsByClass("fc-orange").get(0).text();
			if (tmp.contains("元/"))
				return tmp.substring(0, tmp.indexOf("元/"));
			else {
				return tmp.substring(0, tmp.indexOf("元"));
			}
		} catch (Exception e) {
			return "0";
		}
	}

	public int getWorkTime(Document doc) {
		try {
			String tmp = doc.getElementsByClass("my-freeTime").get(0)
					.select("td").text();
			int total = 0;
			for (int i = 0; i != tmp.length(); ++i) {
				if (tmp.charAt(i) == '√') {
					++total;
				}
			}
			return total;
		} catch (Exception e) {
			return 5;
		}
	}

	public void handle(BloomFilter filter, Element e) {
		String url = e.select("a").get(0).absUrl("href");
		try {
			Document doc = Jsoup
					.connect(url)
					.userAgent(
							"Mozilla/5.0 (Windows NT 5.2) AppleWebKit/534.30 (KHTML, like Gecko) Chrome/12.0.742.122 Safari/534.30")
					.timeout(10000).get();
			System.out.println("1:  " + getWorkDesc(doc));
			// System.out.println(JZName + JZCity + JZDis);
			// System.out.println(getTitle(doc));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
}
