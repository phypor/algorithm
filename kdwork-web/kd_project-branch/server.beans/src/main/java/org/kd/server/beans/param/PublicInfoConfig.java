package org.kd.server.beans.param;

public class PublicInfoConfig {    
	 public static final int SUCCESS_CODE = 0;
	 public static final String SUCCESS_MSG = "成功";
	
	 public static final String currentUser = "current_user";
	 public static final String emailRegex = "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";
	
     public static final int consumerUser = 1;   //普通用户
     public static final int enterpriseUser = 2;   //企业用户
     public static final int employMentUser = 3;   //就业办用户
     public static final int managerUser = 4;   //系统管理员
     
     public static final int BINDING_EMAIL = 1;   //绑定邮箱地址
     public static final int RESET_PASSWD_BY_EMAIL = 2;   //通过邮箱地址重置密码
}
