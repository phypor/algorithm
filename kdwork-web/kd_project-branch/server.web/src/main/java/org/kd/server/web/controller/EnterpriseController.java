package org.kd.server.web.controller;

import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.kd.server.beans.entity.CompanyNature;
import org.kd.server.beans.entity.Enterprise;
import org.kd.server.beans.entity.Industry;
import org.kd.server.beans.entity.ProvinceCityArea;
import org.kd.server.beans.entity.QuantityScale;
import org.kd.server.beans.entity.RecruitUserResume;
import org.kd.server.beans.entity.User;
import org.kd.server.beans.param.PublicInfoConfig;
import org.kd.server.beans.param.RetMsg;
import org.kd.server.beans.vo.Pagination;
import org.kd.server.beans.vo.QueryCondition;
import org.kd.server.beans.vo.UserVo;
import org.kd.server.common.exception.BusinessException;
import org.kd.server.common.util.LogRecord;
import org.kd.server.service.CompanyNatureService;
import org.kd.server.service.EnterpriseService;
import org.kd.server.service.IndustryService;
import org.kd.server.service.ProvinceCityAreaService;
import org.kd.server.service.QuantityScaleService;
import org.kd.server.service.RecruitUserResumeService;
import org.kd.server.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/enterprise")
public class EnterpriseController {
	@Resource(name = "enterpriseService")
	private EnterpriseService enterpriseService;

	@Resource(name = "userService")
	private UserService userService;

	@Resource(name = "industryService")
	private IndustryService industryService;

	@Resource(name = "companyNatureService")
	private CompanyNatureService companyNatureService;

	@Resource(name = "quantityScaleService")
	private QuantityScaleService quantityScaleService;

	@Resource(name = "provinceCityAreaService")
	private ProvinceCityAreaService provinceCityAreaService;

	@Resource(name = "recruitUserResumeService")
	private RecruitUserResumeService recruitUserResumeService;

	@RequestMapping("/enterpriseEditPage")
	public String enterpriseEditPage(Model model) throws Exception {
		List<User> userList = userService.findEnterpriseAdmissionUser();

		Enterprise enterprise = null;
		User user = null;
		if (userList != null && userList.size() > 0) {
			user = userList.get(0);

			if (userList.get(0).getEnterprises() != null
					&& userList.get(0).getEnterprises().size() > 0) {
				enterprise = userList.get(0).getEnterprises().get(0);
				if (enterprise.getUser() != null) {
					enterprise.setUserVo(new UserVo(enterprise.getUser()
							.getId(), enterprise.getUser().getNickName(),
							enterprise.getUser().getHeadImage(), enterprise
									.getUser().getSex(), enterprise.getUser()
									.getEasemobName()));
				}
			}

		}

		List<Industry> industryList = industryService.getAll(Industry.class);
		List<CompanyNature> companyNatureList = companyNatureService
				.getAll(CompanyNature.class);
		List<QuantityScale> quantityScaleList = quantityScaleService
				.getAll(QuantityScale.class);

		model.addAttribute("industryList", industryList);
		model.addAttribute("companyNatureList", companyNatureList);
		model.addAttribute("quantityScaleList", quantityScaleList);

		List<ProvinceCityArea> provinceCityAreaList = provinceCityAreaService
				.getProvince();
		model.addAttribute("provinceCityAreaList", provinceCityAreaList);

		model.addAttribute("userList", userList);
		model.addAttribute("enterprise", enterprise);
		model.addAttribute("user", user);
		return "enterprise/enterpriseEdit";
	}

	

	/**
	 * @author bojiehuang@163.com
	 * @param page
	 * @param pageSize
	 * @param model
	 * @return
	 */
	@RequiresAuthentication
	@RequestMapping("/checkEnterprise")
	public String checkEnterprisePage(@RequestParam(required = false, defaultValue = "1") int page,
			@RequestParam(required = false, defaultValue = "10") int pageSize,Model model) {
		List<QueryCondition> queryConditions = new ArrayList<QueryCondition>();
	    queryConditions.add(new QueryCondition("businessLicenseCheck = false"));
	    queryConditions.add(new QueryCondition("user != null"));
	    Pagination<Enterprise> enterprisePagination = null;
		try {
		  enterprisePagination = enterpriseService
					.getPagination(Enterprise.class, queryConditions,
							"order by id desc", page, pageSize);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    model.addAttribute("result", enterprisePagination);
	    
		return "enterprise/checkEnterprise";
	}

	 
	/**
	 * @author bojiehuang@163.com
	 * 企业认证通过
	 * @param id
	 */
	@ResponseBody
	@RequiresAuthentication
	@RequestMapping("/authenticationEnterprise")
	public Map<String, Object> authenticationEnterprise(long id,HttpServletRequest request){
		LogRecord.info("id:"+ id);
		Enterprise enterprise = null;
		try {
			enterprise = enterpriseService.getById(Enterprise.class, id);
			if(enterprise!=null){
				enterprise.setBusinessLicenseCheck(true);	
				enterpriseService.update(enterprise);
				int percent = completionDegree(enterprise);
				User currentUser = (User) request.getSession().getAttribute(
						PublicInfoConfig.currentUser);
				User user = userService.getById(User.class, currentUser.getId());
				user.setEnterpriseInfoPercent(percent);
				userService.update(user);
				LogRecord.info(enterprise.getName()+" 审核通过 "+percent+"%");
			}else{
				LogRecord.info("没有该企业信息");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Map<String, Object> content = new HashMap<String, Object>();
		content.put("enterprise", "test");
		return content;
	}
	
	/**
	 * @author bojiehuang@163.com
	 * 取消企业认证通过
	 * @param id
	 */
	@ResponseBody
	@RequiresAuthentication
	@RequestMapping("/cancelAuthenticationEnterprise")
	public Map<String, Object> cancelAuthenticationEnterprise(long id,HttpServletRequest request){
		LogRecord.info("id:"+ id);
		Enterprise enterprise = null;
		try {
			enterprise = enterpriseService.getById(Enterprise.class, id);
			if(enterprise!=null){
				enterprise.setBusinessLicenseCheck(false);	
				enterpriseService.update(enterprise);
				int percent = completionDegree(enterprise);
				User currentUser = (User) request.getSession().getAttribute(
						PublicInfoConfig.currentUser);
				User user = userService.getById(User.class, currentUser.getId());
				user.setEnterpriseInfoPercent(percent);
				userService.update(user);
				LogRecord.info(enterprise.getName()+" 审核不通过");
			}else{
				LogRecord.info("没有该企业信息");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Map<String, Object> content = new HashMap<String, Object>();
		content.put("enterprise", "test");
		return content;
	}
	
	@ResponseBody
	@RequiresAuthentication
	@RequestMapping(method = RequestMethod.POST)
	public Map<String, Object> addEnterprise(Enterprise enterprise,
			Model model, HttpServletRequest request) throws Exception {
		Date date = new Date();
		User currentUser = (User) request.getSession().getAttribute(
				PublicInfoConfig.currentUser);
		enterprise.setUser(currentUser);
		 
		if (enterprise.getId() == null || enterprise.getId() == 0) {
			enterprise.setId(null);
			enterprise.setCreateTime(date);
			enterprise.setRefreshTime(date);
			enterprise.seteType(currentUser.getUserType());
			enterpriseService.save(enterprise);
			LogRecord.info(enterprise.getId()+" enterpriseService.save");
		} else {
			if (enterprise.getUser() != null
					&& enterprise.getUser().getId().equals(currentUser.getId())) {
				Enterprise tmp = enterpriseService.findByUserId( currentUser.getId());
				enterprise.setId(tmp.getId());
				LogRecord.info(tmp.getUser().getId()+"");
				enterprise.setBusinessLicenseCheck(tmp.getBusinessLicenseCheck());
				enterprise.setUser(tmp.getUser());
				enterprise.seteType(currentUser.getUserType());
				enterprise.setCreateTime(tmp.getCreateTime());
				enterprise.setRefreshTime(date);
				enterpriseService.update(enterprise);
				LogRecord.info(tmp.getUser().getId()+" enterpriseService.update");
			} else {

				throw new BusinessException(RetMsg.operateAuthorizedError,
						RetMsg.operateAuthorizedErrorMsg);
			}
		}

		int percent = completionDegree(enterprise);
	 
		String dt = new String(
				new SimpleDateFormat("yyyy-MM-dd hh-mm-ss").format(date));

		User user = userService.getById(User.class, currentUser.getId());
		user.setEnterpriseInfoPercent(percent);
		userService.update(user);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("status", PublicInfoConfig.SUCCESS_CODE);
		map.put("msg", PublicInfoConfig.SUCCESS_MSG);

		if (enterprise.getUser() != null) {
			enterprise.setUserVo(new UserVo(enterprise.getUser().getId(),
					enterprise.getUser().getNickName(), enterprise.getUser()
							.getHeadImage(), enterprise.getUser().getSex(),
					enterprise.getUser().getEasemobName()));
		}

		Map<String, Object> content = new HashMap<String, Object>();
		content.put("enterprise", enterprise);
		content.put("percent", percent);

		map.put("result", content);

		return map;
	}

	/**
	 * @author bojiehuang@163.com
	 * @param enterprise
	 * @return
	 */
	private int completionDegree(Enterprise enterprise) {
		LogRecord.info("是否验证 "+enterprise.getBusinessLicenseCheck());
		int percent = 100;
		PropertyDescriptor[] props = null;
		try {
			props = Introspector.getBeanInfo(enterprise.getClass(),
					Object.class).getPropertyDescriptors();
		} catch (IntrospectionException e) {
		}
		if (props != null) {
			for (int i = 0; i < props.length; i++) {
				try {
					Class<?> object = props[i].getPropertyType();// 获取属性的类型
					String name = props[i].getName();

					Method getter = props[i].getReadMethod();
					Object value = getter.invoke(enterprise);
					if (name.equals("businessLicenseImage") ){
						if(value!= null && !value.equals("")){
							if(enterprise.getBusinessLicenseCheck() ==false){
								if(enterprise.geteType() == 2){
							 		LogRecord.info(name + "未认证 -14");
									percent -= 14;			
								}
								if(enterprise.geteType() == 3){
									LogRecord.info(name + "没数据 -26");		
									percent -= 26;	
								}
							}
						}
					}
					if (!object.equals(List.class)
							&& (value == null || value.equals(""))) {
						if (name.equals("logoImage")) {
							percent -= 10;
							LogRecord.info(name + "没数据 -10");
						}
						
						if (name.equals("name") || name.equals("contacts")
								|| name.equals("phone") || name.equals("email")
								|| name.equals("websiteAddress")) {
							LogRecord.info(name + "没数据 -6");
							percent -= 6;
						}

						if (name.equals("province") || name.equals("city")
								|| name.equals("district")
								|| name.equals("detailedAddress")) {
							LogRecord.info(name + "没数据 -4");
							percent -= 4;
						}

						if (enterprise.getUser() != null
								&& enterprise.getUser().getUserType() == 2) {
							if (name.equals("industry")
									|| name.equals("companyNature")
									|| name.equals("quantityScale")) {
								LogRecord.info(name + "没数据 -4");
								percent -= 4;
							}

							if (name.equals("licenseNo")
									|| name.equals("enterprisePhil")
									|| name.equals("enterpriseDesc")) {
								LogRecord.info(name + "没数据 -6");
								percent -= 6;
							}
						 	if (name.equals("businessLicenseImage") ){
						 		LogRecord.info(name + "没数据 -14");
								percent -= 14;
							}
						}

						if (enterprise.getUser() != null
								&& enterprise.getUser().getUserType() == 3) {
						 	if (name.equals("businessLicenseImage") ) {
								LogRecord.info(name + "没数据 -26");
								percent -= 26;
							} 
							if (name.equals("schoolMotto")) {
								LogRecord.info(name + "没数据 -6");
								percent -= 6;
							}

							if (name.equals("enterpriseDesc")) {
								LogRecord.info(name + "没数据 -12");
								percent -= 12;
							}
						}
					} 
					System.out.println("--" + name + "--");
					System.out.println(percent);
				} catch (Exception e) {
				}
			}
		}

		return percent;
	}


	
	@RequestMapping("/findByUserId")
	public String addEnterprise(Long userId, Model model,
			HttpServletRequest request) throws Exception {
		User user = userService.getById(User.class, userId);

		Enterprise enterprise = null;
		if (user.getEnterprises() != null && user.getEnterprises().size() > 0) {
			enterprise = user.getEnterprises().get(0);

			if (enterprise.getUser() != null) {
				enterprise.setUserVo(new UserVo(enterprise.getUser().getId(),
						enterprise.getUser().getNickName(), enterprise
								.getUser().getHeadImage(), enterprise.getUser()
								.getSex(), enterprise.getUser()
								.getEasemobName()));
			}
		}

		if (!request.getRequestURI().endsWith(".json")) {
			List<User> userList = userService.findEnterpriseAdmissionUser();
			List<Industry> industryList = industryService
					.getAll(Industry.class);
			List<CompanyNature> companyNatureList = companyNatureService
					.getAll(CompanyNature.class);
			List<QuantityScale> quantityScaleList = quantityScaleService
					.getAll(QuantityScale.class);
			List<ProvinceCityArea> provinceCityAreaList = provinceCityAreaService
					.getProvince();

			model.addAttribute("provinceCityAreaList", provinceCityAreaList);
			model.addAttribute("industryList", industryList);
			model.addAttribute("companyNatureList", companyNatureList);
			model.addAttribute("quantityScaleList", quantityScaleList);
			model.addAttribute("userList", userList);
			model.addAttribute("enterprise", enterprise);
			model.addAttribute("user", user);
		} else {
			model.addAttribute("result", enterprise);
		}

		return "enterprise/enterpriseEdit";
	}

	@RequiresAuthentication
	@RequestMapping("/userResumeList")
	public String userResumeList(
			@RequestParam(required = false, defaultValue = "1") int page,
			@RequestParam(required = false, defaultValue = "10") int pageSize,
			Model model, HttpServletRequest request) throws Exception {
		User user = (User) request.getSession().getAttribute(PublicInfoConfig.currentUser);
		List<QueryCondition> queryConditions = new ArrayList<QueryCondition>();
		queryConditions.add(new QueryCondition("recruitInfo.enterprise.user.id=" + user.getId()));
		Pagination<RecruitUserResume> recruitUserResumePagination = recruitUserResumeService
				.getPagination(RecruitUserResume.class, queryConditions,"order by id desc", page, pageSize);

		List<RecruitUserResume> recruitUserResumeList = new ArrayList<RecruitUserResume>();
		if (recruitUserResumePagination.getRecordCount() > 0) {
			for (RecruitUserResume recruitUserResume : recruitUserResumePagination
					.getRecordList()) {
				RecruitUserResume newObj = new RecruitUserResume();
				if (recruitUserResume.getSendTime() == null) {
					recruitUserResume.setSendTime(new Date());
				}
				BeanUtils.copyProperties(newObj, recruitUserResume);
				UserVo userVo = null;//newObj.setRecruitInfo(null);//簡歷信息
				userVo = recruitUserResume.getUserResume().getUser().getUserVo();
				newObj.setUserVo(userVo);
				recruitUserResumeList.add(newObj);
				LogRecord.info(newObj.toString());
			}
			recruitUserResumePagination.setRecordList(recruitUserResumeList);
		} 
		LogRecord.info(user.getNickName() + "\n" + "简历数为:"	+ recruitUserResumePagination.getRecordCount());
		
		model.addAttribute("result", recruitUserResumePagination);
		if (!request.getRequestURI().endsWith(".json")) {
			model.addAttribute("userId", user.getId());
		}
		return "enterprise/enterpriseUserResumeList";
	}

	@ResponseBody
	@RequestMapping(value = "/updateUnreadResumeInfo", method = RequestMethod.POST)
	public Model updateUnreadResumeInfo(Long recruitUserResumeId, Model model)
			throws Exception {

		RecruitUserResume recruitUserResume = recruitUserResumeService.getById(
				RecruitUserResume.class, recruitUserResumeId);

		if (!recruitUserResume.getReaded()) {
			recruitUserResume.setReaded(true);
			recruitUserResumeService.update(recruitUserResume);

			User enterpriseUser = userService.getById(User.class,
					recruitUserResume.getRecruitInfo().getEnterprise()
							.getUser().getId());

			int count = enterpriseUser.getUnreadResumeCount() - 1 < 0 ? 0
					: enterpriseUser.getUnreadResumeCount() - 1;
			enterpriseUser.setUnreadResumeCount(count);

			userService.update(enterpriseUser);

		}

		return model;
	}

	@RequestMapping("/enterpriseList")
	public String enterpriseList(
			@RequestParam(required = false, defaultValue = "true") boolean isDebarSelf,
			@RequestParam(required = false, defaultValue = "0") int userType,
			@RequestParam(required = false, defaultValue = "1") int page,
			@RequestParam(required = false, defaultValue = "10") int pageSize,
			Model model, HttpServletRequest request) throws Exception {

		User user = (User) request.getSession().getAttribute(
				PublicInfoConfig.currentUser);

		List<QueryCondition> queryConditions = new ArrayList<QueryCondition>();

		if (user != null && isDebarSelf) {
			queryConditions
					.add(new QueryCondition("user.id != " + user.getId()));
		}

		if (userType != 0) {
			queryConditions.add(new QueryCondition("user.userType = "
					+ userType));
		}
		Pagination<Enterprise> enterprisePagination = enterpriseService
				.getPagination(Enterprise.class, queryConditions,
						"order by id desc", page, pageSize);

		List<Enterprise> enterpriseNewList = new ArrayList<Enterprise>();
		if (enterprisePagination.getRecordCount() > 0) {
			List<Enterprise> enterpriseList = enterprisePagination
					.getRecordList();
			for (Enterprise enterprise : enterpriseList) {

				if (enterprise.getUser() != null) {
					enterprise.setUserVo(new UserVo(enterprise.getUser()
							.getId(), enterprise.getUser().getNickName(),
							enterprise.getUser().getHeadImage(), enterprise
									.getUser().getSex(), enterprise.getUser()
									.getEasemobName()));
				}

				enterpriseNewList.add(enterprise);
			}
			enterprisePagination.setRecordList(enterpriseNewList);
		}

		model.addAttribute("result", enterprisePagination);

		return "";
	}

}
