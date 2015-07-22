package org.kd.server.common.util;

import java.io.File;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSession;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.methods.DeleteMethod;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.PutMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.commons.httpclient.protocol.Protocol;
import org.apache.commons.lang.StringUtils;
import org.kd.server.beans.entity.UserEntity;

/*
 * 环信用户管理工具类
 * 
 */
public class EasemobUtil {

	private static final String TOKEN_URL = "https://a1.easemob.com/guobi/kdwork/token";
	private static final String POST_USER_URL = "https://a1.easemob.com/guobi/kdwork/users";
	private static final String TOKE_REQUESTBODY = "{\"grant_type\":\"client_credentials\",\"client_id\":\"YXA6CbxmcABKEeWmV885Ltj-pQ\",\"client_secret\": \"YXA6LWr0PY2f9LqntRfdjTxl2dQTApw\"}";
	private static final String ENCODING = "UTF-8";
	private static final String tokenFileName = System.getProperty("user.dir")
			+ "/easeMob_access_token.txt";

	/*
	 * 获取TOKEN
	 */
	private static String getToken() throws Exception {
		FileOperation.createFile(new File(tokenFileName));
		Map<String, Object> retMap = FileOperation
				.readEaseMobTokenTxtFile(new File(tokenFileName));
		String accessToken = (String) retMap.get("access_token");
		System.out.println(accessToken);
		// LogRecord.info("accessToken : " + accessToken);
		if (StringUtils.isEmpty(accessToken)) {
			accessToken = createToken();
		} else {
			int expiresIn = Integer.parseInt(retMap.get("expires_in")
					.toString());
			long lastUpdateTime = Long.parseLong(retMap.get("lastUpdateTime")
					.toString());
			long nowTime = new Date().getTime();
			if (nowTime > (expiresIn * 1000 + lastUpdateTime - 5000)) {
				accessToken = createToken();
			}
		}
		return accessToken;
	}

	/*
	 * 保存TOKEN
	 */
	@SuppressWarnings("unchecked")
	private static String saveToken(String retCn) throws Exception {
		Map<String, Object> retMap = JsonUtil.json2Bean(retCn, Map.class);
		String accessToken = (String) retMap.get("access_token");
		if (!StringUtils.isEmpty(accessToken)) {
			int expiresIn = (Integer) retMap.get("expires_in");
			String application = (String) retMap.get("application");
			long lastUpdateTime = new Date().getTime();
			String content = "access_token:" + accessToken + "\r\nexpires_in:"
					+ expiresIn + "\r\napplication:" + application
					+ "\r\nlastUpdateTime:" + lastUpdateTime;

			FileOperation.writeTxtFile(content, new File(tokenFileName));
		}
		return accessToken;
	}

	/*
	 * 生成TOKEN
	 */
	@SuppressWarnings("deprecation")
	private static String createToken() throws Exception {

		HttpClient client = new HttpClient();
		PostMethod method = new PostMethod(TOKEN_URL);
		method.setRequestBody(TOKE_REQUESTBODY);

		method.setRequestHeader("Content-Type", "application/json");
		HttpMethodParams param = method.getParams();
		param.setContentCharset(ENCODING);
		client.executeMethod(method);
		return saveToken(method.getResponseBodyAsString());
	}

	/*
	 * 注册IM用户[单个] 通过授权方式注册用户
	 */
	@SuppressWarnings("deprecation")
	public static String registerEasemobUser(String username, String password)
			throws Exception {

		String accessToken = getToken();
		if (StringUtils.isEmpty(accessToken)) {
			return null;
		}
		HttpClient client = new HttpClient();

		PostMethod method = new PostMethod(POST_USER_URL);
		method.setRequestBody("{\"username\":\"" + username
				+ "\",\"password\":\"" + password + "\"}");

		method.setRequestHeader("Content-Type", "application/json");
		method.setRequestHeader("Authorization", "Bearer " + accessToken);
		HttpMethodParams param = method.getParams();
		param.setContentCharset(ENCODING);
		client.executeMethod(method);
		return method.getResponseBodyAsString();
	}

	/*
	 * 注册IM用户[批量]
	 */
	@SuppressWarnings("deprecation")
	public static String registerEasemobUser(List<UserEntity> userList)
			throws Exception {

		String accessToken = getToken();
		if (StringUtils.isEmpty(accessToken) || userList.isEmpty()) {
			return null;
		}

		HttpClient client = new HttpClient();

		PostMethod method = new PostMethod(POST_USER_URL);
		method.setRequestBody(JsonUtil.bean2Json(userList));

		method.setRequestHeader("Content-Type", "application/json");
		method.setRequestHeader("Authorization", "Bearer " + accessToken);
		HttpMethodParams param = method.getParams();
		param.setContentCharset(ENCODING);
		client.executeMethod(method);
		return method.getResponseBodyAsString();
	}

	/*
	 * 获取IM用户[单个]
	 */
	public static String getOneUser(String username) throws Exception {

		String accessToken = getToken();
		if (StringUtils.isEmpty(accessToken)) {
			return null;
		}

		HttpClient client = new HttpClient();

		HttpMethod method = new GetMethod(POST_USER_URL + "/" + username);

		method.setRequestHeader("Authorization", "Bearer " + accessToken);
		HttpMethodParams param = method.getParams();
		param.setContentCharset(ENCODING);
		client.executeMethod(method);
		return method.getResponseBodyAsString();
	}

	/*
	 * 获取IM用户[批量]
	 * 
	 * cursor 上次请求该接口时返回的cursor 如果没有请传空值
	 */
	public static String getAnyUser(int limitUserSize, String cursor)
			throws Exception {

		String accessToken = getToken();
		if (StringUtils.isEmpty(accessToken)) {
			return null;
		}

		HttpClient client = new HttpClient();

		String psUrl = POST_USER_URL + "?limit=" + limitUserSize;
		if (!StringUtils.isEmpty(cursor)) {
			psUrl += "&cursor=" + cursor;
		}

		HttpMethod method = new GetMethod(psUrl);

		method.setRequestHeader("Authorization", "Bearer " + accessToken);
		HttpMethodParams param = method.getParams();
		param.setContentCharset(ENCODING);
		client.executeMethod(method);
		return method.getResponseBodyAsString();
	}

	/*
	 * 删除IM用户[单个]
	 */
	public static String delOneUser(String username) throws Exception {
		String accessToken = getToken();
		if (StringUtils.isEmpty(accessToken)) {
			return null;
		}

		HttpClient client = new HttpClient();

		HttpMethod method = new DeleteMethod(POST_USER_URL + "/" + username);

		method.setRequestHeader("Authorization", "Bearer " + accessToken);
		HttpMethodParams param = method.getParams();
		param.setContentCharset(ENCODING);
		client.executeMethod(method);
		return method.getResponseBodyAsString();
	}

	/*
	 * 删除IM用户[批量]
	 */
	public static String delAnyUser(int limitUserSize) throws Exception {
		String accessToken = getToken();
		if (StringUtils.isEmpty(accessToken)) {
			return null;
		}

		HttpClient client = new HttpClient();

		HttpMethod method = new DeleteMethod(POST_USER_URL + "?limit="
				+ limitUserSize);

		method.setRequestHeader("Authorization", "Bearer " + accessToken);
		HttpMethodParams param = method.getParams();
		param.setContentCharset(ENCODING);
		client.executeMethod(method);
		return method.getResponseBodyAsString();
	}

	/*
	 * 重置IM用户密码
	 */
	@SuppressWarnings("deprecation")
	public static String resetUserPaswd(String username, String newPassword)
			throws Exception {

		String accessToken = getToken();
		if (StringUtils.isEmpty(accessToken)) {
			return null;
		}

		HttpClient client = new HttpClient();

		PutMethod method = new PutMethod(POST_USER_URL + "/" + username
				+ "/password");
		method.setRequestBody("{\"newpassword\":\"" + newPassword + "\"}");

		method.setRequestHeader("Authorization", "Bearer " + accessToken);
		HttpMethodParams param = method.getParams();
		param.setContentCharset(ENCODING);
		client.executeMethod(method);
		return method.getResponseBodyAsString();
	}

	/*
	 * 修改用户昵称
	 */
	@SuppressWarnings("deprecation")
	public static String resetUserNickName(String username, String nickname)
			throws Exception {

		String accessToken = getToken();
		if (StringUtils.isEmpty(accessToken)) {
			return null;
		}

		HttpClient client = new HttpClient();

		PutMethod method = new PutMethod(POST_USER_URL + "/" + username);
		method.setRequestBody("{\"nickname\":\"" + nickname + "\"}");

		method.setRequestHeader("Authorization", "Bearer " + accessToken);
		HttpMethodParams param = method.getParams();
		param.setContentCharset(ENCODING);
		client.executeMethod(method);
		return method.getResponseBodyAsString();
	}

	/*
	 * 给IM用户的添加好友
	 */
	public static String addFriendUser(String ownerUsername,
			String friendUsername) throws Exception {
		String accessToken = getToken();
		if (StringUtils.isEmpty(accessToken)) {
			return null;
		}

		HttpClient client = new HttpClient();

		PostMethod method = new PostMethod(POST_USER_URL + "/" + ownerUsername
				+ "/contacts/users/" + friendUsername);

		method.setRequestHeader("Content-Type", "application/json");
		method.setRequestHeader("Authorization", "Bearer " + accessToken);
		HttpMethodParams param = method.getParams();
		param.setContentCharset(ENCODING);
		client.executeMethod(method);
		return method.getResponseBodyAsString();
	}

	/*
	 * 解除IM用户的好友关系
	 */
	public static String removeFriendUser(String ownerUsername,
			String friendUsername) throws Exception {
		String accessToken = getToken();
		if (StringUtils.isEmpty(accessToken)) {
			return null;
		}

		HttpClient client = new HttpClient();

		HttpMethod method = new DeleteMethod(POST_USER_URL + "/"
				+ ownerUsername + "/contacts/users/" + friendUsername);

		method.setRequestHeader("Authorization", "Bearer " + accessToken);
		HttpMethodParams param = method.getParams();
		param.setContentCharset(ENCODING);
		client.executeMethod(method);
		return method.getResponseBodyAsString();
	}

	/*
	 * 查看好友
	 */
	public static String findFirendThisUser(String ownerUsername)
			throws Exception {
		String accessToken = getToken();
		if (StringUtils.isEmpty(accessToken)) {
			return null;
		}

		HttpClient client = new HttpClient();
		HttpMethod method = new GetMethod(POST_USER_URL + "/" + ownerUsername
				+ "/contacts/users");

		method.setRequestHeader("Authorization", "Bearer " + accessToken);
		HttpMethodParams param = method.getParams();
		param.setContentCharset(ENCODING);
		client.executeMethod(method);
		return method.getResponseBodyAsString();
	}

	/*
	 * 获取IM用户的黑名单
	 */
	public static String findBlocksFirendThisUser(String ownerUsername)
			throws Exception {
		String accessToken = getToken();
		if (StringUtils.isEmpty(accessToken)) {
			return null;
		}

		HttpClient client = new HttpClient();
		HttpMethod method = new GetMethod(POST_USER_URL + "/" + ownerUsername
				+ "/blocks/users");

		method.setRequestHeader("Authorization", "Bearer " + accessToken);
		HttpMethodParams param = method.getParams();
		param.setContentCharset(ENCODING);
		client.executeMethod(method);
		return method.getResponseBodyAsString();
	}

	/*
	 * 往IM用户的黑名单中加人
	 */
	@SuppressWarnings("deprecation")
	public static String addBlocksFriendUser(String ownerUsername,
			List<String> blocksUserList) throws Exception {
		String accessToken = getToken();
		if (StringUtils.isEmpty(accessToken) || blocksUserList.isEmpty()) {
			return null;
		}

		HttpClient client = new HttpClient();

		StringBuilder sl = new StringBuilder();
		for (int i = 0; i < blocksUserList.size(); i++) {
			String username = blocksUserList.get(i);
			sl.append("\"").append(username).append("\"");
			if (i < (blocksUserList.size() - 1)) {
				sl.append(",");
			}
		}

		PostMethod method = new PostMethod(POST_USER_URL + "/" + ownerUsername
				+ "/blocks/users");
		method.setRequestBody("{\"usernames\":[" + sl.toString() + "]}");

		method.setRequestHeader("Authorization", "Bearer " + accessToken);
		HttpMethodParams param = method.getParams();
		param.setContentCharset(ENCODING);
		client.executeMethod(method);
		return method.getResponseBodyAsString();
	}

	/*
	 * 从IM用户的黑名单中减人
	 */
	public static String removeBlocksFriendUser(String ownerUsername,
			String blockedUsername) throws Exception {
		String accessToken = getToken();
		if (StringUtils.isEmpty(accessToken)) {
			return null;
		}

		HttpClient client = new HttpClient();

		HttpMethod method = new DeleteMethod(POST_USER_URL + "/"
				+ ownerUsername + "/blocks/users/" + blockedUsername);

		method.setRequestHeader("Authorization", "Bearer " + accessToken);
		HttpMethodParams param = method.getParams();
		param.setContentCharset(ENCODING);
		client.executeMethod(method);
		return method.getResponseBodyAsString();
	}

	/*
	 * 查看用户在线状态
	 */
	public static String findUserOnlineStatus(String username) throws Exception {
		String accessToken = getToken();
		if (StringUtils.isEmpty(accessToken)) {
			return null;
		}

		HttpClient client = new HttpClient();
		HttpMethod method = new GetMethod(POST_USER_URL + "/" + username
				+ "/status");

		method.setRequestHeader("Content-Type", "application/json");
		method.setRequestHeader("Authorization", "Bearer " + accessToken);
		HttpMethodParams param = method.getParams();
		param.setContentCharset(ENCODING);
		client.executeMethod(method);
		return method.getResponseBodyAsString();
	}

	/*
	 * 查询离线消息数
	 */
	public static String findOfflineMessageCount(String ownerUsername)
			throws Exception {
		String accessToken = getToken();
		if (StringUtils.isEmpty(accessToken)) {
			return null;
		}

		HttpClient client = new HttpClient();
		HttpMethod method = new GetMethod(POST_USER_URL + "/" + ownerUsername
				+ "/offline_msg_count");

		method.setRequestHeader("Authorization", "Bearer " + accessToken);
		HttpMethodParams param = method.getParams();
		param.setContentCharset(ENCODING);
		client.executeMethod(method);
		return method.getResponseBodyAsString();
	}

	/*
	 * 查询某条离线消息状态
	 */
	public static String findOfflineMessageStatus(String username, String msgId)
			throws Exception {
		String accessToken = getToken();
		if (StringUtils.isEmpty(accessToken)) {
			return null;
		}

		HttpClient client = new HttpClient();
		HttpMethod method = new GetMethod(POST_USER_URL + "/" + username
				+ "/offline_msg_status/" + msgId);

		method.setRequestHeader("Content-Type", "application/json");
		method.setRequestHeader("Authorization", "Bearer " + accessToken);
		HttpMethodParams param = method.getParams();
		param.setContentCharset(ENCODING);
		client.executeMethod(method);
		return method.getResponseBodyAsString();
	}

	/*
	 * 用户账号禁用
	 */
	public static String forbiddenUser(String username) throws Exception {
		String accessToken = getToken();
		if (StringUtils.isEmpty(accessToken)) {
			return null;
		}

		HttpClient client = new HttpClient();

		PostMethod method = new PostMethod(POST_USER_URL + "/" + username
				+ "/deactivate");

		method.setRequestHeader("Content-Type", "application/json");
		method.setRequestHeader("Authorization", "Bearer " + accessToken);
		HttpMethodParams param = method.getParams();
		param.setContentCharset(ENCODING);
		client.executeMethod(method);
		return method.getResponseBodyAsString();
	}

	/*
	 * 用户账号解禁
	 */
	public static String removeForbiddenUser(String username) throws Exception {
		String accessToken = getToken();
		if (StringUtils.isEmpty(accessToken)) {
			return null;
		}

		HttpClient client = new HttpClient();

		PostMethod method = new PostMethod(POST_USER_URL + "/" + username
				+ "/activate");

		method.setRequestHeader("Content-Type", "application/json");
		method.setRequestHeader("Authorization", "Bearer " + accessToken);
		HttpMethodParams param = method.getParams();
		param.setContentCharset(ENCODING);
		client.executeMethod(method);
		return method.getResponseBodyAsString();
	}

	/*
	 * 强制用户下线
	 */
	public static String forceUserOffline(String username) throws Exception {
		String accessToken = getToken();
		if (StringUtils.isEmpty(accessToken)) {
			return null;
		}

		HttpClient client = new HttpClient();
		HttpMethod method = new GetMethod(POST_USER_URL + "/" + username
				+ "/disconnect");

		method.setRequestHeader("Content-Type", "application/json");
		method.setRequestHeader("Authorization", "Bearer " + accessToken);
		HttpMethodParams param = method.getParams();
		param.setContentCharset(ENCODING);
		client.executeMethod(method);
		return method.getResponseBodyAsString();
	}

	public static void main(String[] args) throws Exception {
		resetUserNickName("x", "xxx");
		// System.out.println(EasemobUtil.getAnyUser(200000, ""));
	}
}
