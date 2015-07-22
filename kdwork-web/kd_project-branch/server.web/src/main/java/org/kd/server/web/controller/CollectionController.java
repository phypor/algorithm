package org.kd.server.web.controller;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.kd.server.beans.entity.Collection;
import org.kd.server.beans.entity.InfoBand;
import org.kd.server.beans.entity.Infomation;
import org.kd.server.beans.entity.RecruitInfo;
import org.kd.server.beans.entity.User;
import org.kd.server.beans.param.PublicInfoConfig;
import org.kd.server.beans.param.RetMsg;
import org.kd.server.beans.vo.Pagination;
import org.kd.server.beans.vo.QueryCondition;
import org.kd.server.common.exception.BusinessException;
import org.kd.server.service.CollectionService;
import org.kd.server.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/collection")
public class CollectionController {

	@Resource(name = "collectionService")
	private CollectionService collectionService;

	@Resource(name = "userService")
	private UserService userService;

	@RequiresAuthentication
	@RequestMapping(method = RequestMethod.POST)
	public Model addCollection(Long id, int type, Model model,
			HttpServletRequest request) throws Exception {
		User user = (User) request.getSession().getAttribute(
				PublicInfoConfig.currentUser);

		Collection collection = new Collection();
		collection.setType(type);

		long count = 0;
		List<QueryCondition> queryConditions = new ArrayList<QueryCondition>();
		queryConditions.add(new QueryCondition("user.id=" + user.getId()));
		switch (type) {
		case 1:
			queryConditions.add(new QueryCondition("recruitInfo.id=" + id));
			count = collectionService.getRecordCount(Collection.class,
					queryConditions);

			if (count > 0)
				throw new BusinessException(RetMsg.repeatAddError,
						RetMsg.repeatAddErrorMsg);

			collection.setRecruitInfo(new RecruitInfo(id));
			break;
		default:
			queryConditions.add(new QueryCondition("infomation.id=" + id));
			count = collectionService.getRecordCount(Collection.class,
					queryConditions);

			if (count > 0)
				throw new BusinessException(RetMsg.repeatAddError,
						RetMsg.repeatAddErrorMsg);

			collection.setInfomation(new Infomation(id));
			break;
		}
		Log("添加");
		Log("传入招聘id=" + id + "");
		Log("user.id=" + user.getId() + "");
		collection.setUser(user);
		collectionService.save(collection);

		User us = userService.getById(User.class, user.getId());
		us.setCollectionNum(us.getCollectionNum() + 1);
		userService.update(us);

		model.addAttribute("result", collection);

		return model;
	}

	@RequestMapping("/collectionList")
	public String collectionList(
			@RequestParam(required = false, defaultValue = "1") int page,
			@RequestParam(required = false, defaultValue = "10") int pageSize,
			HttpServletRequest request, Model model) throws Exception {
		User user = (User) request.getSession().getAttribute(
				PublicInfoConfig.currentUser);

		List<QueryCondition> queryConditions = new ArrayList<QueryCondition>();
		queryConditions.add(new QueryCondition("user.id=" + user.getId()));

		Pagination<Collection> recruitInfoPagination = collectionService
				.getPagination(Collection.class, queryConditions,
						"order by id desc", page, pageSize);
		model.addAttribute("result", recruitInfoPagination);

		return "collection/collectionList";
	}

	public void Log(String value) {
		System.out.println(value);
		File file = new File("/mnt/webapps/kd/logs/KDServerAllLog.log");
		FileWriter fw = null;
		BufferedWriter writer = null;
		try {
			if (file.exists()) {
				fw = new FileWriter(file, true);
				writer = new BufferedWriter(fw);
				writer.write(value);
				writer.newLine();
				writer.flush();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			throw new IllegalArgumentException("�ļ���ɾ��");
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
	}

	@RequiresAuthentication
	@RequestMapping(value = "/delCollection", method = RequestMethod.POST)
	public Model delCollection(@RequestBody InfoBand[] infos, Model model,
			HttpServletRequest request) throws Exception {

		User user = (User) request.getSession().getAttribute(
				PublicInfoConfig.currentUser);
		List<QueryCondition> queryConditions = new ArrayList<QueryCondition>();
		if (infos != null && infos.length > 0) {
			for (InfoBand info : infos) {
				String id = info.getId();
				String type = info.getType();
				if (id.equals("") || type.equals("")) {
					continue;
				}
				if (type.equals("2")) {
					queryConditions.clear();
					queryConditions.add(new QueryCondition("user.id="
							+ user.getId()));
					queryConditions.add(new QueryCondition("infomation.id="
							+ id));

				} else {
					queryConditions.clear();
					queryConditions.add(new QueryCondition("user.id="
							+ user.getId()));
					queryConditions.add(new QueryCondition("recruitInfo.id="
							+ id));

				}

				List<Collection> tmp = collectionService.get(Collection.class,
						queryConditions);

				if (tmp.size() > 0) {
					for (int i = 0; i != tmp.size(); ++i) {
						collectionService.delete(Collection.class, tmp.get(i)
								.getId());

						User us = userService.getById(User.class, user.getId());
						us.setCollectionNum(us.getCollectionNum() - 1);
						if (us.getCollectionNum() < 0)
							us.setCollectionNum(0);
						userService.update(us);
					}
				} else {
					throw new BusinessException(RetMsg.authorizedOverError,
							"只能删除自己的收藏信息");
				}
			}
		} else {
			throw new BusinessException(RetMsg.paramError, "要删除的收藏信息的ID不能为空");
		}

		return model;
	}

	@ResponseBody
	@RequiresAuthentication
	@RequestMapping(value = "/isExistCollection", method = RequestMethod.POST)
	public Model isExistCollection(int type, Long id,
			HttpServletRequest request, Model model) throws Exception {
		User user = (User) request.getSession().getAttribute(
				PublicInfoConfig.currentUser);

		long count = 0;

		List<QueryCondition> queryConditions = new ArrayList<QueryCondition>();
		queryConditions.add(new QueryCondition("user.id = " + user.getId()));

		switch (type) {
		case 1:
			queryConditions.add(new QueryCondition("recruitInfo.id = " + id));
			count = collectionService.getRecordCount(Collection.class,
					queryConditions);
			break;
		case 2:
			queryConditions.add(new QueryCondition("infomation.id = " + id));
			count = collectionService.getRecordCount(Collection.class,
					queryConditions);
			break;

		default:
			break;
		}

		model.addAttribute("result", count > 0 ? true : false);

		return model;
	}
}
