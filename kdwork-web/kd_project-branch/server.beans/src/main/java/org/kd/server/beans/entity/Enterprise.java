package org.kd.server.beans.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.kd.server.beans.vo.UserVo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * The persistent class for the enterprise database table.
 * 
 */
@Entity
@Table(name = "ENTERPRISE")
@JsonIgnoreProperties(value = { "hibernateLazyInitializer", "handler" })
public class Enterprise implements Serializable {
	private static final long serialVersionUID = 1L;

	

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Long id;

	@Column(name = "LOGO_IMAGE")
	private String logoImage;
	
	@Column(name = "CREATE_TIME")
	private Date createTime;
	
	@Column(name = "REFRESH_TIME")
	private Date refreshTime;
	
	public Date getRefreshTime() {
		return refreshTime;
	}

	public void setRefreshTime(Date refreshTime) {
		this.refreshTime = refreshTime;
	}

	@Column(name = "NAME")
	private String name;

	@Column(name = "PHONE")
	private String phone;

	@Column(name = "DETAILED_ADDRESS")
	private String detailedAddress;

	@Column(name = "EMAIL")
	private String email;

	@Column(name = "WEBSITE_ADDRESS")
	private String websiteAddress;

	@JsonIgnore
	@Column(name = "BUSINESS_LICENSE_CHECK")
	private boolean businessLicenseCheck;

	@Column(name = "BUSINESS_LICENSE_IMAGE")
	private String businessLicenseImage;

	@Column(name = "CLICK_LIKE_NUM")
	private int clickLikeNum;

	@Column(name = "CONTACTS")
	private String contacts;
	
	@Column(name = "LICENSE_NO")
	private String licenseNo;

	@Column(name = "ENTERPRISE_PHIL")
	private String enterprisePhil;// 企业理念

	@Column(name = "SCHOOL_MOTTO")
	private String schoolMotto;// 学校校训

	@Column(name = "ENTERPRISE_DESC")
	private String enterpriseDesc;
	
	@JsonIgnore
	@Column(name = "HASH")
	private String hash;

	@Column(name = "ETYPE",columnDefinition="INT default 2")
	private int eType;
	
	public int geteType() {
		return eType;
	}

	public void seteType(int eType) {
		this.eType = eType;
	}

	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ENTERPRISE_TYPE")
	private EnterpriseType enterpriseType;

	// bi-directional many-to-one association to CompanyNature
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "COMPANY_NATURE_ID")
	private CompanyNature companyNature;

	// bi-directional many-to-one association to Industry
	@ManyToOne(fetch = FetchType.LAZY)
	private Industry industry;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PROVINCE_ID")
	private ProvinceCityArea province;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CITY_ID")
	private ProvinceCityArea city;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "DISTRICT_ID")
	private ProvinceCityArea district;

	
	
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	// bi-directional many-to-one association to QuantityScale
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "QUANTITY_SCALE_ID")
	private QuantityScale quantityScale;

	// bi-directional many-to-one association to RecruitInfo
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "LAST_RECRUIT_INFO_ID")
	private RecruitInfo recruitInfo;

	// bi-directional many-to-one association to User
	@JsonIgnore
	@ManyToOne(fetch = FetchType.EAGER)
	private User user;
	
	@Transient
	private UserVo userVo;

	// bi-directional many-to-one association to EnterpriseComplain
	@JsonIgnore
	@OneToMany(mappedBy = "enterprise")
	private List<EnterpriseComplain> enterpriseComplains;

	// bi-directional many-to-one association to TrainingInfo
	@JsonIgnore
	@OneToMany(mappedBy = "enterprise")
	private List<TrainingInfo> trainingInfos;

	@JsonIgnore
	@OneToMany(mappedBy = "enterprise")
	private List<RecruitInfo> recruitInfos;

	public String getHash() {
		return hash;
	}

	public void setHash(String hash) {
		this.hash = hash;
	}

	public Enterprise() {
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public boolean getBusinessLicenseCheck() {
		return this.businessLicenseCheck;
	}

	public void setBusinessLicenseCheck(boolean businessLicenseCheck) {
		this.businessLicenseCheck = businessLicenseCheck;
	}

	public String getBusinessLicenseImage() {
		return this.businessLicenseImage;
	}

	public void setBusinessLicenseImage(String businessLicenseImage) {
		this.businessLicenseImage = businessLicenseImage;
	}

	public int getClickLikeNum() {
		return this.clickLikeNum;
	}

	public void setClickLikeNum(int clickLikeNum) {
		this.clickLikeNum = clickLikeNum;
	}

	public String getContacts() {
		return this.contacts;
	}

	public void setContacts(String contacts) {
		this.contacts = contacts;
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

	public String getEnterpriseDesc() {
		return this.enterpriseDesc;
	}

	public void setEnterpriseDesc(String enterpriseDesc) {
		this.enterpriseDesc = enterpriseDesc;
	}

	public String getLicenseNo() {
		return this.licenseNo;
	}

	public void setLicenseNo(String licenseNo) {
		this.licenseNo = licenseNo;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhone() {
		return this.phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public CompanyNature getCompanyNature() {
		return this.companyNature;
	}

	public void setCompanyNature(CompanyNature companyNature) {
		this.companyNature = companyNature;
	}

	public Industry getIndustry() {
		return this.industry;
	}

	public void setIndustry(Industry industry) {
		this.industry = industry;
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

	public QuantityScale getQuantityScale() {
		return this.quantityScale;
	}

	public void setQuantityScale(QuantityScale quantityScale) {
		this.quantityScale = quantityScale;
	}

	public RecruitInfo getRecruitInfo() {
		return this.recruitInfo;
	}

	public void setRecruitInfo(RecruitInfo recruitInfo) {
		this.recruitInfo = recruitInfo;
	}

	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public List<EnterpriseComplain> getEnterpriseComplains() {
		return this.enterpriseComplains;
	}

	public void setEnterpriseComplains(
			List<EnterpriseComplain> enterpriseComplains) {
		this.enterpriseComplains = enterpriseComplains;
	}

	public List<TrainingInfo> getTrainingInfos() {
		return this.trainingInfos;
	}

	public void setTrainingInfos(List<TrainingInfo> trainingInfos) {
		this.trainingInfos = trainingInfos;
	}

	public EnterpriseType getEnterpriseType() {
		return enterpriseType;
	}

	public void setEnterpriseType(EnterpriseType enterpriseType) {
		this.enterpriseType = enterpriseType;
	}

	public List<RecruitInfo> getRecruitInfos() {
		return recruitInfos;
	}

	public void setRecruitInfos(List<RecruitInfo> recruitInfos) {
		this.recruitInfos = recruitInfos;
	}

	public String getWebsiteAddress() {
		return websiteAddress;
	}

	public void setWebsiteAddress(String websiteAddress) {
		this.websiteAddress = websiteAddress;
	}
	
	public String getSchoolMotto() {
		return schoolMotto;
	}

	public void setSchoolMotto(String schoolMotto) {
		this.schoolMotto = schoolMotto;
	}

	public String getEnterprisePhil() {
		return enterprisePhil;
	}

	public void setEnterprisePhil(String enterprisePhil) {
		this.enterprisePhil = enterprisePhil;
	}

	public String getLogoImage() {
		return logoImage;
	}

	public void setLogoImage(String logoImage) {
		this.logoImage = logoImage;
	}

	public UserVo getUserVo() {
		return userVo;
	}

	public void setUserVo(UserVo userVo) {
		this.userVo = userVo;
	}

}