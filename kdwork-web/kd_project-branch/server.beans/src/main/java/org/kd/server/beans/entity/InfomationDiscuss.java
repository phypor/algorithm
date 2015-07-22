package org.kd.server.beans.entity;

import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.Date;


/**
 * The persistent class for the infomation_discuss database table.
 * 
 */
@Entity
@Table(name="INFOMATION_DISCUSS")
public class InfomationDiscuss implements Serializable {
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

	//bi-directional many-to-one association to Infomation
	@NotNull
	@ManyToOne(fetch=FetchType.LAZY)
	private Infomation infomation;

	//bi-directional many-to-one association to User
	@ManyToOne(fetch=FetchType.LAZY)
	private User user;

	public InfomationDiscuss() {
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

	public Infomation getInfomation() {
		return this.infomation;
	}

	public void setInfomation(Infomation infomation) {
		this.infomation = infomation;
	}

	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}