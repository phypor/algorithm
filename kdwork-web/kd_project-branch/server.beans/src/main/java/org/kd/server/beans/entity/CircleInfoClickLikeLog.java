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

@Entity
@Table(name="CIRCLE_INFO_CLICK_LIKE_LOG")
public class CircleInfoClickLikeLog implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="ID")
	private Long id;
	
	@Column(name="CLICK_LIKE_TIME")
	private Date clickLikeTime;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="CIRCLE_INFO_ID")
	private UserCircleInfo userCircleInfo;

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="USER_ID")
	private User user;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getClickLikeTime() {
		return clickLikeTime;
	}

	public void setClickLikeTime(Date clickLikeTime) {
		this.clickLikeTime = clickLikeTime;
	}

	public UserCircleInfo getUserCircleInfo() {
		return userCircleInfo;
	}

	public void setUserCircleInfo(UserCircleInfo userCircleInfo) {
		this.userCircleInfo = userCircleInfo;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
}
