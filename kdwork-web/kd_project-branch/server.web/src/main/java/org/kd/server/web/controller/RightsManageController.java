package org.kd.server.web.controller;

import org.apache.shiro.authz.annotation.RequiresRoles;
import org.kd.server.beans.entity.Permission;
import org.kd.server.beans.entity.Role;
import org.kd.server.beans.entity.User;
import org.kd.server.service.PermissionService;
import org.kd.server.service.RoleService;
import org.kd.server.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/*
 * zmy
 * 
 * 用户角色权限控制器
 * 
 */
@Controller
@RequestMapping("/rightsManage")
public class RightsManageController {
	@Resource(name="roleService")
	private RoleService roleService;
	
	@Resource(name="userService")
	private UserService userService;
	
	@Resource(name="permissionService")
	private PermissionService permissionService;

	//删除所有用户角色 (包括该角色所拥有的权限表信息一起删除)
	@RequestMapping("/delAll")
	@RequiresRoles("administrator")
	public String delAll() throws Exception{
		List<Role> roleList = roleService.getAll(Role.class);
		for (int i = 0; i < roleList.size(); i++) {
			Role role = roleList.get(i);
			roleService.delete(Role.class, role.getId());
		}
	
		return "";
	}
	
	//获取该用户的所有权限
	@RequestMapping("/roles")
	@RequiresRoles("administrator")
	public String roles(Long userId,Model model) throws Exception{
		User user = userService.getById(User.class, userId);
		
		model.addAttribute("roles", user.getRoles());
		return "";
	}
	
	//给指定用户添加角色
	@RequestMapping(value="/addRole",method=RequestMethod.POST)
	@RequiresRoles("administrator")
	public String addRole(Long userId,Long roleId,Model model) throws Exception{
		User user = userService.getById(User.class, userId);
		
		Role role = roleService.getById(Role.class,roleId);
		
		user.getRoles().add(role);
		
		userService.update(user);
		model.addAttribute("userId", user.getId());
		return "";
	}
	
	//给指定用户添加角色
    	@RequiresRoles("administrator")
		@RequestMapping(value="/delRole",method=RequestMethod.POST)
		public String delRole(Long userId,Long roleId,Model model) throws Exception{
			User user = userService.getById(User.class, userId);
			
			Role role = roleService.getById(Role.class,roleId);
			
			user.getRoles().remove(role);
			
			userService.update(user);
			model.addAttribute("userId", user.getId());
			return "";
		}
	
	//权限管理页
    @RequiresRoles("administrator")
	@RequestMapping("/rightsCrudPage")
	public String permissionManagePage(Model model) throws Exception{
		List<Role> roleList = roleService.getAll(Role.class);
		List<User> userList = userService.getAll(User.class);
		
		model.addAttribute("roleList", roleList);
		model.addAttribute("userList", userList);
		return "rightsManagement/rightsCrud";
	}
	
	//初始化角色权限信息
	@RequestMapping("/initData")
	@RequiresRoles("administrator")
	public String initData() throws Exception{
       List<Permission> list = new ArrayList<Permission>();
		
		Permission pmss1 = new Permission();
		pmss1.setName("新建用户");
		pmss1.setDescription("新建用户");
		pmss1.setPermission("user:create");
		
		Permission pmss2 = new Permission();
		pmss2.setName("编辑用户");
		pmss2.setDescription("编辑用户");
		pmss2.setPermission("user:edit");
		
		Permission pmss3 = new Permission();
		pmss3.setName("删除用户");
		pmss3.setDescription("删除用户");
		pmss3.setPermission("user:delete");
		
		Permission pmss4 = new Permission();
		pmss4.setName("审核用户");
		pmss4.setDescription("审核用户");
		pmss4.setPermission("user:audit");
		
		Permission pmss5 = new Permission();
		pmss5.setName("查看用户");
		pmss5.setDescription("查看用户");
		pmss5.setPermission("user:select");
		
		list.add(pmss1);
		list.add(pmss2);
		list.add(pmss3);
		list.add(pmss4);
		list.add(pmss5);
		
		for(Permission pms : list){
			permissionService.save(pms);
		}
		
		List<Permission> list2 = new ArrayList<Permission>();
		list2 = (List<Permission>)permissionService.getAll(Permission.class);
		
		Role role = new Role();
		role.setRole("administrator");
		role.setName("系统管理员角色");
		role.setDescription("系统管理员角色");
		role.setPermissions(list2);
		roleService.save(role);
		
		List<Permission> list3 = new ArrayList<Permission>();
		list3 = permissionService.getByJpql("select p from Permission p where p.name=?0", "查看用户");
		
		Role role2 = new Role();
		role2.setRole("common");
		role2.setName("普通用户角色");
		role2.setDescription("普通用户角色");
		role2.setPermissions(list3);
		roleService.save(role2);
		
		Role role3 = new Role();
		role3.setRole("enterprise");
		role3.setName("企业用户角色");
		role3.setDescription("企业用户角色");
		role3.setPermissions(list3);
		roleService.save(role3);
		
		Role role4 = new Role();
		role3.setRole("admissions");
		role3.setName("招生办用户角色");
		role3.setDescription("招生办用户角色");
		role3.setPermissions(list3);
		roleService.save(role4);

		return "";
	}
}
