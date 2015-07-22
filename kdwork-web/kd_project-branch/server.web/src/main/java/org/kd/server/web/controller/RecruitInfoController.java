package org.kd.server.web.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.subject.Subject;
import org.kd.server.beans.entity.DateInterval;
import org.kd.server.beans.entity.Enterprise;
import org.kd.server.beans.entity.MeteringMode;
import org.kd.server.beans.entity.Position;
import org.kd.server.beans.entity.ProvinceCityArea;
import org.kd.server.beans.entity.RecruitInfo;
import org.kd.server.beans.entity.Section;
import org.kd.server.beans.entity.User;
import org.kd.server.beans.param.PublicInfoConfig;
import org.kd.server.beans.param.RetMsg;
import org.kd.server.beans.vo.Pagination;
import org.kd.server.beans.vo.QueryCondition;
import org.kd.server.beans.vo.UserVo;
import org.kd.server.common.exception.BusinessException;
import org.kd.server.common.util.DateUtil;
import org.kd.server.common.util.LogRecord;
import org.kd.server.service.DateIntervalService;
import org.kd.server.service.EnterpriseService;
import org.kd.server.service.MeteringModeService;
import org.kd.server.service.PositionService;
import org.kd.server.service.ProvinceCityAreaService;
import org.kd.server.service.RecruitInfoService;
import org.kd.server.service.SectionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/recruitInfo")
public class RecruitInfoController {
	@Resource(name = "enterpriseService")
	private EnterpriseService enterpriseService;

	@Resource(name = "positionService")
	private PositionService positionService;

	@Resource(name = "recruitInfoService")
	private RecruitInfoService recruitInfoService;

	@Resource(name = "sectionService")
	private SectionService sectionService;

	@Resource(name = "provinceCityAreaService")
	private ProvinceCityAreaService provinceCityAreaService;

	@Resource(name = "dateIntervalService")
	private DateIntervalService dateIntervalService;

	@Resource(name = "meteringModeService")
	private MeteringModeService meteringModeService;

	@RequestMapping("/recruitInfoList")
	public String recruitInfoList(
			@RequestParam(required = false, defaultValue = "1") int page,
			@RequestParam(required = false, defaultValue = "10") int pageSize,
			Model model, HttpServletRequest request) throws Exception {

		List<QueryCondition> queryConditions = new ArrayList<QueryCondition>();

		Pagination<RecruitInfo> recruitInfoPagination = recruitInfoService
				.getPagination(RecruitInfo.class, queryConditions,
						"order by refreshTime desc", page, pageSize);

		List<RecruitInfo> recruitInfoNewList = new ArrayList<RecruitInfo>();
		Enterprise enterprise = null;
		if (recruitInfoPagination.getRecordCount() > 0) {
			List<RecruitInfo> recruitInfoList = recruitInfoPagination
					.getRecordList();
			for (RecruitInfo recruitInfo : recruitInfoList) {

				if (recruitInfo.getEnterprise() != null
						&& recruitInfo.getEnterprise().getUser() != null) {
					enterprise = recruitInfo.getEnterprise();
					enterprise.setUserVo(new UserVo(enterprise.getUser()
							.getId(), enterprise.getUser().getNickName(),
							enterprise.getUser().getHeadImage(), enterprise
									.getUser().getSex(), enterprise.getUser()
									.getEasemobName()));
					recruitInfo.setEnterprise(enterprise);
				}

				recruitInfoNewList.add(recruitInfo);
			}
			recruitInfoPagination.setRecordList(recruitInfoNewList);
		}

		model.addAttribute("result", recruitInfoPagination);

		if (!request.getRequestURI().endsWith(".json")) {
			List<Section> sectionList = sectionService.getAll(Section.class);
			model.addAttribute("sectionList", sectionList);

			List<Position> positionList = positionService
					.getAll(Position.class);
			model.addAttribute("positionList", positionList);

			List<ProvinceCityArea> provinceCityAreaList = provinceCityAreaService
					.getProvince();
			model.addAttribute("provinceCityAreaList", provinceCityAreaList);

			List<DateInterval> dateIntervalList = dateIntervalService
					.getAll(DateInterval.class);
			model.addAttribute("dateIntervalList", dateIntervalList);
		}
		return "recruitInfo/recruitInfoList";
	}

	@RequiresAuthentication
	@RequestMapping("/myRecruitInfoList")
	public String myRecruitInfoList(
			@RequestParam(required = false, defaultValue = "1") int page,
			@RequestParam(required = false, defaultValue = "10") int pageSize,
			Model model, HttpServletRequest request) throws Exception {
		User user = (User) request.getSession().getAttribute(
				PublicInfoConfig.currentUser);

		List<QueryCondition> queryConditions = new ArrayList<QueryCondition>();
		queryConditions.add(new QueryCondition("enterprise.user.id = "
				+ user.getId()));

		Pagination<RecruitInfo> recruitInfoPagination = recruitInfoService
				.getPagination(RecruitInfo.class, queryConditions,
						"order by refreshTime desc", page, pageSize);

		List<RecruitInfo> recruitInfoNewList = new ArrayList<RecruitInfo>();
		Enterprise enterprise = null;
		if (recruitInfoPagination.getRecordCount() > 0) {
			List<RecruitInfo> recruitInfoList = recruitInfoPagination
					.getRecordList();
			for (RecruitInfo recruitInfo : recruitInfoList) {

				if (recruitInfo.getEnterprise() != null
						&& recruitInfo.getEnterprise().getUser() != null) {
					enterprise = recruitInfo.getEnterprise();
					enterprise.setUserVo(new UserVo(enterprise.getUser()
							.getId(), enterprise.getUser().getNickName(),
							enterprise.getUser().getHeadImage(), enterprise
									.getUser().getSex(), enterprise.getUser()
									.getEasemobName()));
					recruitInfo.setEnterprise(enterprise);
				}

				recruitInfoNewList.add(recruitInfo);
			}
			recruitInfoPagination.setRecordList(recruitInfoNewList);
		}

		model.addAttribute("result", recruitInfoPagination);

		return "";
	}

	@ResponseBody
	@RequiresAuthentication
	@RequestMapping(method = RequestMethod.POST)
	public Map<String, Object> addRecruitInfo(
			@RequestParam(required = false, defaultValue = "") String startTime,
			@RequestParam(required = false, defaultValue = "") String endTime,
			HttpServletRequest request, @Valid RecruitInfo recruitInfo,
			BindingResult bind) throws Exception {

		if (recruitInfo.getEnterprise() == null
				|| recruitInfo.getEnterprise().getId() == null) {
			throw new BusinessException(RetMsg.paramError, "关联企业不能为空");
		}
		// LogRecord.info("发布招聘信息 ：id 为  " + recruitInfo.getId());
		User user = (User) request.getSession().getAttribute(
				PublicInfoConfig.currentUser);

		Enterprise enterprise = enterpriseService.getById(Enterprise.class,
				recruitInfo.getEnterprise().getId());
		if (enterprise == null) {
			throw new BusinessException(RetMsg.paramError, "关联企业不存在");
		}

		String check = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
		Pattern regex = Pattern.compile(check);
		Matcher matcher = regex.matcher(enterprise.getEmail());
		if (!matcher.matches()) {
			throw new BusinessException(RetMsg.paramError, "邮箱地址不符合规范");

		}

		/*
		 * if(bind.hasErrors()){ throw new
		 * BusinessException(RetMsg.paramError,"邮箱地址不符合规范"); }
		 */

		Subject userSub = SecurityUtils.getSubject();

		if (!userSub.hasRole("administrator")
				&& !enterprise.getUser().getId().equals(user.getId())) {
			throw new BusinessException(RetMsg.authorizedOverError,
					RetMsg.authorizedOverErrorMsg);
		}

		Date date = new Date();
		recruitInfo.setCreateTime(date);
		if (startTime.matches(DateUtil.dateRegex)
				&& endTime.matches(DateUtil.dateRegex)) {
			recruitInfo.setRecruitEnableTime(DateUtil.parseDateByString(
					startTime, "yyyy-MM-dd"));
			recruitInfo.setRecruitDisableTime(DateUtil.parseDateByString(
					endTime, "yyyy-MM-dd"));
		} else {
			recruitInfo.setRecruitEnableTime(date);
			recruitInfo.setRecruitDisableTime(DateUtil.addYearsToDate(date, 1));
		}

		if (recruitInfo.getId() == null || recruitInfo.getId() == 0) {
			recruitInfo.setId(null);
			recruitInfo.setRefreshTime(new Date());
			recruitInfoService.save(recruitInfo);
		} else {
			recruitInfoService.update(recruitInfo);
		}

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("status", PublicInfoConfig.SUCCESS_CODE);
		map.put("msg", PublicInfoConfig.SUCCESS_MSG);

		if (recruitInfo.getEnterprise() != null
				&& recruitInfo.getEnterprise().getUser() != null) {
			Enterprise enterprise2 = recruitInfo.getEnterprise();
			enterprise2.setUserVo(new UserVo(enterprise2.getUser().getId(),
					enterprise2.getUser().getNickName(), enterprise2.getUser()
							.getHeadImage(), enterprise2.getUser().getSex(),
					enterprise2.getUser().getEasemobName()));
			recruitInfo.setEnterprise(enterprise2);
		}

		map.put("result", recruitInfo);

		return map;
	}

	@ResponseBody
	@RequestMapping("/findByEnterpriseId")
	public Map<String, Object> findByEnterpriseId(Long enterpriseId)
			throws Exception {
		Enterprise enterprise1 = enterpriseService.getById(Enterprise.class,
				enterpriseId);

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("status", PublicInfoConfig.SUCCESS_CODE);
		map.put("msg", PublicInfoConfig.SUCCESS_MSG);

		List<RecruitInfo> recruitInfoList = enterprise1.getRecruitInfos();
		List<RecruitInfo> recruitInfoNewList = new ArrayList<RecruitInfo>();

		if (!recruitInfoList.isEmpty()) {
			Enterprise enterprise = null;
			for (RecruitInfo recruitInfo : recruitInfoList) {
				if (recruitInfo.getEnterprise() != null
						&& recruitInfo.getEnterprise().getUser() != null) {
					enterprise = recruitInfo.getEnterprise();
					enterprise.setUserVo(new UserVo(enterprise.getUser()
							.getId(), enterprise.getUser().getNickName(),
							enterprise.getUser().getHeadImage(), enterprise
									.getUser().getSex(), enterprise.getUser()
									.getEasemobName()));
					recruitInfo.setEnterprise(enterprise);
					recruitInfoNewList.add(recruitInfo);
				}
			}
		}

		map.put("result", recruitInfoNewList);
		return map;
	}

	// 删除招聘信息接口
	@RequiresRoles("administrator")
	@ResponseBody
	@RequestMapping(value = "/delRecruitInfo", method = RequestMethod.POST)
	public Model delRecruitInfo(Long id, Model model) throws Exception {
		recruitInfoService.delete(RecruitInfo.class, id);
		return model;
	}

	@RequestMapping("/search")
	public String searchRecruitInfo(
			@RequestParam(required = false, defaultValue = "19") Long provinceId,
			@RequestParam(required = false, defaultValue = "234") Long cityId,
			@RequestParam(required = false, defaultValue = "") Long districtId,
			@RequestParam(required = false, defaultValue = "") Long sectionId,
			@RequestParam(required = false, defaultValue = "") Long positionId,
			@RequestParam(required = false, defaultValue = "1") int page,
			@RequestParam(required = false, defaultValue = "10") int pageSize,
			@RequestParam(required = false, defaultValue = "1") int dateIntervalId,
			Model model, HttpServletRequest request) throws Exception {

		List<QueryCondition> queryConditions = new ArrayList<QueryCondition>();
		if (provinceId != null) {
			queryConditions
					.add(new QueryCondition("province.id=" + provinceId));
		}

		if (cityId != null) {
			queryConditions.add(new QueryCondition("city.id=" + cityId));
		}

		if (districtId != null) {
			queryConditions
					.add(new QueryCondition("district.id=" + districtId));
		}

		if (sectionId != null) {
			queryConditions.add(new QueryCondition("section.id=" + sectionId));
		}

		if (positionId != null) {
			queryConditions
					.add(new QueryCondition("position.id=" + positionId));
		}

		if (dateIntervalId != 1) {
			Date date = null;
			switch (dateIntervalId) {
			case 2:
				date = DateUtil.parseDateByString(
						DateUtil.formatDateByString(new Date(), "yyyy-MM-dd"),
						"yyyy-MM-dd");
				break;
			case 3:
				date = DateUtil.findFirstDayOfWork(new Date());
				break;
			case 4:
				date = DateUtil.findFirstDayOfMonth(new Date());
				break;
			case 5:
				date = DateUtil.findMonthBeforeOrAfter(new Date(), -2);
				break;
			case 6:
				date = DateUtil.findMonthBeforeOrAfter(new Date(), -3);
				break;
			default:
				break;
			}
			if (date != null) {
				queryConditions.add(new QueryCondition("recruitEnableTime",
						QueryCondition.GE, date));
			}

		}

		Pagination<RecruitInfo> recruitInfoPagination = recruitInfoService
				.getPagination(RecruitInfo.class, queryConditions,
						"order by refreshTime desc", page, pageSize);

		List<RecruitInfo> recruitInfoNewList = new ArrayList<RecruitInfo>();
		Enterprise enterprise = null;
		if (recruitInfoPagination.getRecordCount() > 0) {
			List<RecruitInfo> recruitInfoList = recruitInfoPagination
					.getRecordList();
			for (RecruitInfo recruitInfo : recruitInfoList) {

				if (recruitInfo.getEnterprise() != null
						&& recruitInfo.getEnterprise().getUser() != null) {
					enterprise = recruitInfo.getEnterprise();
					enterprise.setUserVo(new UserVo(enterprise.getUser()
							.getId(), enterprise.getUser().getNickName(),
							enterprise.getUser().getHeadImage(), enterprise
									.getUser().getSex(), enterprise.getUser()
									.getEasemobName()));
					recruitInfo.setEnterprise(enterprise);
				}

				recruitInfoNewList.add(recruitInfo);
			}
			recruitInfoPagination.setRecordList(recruitInfoNewList);
		}

		model.addAttribute("result", recruitInfoPagination);

		if (!request.getRequestURI().endsWith(".json")) {
			List<Position> positionList = positionService
					.getAll(Position.class);
			model.addAttribute("positionList", positionList);

			List<Section> sectionList = sectionService.getAll(Section.class);
			model.addAttribute("sectionList", sectionList);

			List<ProvinceCityArea> provinceCityAreaList = provinceCityAreaService
					.getProvince();
			model.addAttribute("provinceCityAreaList", provinceCityAreaList);

			List<DateInterval> dateIntervalList = dateIntervalService
					.getAll(DateInterval.class);
			model.addAttribute("dateIntervalList", dateIntervalList);

			model.addAttribute("provinceId", provinceId);
			model.addAttribute("cityId", cityId);
			model.addAttribute("districtId", districtId);
			model.addAttribute("positionId", positionId);
			model.addAttribute("dateIntervalId", dateIntervalId);
			model.addAttribute("sectionId", sectionId);
		}
		return "recruitInfo/recruitInfoList";
	}

	@ResponseBody
	@RequestMapping("/sectionList")
	public Model sectionList(Model model) throws Exception {
		List<Section> sectionList = sectionService.getAll(Section.class);
		model.addAttribute("result", sectionList);

		return model;
	}

	@ResponseBody
	@RequestMapping("/meteringModeList")
	public Model meteringModeList(Model model) throws Exception {
		List<MeteringMode> meteringModeList = meteringModeService
				.getAll(MeteringMode.class);
		model.addAttribute("result", meteringModeList);

		return model;
	}
}
