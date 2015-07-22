package org.kd.server.beans.param;

public class RetMsg {   
		
	public static final int successStatus = 0;
    public static final String successStatusMsg = "成功";
	
    public static final int loginFailure = 1001;
    public static final String loginFailureMsg = "登录失败";
    
    public static final int registerExistsLoginName = 1002;
    public static final String registerExistsLoginNameMsg = "该用户名已存在，不能使用";
    
    public static final int registerExistsEmail = 1003;
    public static final String registerExistsEmailMsg = "该邮箱地址已被占用，不能使用";
    
    public static final int paramIsEmpty = 1004;
    public static final String paramIsEmptyMsg = "参数为空，验证失败";
    
    public static final int haveNotLogined = 1005;
    public static final String haveNotLoginedMsg = "没有登录，当前会话不存在";
    
    public static final int haveNotRoleAuthorized = 1006;
    public static final String haveNotRoleAuthorizedMsg = "当前用户没有该角色的权限";
    
    public static final int unknowException = 1007;
    public static final String unknowExceptionMsg = "未知的异常";
    
    public static final int registerBeanRegError = 1008;
    public static final String registerBeanRegErrorMsg = "注册用户的参数不符合规定";
    
    public static final int loginNameUnknow = 1009;
    public static final String loginNameUnknowMsg = "用户名错误";
    
    public static final int passwordError = 1010;
    public static final String passwordErrorMsg = "密码错误";
    
    public static final int lockedUser = 1011;
    public static final String lockedUserMsg = "验证未通过，账号已锁定";  
    
    public static final int excessiveError = 1012;
    public static final String excessiveErrorMsg = "验证未通过,错误次数过多";
    
    public static final int uploadImgStandardError= 1013;
    public static final String uploadImgStandardErrorMsg = "上传图片格式不对或图片大小超出限制";
    
    public static final int paramError= 1014;
    public static final String paramErrorMsg = "传参异常";
    
    public static final int cityEmptyError= 1015;
    public static final String cityEmptyErrorMsg = "该城市信息不存在";
    
    public static final int emailError= 1016;
    public static final String emailErrorMsg = "邮箱地址不符合规范";
    
    public static final int userResumeEmptyError= 1017;
    public static final String userResumeEmptyErrorMsg = "用户简历为空";
    
    public static final int repeatAddError= 1018;
    public static final String repeatAddErrorMsg = "不能重复该操作";
    
    public static final int authorizedOverError= 1019;
    public static final String authorizedOverErrorMsg = "没有该权限";
    
    public static final int operateAuthorizedError= 1020;
    public static final String operateAuthorizedErrorMsg = "要操作的数据不属于当前用户";
    
    public static final int registerExistsNickNameError = 1021;
    public static final String registerExistsNickNameErrorMsg = "该用户昵称已存在，不能使用";
    
    public static final int registerEasemobUserError = 1022;
    public static final String registerEasemobUserErrorMsg = "同步注册环信用户失败";
    
    public static final int updateEasemobUserNickNameError = 1023;
    public static final String updateEasemobUserNickNameErrorMsg = "修改环信用户昵称失败";
    
    public static final int addEasemobUserFirendError = 1024;
    public static final String addEasemobUserFirendErrorMsg = "添加环信用户好友失败";
    
    public static final int removeEasemobUserFirendError = 1025;
    public static final String removeEasemobUserFirendErrorMsg = "移除环信用户好友失败";
    
    public static final int emailNoBindingError= 1026;
    public static final String emailNoBindingErrorMsg = "该邮箱地址未绑定账号";
    
    public static final int updateEasemobUserPasswdError = 1027;
    public static final String updateEasemobUserPasswdErrorMsg = "重置环信用户密码失败";
    
    public static final int userEmptyError = 1028;
    public static final String userEmptyErrorMsg = "该用户不存在";
}
