package org.kd.server.beans.entity;

import java.io.Serializable;

import javax.persistence.*;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * The persistent class for the user database table.
 * 
 */
@Entity
@Table(name = "USER")
@JsonIgnoreProperties(value = { "hibernateLazyInitializer", "handler" })
public class User implements Serializable {

	@Override
	public String toString() {
		return "User [id=" + id + ", sex=" + sex + ", easemobName="
				+ easemobName + ", easemobKey=" + easemobKey + ", email="
				+ email + ", isValidateEmail=" + isValidateEmail
				+ ", mobilePhone=" + mobilePhone + ", backgroundImage="
				+ backgroundImage + ", headImage=" + headImage
				+ ", unreadCircleInfoNum=" + unreadCircleInfoNum
				+ ", friendsNum=" + friendsNum + ", indiviSign=" + indiviSign
				+ ", collectionNum=" + collectionNum + ", unreadTalkCount="
				+ unreadTalkCount + ", resumeIntrodPercent="
				+ resumeIntrodPercent + ", enterpriseInfoPercent="
				+ enterpriseInfoPercent + ", unreadMessageNum="
				+ unreadMessageNum + ", unreadCircleInfoCount="
				+ unreadCircleInfoCount + ", unreadResumeCount="
				+ unreadResumeCount + ", lastAddressCoordinates="
				+ lastAddressCoordinates + ", lastAddressName="
				+ lastAddressName + ", messageShock=" + messageShock
				+ ", messageVoice=" + messageVoice + ", messageNoticeShow="
				+ messageNoticeShow + ", firnedCircleRenewRemind="
				+ firnedCircleRenewRemind + ", logoutStillPush="
				+ logoutStillPush + ", clientPlatformType="
				+ clientPlatformType + ", addMeNeedValid=" + addMeNeedValid
				+ ", showMeVicinity=" + showMeVicinity
				+ ", strangerLookResume=" + strangerLookResume
				+ ", minTrafficImg=" + minTrafficImg + ", lastLoginTime="
				+ lastLoginTime + ", loginName=" + loginName + ", nickName="
				+ nickName + ", password=" + password + ", pushEnable="
				+ pushEnable + ", registerTime=" + registerTime + ", userType="
				+ userType + ", enterprises=" + enterprises
				+ ", enterpriseDiscusses=" + enterpriseDiscusses
				+ ", infomationDiscusses=" + infomationDiscusses
				+ ", alumniEnterprises=" + alumniEnterprises + ", userCircles="
				+ userCircles + ", userResumes=" + userResumes + ", roles="
				+ roles + ", friendCircles=" + friendCircles
				+ ", recruitInfos=" + recruitInfos + "]";
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static final String ACCESS_TOKEN_NAME = "ACCESS_TOKEN";

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Long id;

	@Column(name = "SEX")
	private int sex;

	@Column(name = "EASEMOB_NAME")
	private String easemobName;

	@Column(name = "EASEMOB_KEY")
	private String easemobKey;

	@Email
	@Column(name = "EMAIL")
	private String email;

	@Column(name = "IS_VALIDATE_EMAIL")
	private boolean isValidateEmail;

	@Column(name = "MOBILE_PHONE")
	private String mobilePhone;

	@Pattern(regexp = "^[\\s\\S]{0,300}$")
	@Column(name = "BACKGROUND_IMAGE")
	private String backgroundImage;

	@Pattern(regexp = "^[\\s\\S]{0,300}$")
	@Column(name = "HEAD_IMAGE")
	private String headImage;

	@Column(name = "UNREAD_CIRCLE_INFO_NUM")
	private int unreadCircleInfoNum;

	@Column(name = "FRIENDS_NUM")
	private int friendsNum;

	@Pattern(regexp = "^[\\s\\S]{0,300}$")
	@Column(name = "INDIVIDUALITY_SIGNATURE")
	private String indiviSign;

	@Column(name = "COLLECTION_NUM")
	private int collectionNum;

	@Column(name = "UNREAD_TALK_COUNT")
	private int unreadTalkCount;

	@Column(name = "RESUME_INTROD_PERCENT")
	private int resumeIntrodPercent;

	@Column(name = "ENTERPRISE_INFO_PERCENT")
	private int enterpriseInfoPercent;

	@Column(name = "UNREAD_MESSAGE_NUM")
	private int unreadMessageNum;

	@Column(name = "UNREAD_CIRCLE_INFO_COUNT")
	private int unreadCircleInfoCount;

	@Column(name = "UNREAD_RESUME_COUNT")
	private int unreadResumeCount;

	@Column(name = "LAST_ADDRESS_COORDINATES")
	private String lastAddressCoordinates;

	@Column(name = "LAST_ADDRESS_NAME")
	private String lastAddressName;

	@Column(name = "MESSAGE_SHOCK")
	private boolean messageShock;

	@Column(name = "MESSAGE_VOICE")
	private boolean messageVoice;

	@Column(name = "MESSAGE_NOTICE_SHOW")
	private boolean messageNoticeShow;

	@Column(name = "FIRNED_CIRCLE_RENEW_REMIND")
	private boolean firnedCircleRenewRemind;

	@Column(name = "LOGOUT_STILL_PUSH")
	private boolean logoutStillPush;

	@ManyToOne
	@JoinColumn(name = "CLIENT_PLATFORM_TYPE")
	private ClientPlatformType clientPlatformType;

	@Column(name = "ADD_ME_NEED_VALID")
	private boolean addMeNeedValid;

	@Column(name = "SHOW_ME_VICINITY")
	private boolean showMeVicinity;

	@Column(name = "STRANGER_LOOK_RESUME")
	private boolean strangerLookResume;

	@Column(name = "MIN_TRAFFIC_IMG")
	private boolean minTrafficImg;

	@Column(name = "LAST_LOGIN_TIME")
	private Date lastLoginTime;

	@Pattern(regexp = "^[\\s\\S]{1,30}$")
	@Column(name = "LOGIN_NAME")
	private String loginName;

	@Pattern(regexp = "^[\\s\\S]{1,30}$")
	@Column(name = "NICK_NAME")
	private String nickName;

	@JsonIgnore
	@NotEmpty
	@Column(name = "PASSWORD")
	private String password;

	@Column(name = "PUSH_ENABLE")
	private boolean pushEnable;

	@Column(name = "REGISTER_TIME")
	private Date registerTime;

	@Column(name = "USER_TYPE")
	private int userType; // 1 普通用户 2 企业 3 就业办

	// bi-directional many-to-one association to Enterprise
	@JsonIgnore
	@OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
	private List<Enterprise> enterprises;

	// bi-directional many-to-one association to EnterpriseDiscuss
	@JsonIgnore
	@OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
	private List<EnterpriseDiscuss> enterpriseDiscusses;

	// bi-directional many-to-one association to InfomationDiscuss
	@JsonIgnore
	@OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
	private List<InfomationDiscuss> infomationDiscusses;

	@JsonIgnore
	@ManyToMany(cascade = { CascadeType.PERSIST }, fetch = FetchType.LAZY)
	@JoinTable(name = "USER_CIRCLE_ALUMNI", joinColumns = { @JoinColumn(name = "USER_ID", referencedColumnName = "ID") }, inverseJoinColumns = { @JoinColumn(name = "ENTERPRISE_ID", referencedColumnName = "ID") })
	private List<Enterprise> alumniEnterprises;

	@JsonIgnore
	@ManyToMany(cascade = { CascadeType.PERSIST }, fetch = FetchType.EAGER)
	@JoinTable(name = "USER_CIRCLE_FIRENDS", joinColumns = { @JoinColumn(name = "USER1", referencedColumnName = "ID") }, inverseJoinColumns = { @JoinColumn(name = "USER2", referencedColumnName = "ID") })
	private Set<User> userCircles;

	// bi-directional many-to-one association to UserResume
	@JsonIgnore
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "user")
	private List<UserResume> userResumes;

	@JsonIgnore
	@ManyToMany(cascade = { CascadeType.PERSIST })
	@JoinTable(name = "ROLE_USER", joinColumns = { @JoinColumn(name = "USER_ID", referencedColumnName = "ID") }, inverseJoinColumns = { @JoinColumn(name = "ROLE_ID", referencedColumnName = "ID") })
	private List<Role> roles;

	@JsonIgnore
	@ManyToMany(cascade = { CascadeType.PERSIST }, fetch = FetchType.EAGER)
	@JoinTable(name = "USER_CIRCLE", joinColumns = { @JoinColumn(name = "USER_ID", referencedColumnName = "ID") }, inverseJoinColumns = { @JoinColumn(name = "FRIEND_CIRCLE_ID", referencedColumnName = "ID") })
	private Set<FriendCircle> friendCircles;

	@JsonIgnore
	@ManyToMany(cascade = { CascadeType.PERSIST }, fetch = FetchType.LAZY)
	@JoinTable(name = "USER_COLLECTION", joinColumns = { @JoinColumn(name = "USER_ID", referencedColumnName = "ID") }, inverseJoinColumns = { @JoinColumn(name = "RECRUIT_INFO_ID", referencedColumnName = "ID") })
	private List<RecruitInfo> recruitInfos;

	public User() {
	}

	public User(Long id) {
		super();
		this.id = id;
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getHeadImage() {
		return this.headImage;
	}

	public void setHeadImage(String headImage) {
		this.headImage = headImage;
	}

	public String getLastAddressCoordinates() {
		return this.lastAddressCoordinates;
	}

	public void setLastAddressCoordinates(String lastAddressCoordinates) {
		this.lastAddressCoordinates = lastAddressCoordinates;
	}

	public String getLastAddressName() {
		return this.lastAddressName;
	}

	public void setLastAddressName(String lastAddressName) {
		this.lastAddressName = lastAddressName;
	}

	public Date getLastLoginTime() {
		return this.lastLoginTime;
	}

	public void setLastLoginTime(Date lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}

	public String getLoginName() {
		return this.loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getNickName() {
		return this.nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean getPushEnable() {
		return this.pushEnable;
	}

	public void setPushEnable(boolean pushEnable) {
		this.pushEnable = pushEnable;
	}

	public Date getRegisterTime() {
		return this.registerTime;
	}

	public void setRegisterTime(Date registerTime) {
		this.registerTime = registerTime;
	}

	public int getUserType() {
		return this.userType;
	}

	public void setUserType(int userType) {
		this.userType = userType;
	}

	public List<Enterprise> getEnterprises() {
		return this.enterprises;
	}

	public void setEnterprises(List<Enterprise> enterprises) {
		this.enterprises = enterprises;
	}

	public List<EnterpriseDiscuss> getEnterpriseDiscusses() {
		return this.enterpriseDiscusses;
	}

	public void setEnterpriseDiscusses(
			List<EnterpriseDiscuss> enterpriseDiscusses) {
		this.enterpriseDiscusses = enterpriseDiscusses;
	}

	public List<InfomationDiscuss> getInfomationDiscusses() {
		return this.infomationDiscusses;
	}

	public void setInfomationDiscusses(
			List<InfomationDiscuss> infomationDiscusses) {
		this.infomationDiscusses = infomationDiscusses;
	}

	public List<Enterprise> getAlumniEnterprises() {
		return alumniEnterprises;
	}

	public void setAlumniEnterprises(List<Enterprise> alumniEnterprises) {
		this.alumniEnterprises = alumniEnterprises;
	}

	public Set<User> getUserCircles() {
		return userCircles;
	}

	public void setUserCircles(Set<User> userCircles) {
		this.userCircles = userCircles;
	}

	public List<UserResume> getUserResumes() {
		return userResumes;
	}

	public void setUserResumes(List<UserResume> userResumes) {
		this.userResumes = userResumes;
	}

	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

	public Set<FriendCircle> getFriendCircles() {
		return friendCircles;
	}

	public void setFriendCircles(Set<FriendCircle> friendCircles) {
		this.friendCircles = friendCircles;
	}

	public List<RecruitInfo> getRecruitInfos() {
		return recruitInfos;
	}

	public void setRecruitInfos(List<RecruitInfo> recruitInfos) {
		this.recruitInfos = recruitInfos;
	}

	public String getBackgroundImage() {
		return backgroundImage;
	}

	public void setBackgroundImage(String backgroundImage) {
		this.backgroundImage = backgroundImage;
	}

	public int getUnreadCircleInfoNum() {
		return unreadCircleInfoNum;
	}

	public void setUnreadCircleInfoNum(int unreadCircleInfoNum) {
		this.unreadCircleInfoNum = unreadCircleInfoNum;
	}

	public int getFriendsNum() {
		return friendsNum;
	}

	public void setFriendsNum(int friendsNum) {
		this.friendsNum = friendsNum;
	}

	public int getCollectionNum() {
		return collectionNum;
	}

	public void setCollectionNum(int collectionNum) {
		this.collectionNum = collectionNum;
	}

	public int getUnreadMessageNum() {
		return unreadMessageNum;
	}

	public void setUnreadMessageNum(int unreadMessageNum) {
		this.unreadMessageNum = unreadMessageNum;
	}

	public int getResumeIntrodPercent() {
		return resumeIntrodPercent;
	}

	public void setResumeIntrodPercent(int resumeIntrodPercent) {
		this.resumeIntrodPercent = resumeIntrodPercent;
	}

	public int getUnreadTalkCount() {
		return unreadTalkCount;
	}

	public void setUnreadTalkCount(int unreadTalkCount) {
		this.unreadTalkCount = unreadTalkCount;
	}

	public int getUnreadCircleInfoCount() {
		return unreadCircleInfoCount;
	}

	public void setUnreadCircleInfoCount(int unreadCircleInfoCount) {
		this.unreadCircleInfoCount = unreadCircleInfoCount;
	}

	public int getUnreadResumeCount() {
		return unreadResumeCount;
	}

	public void setUnreadResumeCount(int unreadResumeCount) {
		this.unreadResumeCount = unreadResumeCount;
	}

	public String getIndiviSign() {
		return indiviSign;
	}

	public void setIndiviSign(String indiviSign) {
		this.indiviSign = indiviSign;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof User) {
			User t = (User) obj;
			return this.getId().equals(t.getId());
		}
		return super.equals(obj);
	}

	public int hashCode() {
		return this.getId() != null ? this.getId().intValue() : 0;
	}

	public boolean isMessageShock() {
		return messageShock;
	}

	public void setMessageShock(boolean messageShock) {
		this.messageShock = messageShock;
	}

	public boolean isMessageVoice() {
		return messageVoice;
	}

	public void setMessageVoice(boolean messageVoice) {
		this.messageVoice = messageVoice;
	}

	public boolean isMessageNoticeShow() {
		return messageNoticeShow;
	}

	public void setMessageNoticeShow(boolean messageNoticeShow) {
		this.messageNoticeShow = messageNoticeShow;
	}

	public boolean isFirnedCircleRenewRemind() {
		return firnedCircleRenewRemind;
	}

	public void setFirnedCircleRenewRemind(boolean firnedCircleRenewRemind) {
		this.firnedCircleRenewRemind = firnedCircleRenewRemind;
	}

	public boolean isLogoutStillPush() {
		return logoutStillPush;
	}

	public void setLogoutStillPush(boolean logoutStillPush) {
		this.logoutStillPush = logoutStillPush;
	}

	public boolean isAddMeNeedValid() {
		return addMeNeedValid;
	}

	public void setAddMeNeedValid(boolean addMeNeedValid) {
		this.addMeNeedValid = addMeNeedValid;
	}

	public boolean isShowMeVicinity() {
		return showMeVicinity;
	}

	public void setShowMeVicinity(boolean showMeVicinity) {
		this.showMeVicinity = showMeVicinity;
	}

	public boolean isStrangerLookResume() {
		return strangerLookResume;
	}

	public void setStrangerLookResume(boolean strangerLookResume) {
		this.strangerLookResume = strangerLookResume;
	}

	public boolean isMinTrafficImg() {
		return minTrafficImg;
	}

	public void setMinTrafficImg(boolean minTrafficImg) {
		this.minTrafficImg = minTrafficImg;
	}

	public String getMobilePhone() {
		return mobilePhone;
	}

	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}

	public int getSex() {
		return sex;
	}

	public void setSex(int sex) {
		this.sex = sex;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public boolean getIsValidateEmail() {
		return isValidateEmail;
	}

	public void setIsValidateEmail(boolean isValidateEmail) {
		this.isValidateEmail = isValidateEmail;
	}

	public String getEasemobKey() {
		return easemobKey;
	}

	public void setEasemobKey(String easemobKey) {
		this.easemobKey = easemobKey;
	}

	public int getEnterpriseInfoPercent() {
		return enterpriseInfoPercent;
	}

	public void setEnterpriseInfoPercent(int enterpriseInfoPercent) {
		this.enterpriseInfoPercent = enterpriseInfoPercent;
	}

	public String getEasemobName() {
		return easemobName;
	}

	public void setEasemobName(String easemobName) {
		this.easemobName = easemobName;
	}

	public ClientPlatformType getClientPlatformType() {
		return clientPlatformType;
	}

	public void setClientPlatformType(ClientPlatformType clientPlatformType) {
		this.clientPlatformType = clientPlatformType;
	}
}