package org.kd.server.beans.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

/*
 * 学校信息表
 * 
 * zmy
 */
@Entity
@Table(name = "SCHOOL")
public class School implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Long id;

	/*
	 * 学校名称
	 */
	@Column(name = "SCHOOL_NAME")
	private String schoolName;

	/*
	 * 学校拼音字母
	 */
	@JsonIgnore
	@Column(name = "SCHOOL_PINYIN_CODE")
	private String schoolPinYinCode;

	/*
	 * 关联省份ID
	 */
	@JsonIgnore
	@Column(name = "PRO_ID")
	private Long proId;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getSchoolName() {
		return schoolName;
	}

	public void setSchoolName(String schoolName) {
		this.schoolName = schoolName;
	}

	public String getSchoolPinYinCode() {
		return schoolPinYinCode;
	}

	public void setSchoolPinYinCode(String schoolPinYinCode) {
		this.schoolPinYinCode = schoolPinYinCode;
	}

	public Long getProId() {
		return proId;
	}

	public void setProId(Long proId) {
		this.proId = proId;
	}

}
