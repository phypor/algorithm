package org.kd.server.web.controller;

import org.kd.server.beans.entity.ProvinceCityArea;
import org.kd.server.beans.param.PublicInfoConfig;
import org.kd.server.beans.param.RetMsg;
import org.kd.server.beans.vo.QueryCondition;
import org.kd.server.common.exception.BusinessException;
import org.kd.server.common.util.LogRecord;
import org.kd.server.service.ProvinceCityAreaService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@Controller
public class ProvinceCityAreaController {
	@Resource(name = "provinceCityAreaService")
	private ProvinceCityAreaService provinceCityAreaService;

	@RequestMapping("/provinceCityArea/initData")
	public String initData(HttpServletRequest request, Model model)
			throws Exception {
		// URL file = this.getClass().getClassLoader().getResource("area.txt");
		String file_path = "C:/area.txt";
		int count = 0;
		BufferedReader br = new BufferedReader(new InputStreamReader(
				new FileInputStream(file_path/* file.getFile() */)));
		for (String line = br.readLine(); line != null; line = br.readLine()) {
			System.out.println(line);
			String[] array = line.split(",");

			ProvinceCityArea provinceCityArea = new ProvinceCityArea();

			provinceCityArea.setName(array[1].replace("\"", ""));

			if (array.length > 2) {
				provinceCityArea.setProvinceCityArea(new ProvinceCityArea(Long
						.parseLong(array[2])));
			}

			provinceCityAreaService.save(provinceCityArea);

			count++;
		}
		br.close();
		model.addAttribute("count", count);
		return "";
	}

	@ResponseBody
	@RequestMapping(value = "/provinceCityArea/findCityByName", method = RequestMethod.POST)
	public Model findCityByName(String cityName, Model model) throws Exception {

		List<QueryCondition> queryConditions = new ArrayList<QueryCondition>();
		queryConditions.add(new QueryCondition("name", QueryCondition.EQ,
				cityName.trim()));

		ProvinceCityArea provinceCityArea = (ProvinceCityArea) provinceCityAreaService
				.getSingleResult(ProvinceCityArea.class, queryConditions);

		if (provinceCityArea == null) {
			LogRecord.error("该城市信息不存在-->" + cityName);

			throw new BusinessException(RetMsg.cityEmptyError, "该城市信息不存在");
		}

		model.addAttribute("status", PublicInfoConfig.SUCCESS_CODE);
		model.addAttribute("msg", PublicInfoConfig.SUCCESS_MSG);
		model.addAttribute("result", provinceCityArea);
		return model;
	}

	@ResponseBody
	@RequestMapping("/provinceCityArea")
	public Map<String, Object> provinceCityArea(
			@RequestParam(required = false, defaultValue = "0") Long id,
			HttpServletRequest request) throws Exception {
		List<ProvinceCityArea> provinceCityAreaList = null;
		String pro[] = { "广东省", "北京市", "上海市", "重庆市", "天津市", "福建省", "湖南省",
				"湖北省", "江苏省", "江西省", "浙江省", "安徽省", "四川省", "云南省", "海南省", "山西省",
				"河南省", "河北省", "山东省", "辽宁省", "吉林省", "陕西省", "甘肃省", "青海省", "贵州省",
				"台湾省", "黑龙江省", "西藏自治区", "内蒙古自治区", "香港特别行政区", "澳门特别行政区",
				"宁夏回族自治区", "广西壮族自治区", "新疆维吾尔自治区" };
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("status", PublicInfoConfig.SUCCESS_CODE);
		map.put("msg", PublicInfoConfig.SUCCESS_MSG);

		if (id == null || new Long(0).equals(id)) {
			List<ProvinceCityArea> tmp = new ArrayList<ProvinceCityArea>();
			provinceCityAreaList = provinceCityAreaService.getProvince();
			for (int i = 0; i != pro.length; ++i) {
				Iterator<ProvinceCityArea> iter = provinceCityAreaList
						.iterator();
				while (iter.hasNext()) {
					ProvinceCityArea p = iter.next();
					if (pro[i].equals(p.getName())) {
						tmp.add(p);
						iter.remove();
						break;
					}
				}
			}
			provinceCityAreaList = tmp;
		} else {
			provinceCityAreaList = provinceCityAreaService.findByParentId(id);
		}

		map.put("result", provinceCityAreaList);
		return map;
	}
}
