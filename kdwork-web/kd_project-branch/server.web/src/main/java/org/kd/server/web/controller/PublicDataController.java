package org.kd.server.web.controller;

import org.kd.server.beans.entity.*;
import org.kd.server.beans.vo.QueryCondition;
import org.kd.server.common.util.ValidateCode;
import org.kd.server.service.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller
public class PublicDataController {

	@Resource(name = "companyNatureService")
	private CompanyNatureService companyNatureService;

	@Resource(name = "positionService")
	private PositionService positionService;

	@Resource(name = "degreesService")
	private DegreesService DegreesService;
	@Resource(name = "schoolService")
	private SchoolService schoolService;
	@Resource(name = "gradeService")
	private GradeService gradeService;

	@Resource(name = "industryService")
	private IndustryService industryService;

	@Resource(name = "quantityScaleService")
	private QuantityScaleService quantityScaleService;

	@Resource(name = "complainTypeService")
	private ComplainTypeService complainTypeService;

	/**
	 * 生成验证码
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(value = "/validateCode")
	public void validateCode(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		response.setHeader("Cache-Control", "no-cache");
		String verifyCode = ValidateCode.generateTextCode(
				ValidateCode.TYPE_NUM_UPPER, 4, null);
		request.getSession().setAttribute("validateCode", verifyCode);
		response.setContentType("image/jpeg");
		BufferedImage bim = ValidateCode.generateImageCode(verifyCode, 90, 30,
				3, true, Color.WHITE, Color.BLACK, null);
		ImageIO.write(bim, "JPEG", response.getOutputStream());
	}

	// 获取年级列表
	@ResponseBody
	@RequestMapping("/schoolsList")
	public Model schoolsList(Model model,
			@RequestParam(required = true, defaultValue = "-1") Long proId)
			throws Exception {
		if (proId == -1) {
			model.addAttribute("result", "failed to get schools");
			return model;
		}
		List<QueryCondition> queryConditions = new ArrayList<QueryCondition>();
		queryConditions.add(new QueryCondition("proId", QueryCondition.EQ,
				proId));
		List<School> list = (List<School>) schoolService.get(School.class,
				queryConditions);
		model.addAttribute("result", list);
		return model;
	}

	// 获取年级列表
	@ResponseBody
	@RequestMapping("/gradesList")
	public Model gradesList(Model model) throws Exception {
		List<Grade> list = (List<Grade>) gradeService.getAll(Grade.class);
		model.addAttribute("result", list);
		return model;
	}

	// 获取学历列表
	@ResponseBody
	@RequestMapping("/degreesList")
	public Model degreesList(Model model) throws Exception {
		List<Degrees> list = (List<Degrees>) DegreesService
				.getAll(Degrees.class);
		model.addAttribute("result", list);
		return model;
	}

	// 获取职位信息列表
	@ResponseBody
	@RequestMapping("/positionList")
	public Model positionList(Model model,
			@RequestParam(required = false, defaultValue = "0") Long parentId,
			Long sectionId) throws Exception {
		// model.addAttribute("result", positionService.getAll(Position.class));
		if (sectionId != null) {
			if (parentId == null || new Long(0).equals(parentId)) {
				List<QueryCondition> queryConditions = new ArrayList<QueryCondition>();
				queryConditions.add(new QueryCondition("section.id",
						QueryCondition.EQ, sectionId));
				queryConditions.add(new QueryCondition("position.id",
						QueryCondition.EQ, null));
				List<Position> list = (List<Position>) positionService.get(
						Position.class, queryConditions);
				model.addAttribute("result", list);
			} else {

				List<QueryCondition> queryConditions = new ArrayList<QueryCondition>();
				queryConditions.add(new QueryCondition("section.id",
						QueryCondition.EQ, sectionId));
				queryConditions.add(new QueryCondition("position.id",
						QueryCondition.EQ, parentId));
				List<Position> list = positionService.get(Position.class,
						queryConditions);
				model.addAttribute("result", list);
			}

		}
		return model;
	}

	// 获取行业信息列表
	@ResponseBody
	@RequestMapping("/industryList")
	public Model industryList(Model model) throws Exception {
		model.addAttribute("result", industryService.getAll(Industry.class));

		return model;
	}

	// 获取公司性质信息列表
	@ResponseBody
	@RequestMapping("/companyNatureList")
	public Model companyNatureList(Model model) throws Exception {
		model.addAttribute("result",
				companyNatureService.getAll(CompanyNature.class));

		return model;
	}

	// 获取人员规模信息列表
	@ResponseBody
	@RequestMapping("/quantityScaleList")
	public Model quantityScaleList(Model model) throws Exception {
		model.addAttribute("result",
				quantityScaleService.getAll(QuantityScale.class));

		return model;
	}

	// 获取投诉类型信息列表
	@ResponseBody
	@RequestMapping("/complainTypeList")
	public Model complainTypeList(Model model) throws Exception {
		model.addAttribute("result",
				complainTypeService.getAll(ComplainType.class));

		return model;
	}
}
