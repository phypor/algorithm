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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import org.kd.server.beans.vo.UserVo;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="RECRUIT_INFO_DISCUSS")
public class RecruitInfoDiscuss implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id  
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="ID")
	private Long id;
	
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name="USER_ID")
	private User user;
	
	@Transient
	private UserVo userVo;
	
	@NotNull
	@ManyToOne
	@JoinColumn(name="RECRUIT_INFO_ID")
	private RecruitInfo recruitInfo;
	
	@Column(name="DISCUSS_TIME")
	private Date discussTime;
	
	@Pattern(regexp="^[\\s\\S]{1,500}$")
	@Column(name="CONTENT")
	private String content;

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

	public RecruitInfo getRecruitInfo() {
		return recruitInfo;
	}

	public void setRecruitInfo(RecruitInfo recruitInfo) {
		this.recruitInfo = recruitInfo;
	}

	public Date getDiscussTime() {
		return discussTime;
	}

	public void setDiscussTime(Date discussTime) {
		this.discussTime = discussTime;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public UserVo getUserVo() {
		return userVo;
	}

	public void setUserVo(UserVo userVo) {
		this.userVo = userVo;
	}

}
