package org.kd.server.beans.entity;

import java.io.Serializable;

import javax.persistence.*;

import org.hibernate.validator.constraints.Email;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * The persistent class for the recruit_info database table.
 * 
 */
@Entity
@Table(name = "RECRUIT_INFO")
@JsonIgnoreProperties(value = { "hibernateLazyInitializer", "handler" })
public class RecruitInfo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Long id;

	@Column(name = "CONTACTS")
	private String contacts;

	@ManyToOne
	@JoinColumn(name = "INFO_PLATFORM_TYPE")
	private InfoPlatformType infoPlatformType;

	public InfoPlatformType getInfoPlatformType() {
		return infoPlatformType;
	}

	public void setInfoPlatformType(InfoPlatformType infoPlatformType) {
		this.infoPlatformType = infoPlatformType;
	}

	@Column(name = "CREATE_TIME")
	private Date createTime;
	
	@Column(name = "REFRESH_TIME")
	private Date refreshTime;

	@Column(name = "DETAILED_ADDRESS")
	private String detailedAddress;

	@Email
	@Column(name = "EMAIL")
	private String email;

	@Column(name = "NEED_NUM")
	private int needNum;

	@Column(name = "BENEFITS")
	private String benefits;

	@Column(name = "REQUESTS")
	private String requests;

	@JsonIgnore
	@Column(name = "AUDITED")
	private boolean audited;

	@Column(name = "PHONE")
	private String phone;

	@Column(name = "RECRUIT_DISABLE_TIME")
	private Date recruitDisableTime;

	@Column(name = "RECRUIT_ENABLE_TIME")
	private Date recruitEnableTime;

	@Column(name = "SALARY_DAY")
	private String salaryDay;

	@Column(name = "TITLE")
	private String title;

	@Column(name = "TITLE_IMAGE")
	private String titleImage;

	@Column(name = "WORK_DESC")
	private String workDesc;

	@Column(name = "SUPPLEMENTARY")
	private String supplementary;

	@Column(name = "WORKING_WEEK")
	private int workingWeek;

	// bi-directional many-to-one association to Enterprise
	@JsonIgnore
	@OneToMany(mappedBy = "recruitInfo")
	private List<Enterprise> enterprises;

	// bi-directional many-to-one association to Position
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "POSITION_ID")
	private Position position;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ENTERPRISE_ID")
	private Enterprise enterprise;

	// bi-directional many-to-one association to ProvinceCityArea
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "PROVINCE_ID")
	private ProvinceCityArea province;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "DISTRICT_ID")
	private ProvinceCityArea district;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "CITY_ID")
	private ProvinceCityArea city;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "SECTION_ID")
	private Section section;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "METERING_MODE_ID")
	private MeteringMode meteringMode;

	public String getHash() {
		return hash;
	}

	public void setHash(String hash) {
		this.hash = hash;
	}

	@JsonIgnore
	@Column(name = "HASH")
	private String hash;

	@JsonIgnore
	@ManyToMany(cascade = { CascadeType.PERSIST }, fetch = FetchType.LAZY)
	@JoinTable(name = "USER_COLLECTION", joinColumns = { @JoinColumn(name = "RECRUIT_INFO_ID", referencedColumnName = "ID") }, inverseJoinColumns = { @JoinColumn(name = "USER_ID", referencedColumnName = "ID") })
	private List<User> users;

	@JsonIgnore
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "RECRUIT_USER_RESUME", joinColumns = { @JoinColumn(name = "RECRUIT_INFO_ID", referencedColumnName = "ID") }, inverseJoinColumns = { @JoinColumn(name = "USER_RESUME_ID", referencedColumnName = "ID") })
	private Set<UserResume> userResumes;
	
	@JsonIgnore
	@OneToMany(mappedBy = "recruitInfo",cascade=CascadeType.REMOVE)
	private List<Collection> collectionList;

	public RecruitInfo(Long id) {
		super();
		this.id = id;
	}

	public RecruitInfo() {
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getContacts() {
		return this.contacts;
	}

	public void setContacts(String contacts) {
		this.contacts = contacts;
	}

	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getDetailedAddress() {
		return this.detailedAddress;
	}

	public void setDetailedAddress(String detailedAddress) {
		this.detailedAddress = detailedAddress;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getNeedNum() {
		return this.needNum;
	}

	public void setNeedNum(int needNum) {
		this.needNum = needNum;
	}

	public String getPhone() {
		return this.phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Date getRecruitDisableTime() {
		return this.recruitDisableTime;
	}

	public void setRecruitDisableTime(Date recruitDisableTime) {
		this.recruitDisableTime = recruitDisableTime;
	}

	public Date getRecruitEnableTime() {
		return this.recruitEnableTime;
	}

	public void setRecruitEnableTime(Date recruitEnableTime) {
		this.recruitEnableTime = recruitEnableTime;
	}

	public String getSalaryDay() {
		return this.salaryDay;
	}

	public void setSalaryDay(String salaryDay) {
		this.salaryDay = salaryDay;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getWorkDesc() {
		return this.workDesc;
	}

	public void setWorkDesc(String workDesc) {
		this.workDesc = workDesc;
	}

	public List<Enterprise> getEnterprises() {
		return this.enterprises;
	}

	public void setEnterprises(List<Enterprise> enterprises) {
		this.enterprises = enterprises;
	}

	public Position getPosition() {
		return this.position;
	}

	public void setPosition(Position position) {
		this.position = position;
	}

	public ProvinceCityArea getProvince() {
		return province;
	}

	public void setProvince(ProvinceCityArea province) {
		this.province = province;
	}

	public ProvinceCityArea getDistrict() {
		return district;
	}

	public void setDistrict(ProvinceCityArea district) {
		this.district = district;
	}

	public ProvinceCityArea getCity() {
		return city;
	}

	public void setCity(ProvinceCityArea city) {
		this.city = city;
	}

	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}

	public boolean isAudited() {
		return audited;
	}

	public void setAudited(boolean audited) {
		this.audited = audited;
	}

	public Enterprise getEnterprise() {
		return enterprise;
	}

	public void setEnterprise(Enterprise enterprise) {
		this.enterprise = enterprise;
	}

	public String getSupplementary() {
		return supplementary;
	}

	public void setSupplementary(String supplementary) {
		this.supplementary = supplementary;
	}

	public Section getSection() {
		return section;
	}

	public void setSection(Section section) {
		this.section = section;
	}

	public String getTitleImage() {
		return titleImage;
	}

	public void setTitleImage(String titleImage) {
		this.titleImage = titleImage;
	}

	public Set<UserResume> getUserResumes() {
		return userResumes;
	}

	public void setUserResumes(Set<UserResume> userResumes) {
		this.userResumes = userResumes;
	}

	public MeteringMode getMeteringMode() {
		return meteringMode;
	}

	public void setMeteringMode(MeteringMode meteringMode) {
		this.meteringMode = meteringMode;
	}

	public int getWorkingWeek() {
		return workingWeek;
	}

	public void setWorkingWeek(int workingWeek) {
		this.workingWeek = workingWeek;
	}

	public String getBenefits() {
		return benefits;
	}

	public void setBenefits(String benefits) {
		this.benefits = benefits;
	}

	public String getRequests() {
		return requests;
	}

	public void setRequests(String requests) {
		this.requests = requests;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof RecruitInfo) {
			RecruitInfo t = (RecruitInfo) obj;
			return this.getId().equals(t.getId());
		}
		return super.equals(obj);
	}

	public int hashCode() {
		return this.getId() != null ? this.getId().intValue() : 0;
	}

	public Date getRefreshTime() {
		return refreshTime;
	}

	public void setRefreshTime(Date refreshTime) {
		this.refreshTime = refreshTime;
	}

	public List<Collection> getCollectionList() {
		return collectionList;
	}

	public void setCollectionList(List<Collection> collectionList) {
		this.collectionList = collectionList;
	}
	
}