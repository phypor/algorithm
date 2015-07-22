package org.kd.server.beans.entity;

import java.io.Serializable;

import javax.persistence.*;

import java.util.Date;


/**
 * The persistent class for the training_info database table.
 * 
 */
@Entity
@Table(name="TRAINING_INFO")
public class TrainingInfo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="ID")
	private Long id;

	@Column(name="ADDRESS_DESC")
	private String addressDesc;

	@Column(name="CREATE_TIME")
	private Date createTime;

	@Column(name="TITLE")
	private String title;

	@Column(name="TRAINING_CONTENT")
	private String trainingContent;

	@Column(name="TRAINING_DISABLE_TIME")
	private Date trainingDisableTime;

	@Column(name="TRAINING_ENABLE_TIME")
	private Date trainingEnableTime;

	@Column(name="TRAINING_FUNDS")
	private double trainingFunds;

	//bi-directional many-to-one association to Enterprise
	@ManyToOne(fetch=FetchType.LAZY)
	private Enterprise enterprise;

	//bi-directional many-to-one association to ProvinceCityArea
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="PROVINCE_CITY_AREA_ID")
	private ProvinceCityArea provinceCityArea;

	public TrainingInfo() {
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAddressDesc() {
		return this.addressDesc;
	}

	public void setAddressDesc(String addressDesc) {
		this.addressDesc = addressDesc;
	}

	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getTrainingContent() {
		return this.trainingContent;
	}

	public void setTrainingContent(String trainingContent) {
		this.trainingContent = trainingContent;
	}

	public Date getTrainingDisableTime() {
		return this.trainingDisableTime;
	}

	public void setTrainingDisableTime(Date trainingDisableTime) {
		this.trainingDisableTime = trainingDisableTime;
	}

	public Date getTrainingEnableTime() {
		return this.trainingEnableTime;
	}

	public void setTrainingEnableTime(Date trainingEnableTime) {
		this.trainingEnableTime = trainingEnableTime;
	}

	public double getTrainingFunds() {
		return this.trainingFunds;
	}

	public void setTrainingFunds(double trainingFunds) {
		this.trainingFunds = trainingFunds;
	}

	public Enterprise getEnterprise() {
		return this.enterprise;
	}

	public void setEnterprise(Enterprise enterprise) {
		this.enterprise = enterprise;
	}

	public ProvinceCityArea getProvinceCityArea() {
		return this.provinceCityArea;
	}

	public void setProvinceCityArea(ProvinceCityArea provinceCityArea) {
		this.provinceCityArea = provinceCityArea;
	}

}