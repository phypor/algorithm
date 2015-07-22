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
import javax.persistence.Transient;

import org.hibernate.validator.constraints.NotEmpty;
import org.kd.server.beans.vo.UserVo;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="RECRUIT_INFO_COMPLAIN")
public class RecruitInfoComplain implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="ID")
	private Long id;
	
	@Column(name="COMPLAIN_TIME")
	private Date complainTime;
	
	@NotEmpty
	@Column(name="CONTENT")
	private String content;
	
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name="RECRUIT_INFO_ID")
	private RecruitInfo recruitInfo;
	
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name="USER_ID")
	private User user;
	
	@Transient
	private UserVo userVo;
	
	@ManyToOne
	@JoinColumn(name="COMPLAIN_TYPE_ID")
	private ComplainType complainType;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getComplainTime() {
		return complainTime;
	}

	public void setComplainTime(Date complainTime) {
		this.complainTime = complainTime;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public RecruitInfo getRecruitInfo() {
		return recruitInfo;
	}

	public void setRecruitInfo(RecruitInfo recruitInfo) {
		this.recruitInfo = recruitInfo;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public ComplainType getComplainType() {
		return complainType;
	}

	public void setComplainType(ComplainType complainType) {
		this.complainType = complainType;
	}

	public UserVo getUserVo() {
		return userVo;
	}

	public void setUserVo(UserVo userVo) {
		this.userVo = userVo;
	}
	
}
