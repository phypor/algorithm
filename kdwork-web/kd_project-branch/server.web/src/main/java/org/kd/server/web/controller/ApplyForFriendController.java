package org.kd.server.web.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.kd.server.beans.entity.ApplyForFriend;
import org.kd.server.beans.entity.FriendCircle;
import org.kd.server.beans.entity.User;
import org.kd.server.beans.param.PublicInfoConfig;
import org.kd.server.beans.param.RetMsg;
import org.kd.server.beans.vo.Pagination;
import org.kd.server.beans.vo.QueryCondition;
import org.kd.server.common.exception.BusinessException;
import org.kd.server.common.util.EasemobUtil;
import org.kd.server.common.util.JsonUtil;
import org.kd.server.service.ApplyForFriendService;
import org.kd.server.service.UserCircleFirendService;
import org.kd.server.service.UserCircleInfoService;
import org.kd.server.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@Transactional
@RequestMapping("/applyForFriend")
public class ApplyForFriendController {
	@Resource(name = "userService")
	private UserService userService;

	@Resource(name = "applyForFriendService")
	private ApplyForFriendService applyForFriendService;

	@Resource(name = "userCircleInfoService")
	private UserCircleInfoService userCircleInfoService;

	@Resource(name = "userCircleFirendService")
	private UserCircleFirendService userCircleFirendService;

	@RequiresAuthentication
	@RequestMapping("/applyForFriendList")
	public String applyForFriendList(
			@RequestParam(required = false, defaultValue = "1") int page,
			@RequestParam(required = false, defaultValue = "10") int pageSize,
			HttpServletRequest request, Model model) throws Exception {

		User user = (User) request.getSession().getAttribute(
				PublicInfoConfig.currentUser);
		if (!request.getRequestURI().endsWith(".json")) {
			List<User> userList = userService.getAll(User.class);
			model.addAttribute("userList", userList);
		}
		List<QueryCondition> queryConditions = new ArrayList<QueryCondition>();
		queryConditions.add(new QueryCondition("examinationUser.id="
				+ user.getId()));
		queryConditions.add(new QueryCondition("approval=0"));

		Pagination<ApplyForFriend> applyForFriendPagination = applyForFriendService
				.getPagination(ApplyForFriend.class, queryConditions,
						"order by id desc", page, pageSize);

		model.addAttribute("result", applyForFriendPagination);
		return "applyForFriend/applyForFriendList";
	}

	@ResponseBody
	@RequiresAuthentication
	@RequestMapping(value = "/applyAddFriend", method = RequestMethod.POST)
	public Model applyAddFriend(
			Long userId,
			@RequestParam(required = false, defaultValue = "") String applyDesc,
			Model model, HttpServletRequest request) throws Exception {
		User user = (User) request.getSession().getAttribute(
				PublicInfoConfig.currentUser);

		ApplyForFriend applyForFriend = (ApplyForFriend) applyForFriendService
				.findByApplyExaminationUserId(user.getId(), userId);

		if (applyForFriend == null) {
			applyForFriend = new ApplyForFriend();
			applyForFriend.setApplyUser(user);
			applyForFriend.setExaminationUser(new User(userId));
			applyForFriend.setApplyTime(new Date());
			applyForFriend.setApplyDesc(applyDesc);
			applyForFriend.setApproval(0);
			applyForFriendService.save(applyForFriend);
		} else {
			applyForFriend.setApplyTime(new Date());
			applyForFriend.setApplyDesc(applyDesc);
			applyForFriendService.update(applyForFriend);
		}

		model.addAttribute("result", applyForFriend);
		return model;
	}

	@Transactional
	@ResponseBody
	@RequiresAuthentication
	@RequestMapping(value = "/handleApply", method = RequestMethod.POST)
	public Model handleApply(int approval, Long applyForFriendId, Model model,
			HttpServletRequest request) throws Exception {
		User user = (User) request.getSession().getAttribute(
				PublicInfoConfig.currentUser);

		ApplyForFriend applyForFriend = applyForFriendService.getById(
				ApplyForFriend.class, applyForFriendId);

		applyForFriend.setApproval(approval);
		switch (approval) {
		case 1:
			becomeFriend(user, applyForFriend.getApplyUser());
			break;
		case 3:
			user.getUserCircles().remove(applyForFriend.getApplyUser());
			userService.update(user);

			String retContent = EasemobUtil.removeFriendUser(user
					.getEasemobName(), applyForFriend.getApplyUser()
					.getEasemobName());
			if (JsonUtil.json2Bean(retContent, Map.class).get("error") != null) {
				throw new BusinessException(
						RetMsg.removeEasemobUserFirendError,
						RetMsg.removeEasemobUserFirendErrorMsg);
			}
			break;
		default:
			break;
		}

		return model;
	}

	@ResponseBody
	@RequestMapping(value = "/becomeEasemobFirend", method = RequestMethod.POST)
	public Model becomeEasemobFirend(String easemobName1, String easemobName2,
			Model model) throws Exception {
		User user1 = userService.getUserByEasemobName(easemobName1);
		User user2 = userService.getUserByEasemobName(easemobName2);

		becomeFriend(user1, user2);
		return model;
	}

	private void becomeFriend(User user1, User user2) throws Exception {
		if (user1 != null && user2 != null) {
			if (!user1.getUserCircles().contains(user2)) {
				user1.getUserCircles().add(user2);
				userService.update(user1);

				String retContent = EasemobUtil.addFriendUser(
						user1.getEasemobName(), user2.getEasemobName());
				if (JsonUtil.json2Bean(retContent, Map.class).get("error") != null) {
					throw new BusinessException(
							RetMsg.addEasemobUserFirendError,
							RetMsg.addEasemobUserFirendErrorMsg);
				}
			}

			FriendCircle friendCircle1 = userCircleInfoService
					.findFriendCircleByUserId(user1.getId());

			User friend = userService.getById(User.class, user2.getId());
			if (friendCircle1 != null
					&& !friend.getFriendCircles().contains(friendCircle1)) {
				friend.getFriendCircles().add(friendCircle1);
				userService.update(friend);
			}

			if (!friend.getUserCircles().contains(user1)) {
				friend.getUserCircles().add(user1);
				userService.update(friend);

				String retContent = EasemobUtil.addFriendUser(
						friend.getEasemobName(), user1.getEasemobName());
				if (JsonUtil.json2Bean(retContent, Map.class).get("error") != null) {
					throw new BusinessException(
							RetMsg.addEasemobUserFirendError,
							RetMsg.addEasemobUserFirendErrorMsg);
				}
			}

			FriendCircle friendCircle2 = userCircleInfoService
					.findFriendCircleByUserId(user2.getId());

			if (friendCircle2 != null
					&& !user1.getFriendCircles().contains(friendCircle2)) {
				user1.getFriendCircles().add(friendCircle2);
				userService.update(user1);
			}
		} else {
			throw new BusinessException(RetMsg.userEmptyError,
					RetMsg.userEmptyErrorMsg);
		}
	}
}
