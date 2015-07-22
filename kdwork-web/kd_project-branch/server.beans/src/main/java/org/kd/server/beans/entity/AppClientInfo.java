package org.kd.server.beans.entity;

import java.io.Serializable;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="APP_CLIENT_INFO")
public class AppClientInfo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="ID")
	private Long id;
	
	@Column(name="VERSION_CODE")
	private double versionCode;
	
	@Column(name="VERSION_NAME")
	private String versionName;
	
	@ManyToOne
	@JoinColumn(name="PLATFORM_TYPE_ID")
	private AppPlatformType appPlatformType;
	
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name="CREATE_USER_ID")
	private User createUser;
	
	@Column(name="CREATE_TIME")
	private Date createTime;
	
	@Column(name="APP_SIZE_BYTE")
	private int appSizeByte;
	
	@Column(name="APP_SIZE_MB")
	private double appSizeMb;
	
	@Column(name="PUSH_TIME")
	private Date pushTime;
	
	@ManyToOne
	@JoinColumn(name="CHANNEL_TYPE_ID")
	private AppChannelType appChannelType;
	
	@Column(name="IS_CONSTRAINT_RENEW")
	private boolean isConstraintRenew;
	
	@Column(name="DOWNLOAD_URL")
	private String downloadUrl;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public double getVersionCode() {
		return versionCode;
	}

	public void setVersionCode(double versionCode) {
		this.versionCode = versionCode;
	}

	public String getVersionName() {
		return versionName;
	}

	public void setVersionName(String versionName) {
		this.versionName = versionName;
	}

	public AppPlatformType getAppPlatformType() {
		return appPlatformType;
	}

	public void setAppPlatformType(AppPlatformType appPlatformType) {
		this.appPlatformType = appPlatformType;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public int getAppSizeByte() {
		return appSizeByte;
	}

	public void setAppSizeByte(int appSizeByte) {
		this.appSizeByte = appSizeByte;
	}

	public double getAppSizeMb() {
		return appSizeMb;
	}

	public void setAppSizeMb(double appSizeMb) {
		this.appSizeMb = appSizeMb;
	}

	public Date getPushTime() {
		return pushTime;
	}

	public void setPushTime(Date pushTime) {
		this.pushTime = pushTime;
	}

	public AppChannelType getAppChannelType() {
		return appChannelType;
	}

	public void setAppChannelType(AppChannelType appChannelType) {
		this.appChannelType = appChannelType;
	}

	public boolean getIsConstraintRenew() {
		return isConstraintRenew;
	}

	public void setIsConstraintRenew(boolean isConstraintRenew) {
		this.isConstraintRenew = isConstraintRenew;
	}

	public String getDownloadUrl() {
		return downloadUrl;
	}

	public void setDownloadUrl(String downloadUrl) {
		this.downloadUrl = downloadUrl;
	}

	public User getCreateUser() {
		return createUser;
	}

	public void setCreateUser(User createUser) {
		this.createUser = createUser;
	}
	
}
