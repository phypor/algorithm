package org.kd.server.beans.entity;

import java.io.Serializable;

import javax.persistence.*;

import java.util.Date;


/**
 * The persistent class for the enterprise_discuss database table.
 * 
 */
@Entity
@Table(name="ENTERPRISE_DISCUSS")
public class EnterpriseDiscuss implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="ID")
	private Long id;

	@Column(name="CONTENT")
	private String content;

	@Column(name="DISCUSS_TIME")
	private Date discussTime;

	//bi-directional many-to-one association to User
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="DISCUSS_USER_ID")
	private User user;

	public EnterpriseDiscuss() {
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

	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}