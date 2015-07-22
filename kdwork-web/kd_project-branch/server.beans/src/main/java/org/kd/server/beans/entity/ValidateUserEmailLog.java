package org.kd.server.beans.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="VALIDATE_USER_EMAIL_LOG")
public class ValidateUserEmailLog implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="ID")
	 private Long id;
	 
	@JsonIgnore
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="USER_ID")
	 private User user;
	 
	@Column(name="VALIDATE_CODE")
	 private int validateCode;
	
	@Column(name="EMAIL")
	 private String email;
	
	@Column(name="TYPE")
	 private int type;
	 
	@Column(name="SEND_TIME")
	 private Date sendTime;
	 
	@Column(name="OUT_TIME")
	 private Long outTime;
	 
	@Column(name="VALIDATE_KEY")
	 private String validateKey;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public int getValidateCode() {
		return validateCode;
	}

	public void setValidateCode(int validateCode) {
		this.validateCode = validateCode;
	}

	public Date getSendTime() {
		return sendTime;
	}

	public void setSendTime(Date sendTime) {
		this.sendTime = sendTime;
	}

	public Long getOutTime() {
		return outTime;
	}

	public void setOutTime(Long outTime) {
		this.outTime = outTime;
	}

	public String getValidateKey() {
		return validateKey;
	}

	public void setValidateKey(String validateKey) {
		this.validateKey = validateKey;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}
	
}
