package org.kd.server.beans.entity;

import java.io.Serializable;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.kd.server.beans.vo.UserVo;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Date;


/**
 * The persistent class for the circle_info_discuss database table.
 * 
 */
@Entity
@Table(name="CIRCLE_INFO_DISCUSS")
public class CircleInfoDiscuss implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="ID")
	private Long id;

	@Pattern(regexp="^[\\s\\S]{1,500}$")
	@Column(name="CONTENT")
	private String content;

	@Column(name="DISCUSS_TIME")
	private Date discussTime;

	//bi-directional many-to-one association to UserCircleInfo
	@NotNull
	@JsonIgnore
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="USER_CIRCLE_INFO_ID")
	private UserCircleInfo userCircleInfo;

	//bi-directional many-to-one association to User
	@JsonIgnore
	@NotNull
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="USER_QUILT")
	private User userQuilt;
	
	@Transient
	private UserVo quiltUserVo;

	//bi-directional many-to-one association to User
	@JsonIgnore
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="USER_ACTIVE")
	private User userActive;
	
	@Transient
	private UserVo activeUserVo;

	public CircleInfoDiscuss() {
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getDiscussTime() {
		return this.discussTime;
	}

	public void setDiscussTime(Date discussTime) {
		this.discussTime = discussTime;
	}

	public UserCircleInfo getUserCircleInfo() {
		return this.userCircleInfo;
	}

	public void setUserCircleInfo(UserCircleInfo userCircleInfo) {
		this.userCircleInfo = userCircleInfo;
	}

	public User getUserQuilt() {
		return userQuilt;
	}

	public void setUserQuilt(User userQuilt) {
		this.userQuilt = userQuilt;
	}

	public User getUserActive() {
		return userActive;
	}

	public void setUserActive(User userActive) {
		this.userActive = userActive;
	}

	public UserVo getQuiltUserVo() {
		return quiltUserVo;
	}

	public void setQuiltUserVo(UserVo quiltUserVo) {
		this.quiltUserVo = quiltUserVo;
	}

	public UserVo getActiveUserVo() {
		return activeUserVo;
	}

	public void setActiveUserVo(UserVo activeUserVo) {
		this.activeUserVo = activeUserVo;
	}

}