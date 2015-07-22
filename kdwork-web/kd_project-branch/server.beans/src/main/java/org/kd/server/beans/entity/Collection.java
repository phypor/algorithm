package org.kd.server.beans.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name="COLLECTION")
@JsonIgnoreProperties(value={"hibernateLazyInitializer","handler"})
public class Collection implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="ID")
	private Long id;
	
	@ManyToOne
	@JoinColumn(name="RECRUIT_INFO_ID")
	private RecruitInfo recruitInfo;
	
	@ManyToOne
	@JoinColumn(name="INFOMATION_ID")
	private Infomation infomation;
	
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name="USER_ID")
	private User user;
	
	@Column(name="TYPE")
	private int type;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public RecruitInfo getRecruitInfo() {
		return recruitInfo;
	}

	public void setRecruitInfo(RecruitInfo recruitInfo) {
		this.recruitInfo = recruitInfo;
	}

	public Infomation getInfomation() {
		return infomation;
	}

	public void setInfomation(Infomation infomation) {
		this.infomation = infomation;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}
	
}
