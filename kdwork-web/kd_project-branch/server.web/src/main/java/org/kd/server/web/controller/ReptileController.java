package org.kd.server.web.controller;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Positions;

import org.apache.shiro.authz.annotation.RequiresRoles;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.kd.server.beans.entity.CompanyNature;
import org.kd.server.beans.entity.Enterprise;
import org.kd.server.beans.entity.EnterpriseType;
import org.kd.server.beans.entity.Industry;
import org.kd.server.beans.entity.InfoPlatformType;
import org.kd.server.beans.entity.Infomation;
import org.kd.server.beans.entity.MeteringMode;
import org.kd.server.beans.entity.Position;
import org.kd.server.beans.entity.ProvinceCityArea;
import org.kd.server.beans.entity.QuantityScale;
import org.kd.server.beans.entity.RecruitInfo;
import org.kd.server.beans.entity.Section;
import org.kd.server.beans.vo.QueryCondition;
import org.kd.server.common.util.JsonUtil;
import org.kd.server.common.util.LogRecord;
import org.kd.server.common.util.MD5;
import org.kd.server.service.CompanyNatureService;
import org.kd.server.service.EnterpriseService;
import org.kd.server.service.EnterpriseTypeService;
import org.kd.server.service.IndustryService;
import org.kd.server.service.InfomationService;
import org.kd.server.service.MeteringModeService;
import org.kd.server.service.PositionService;
import org.kd.server.service.ProvinceCityAreaService;
import org.kd.server.service.QuantityScaleService;
import org.kd.server.service.RecruitInfoService;
import org.kd.server.service.SectionService;
import org.phypor.algorithm.da.BloomFilter;
import org.phypor.algorithm.da.SimHash;
import org.phypor.algorithm.da.UUID64;
import org.phypor.http.HttpPost;
import org.phypor.kdcrawler.Edu163SingeCrawler;
import org.phypor.kdcrawler.GanJIJZCrawler;
import org.phypor.kdcrawler.GanJIQJGameSingeCrawler;
import org.phypor.kdcrawler.GanJiCrawlerUtils;
import org.phypor.kdcrawler.GanJiQJKuaiJISingeCrawler;
import org.phypor.kdcrawler.GanJiQJSoftWareSingeCrawler;
import org.phypor.kdcrawler.GanJiQJXiaoShouSingeCrawler;
import org.phypor.kdcrawler.HtmlProcess;
import org.phypor.kdcrawler.TextProcess;
import org.phypor.kdcrawler.UnivsSingeCrawler;
import org.phypor.kdcrawler.XuanjianghuiSingeCrawler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

import freemarker.template.MalformedTemplateNameException;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateNotFoundException;

/**
 * 
 * @author bojie
 * 
 */
@Controller
public class ReptileController {
	
	@Resource(name = "recruitInfoService")
	private RecruitInfoService recruitInfoService;
	@Resource(name = "infomationService")
	private InfomationService infomationService;
	@Resource(name = "companyNatureService")
	private CompanyNatureService companyNatureService;
	@Resource(name = "enterpriseService")
	private EnterpriseService enterpriseService;
	@Resource(name = "meteringModeService")
	private MeteringModeService meteringModeService;
	@Resource(name = "provinceCityAreaService")
	private ProvinceCityAreaService provinceCityAreaService;
	@Resource(name = "positionService")
	private PositionService positionService;
	@Resource(name = "sectionService")
	private SectionService sectionService;
	@Resource(name = "industryService")
	private IndustryService industryService;
	@Resource(name = "enterpriseTypeService")
	EnterpriseTypeService enterpriseTypeService;
	@Resource(name = "quantityScaleService")
	QuantityScaleService quantityScaleService;

	@Autowired
	HttpServletRequest request;
	@Autowired
	HttpServletResponse response;
	 
	@ResponseBody
	@RequestMapping("/test")
	public String test()   {
			return "xxx";
	}

	  
	public void writeNewsData(Map<String, String> map, File fileName) {
		if (!fileName.exists())
			try {
				fileName.setWritable(true, false);
				fileName.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		else {
			return;
		}
		String json = "";
		try {
			json = JsonUtil.bean2Json(map);
			LogRecord.info(json);
			FileWriter fw = null;
			BufferedWriter writer = null;
			try {
				fw = new FileWriter(fileName);
				writer = new BufferedWriter(fw);
				writer.write(json);
				writer.newLine();
				writer.flush();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				throw new IllegalArgumentException("无法创建！！！文件");
			} finally {
				try {
					if (writer != null) {
						writer.close();
					}
					if (fw != null) {
						fw.close();
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public Map<String, String> readNewsData(File fileName) {
		if (!fileName.exists())
			return null;
		String text = "";
		Map<String, String> retMap = new HashMap<String, String>();
		FileReader fileReader = null;
		BufferedReader bufferedReader = null;
		try {
			fileReader = new FileReader(fileName);
			bufferedReader = new BufferedReader(fileReader);
			try {
				String read = "";
				while ((read = bufferedReader.readLine()) != null) {
					text += read;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (bufferedReader != null) {
				try {
					bufferedReader.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if (fileReader != null) {
				try {
					fileReader.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		System.out.println("读取出来的文件内容是：" + "\r\n" + retMap.toString());
		try {
			retMap = JsonUtil.json2Bean(text, Map.class);
		} catch (JsonParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return retMap;
	}

	public String getNewsHtmlModel() throws IOException {
		String path = this.getClass().getClassLoader()
				.getResource("/htmlModel.txt").getPath();

		BufferedReader br = new BufferedReader(new InputStreamReader(
				new FileInputStream(path), "UTF-8"));
		String str = null;
		String text = "";
		while ((str = br.readLine()) != null) {
			text += str;
		}
		br.close();
		return text;
	}

	@ResponseBody
	@RequestMapping("/news")
	public String news(@RequestParam(defaultValue = "0") String id) {
		if(id.length() > 10){//兼容旧版的news id的访问
			String html = "";
			try {
				html = getNewsHtmlModel();
			} catch (IOException e) {
				e.printStackTrace();
			}
			Map<String,String> map = readNewsData(new File("/kdnewsdata/"+id+".data"));
			html = html.replace("{$title}",map.get("title"));
			html = html.replace("{$pubdate}", map.get("pubdate"));
			html = html.replace("{$source}", map.get("source"));
			html = html.replace("{$article}", map.get("content"));
			try {
				return new String(html.getBytes("UTF-8"), "8859_1");
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		long l_id =  Long.valueOf(id).longValue(); 
		if (l_id == 0)return "error";
		Infomation infomation = null;
		try {
			infomation = infomationService.getById(Infomation.class, l_id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(infomation == null) return "error !";
		String html = "";
		try {
			html = getNewsHtmlModel();
		} catch (IOException e) {
			e.printStackTrace();
		}
		html = html.replace("{$title}",infomation.getTitle());
		html = html.replace("{$pubdate}", infomation.getCreateTime().toLocaleString());
		html = html.replace("{$source}", infomation.getMediaPlatform());
		html = html.replace("{$article}", infomation.getContent());
		try {
			return new String(html.getBytes("UTF-8"), "8859_1");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return "error !";
	}

	@RequestMapping("/kd_JianJi")
	public void kd_JianJi() {
		String seeds[] = { "http://wap.ganji.com/gz/parttime_wanted/",
				"http://wap.ganji.com/sz/parttime_wanted/",
				"http://wap.ganji.com/dg/parttime_wanted/" };
		final long sessionid = 1;
		new GanJIJZCrawler(seeds, "kd_jz_job") {

			@Override
			public void handle(BloomFilter filter, Element e) {
				try {
					String visitedurl = e.select("a").first().absUrl("href");
					System.out.println(visitedurl);
					Document doc = null;
					doc = Jsoup
							.connect(visitedurl)
							.userAgent(
									"Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.31 (KHTML, like Gecko) Chrome/26.0.1410.64 Safari/537.31")
							.timeout(5000).get();

					String title = getTitle(doc);
					String phone = getPhone(doc);
					String detailedAddress = getDetailedAddress(doc);
					String contact = getContact(doc);

					ProvinceCityArea district = null;

					district = (ProvinceCityArea) provinceCityAreaService
							.findByName(JZDis);

					ProvinceCityArea city = district.getProvinceCityArea();
					ProvinceCityArea province = city.getProvinceCityArea();
					Map<String, String> companyMsg = getCompanyMsg(doc);

					if (!title.equals("") && !phone.equals("")
							&& !detailedAddress.equals("")
							&& companyMsg != null) {
						RecruitInfo recruitInfo = new RecruitInfo();

						String enterpriseDesc = companyMsg.get("int");
						String com_name = companyMsg.get("name");

						// ----------------------------------------------
						String enterpriseHash = getEnterpriseHash(com_name,
								detailedAddress, enterpriseDesc);
						Enterprise enterprise = new Enterprise();
						enterprise.setHash(enterpriseHash);
						enterprise.setContacts(contact);
						enterprise.setDetailedAddress(detailedAddress);
						enterprise.setCity(city);
						enterprise.setEnterpriseDesc(enterpriseDesc);
						enterprise.setDistrict(district);
						enterprise.setProvince(province);
						enterprise.setPhone(phone);
						enterprise.setName(com_name);
						String strEnterpriseTye = companyMsg.get("type");
						if (strEnterpriseTye.equals("民营 ")) {
							strEnterpriseTye = "民营企业";
						}
						if (strEnterpriseTye.equals("国企  ")) {
							strEnterpriseTye = "国有企业";
						}
						if (strEnterpriseTye.equals("国企  ")) {
							strEnterpriseTye = "国有企业";
						}
						EnterpriseType enterpriseType = enterpriseTypeService
								.findByName(strEnterpriseTye);
						if (enterpriseType == null) {// 企业性质
							enterpriseType = enterpriseTypeService
									.findByName("其他");
						}
						enterprise.setEnterpriseType(enterpriseType);

						Industry industry = industryService
								.findByName(companyMsg.get("ind"));
						if (null == industryService.findByName(companyMsg
								.get("ind"))) {
							industry = industryService.findByName("其他行业");
						}
						enterprise.setIndustry(industry);

						String strSize = companyMsg.get("size");
						strSize = strSize.replace("以上", "");
						strSize = strSize.replace("以下", "");
						strSize = strSize.replace("人", "");
						System.out.println(strSize);
						String tmp[] = strSize.split("-");
						int size = 0;
						if (tmp.length == 0) {
							size = Integer.parseInt(strSize);
						} else {
							for (int i = 0; i != tmp.length; ++i) {
								size += Integer.parseInt(tmp[i]);
							}
							size = size / tmp.length;
						}
						boolean isCheck = false;
						if (size < 20 && !isCheck) {
							strSize = "0-20";
							isCheck = true;
						}
						if (size < 50 && !isCheck) {
							strSize = "21-50";
							isCheck = true;
						}
						if (size < 100 && !isCheck) {
							strSize = "51-100";
							isCheck = true;
						}
						if (size < 300 && !isCheck) {
							strSize = "101-300";
							isCheck = true;
						}
						if (size < 500 && !isCheck) {
							strSize = "301-500";
							isCheck = true;
						}
						if (size < 1000 && !isCheck) {
							strSize = "501-1000";
							isCheck = true;
						}
						if (size < 3000 && !isCheck) {
							strSize = "1001-3000";
							isCheck = true;
						}
						if (size < 5000 && !isCheck) {
							strSize = "3001-5000";
							isCheck = true;
						}
						if (size < 10000 && !isCheck) {
							strSize = "5001-10000";
							isCheck = true;
						}
						if (size > 10000 && !isCheck) {
							strSize = "10000以上";
							isCheck = true;
						}
						QuantityScale quantityScale = quantityScaleService
								.getByName(strSize);
						enterprise.setQuantityScale(quantityScale);
						Enterprise tmpEnterprise = enterpriseService
								.findByHash(enterpriseHash);
						if (tmpEnterprise == null)
							enterpriseService.save(enterprise);
						else {
							enterprise.setId(tmpEnterprise.getId());
							enterpriseService.update(enterprise);
						}
						// ----------------------------------------------

						recruitInfo.setAudited(getAudited(doc));
						recruitInfo.setSection((Section) sectionService
								.getById(Section.class, sessionid));

						if (JZName.equals("传单派发")) {
							JZName = "派单";
						}
						if (JZName.equals("手工制作")) {
							JZName = "手工";
						}
						if (JZName.equals("问卷调查")) {
							JZName = "问卷";
						}
						if (JZName.equals("服务员")) {
							JZName = "服务";
						}
						if (JZName.equals("摄影师")) {
							JZName = "摄影";
						}
						if (JZName.equals("设计制作")) {
							JZName = "设计";
						}
						if (JZName.equals("促销员")) {
							JZName = "市场";
						}

						Position position = positionService.getByName(JZName);
						if (null == position) {
							position = positionService.getByName("其他");
						}

						recruitInfo.setPosition(position);
						recruitInfo.setDistrict(district);
						recruitInfo.setCity(city);
						recruitInfo.setProvince(province);
						recruitInfo.setContacts(contact);
						recruitInfo.setCreateTime(getCreateTime(doc));
						recruitInfo.setDetailedAddress(detailedAddress);
						recruitInfo.setRequests(getRequset(doc));
						recruitInfo
								.setMeteringMode((MeteringMode) meteringModeService
										.getById(MeteringMode.class,
												getMeteringMode(doc)));
						String recruitHash = getRecruitInfoHash(title,
								getWorkDesc(doc));
						System.out.println(recruitHash);
						recruitInfo.setHash(recruitHash);
						recruitInfo.setPhone(phone);
						recruitInfo.setSalaryDay(getSalaryDay(doc));
						recruitInfo.setTitle(title);
						recruitInfo.setWorkDesc(getWorkDesc(doc));
						recruitInfo.setWorkingWeek(getWorkTime(doc));
						recruitInfo.setNeedNum(getNeedNum(doc));
						recruitInfo.setEnterprise(enterprise);
						recruitInfo.setInfoPlatformType(infomationService
								.getById(InfoPlatformType.class, 5L));
						recruitInfo.setRefreshTime(getCreateTime(doc));
						RecruitInfo tmpRecruitInfo = recruitInfoService
								.findByHash(recruitHash);
						if (null == tmpRecruitInfo) {
							recruitInfoService.save(recruitInfo);
						} else {
							recruitInfo.setId(tmpRecruitInfo.getId());
							recruitInfoService.update(recruitInfo);
						}
					}
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}

		};

	}

	/**
	 * 不公开接口
	 */
	@RequestMapping("/Edu163News")
	public void Edu163News() {
		Calendar cal = Calendar.getInstance();
		int year = cal.get(Calendar.YEAR);
		int mouth = cal.get(Calendar.MONTH) + 1;
		int day = cal.get(Calendar.DATE);
		String str_day = day + "";
		String str_mouth = mouth + "";
		if (day < 10)
			str_day = "0" + day;
		if (mouth < 10)
			str_mouth = "0" + mouth;
		String str_year = (year + "").substring(2);
		new Edu163SingeCrawler("http://daxue.163.com/" + str_year + "/"
				+ str_mouth + str_day + "/\\d+/\\w+.*.html",
				new String[] { "http://daxue.163.com/" }) {
			@Override
			public void handle(String href) {
				Document page = getHtmlDocument(href);

				Set<String> urlSet = getHtmlLinks(page);
				Iterator<String> iter = urlSet.iterator();
				String tmp_url = href.replaceAll(".html", "_");
				boolean isExsitOtherPage = false;
				while (iter.hasNext() && isExsitOtherPage == false) {
					String s = iter.next();
					if (s.contains(tmp_url)) {
						isExsitOtherPage = true;
					}
				}
				if (isExsitOtherPage) {
					System.out.println("存在分页！");
					href = tmp_url + "all.html";
				} else
					System.out.println("不存在分页！");

				Map<String, Elements> map = getElementsByCSS(
						getHtmlDocument(href), "h1#h1title",
						"div.ep-time-soure.cDGray", "a#ne_article_source",
						"div#endText");
				if (map != null) {
					String title = map.get("title").first().text().trim();
					title = title.replaceAll("（全文）", "");
					String content = TextProcess.preProcess(map.get("content")
							.first().html());
					if (map.get("content").first().text().trim().equals(""))
						return;
					String tmp[] = map.get("pubdate").first().ownText().trim()
							.split(" ");
					String pubdate = (tmp[0] + " " + tmp[1]).replaceAll("来源:",
							"").trim();

					SimpleDateFormat df = new SimpleDateFormat(
							"yyyy-MM-dd hh:mm:ss");
					String source = map.get("source").first().text().trim();
					if (source.equals("网易教育频道综合"))
						return;
					String fileName = new SimHash(title, 128).simHash()
							.toString();

					// ////////////////////////////////////////////////
					Document web_doc = null;
					Elements elems = web_doc.select("div.ep-source.cDGray");
					for (int i = 0; i != elems.size(); ++i) {
						web_doc.select("div.ep-source.cDGray").get(i).remove();
					}
				/*	writeToHtmlFile(fileName, web_doc.html());*/
					String web_url = "http://114.215.97.225:8008/kd_news/"
							+ fileName + ".html";
					// ////////////////////////////////////////////////
					Date date = null;
					try {
						date = df.parse(pubdate);
						System.out.println(date);
					} catch (ParseException e) {
						return;
					}

					content = HtmlProcess.packet(content);
					Document doc = Jsoup.parse(content);
					// ////////////////////////////////////////////////
					elems = doc.select("div.ep-source.cDGray");
					for (int i = 0; i != elems.size(); ++i) {
						elems.get(i).remove();
					}
					// ////////////////////////////////////////////////
					doc.getElementById("source").appendText("来源：" + source);
					content = doc.html();
					String image_url = "";//getWebTitleImage(doc, fileName);
					Infomation infomation = new Infomation(0, content, web_url,
							date, 0, source, 0, title, image_url, 1, null, null);
					try {
						if (infomationService.saveOrUpadte(infomation) == false) {
							System.out.println("更新数据...");
						}
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		};
	}


	public String IMG_UPLOAD_URL = "http://120.24.223.221/getUrlImage.php";
	public String IMG_HOST = "http://120.24.223.221/";

	public Map<String, String> getWebAllImages(String content) {
		Document doc = Jsoup.parse(content);
		Elements imgElems = doc.select("img");
		String params = "";
		String titleImgURL = "";
		UUID64 uuid64 = new UUID64();
		Map<String, String> map = new HashMap<String, String>();
		for (int i = 0; i != imgElems.size(); ++i) {
			Element imgElem = imgElems.get(i);
			String imgURL = imgElem.absUrl("src");
			if (imgURL.contains("?"))
				imgURL = imgURL.substring(0, imgURL.indexOf("?"));
			if (imgURL.contains("#"))
				imgURL = imgURL.substring(0, imgURL.indexOf("#"));
			if (!imgURL.equals("") && !imgURL.equals("javascript:void(0);")
					&& !imgURL.equals("javascript:;")) {
				if (imgURL.endsWith(".gif") || imgURL.endsWith(".jpg")
						|| imgURL.endsWith(".png")) {
					String imgType = imgURL.substring(imgURL.lastIndexOf("."));
					String str_uuid = uuid64.getUUID();
					titleImgURL = IMG_HOST + "imgs/" + str_uuid + imgType
							+ ".200X150" + imgType;
					content = content.replace(imgURL, IMG_HOST + "imgs/"
							+ str_uuid + imgType);
					params = params + "map[" + str_uuid + "]=" + imgURL + "&";
				}
			}
		}
		new HttpPost(IMG_UPLOAD_URL, params + "upload_key=123qweasdzxc");
		map.put("titleImgURL", titleImgURL);
		map.put("content", content);
		return map;
	}

	/**
	 * 不公开接口
	 */
	@RequestMapping("/EduUnivsNews")
	public void EduUnivsNews() {
		Calendar cal = Calendar.getInstance();
		int year = cal.get(Calendar.YEAR);
		int mouth = cal.get(Calendar.MONTH) + 1;
		int day = cal.get(Calendar.DATE);
		String str_day = day + "";
		String str_mouth = mouth + "";
		if (day < 10)
			str_day = "0" + day;
		if (mouth < 10)
			str_mouth = "0" + mouth;
		new UnivsSingeCrawler("http://news.univs.cn/" + year + "/" + str_mouth
				+ str_day + "/\\d+.shtml",
				new String[] { "http://news.univs.cn/gxkx/" }) {

			@Override
			public void handle(String href) {
				Map<String, Elements> map = getElementsByCSS(
						getHtmlDocument(href), "h2.h2.fz-24.HT",
						"div.cor-666.mar-t-10.ov", "div.cor-666.mar-t-10.ov",
						"div#ctrlfscont");
				if (map != null) {
					String title = map.get("title").get(0).text().trim();
					try {
						if (infomationService.getByTitle(title) != null) {
							LogRecord.info("repeat infomation!");
							return;
						}
					} catch (Exception e1) {
						return;
					}
					String content = TextProcess.preProcess(map.get("content").get(0).html());
					Map<String, String> tc = getWebAllImages(content);
					content = tc.get("content");//
					String image_url = tc.get("titleImgURL");//
					String tmp[] = map.get("pubdate").get(0).text().trim().split(" ");
					String pubdate = tmp[3] + " " + tmp[4];//
					SimpleDateFormat df = new SimpleDateFormat("yyyy年MM月dd日 hh:mm:ss");
					Date date = null;
					try {
						date = df.parse(pubdate);
						System.out.println(date);
					} catch (ParseException e) {
						return;
					}
					String source = tmp[5].replaceAll("来源：", "");//
					String web_url = "http://www.kdwork.com/news?id=";
					content = HtmlProcess.packet(content);
					Document doc = Jsoup.parse(content);
					doc.getElementById("source").appendText(tmp[5]);
					content = doc.html();
					Infomation infomation = new Infomation(0, content, "",
							date, 0, source, 0, title, image_url, 1, null, null);
					try {
						infomationService.saveOrUpadte(infomation);
						long id = infomation.getId();
						web_url+=id;
						infomation.setUrl(web_url);
						LogRecord.info(web_url);
						infomationService.update(infomation);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}

		};
	}

	@RequestMapping("/kd_talk")
	public void kd_talk() {
		String Regex="^http://bbs.xuanjianghui.com.cn/thread.*";
	     new XuanjianghuiSingeCrawler(Regex, new String[]{"http://bbs.xuanjianghui.com.cn/forum-66-1.html"} ){
			@Override
			public void handle(String href) {
			 System.out.println(href);
			 Document doc = getHtmlDocument(href);
			 Date date = null;
			 String content = doc.select("div.pct").first().html();
			 String title = doc.getElementById("thread_subject").text().trim();
			 Pattern p = Pattern.compile("[0-9]{4}[年|\\-|/][0-9]{1,2}[月|\\-|/][0-9]{1,2}");
				Matcher m = p.matcher(doc.select("div.pct").text());
				if (m.find()) {
					if (!"".equals(m.group())) {
						String sdate = m.group();
						sdate = sdate.replaceAll("年", "-");
						sdate = sdate.replaceAll("月", "-");
						sdate = sdate.replaceAll("/", "-");
						SimpleDateFormat df = new SimpleDateFormat(
								"yyyy-MM-dd");	 
					  try {
						date = df.parse(sdate);
					} catch (ParseException e) {
						e.printStackTrace();
					}
					String web_url = "http://www.kdwork.com/news?id=";
					String source = null;
					Infomation infomation = new Infomation(0, content, web_url,
								date, 0, source, 0, title, "", 2, null, null);
						try {
							infomationService.saveOrUpadte(infomation);
							web_url += infomation.getId();
							LogRecord.info(web_url);
						} catch (Exception e) {
							e.printStackTrace();
						}
					  
					}
					}
			}
	    	
	    };
	}

	@RequestMapping("/ganJiQJKuaiJISingeCrawler")
	public void ganJiQJKuaiJISingeCrawler() {
		String preUrl = "http://wap.ganji.com/gz/zpcaikuai/";
		String Regex = "http://wap.ganji.com/gz/zpcaiwushenji/\\d+x.*";

		String seeds[] = new String[] { preUrl + "tianhe/", preUrl + "huadu/",
				preUrl + "haizhu/", preUrl + "yuexiu/", preUrl + "baiyun/",
				preUrl + "liwan/", preUrl + "fanyu/", preUrl + "huangpu/",
				preUrl + "luogang/", preUrl + "nansha/", preUrl + "zengcheng/",
				preUrl + "conghua/" };
		new GanJiQJKuaiJISingeCrawler(Regex, seeds) {

			@Override
			public void handle(String href) {
				Document doc = getHtmlDocument(href);
				if (doc != null)
					addRecruitInfo(doc, new GanJiCrawlerUtils(), JobDis, "会计",
							3L);
			}

		};
	}

	@RequestMapping("/ganJIQJGameSingeCrawler")
	public void ganJIQJGameSingeCrawler() {
		String preUrl = "http://wap.ganji.com/gz/zpyouxisheji/graduate/";
		String Regex = "http://wap.ganji.com/gz/zpyouxisheji/\\d+x.*";

		String seeds[] = new String[] { preUrl + "tianhe/", preUrl + "huadu/",
				preUrl + "haizhu/", preUrl + "yuexiu/", preUrl + "baiyun/",
				preUrl + "liwan/", preUrl + "fanyu/", preUrl + "huangpu/",
				preUrl + "luogang/", preUrl + "nansha/",
				preUrl + "jingjikaifa/", preUrl + "zengcheng/",
				preUrl + "conghua/" };
		new GanJIQJGameSingeCrawler(Regex, seeds) {

			@Override
			public void handle(String href) {
				Document doc = getHtmlDocument(href);
				if (doc != null)
					addRecruitInfo(doc, new GanJiCrawlerUtils(), JobDis,
							"游戏开发", 3L);
			}

		};
	}

	@RequestMapping("/ganJiQJSoftWareSingeCrawler")
	public void ganJiQJSoftWareSingeCrawler() {
		String preUrl = "http://wap.ganji.com/gz/zpruanjiangongchengshi/graduate/";
		String Regex = "http://wap.ganji.com/gz/zpruanjiangongchengshi/\\d+x.*";

		String seeds[] = new String[] { preUrl + "tianhe/", preUrl + "huadu/",
				preUrl + "haizhu/", preUrl + "yuexiu/", preUrl + "baiyun/",
				preUrl + "liwan/", preUrl + "fanyu/", preUrl + "huangpu/",
				preUrl + "luogang/", preUrl + "nansha/",
				preUrl + "jingjikaifa/", preUrl + "zengcheng/",
				preUrl + "conghua/" };
		new GanJiQJSoftWareSingeCrawler(Regex, seeds) {
			@Override
			public void handle(String href) {
				Document doc = getHtmlDocument(href);
				if (doc != null)
					addRecruitInfo(doc, new GanJiCrawlerUtils(), JobDis,
							"软件开发", 3L);
			}

		};
	}

	@RequestMapping("/ganJiQJXiaoShouSingeCrawler")
	public void ganJiQJXiaoShouSingeCrawler() {
		String preUrl = "http://wap.ganji.com/gz/zpshichangyingxiao/";
		String Regex = "http://wap.ganji.com/gz/zpshichangyingxiao/\\d+x.*";

		String seeds[] = new String[] { preUrl + "tianhe/", preUrl + "huadu/",
				preUrl + "haizhu/", preUrl + "yuexiu/", preUrl + "baiyun/",
				preUrl + "liwan/", preUrl + "fanyu/", preUrl + "huangpu/",
				preUrl + "luogang/", preUrl + "nansha/",
				preUrl + "jingjikaifa/", preUrl + "zengcheng/",
				preUrl + "conghua/" };
		new GanJiQJXiaoShouSingeCrawler(Regex, seeds) {
			@Override
			public void handle(String href) {
				Document doc = getHtmlDocument(href);
				if (doc != null)
					addRecruitInfo(doc, new GanJiCrawlerUtils(), JobDis, "销售",
							3L);
			}
		};
	}

	@RequestMapping("/ganJiQJFaLvSingeCrawler")
	public void ganJiQJFaLvSingeCrawler() {
		String preUrl = "http://wap.ganji.com/gz/zpfalv/";
		String Regex = "http://wap.ganji.com/gz/zpfalv/\\d+x.*";

		String seeds[] = new String[] { preUrl + "tianhe/", preUrl + "huadu/",
				preUrl + "haizhu/", preUrl + "yuexiu/", preUrl + "baiyun/",
				preUrl + "liwan/", preUrl + "fanyu/", preUrl + "huangpu/",
				preUrl + "luogang/", preUrl + "nansha/",
				preUrl + "jingjikaifa/", preUrl + "zengcheng/",
				preUrl + "conghua/" };
		new GanJiQJXiaoShouSingeCrawler(Regex, seeds) {
			@Override
			public void handle(String href) {
				Document doc = getHtmlDocument(href);
				if (doc != null)
					addRecruitInfo(doc, new GanJiCrawlerUtils(), JobDis, "法律",
							3L);
			}
		};
	}

	 
	
	public void addRecruitInfo(Document doc, GanJiCrawlerUtils gcu,
			String JobDis, String JobName, long sessionid) {
		try {
			String title = gcu.getTitle(doc);
			String phone = gcu.getPhone(doc);
			String detailedAddress = gcu.getDetailedAddress(doc);
			String contact = gcu.getContact(doc);

			ProvinceCityArea district = null;

			district = (ProvinceCityArea) provinceCityAreaService
					.findByName(JobDis);

			ProvinceCityArea city = district.getProvinceCityArea();
			ProvinceCityArea province = city.getProvinceCityArea();
			Map<String, String> companyMsg = gcu.getCompanyMsg(doc);

			if (!title.equals("") && !phone.equals("")
					&& !detailedAddress.equals("") && companyMsg != null) {
				RecruitInfo recruitInfo = new RecruitInfo();

				String enterpriseDesc = companyMsg.get("int");
				String com_name = companyMsg.get("name");

				// ----------------------------------------------
				String enterpriseHash = MD5.crypt(com_name + "-"
						+ detailedAddress + "-" + enterpriseDesc);
				Enterprise enterprise = new Enterprise();
				enterprise.setHash(enterpriseHash);
				enterprise.setContacts(contact);
				enterprise.setDetailedAddress(detailedAddress);
				enterprise.setCity(city);
				enterprise.setEnterpriseDesc(enterpriseDesc);
				enterprise.setDistrict(district);
				enterprise.setProvince(province);
				enterprise.setPhone(phone);
				enterprise.setName(com_name);
				String strEnterpriseTye = companyMsg.get("type");
				if (strEnterpriseTye.equals("股份制企业")) {
					strEnterpriseTye = "股份制公司";
				}
				if (strEnterpriseTye.equals("国企")) {
					strEnterpriseTye = "国有企业";
				}

				CompanyNature companyNature = companyNatureService
						.findByName(strEnterpriseTye);
				if (companyNature == null) {// 企业性质
					companyNature = companyNatureService.findByName("其他");
				}
				enterprise.setCompanyNature(companyNature);

				Industry industry = industryService.findByName(companyMsg
						.get("ind"));
				if (null == industryService.findByName(companyMsg.get("ind"))) {
					industry = industryService.findByName("其他行业");
				}
				enterprise.setIndustry(industry);

				String strSize = companyMsg.get("size");
				strSize = strSize.replace("以上", "");
				strSize = strSize.replace("以下", "");
				strSize = strSize.replace("人", "");
				System.out.println(strSize);
				String tmp[] = strSize.split("-");
				int size = 0;
				if (tmp.length == 0) {
					size = Integer.parseInt(strSize);
				} else {
					for (int i = 0; i != tmp.length; ++i) {
						size += Integer.parseInt(tmp[i]);
					}
					size = size / tmp.length;
				}
				boolean isCheck = false;
				if (size < 20 && !isCheck) {
					strSize = "0-20";
					isCheck = true;
				}
				if (size < 50 && !isCheck) {
					strSize = "21-50";
					isCheck = true;
				}
				if (size < 100 && !isCheck) {
					strSize = "51-100";
					isCheck = true;
				}
				if (size < 300 && !isCheck) {
					strSize = "101-300";
					isCheck = true;
				}
				if (size < 500 && !isCheck) {
					strSize = "301-500";
					isCheck = true;
				}
				if (size < 1000 && !isCheck) {
					strSize = "501-1000";
					isCheck = true;
				}
				if (size < 3000 && !isCheck) {
					strSize = "1001-3000";
					isCheck = true;
				}
				if (size < 5000 && !isCheck) {
					strSize = "3001-5000";
					isCheck = true;
				}
				if (size < 10000 && !isCheck) {
					strSize = "5001-10000";
					isCheck = true;
				}
				if (size > 10000 && !isCheck) {
					strSize = "10000以上";
					isCheck = true;
				}
				QuantityScale quantityScale = quantityScaleService
						.getByName(strSize);
				enterprise.setQuantityScale(quantityScale);
				Enterprise tmpEnterprise = enterpriseService
						.findByHash(enterpriseHash);
				if (tmpEnterprise == null)
					enterpriseService.save(enterprise);
				else {
					enterprise.setId(tmpEnterprise.getId());
					enterpriseService.update(enterprise);
				}
				// ----------------------------------------------

				recruitInfo.setAudited(gcu.getAudited(doc));
				recruitInfo.setSection((Section) sectionService.getById(
						Section.class, sessionid));

				Position position = positionService.getByNameAndSessionId(
						JobName, sessionid);
				if (null == position) {
					position = positionService.getByNameAndSessionId("其他",
							sessionid);
				}
				recruitInfo.setInfoPlatformType(infomationService.getById(
						InfoPlatformType.class, 5L));
				recruitInfo.setPosition(position);
				recruitInfo.setDistrict(district);
				recruitInfo.setCity(city);
				recruitInfo.setProvince(province);
				recruitInfo.setContacts(contact);
				recruitInfo.setCreateTime(gcu.getCreateTime(doc));
				recruitInfo.setDetailedAddress(detailedAddress);
				recruitInfo.setRequests(gcu.getRequset(doc));
				recruitInfo.setMeteringMode((MeteringMode) meteringModeService
						.getById(MeteringMode.class, gcu.getMeteringMode(doc)));
				String recruitHash = MD5.crypt(title + "-"
						+ gcu.getWorkDesc(doc));
				System.out.println(recruitHash);
				recruitInfo.setHash(recruitHash);
				recruitInfo.setPhone(phone);
				recruitInfo.setSalaryDay(gcu.getSalaryDay(doc));
				recruitInfo.setTitle(title);
				recruitInfo.setWorkDesc(gcu.getWorkDesc(doc));
				recruitInfo.setWorkingWeek(gcu.getWorkTime(doc));
				recruitInfo.setNeedNum(gcu.getNeedNum(doc));
				recruitInfo.setEnterprise(enterprise);
				RecruitInfo tmpRecruitInfo = recruitInfoService
						.findByHash(recruitHash);
				if (null == tmpRecruitInfo) {
					recruitInfoService.save(recruitInfo);
				} else {
					recruitInfo.setId(tmpRecruitInfo.getId());
					recruitInfoService.update(recruitInfo);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}