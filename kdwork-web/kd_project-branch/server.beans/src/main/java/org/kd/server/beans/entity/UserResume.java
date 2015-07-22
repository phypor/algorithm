package org.kd.server.beans.entity;

import java.io.Serializable;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;

/**
 * The persistent class for the user_resume database table.
 * 
 */
@Entity
@Table(name = "USER_RESUME")
@JsonIgnoreProperties(value = { "hibernateLazyInitializer", "handler" })
public class UserResume implements Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	public String toString() {
		return "UserResume [id=" + id + ", email=" + email + ", userImage="
				+ userImage + ", goodSkills=" + goodSkills + ", specialty="
				+ specialty + ", mobilePhone=" + mobilePhone + ", nation="
				+ nation + ", qq=" + qq + ", sex=" + sex + ", detailedAddress="
				+ detailedAddress + ", ugrade=" + ugrade + ", userAge="
				+ userAge + ", userAtSchool=" + userAtSchool + ", userName="
				+ userName + ", userStature=" + userStature
				+ ", winnerExperience=" + winnerExperience + ", workExprience="
				+ workExprience + ", workIntention=" + workIntention
				+ ", introduce=" + introduce + ", degrees=" + degrees
				+ ", grade=" + grade + ", recruitInfos=" + recruitInfos
				+ ", province=" + province + ", city=" + city + ", district="
				+ district + ", user=" + user + "]";
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Long id;

	@Column(name = "EMAIL")
	private String email;//

	@Column(name = "USER_IMAGE")
	private String userImage;

	@Column(name = "GOOD_SKILLS")
	private String goodSkills;

	@Column(name = "SPECIALTY")
	private String specialty;

	@Column(name = "MOBILE_PHONE")
	private String mobilePhone;

	@Column(name = "NATION")
	private String nation;

	@Column(name = "QQ")
	private long qq;

	@Column(name = "SEX")
	private int sex;

	@Column(name = "DETAILED_ADDRESS")
	private String detailedAddress;

	@Column(name = "U_GRADE")
	private String ugrade;

	public String getUgrade() {
		return ugrade;
	}

	public void setUgrade(String ugrade) {
		this.ugrade = ugrade;
	}

	@Column(name = "USER_AGE")
	private int userAge;

	@Column(name = "USER_AT_SCHOOL")
	private String userAtSchool;

	@Column(name = "USER_NAME")
	private String userName;

	@Column(name = "USER_STATURE")
	private String userStature;

	@Column(name = "WINNER_EXPERIENCE")
	private String winnerExperience;

	@Column(name = "WORK_EXPRIENCE")
	private String workExprience;

	@Column(name = "WORK_INTENTION")
	private String workIntention;

	@Column(name = "INTRODUCE")
	private String introduce;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "DEGREES_ID")
	private Degrees degrees;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "GRADE_ID")
	private Grade grade;

	@JsonIgnore
	@ManyToMany(cascade = { CascadeType.REMOVE }, fetch = FetchType.LAZY)
	@JoinTable(name = "RECRUIT_USER_RESUME", joinColumns = { @JoinColumn(name = "USER_RESUME_ID", referencedColumnName = "ID") }, inverseJoinColumns = { @JoinColumn(name = "RECRUIT_INFO_ID", referencedColumnName = "ID") })
	private List<RecruitInfo> recruitInfos;

	// bi-directional many-to-one association to ProvinceCityArea
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PROVINCE_ID")
	private ProvinceCityArea province;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CITY_ID")
	private ProvinceCityArea city;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "DISTRICT_ID")
	private ProvinceCityArea district;

	// bi-directional many-to-one association to User
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "USER_ID")
	private User user;

	public UserResume() {
	}

	public Degrees getDegrees() {
		return degrees;
	}

	public void setDegrees(Degrees degrees) {
		this.degrees = degrees;
	}

	public Grade getGrade() {
		return grade;
	}

	public void setGrade(Grade grade) {
		this.grade = grade;
	}

	public String getUserImage() {
		return userImage;
	}

	public void setUserImage(String userImage) {
		this.userImage = userImage;
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getGoodSkills() {
		return this.goodSkills;
	}

	public void setGoodSkills(String goodSkills) {
		this.goodSkills = goodSkills;
	}

	public String getMobilePhone() {
		return this.mobilePhone;
	}

	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}

	public String getNation() {
		return this.nation;
	}

	public void setNation(String nation) {
		this.nation = nation;
	}

	public long getQq() {
		return this.qq;
	}

	public void setQq(long qq) {
		this.qq = qq;
	}

	public int getSex() {
		return this.sex;
	}

	public void setSex(int sex) {
		this.sex = sex;
	}

	public int getUserAge() {
		return this.userAge;
	}

	public void setUserAge(int userAge) {
		this.userAge = userAge;
	}

	public String getUserAtSchool() {
		return this.userAtSchool;
	}

	public void setUserAtSchool(String userAtSchool) {
		this.userAtSchool = userAtSchool;
	}

	public String getUserName() {
		return this.userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserStature() {
		return userStature;
	}

	public void setUserStature(String userStature) {
		this.userStature = userStature;
	}

	public String getWinnerExperience() {
		return this.winnerExperience;
	}

	public void setWinnerExperience(String winnerExperience) {
		this.winnerExperience = winnerExperience;
	}

	public String getWorkExprience() {
		return this.workExprience;
	}

	public void setWorkIntention(String workIntention) {
		this.workIntention = workIntention;
	}

	public List<RecruitInfo> getRecruitInfos() {
		return recruitInfos;
	}

	public void setRecruitInfos(List<RecruitInfo> recruitInfos) {
		this.recruitInfos = recruitInfos;
	}

	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getDetailedAddress() {
		return detailedAddress;
	}

	public void setDetailedAddress(String detailedAddress) {
		this.detailedAddress = detailedAddress;
	}

	public String getIntroduce() {
		return introduce;
	}

	public void setIntroduce(String introduce) {
		this.introduce = introduce;
	}

	public ProvinceCityArea getProvince() {
		return province;
	}

	public void setProvince(ProvinceCityArea province) {
		this.province = province;
	}

	public ProvinceCityArea getCity() {
		return city;
	}

	public void setCity(ProvinceCityArea city) {
		this.city = city;
	}

	public ProvinceCityArea getDistrict() {
		return district;
	}

	public void setDistrict(ProvinceCityArea district) {
		this.district = district;
	}

	public String getWorkIntention() {
		return workIntention;
	}

	public void setWorkExprience(String workExprience) {
		this.workExprience = workExprience;
	}

	public String getSpecialty() {
		return specialty;
	}

	public void setSpecialty(String specialty) {
		this.specialty = specialty;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof UserResume) {
			UserResume t = (UserResume) obj;
			return this.getId().equals(t.getId());
		}
		return super.equals(obj);
	}

	public int hashCode() {
		return this.getId() != null ? this.getId().intValue() : 0;
	}

}