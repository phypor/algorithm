package org.kd.server.beans.vo;

public class UserVo {
	private Long id;
	private String nickName;
	private String headImage;
	private int sex;
	private String easemobName;

	public UserVo(Long id, String nickName, String headImage,
			int sex, String easemobName) {
		super();
		this.id = id;
		this.nickName = nickName;
		this.headImage = headImage;
		this.sex = sex;
		this.easemobName = easemobName;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getHeadImage() {
		return headImage;
	}

	public void setHeadImage(String headImage) {
		this.headImage = headImage;
	}

	public int getSex() {
		return sex;
	}

	public void setSex(int sex) {
		this.sex = sex;
	}

	public String getEasemobName() {
		return easemobName;
	}

	public void setEasemobName(String easemobName) {
		this.easemobName = easemobName;
	}
	
}
