package org.kd.server.web.controller;

import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.kd.server.beans.entity.User;
import org.kd.server.beans.entity.UserCircleFirend;
import org.kd.server.beans.param.PublicInfoConfig;
import org.kd.server.beans.vo.Pagination;
import org.kd.server.beans.vo.QueryCondition;
import org.kd.server.beans.vo.UserVo;
import org.kd.server.service.UserCircleFirendService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/userCircleFirend")
public class UserCircleFirendController {
	@Resource(name = "userCircleFirendService")
	private UserCircleFirendService userCircleFirendService;

	// 获取当前用户的朋友列表接口
	@RequiresAuthentication
	@RequestMapping("/userCircleFirendList")
	public String userCircleFirendList(
			@RequestParam(required = false, defaultValue = "1") int page,
			@RequestParam(required = false, defaultValue = "10") int pageSize,
			HttpServletRequest request, Model model) throws Exception {
		User user = (User) request.getSession().getAttribute(
				PublicInfoConfig.currentUser);

		List<QueryCondition> queryConditions = new ArrayList<QueryCondition>();
		queryConditions.add(new QueryCondition("user1.id =" + user.getId()));

		Pagination<UserCircleFirend> userCircleFirendPagination = userCircleFirendService
				.getPagination(UserCircleFirend.class, queryConditions,
						"order by id desc", page, pageSize);

		List<UserCircleFirend> userCircleFirendNewList = new ArrayList<UserCircleFirend>();
		if (userCircleFirendPagination.getRecordCount() > 0) {
			List<UserCircleFirend> userCircleFirendList = userCircleFirendPagination
					.getRecordList();
			for (UserCircleFirend userCircleFirend : userCircleFirendList) {

				userCircleFirend.setUserVo(new UserVo(userCircleFirend
						.getUser().getId(), userCircleFirend.getUser()
						.getNickName(), userCircleFirend.getUser()
						.getHeadImage(), userCircleFirend.getUser().getSex(),
						userCircleFirend.getUser().getEasemobName()));

				userCircleFirendNewList.add(userCircleFirend);
			}
			userCircleFirendPagination.setRecordList(userCircleFirendNewList);
		}
		model.addAttribute("result", userCircleFirendPagination);

		return "userCircleFirend/userCircleFirend";
	}
}
