package org.kd.server.web.controller;

import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.LogRecord;

import javax.annotation.Resource;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.subject.Subject;
import org.kd.server.beans.entity.Degrees;
import org.kd.server.beans.entity.Enterprise;
import org.kd.server.beans.entity.Grade;
import org.kd.server.beans.entity.ProvinceCityArea;
import org.kd.server.beans.entity.RecruitInfo;
import org.kd.server.beans.entity.RecruitUserResume;
import org.kd.server.beans.entity.User;
import org.kd.server.beans.entity.UserResume;
import org.kd.server.beans.param.PublicInfoConfig;
import org.kd.server.beans.param.RetMsg;
import org.kd.server.beans.vo.Pagination;
import org.kd.server.beans.vo.QueryCondition;
import org.kd.server.common.exception.BusinessException;
import org.kd.server.common.util.DateUtil;
import org.kd.server.common.util.MailSender;
import org.kd.server.service.DegreesService;
import org.kd.server.service.EnterpriseService;
import org.kd.server.service.GradeService;
import org.kd.server.service.ProvinceCityAreaService;
import org.kd.server.service.RecruitInfoService;
import org.kd.server.service.RecruitUserResumeService;
import org.kd.server.service.UserResumeService;
import org.kd.server.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/*
 * zmy
 * SET FOREIGN_KEY_CHECKS=0;
 * 用户简历控制器
 */
@Controller
@RequestMapping(value = "/userResume")
public class UserResumeController {
	@Resource(name = "userService")
	private UserService userService;

	@Resource(name = "recruitUserResumeService")
	private RecruitUserResumeService recruitUserResumeService;

	@Resource(name = "userResumeService")
	private UserResumeService userResumeService;

	@Resource(name = "enterpriseService")
	private EnterpriseService enterpriseService;

	@Resource(name = "recruitInfoService")
	private RecruitInfoService recruitInfoService;

	@Resource(name = "provinceCityAreaService")
	private ProvinceCityAreaService provinceCityAreaService;

	@Resource(name = "degreesService")
	private DegreesService degreesService;

	@Resource(name = "gradeService")
	private GradeService gradeService;

	@Inject
	private MailSender mailSender;

	@RequestMapping("/management")
	public String management(Model model) throws Exception {
		List<User> userList = userService.getAll(User.class);

		List<ProvinceCityArea> provinceCityAreaList = provinceCityAreaService
				.getProvince();
		model.addAttribute("provinceCityAreaList", provinceCityAreaList);

		List<UserResume> userResumes = userList.get(0).getUserResumes();

		model.addAttribute(
				"userResume",
				userResumes != null && userResumes.size() > 0 ? userResumes
						.get(0) : null);
		model.addAttribute("userList", userList);
		model.addAttribute("userId", userList.get(0).getId());
		model.addAttribute("degreesList", degreesService.getAll(Degrees.class));
		model.addAttribute("gradeList", gradeService.getAll(Grade.class));

		return "userResume/userResumeEdit";
	}

	@ResponseBody
	@RequiresAuthentication
	@RequestMapping(method = RequestMethod.POST)
	public Map<String, Object> addUserResume(UserResume userResume,
			Model model, HttpServletRequest request) throws Exception {
		User currentUser = (User) request.getSession().getAttribute(
				PublicInfoConfig.currentUser);

		Subject userSub = SecurityUtils.getSubject();
		if (!userSub.hasRole("administrator")) {
			userResume.setUser(currentUser);
		}

		if (userResume.getId() == null) {
			userResumeService.save(userResume);
		} else {
			userResumeService.update(userResume);
		}

		User user = userService.getById(User.class, userResume.getUser()
				.getId());

		int percent = completionDegree(userResume);
		user.setResumeIntrodPercent(percent);

		user.setSex(userResume.getSex());

		userService.update(user);

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("status", PublicInfoConfig.SUCCESS_CODE);
		map.put("msg", PublicInfoConfig.SUCCESS_MSG);

		Map<String, Object> contentMap = new HashMap<String, Object>();
		contentMap.put("userResume", userResume);
		contentMap.put("resumeIntrodPercent", percent);

		map.put("result", contentMap);

		return map;
	}

	private int completionDegree(UserResume userResume) {
		int degree = 100;
		PropertyDescriptor[] props = null;
		org.kd.server.common.util.LogRecord.info("-------" + new Date()
				+ "-------------");
		org.kd.server.common.util.LogRecord.info(userResume.getUser()
				.getNickName() + " 提交简历");
 
		try {
			props = Introspector.getBeanInfo(userResume.getClass(),
					Object.class).getPropertyDescriptors();
		} catch (IntrospectionException e) {
		}
		if (props != null) {
			for (int i = 0; i < props.length; i++) {
				try {
					Class<?> object = props[i].getPropertyType();// 获取属性的类型
					String name = props[i].getName();

					Method getter = props[i].getReadMethod();
					Object value = getter.invoke(userResume);

					if (name.equals("qq")
							&& Long.valueOf(value.toString()) == 0L) {
						degree -= 4;
						continue;
					}
					if (!object.equals(List.class)
							&& (value == null || value.equals(""))) {
						if (name.equals("province") || name.equals("city")
								|| name.equals("district")
								|| name.equals("detailedAddress")
								|| name.equals("grade")
								|| name.equals("degrees")) {
							degree -= 2;// 2*6=12
							org.kd.server.common.util.LogRecord.info(name
									+ " 为空  - 6");
						} else if (name.equals("userImage")) {
							degree -= 10;// 10 22
							org.kd.server.common.util.LogRecord.info(name
									+ " 为空  - 6");
						} else if (name.equals("introduce")) {
							degree -= 12;// 12 34
							org.kd.server.common.util.LogRecord.info(name
									+ " 为空  - 6");
						} else if (name.equals("workIntention")
								|| name.equals("workExprience")
								|| name.equals("winnerExperience")
								|| name.equals("goodSkills")) {
							degree -= 6; // 4*6=24 58
							org.kd.server.common.util.LogRecord.info(name
									+ " 为空  - 6");
						} else {
							org.kd.server.common.util.LogRecord.info(name
									+ " 为空  - 4");
							degree -= 4;
						}

					}
					System.out.println("--" + name + "--");
					System.out.println(degree);
				} catch (Exception e) {
				}
			}
		}

		return degree;
	}

	@RequestMapping("/findByUserId")
	public String addUserResume(Long userId, Model model,
			HttpServletRequest request) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		User user = userService.getById(User.class, userId);
		if (user != null && user.getUserType() == 2) {

			map.put("enterprise", enterpriseService.findByUserId(user.getId()));
			model.addAttribute("result", map);
			return "userResume/userResumeEdit";
		}
		List<UserResume> userResumes = user.getUserResumes();

		UserResume userResume = userResumes != null && userResumes.size() > 0 ? userResumes
				.get(0) : null;

		if (!request.getRequestURI().endsWith(".json")) {
			List<User> userList = userService.getAll(User.class);

			List<ProvinceCityArea> provinceCityAreaList = provinceCityAreaService
					.getProvince();
			model.addAttribute("provinceCityAreaList", provinceCityAreaList);

			model.addAttribute("userList", userList);

			model.addAttribute("userId", userId);
		}
		map.put("userResume", userResume);
		model.addAttribute("result", map);
		return "userResume/userResumeEdit";
	}

	@ResponseBody
	@RequiresAuthentication
	@RequestMapping(value = "/sendResumeForJob", method = RequestMethod.POST)
	public Model sendResume(
			@RequestParam(required = false, defaultValue = "") String email,
			String introduce,
			@RequestParam(required = false, defaultValue = "") Long recruitInfoId,
			Model model, HttpServletRequest request) throws Exception {
		User user = (User) request.getSession().getAttribute(
				PublicInfoConfig.currentUser);
		 UserResume userResume = null;

		if (user.getUserResumes() != null && user.getUserResumes().size() > 0) {
			userResume = user.getUserResumes().get(0);
		} else {
			throw new BusinessException(RetMsg.userResumeEmptyError,
					RetMsg.userResumeEmptyErrorMsg);
		}

		if (!StringUtils.isEmpty(email)) {
			RecruitInfo recruitInfo = recruitInfoService.getById(
					RecruitInfo.class, recruitInfoId);
			Enterprise enterprise = recruitInfo.getEnterprise();

			if (StringUtils.isEmpty(enterprise.getEmail())
					|| !enterprise.getEmail().matches(
							PublicInfoConfig.emailRegex)) {
				throw new BusinessException(RetMsg.emailError,
						RetMsg.emailErrorMsg);
			}

			if (recruitInfo.getUserResumes() != null) {
				if (!recruitInfo.getUserResumes().contains(userResume)) {
					recruitInfo.getUserResumes().add(userResume);
				}
			} else {
				Set<UserResume> userResumes = new HashSet<UserResume>();
				userResumes.add(userResume);
				recruitInfo.setUserResumes(userResumes);
			}

			recruitInfoService.update(recruitInfo);

			User enterpriseUser = userService.getById(User.class, recruitInfo
					.getEnterprise().getUser().getId());
			enterpriseUser
					.setUnreadResumeCount(user.getUnreadResumeCount() + 1);
			userService.update(enterpriseUser);

			List<QueryCondition> queryConditions = new ArrayList<QueryCondition>();
			queryConditions.add(new QueryCondition("userResume.user.id="
					+ user.getId()));
			queryConditions.add(new QueryCondition("recruitInfo.id="
					+ recruitInfo.getId()));
			RecruitUserResume recruitUserResume = (RecruitUserResume) recruitUserResumeService
					.getSingleResult(RecruitUserResume.class, queryConditions);
			if (recruitUserResume != null) {
				recruitUserResume.setSendTime(new Date());
				recruitUserResumeService.update(recruitUserResume);
				org.kd.server.common.util.LogRecord
						.info("recruitUserResume save");
			} else {
				org.kd.server.common.util.LogRecord
						.info("recruitUserResume == null");
			}
			email = enterprise.getEmail();
		} else {
			if (!email.matches(PublicInfoConfig.emailRegex)) {
				throw new BusinessException(RetMsg.emailError,
						RetMsg.emailErrorMsg);
			}
		}

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("content", introduce);

		map.put("sendTime",
				DateUtil.formatDateByString(new Date(), "yyyy-MM-dd HH:mm:ss"));
		map.put("userResume", userResume);

		mailSender
				.send(map,
						"尊敬的快点优职UJOB用户，有新的求职者向您邮箱发送简历，请贵公司及时查看并回复，欢迎使用快点优职UJOB平台。(快点优职，让天下没有难找的工作)",
						"sendResume.ftl", email);

		return model;
	}

	@RequestMapping("/sendUserResumePage")
	public String sendUserResumePage(Long recruitInfoId, Model model)
			throws Exception {

		RecruitInfo recruitInfo = recruitInfoService.getById(RecruitInfo.class,
				recruitInfoId);
		model.addAttribute("recruitInfo", recruitInfo);
		return "userResume/sendUserResume";
	}

	@RequiresAuthentication
	@RequestMapping("/sendUserResumeLog")
	public String sendUserResumeLog(Model model, HttpServletRequest request,
			@RequestParam(required = false, defaultValue = "1") int page,
			@RequestParam(required = false, defaultValue = "10") int pageSize)
			throws Exception {
		User user = (User) request.getSession().getAttribute(
				PublicInfoConfig.currentUser);

		List<QueryCondition> queryConditions = new ArrayList<QueryCondition>();
		queryConditions.add(new QueryCondition("userResume.user.id="
				+ user.getId()));

		Pagination<RecruitUserResume> recruitUserResumePagination = recruitUserResumeService
				.getPagination(RecruitUserResume.class, queryConditions,
						"order by id desc", page, pageSize);

		List<RecruitUserResume> recruitUserResumeList = new ArrayList<RecruitUserResume>();
		if (recruitUserResumePagination.getRecordCount() > 0) {
			for (RecruitUserResume recruitUserResume : recruitUserResumePagination
					.getRecordList()) {
				RecruitUserResume newObj = new RecruitUserResume();

				if (recruitUserResume.getSendTime() == null) {
					recruitUserResume.setSendTime(new Date());
				}

				BeanUtils.copyProperties(newObj, recruitUserResume);
				newObj.setUserResume(null);
				recruitUserResumeList.add(newObj);
			}
			recruitUserResumePagination.setRecordList(recruitUserResumeList);
		}

		model.addAttribute("result", recruitUserResumePagination);
		return "";
	}
}
