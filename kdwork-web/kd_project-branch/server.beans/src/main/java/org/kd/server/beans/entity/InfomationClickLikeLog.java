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

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name="INFOMATION_CLICK_LIKE_LOG")
@JsonIgnoreProperties(value={"hibernateLazyInitializer","handler"})
public class InfomationClickLikeLog implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="ID")
	private Long id;
	
	@ManyToOne
	@JoinColumn(name="USER_ID")
	private User user;
	
	@ManyToOne
	@JoinColumn(name="INFOMATION_ID")
	private Infomation infomation;
	
	@Column(name="CLICK_LIKE_TIME")
	private Date clickLikeTime;

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

	public Infomation getInfomation() {
		return infomation;
	}

	public void setInfomation(Infomation infomation) {
		this.infomation = infomation;
	}

	public Date getClickLikeTime() {
		return clickLikeTime;
	}

	public void setClickLikeTime(Date clickLikeTime) {
		this.clickLikeTime = clickLikeTime;
	}

}
