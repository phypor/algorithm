package org.kd.server.web.controller;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.authz.annotation.RequiresRoles;
import org.kd.server.beans.entity.ClientPlatformType;
import org.kd.server.beans.entity.CompanyNature;
import org.kd.server.beans.entity.DateInterval;
import org.kd.server.beans.entity.Degrees;
import org.kd.server.beans.entity.Grade;
import org.kd.server.beans.entity.ImageType;
import org.kd.server.beans.entity.Industry;
import org.kd.server.beans.entity.InfoPlatformType;
import org.kd.server.beans.entity.InitialInfo;
import org.kd.server.beans.entity.Position;
import org.kd.server.beans.entity.QuantityScale;
import org.kd.server.beans.entity.School;
import org.kd.server.beans.entity.Section;
import org.kd.server.beans.entity.User;
import org.kd.server.beans.entity.UserEntity;
import org.kd.server.beans.param.ImageConfig;
import org.kd.server.beans.vo.QueryCondition;
import org.kd.server.common.util.EasemobUtil;
import org.kd.server.common.util.MD5;
import org.kd.server.service.ClientPlatformTypeService;
import org.kd.server.service.CompanyNatureService;
import org.kd.server.service.DateIntervalService;
import org.kd.server.service.DegreesService;
import org.kd.server.service.GradeService;
import org.kd.server.service.ImageTypeService;
import org.kd.server.service.IndustryService;
import org.kd.server.service.InfoPlatformTypeService;
import org.kd.server.service.InitialInfoService;
import org.kd.server.service.PositionService;
import org.kd.server.service.QuantityScaleService;
import org.kd.server.service.SchoolService;
import org.kd.server.service.SectionService;
import org.kd.server.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/*
 * 
 * zmy
 * 
 * 初始化信息控制器
 */
@Controller
public class InitialInfoController {
	@Resource(name = "initialInfoService")
	private InitialInfoService initialInfoService;
	@Resource(name = "degreesService")
	private DegreesService degreesService;
	@Resource(name = "industryService")
	private IndustryService industryService;
	@Resource(name = "gradeService")
	private GradeService gradeService;
	@Resource(name = "companyNatureService")
	private CompanyNatureService companyNatureService;

	@Resource(name = "quantityScaleService")
	private QuantityScaleService quantityScaleService;

	@Resource(name = "positionService")
	private PositionService positionService;

	@Resource(name = "sectionService")
	private SectionService sectionService;

	@Resource(name = "imageTypeService")
	private ImageTypeService imageTypeService;

	@Resource(name = "dateIntervalService")
	private DateIntervalService dateIntervalService;
	@Resource(name = "userService")
	private UserService userService;
	@Resource(name = "schoolService")
	private SchoolService schoolService;
	@Resource(name = "clientPlatformTypeService")
	private ClientPlatformTypeService clientPlatformTypeService;
	@Resource(name = "infoPlatformTypeService")
	private InfoPlatformTypeService infoPlatformTypeService;

	// 图片类型信息初始化接口
	@ResponseBody
	@RequestMapping("/initialImageType")
	public Map<String, Object> initialImageType() throws Exception {
		int count = 0;

		ImageType imageType1 = new ImageType();
		imageType1.setCode(ImageConfig.SYGT);
		imageType1.setName("首页滚图");

		imageTypeService.save(imageType1);

		count++;

		ImageType imageType2 = new ImageType();
		imageType2.setCode(ImageConfig.XXGT);
		imageType2.setName("消息滚图");

		imageTypeService.save(imageType2);

		count++;

		ImageType imageType3 = new ImageType();
		imageType3.setCode(ImageConfig.KXGT);
		imageType3.setName("快讯滚图");

		imageTypeService.save(imageType3);

		count++;

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("count", count);
		return map;
	}

	/**
	 * +bojie 添加初始化客户端登录平台接口
	 * 
	 * @throws IOException
	 * @throws Exception
	 */
	@RequestMapping("/infoPlatformType")
	public void InitalInfoPlatformType() throws IOException, Exception {
		String file_path = "C:/infoplatformtype.txt";
		BufferedReader br = new BufferedReader(new InputStreamReader(
				new FileInputStream(file_path), "GB2312"));
		String name = null;
		while ((name = br.readLine()) != null) {
			InfoPlatformType infoPlatformType = new InfoPlatformType();
			infoPlatformType.setName(name);
			infoPlatformTypeService.save(infoPlatformType);
		}
		br.close();
	}

	/**
	 * +bojie 添加初始化客户端登录平台接口
	 * 
	 * @throws IOException
	 * @throws Exception
	 */
	@RequestMapping("/InitalClientPlatformType")
	public void InitalClientPlatformType() throws IOException, Exception {
		String file_path = "C:/clientplatformtype.txt";
		BufferedReader br = new BufferedReader(new InputStreamReader(
				new FileInputStream(file_path), "GB2312"));
		String name = null;
		while ((name = br.readLine()) != null) {
			ClientPlatformType clientPlatformType = new ClientPlatformType();
			clientPlatformType.setName(name);
			clientPlatformTypeService.save(clientPlatformType);
		}
		br.close();
	}

	/**
	 * +bojie 添加初始化School接口
	 * 
	 * @throws IOException
	 * @throws Exception
	 */
	@RequestMapping("/InitalSchools")
	public void InitalSchools() throws IOException, Exception {
		String file_path = "C:/schools.txt";
		BufferedReader br = new BufferedReader(new InputStreamReader(
				new FileInputStream(file_path), "GB2312"));
		String str = null;
		while ((str = br.readLine()) != null) {
			String tmp[] = str.split(" ");
			School school = new School();
			school.setSchoolName(tmp[0]);
			school.setProId(new Long((long) Integer.parseInt(tmp[1])));
			schoolService.save(school);
		}
		br.close();
	}

	/**
	 * +bojie 添加初始化Grades接口
	 * 
	 * @throws Exception
	 */
	@RequestMapping("/InitialGrades")
	public void InitialGrades() throws Exception {
		String file_path = "C:/grades.txt";
		BufferedReader br = new BufferedReader(new InputStreamReader(
				new FileInputStream(file_path), "GB2312"));
		String name = null;
		while ((name = br.readLine()) != null) {
			Grade grades = new Grade();
			grades.setName(name);
			gradeService.save(grades);
		}
		br.close();
	}

	/**
	 * +bojie 添加初始化Degrees接口
	 * 
	 * @throws Exception
	 */
	@RequestMapping("/InitialDegrees")
	public void InitialDegrees() throws Exception {
		String file_path = "C:/degrees.txt";
		BufferedReader br = new BufferedReader(new InputStreamReader(
				new FileInputStream(file_path), "GB2312"));
		String name = null;
		while ((name = br.readLine()) != null) {
			Degrees degrees = new Degrees();
			degrees.setName(name);
			degreesService.save(degrees);
		}
		br.close();
	}

	/**
	 * +bojie 添加初始化position接口
	 * 
	 * @throws Exception
	 */
	@RequestMapping("/InitialPosition")
	public void InitialPosition() throws Exception {
		String file_path = "C:/position.txt";
		BufferedReader br = new BufferedReader(new InputStreamReader(
				new FileInputStream(file_path), "GB2312"));
		String str = null;
		while ((str = br.readLine()) != null) {
			System.out.println(str);
			String[] ar = str.split(" ");
			Position position = new Position();
			Section section = sectionService.getById(Section.class, new Long(
					(long) Integer.parseInt(ar[1])));
			position.setName(ar[0]);
			position.setSection(section);
			positionService.save(position);
		}
		br.close();
	}

	// 版块信息初始化接口
	@ResponseBody
	@RequestMapping("/initialSection")
	public Map<String, Object> initialSection() throws Exception {
		int count = 0;

		Section section4 = new Section();
		section4.setName("最新兼职");

		sectionService.save(section4);

		count++;

		Section section1 = new Section();
		section1.setName("名企实习");

		sectionService.save(section1);

		count++;

		Section section2 = new Section();
		section2.setName("理想全职");

		sectionService.save(section2);

		count++;

		Section section3 = new Section();
		section3.setName("义旅意行");

		sectionService.save(section3);

		count++;

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("count", count);
		return map;
	}

	// 时间区间信息初始化接口
	@ResponseBody
	@RequestMapping("/initialDateInterval")
	public Map<String, Object> initialDateInterval() throws Exception {
		int count = 0;

		DateInterval dateInterval1 = new DateInterval();
		dateInterval1.setName("全部");

		dateIntervalService.save(dateInterval1);

		count++;

		DateInterval dateInterval7 = new DateInterval();
		dateInterval7.setName("当天");

		dateIntervalService.save(dateInterval7);

		count++;

		DateInterval dateInterval2 = new DateInterval();
		dateInterval2.setName("本周内");

		dateIntervalService.save(dateInterval2);

		count++;

		DateInterval dateInterval3 = new DateInterval();
		dateInterval3.setName("本月内");

		dateIntervalService.save(dateInterval3);

		count++;

		DateInterval dateInterval8 = new DateInterval();
		dateInterval8.setName("两个月内");

		dateIntervalService.save(dateInterval8);

		count++;

		DateInterval dateInterval4 = new DateInterval();
		dateInterval4.setName("三个月内");

		dateIntervalService.save(dateInterval4);

		count++;

		DateInterval dateInterval5 = new DateInterval();
		dateInterval5.setName("暑假");

		dateIntervalService.save(dateInterval5);

		count++;

		DateInterval dateInterval6 = new DateInterval();
		dateInterval6.setName("寒假");

		dateIntervalService.save(dateInterval6);

		count++;

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("count", count);
		return map;
	}

	// app信息初始化接口
	@ResponseBody
	@RequestMapping(value = "/initialInfo", method = RequestMethod.POST)
	public Model addInititalInfo(InitialInfo initialInfo,
			HttpServletRequest request, Model model) throws Exception {
		initialInfoService.addInitialInfo(initialInfo, request);
		return model;
	}

	@ResponseBody
	@RequestMapping("/industry/initData")
	public String industryInitData(HttpServletRequest request, Model model)
			throws Exception {
		URL file = this.getClass().getClassLoader().getResource("industry.txt");

		int count = 0;
		BufferedReader br = new BufferedReader(new InputStreamReader(
				new FileInputStream(file.getFile())));
		for (String line = br.readLine(); line != null; line = br.readLine()) {
			System.out.println(line);

			Industry industry = new Industry();
			industry.setName(line);

			industryService.save(industry);

			count++;
		}
		br.close();

		model.addAttribute("count", count);
		return "";
	}

	@RequestMapping("/companyNature/initData")
	public String companyNatureInitData(HttpServletRequest request, Model model)
			throws Exception {
		URL file = this.getClass().getClassLoader()
				.getResource("companyNature.txt");

		int count = 0;
		BufferedReader br = new BufferedReader(new InputStreamReader(
				new FileInputStream(file.getFile())));
		for (String line = br.readLine(); line != null; line = br.readLine()) {
			System.out.println(line);

			CompanyNature companyNature = new CompanyNature();
			companyNature.setName(line);

			companyNatureService.save(companyNature);

			count++;
		}
		br.close();

		model.addAttribute("count", count);
		return "";
	}

	@ResponseBody
	@RequestMapping("/easeMob/synchroUserNamePasswd")
	public Model synchroUserNamePasswd(HttpServletRequest request, Model model)
			throws Exception {
		List<User> userList = userService.getAll(User.class);

		int count = 0;
		if (!userList.isEmpty()) {
			for (User user : userList) {
				user.setEasemobKey(MD5.crypt(user.getPassword() + "ujob"
						+ user.getId()));
				user.setEasemobName(MD5.crypt(user.getLoginName() + "ujob"
						+ new Date().getTime()));
				userService.update(user);

				count++;
			}
		}

		model.addAttribute("result", "已经同步:" + count + "个用户的密码。");
		return model;
	}

	@ResponseBody
	@RequestMapping("/easeMob/removeUser")
	public Model easeMobRemoveUser(HttpServletRequest request, Model model)
			throws Exception {
		String retC = EasemobUtil.delAnyUser(30000);
		model.addAttribute("result", retC);
		return model;
	}

	@ResponseBody
	@RequestMapping("/easeMob/initData")
	public Model easeMobInitData(HttpServletRequest request, Model model)
			throws Exception {
		List<QueryCondition> queryConditions = new ArrayList<QueryCondition>();
		queryConditions.add(new QueryCondition("password is not null "));
		List<User> userList = userService.get(User.class, queryConditions);

		List<UserEntity> regsList = new ArrayList<UserEntity>();

		for (int i = 0; i < userList.size(); i++) {
			User user = userList.get(i);
			UserEntity userEntity = new UserEntity();
			userEntity.setUsername(user.getEasemobName());
			userEntity.setPassword(user.getEasemobKey());

			regsList.add(userEntity);
		}

		String cn = EasemobUtil.registerEasemobUser(regsList);
		model.addAttribute("result", cn);
		return model;
	}

	@RequestMapping("/position/initData")
	public String positionInitData(HttpServletRequest request, Model model)
			throws Exception {
		URL file = this.getClass().getClassLoader().getResource("position.txt");

		int count = 0;
		BufferedReader br = new BufferedReader(new InputStreamReader(
				new FileInputStream(file.getFile())));
		for (String line = br.readLine(); line != null; line = br.readLine()) {
			System.out.println(line);

			Position position = new Position();
			position.setName(line);

			positionService.save(position);

			count++;
		}
		br.close();

		model.addAttribute("count", count);
		return "";
	}

	@RequestMapping("/quantityScale/initData")
	public String quantityScaleInitData(HttpServletRequest request, Model model)
			throws Exception {
		URL file = this.getClass().getClassLoader()
				.getResource("quantityScale.txt");

		int count = 0;
		BufferedReader br = new BufferedReader(new InputStreamReader(
				new FileInputStream(file.getFile())));
		for (String line = br.readLine(); line != null; line = br.readLine()) {
			System.out.println(line);

			QuantityScale quantityScale = new QuantityScale();
			quantityScale.setName(line);

			quantityScaleService.save(quantityScale);

			count++;
		}
		br.close();

		model.addAttribute("count", count);
		return "";
	}

	@RequiresRoles("administrator")
	@RequestMapping("/view")
	public String view(String url) {
		return url;
	}
}
