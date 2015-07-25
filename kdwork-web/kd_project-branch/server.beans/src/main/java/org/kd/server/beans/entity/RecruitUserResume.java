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
import javax.persistence.Transient;

import org.kd.server.beans.vo.UserVo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name="RECRUIT_USER_RESUME")
@JsonIgnoreProperties(value={"hibernateLazyInitializer","handler"})
public class RecruitUserResume implements Serializable{
	
	@Override
	public String toString() {
		return "RecruitUserResume [id=" + id + ", userVo=" + userVo
				+ ", userResume=" + userResume + ", recruitInfo=" + recruitInfo
				+ ", readed=" + readed + ", sendTime=" + sendTime + "]";
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="ID")
	private Long id;
	
	@Transient
	private UserVo userVo;
	
	 
	public UserVo getUserVo() {
		return userVo;
	}

	public void setUserVo(UserVo userVo) {
		this.userVo = userVo;
	}

	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="USER_RESUME_ID")
	private UserResume userResume;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="RECRUIT_INFO_ID")
	private RecruitInfo  recruitInfo;
	
	@Column(name="READED")
	private boolean readed;
	
	@Column(name="SEND_TIME")
	private Date sendTime;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public UserResume getUserResume() {
		return userResume;
	}

	public void setUserResume(UserResume userResume) {
		this.userResume = userResume;
	}

	public RecruitInfo getRecruitInfo() {
		return recruitInfo;
	}

	public void setRecruitInfo(RecruitInfo recruitInfo) {
		this.recruitInfo = recruitInfo;
	}

	public boolean getReaded() {
		return readed;
	}

	public void setReaded(boolean readed) {
		this.readed = readed;
	}

	public Date getSendTime() {
		return sendTime;
	}

	public void setSendTime(Date sendTime) {
		this.sendTime = sendTime;
	}
	
}
