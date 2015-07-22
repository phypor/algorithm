package org.kd.server.beans.entity;

import java.io.Serializable;

import javax.persistence.*;


/**
 * The persistent class for the kd_info database table.
 * 
 */
@Entity
@Table(name="KD_INFO")
public class KdInfo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="ID")
	private Long id;

	@Column(name="ABOUT_INFO")
	private String aboutInfo;

	@Column(name="COMPANY_NAME")
	private String companyName;

	@Column(name="USER_HELP")
	private String userHelp;

	public KdInfo() {
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAboutInfo() {
		return this.aboutInfo;
	}

	public void setAboutInfo(String aboutInfo) {
		this.aboutInfo = aboutInfo;
	}

	public String getCompanyName() {
		return this.companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getUserHelp() {
		return this.userHelp;
	}

	public void setUserHelp(String userHelp) {
		this.userHelp = userHelp;
	}

}