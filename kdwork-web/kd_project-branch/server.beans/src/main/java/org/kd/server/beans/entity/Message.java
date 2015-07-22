package org.kd.server.beans.entity;

import java.io.Serializable;

import javax.persistence.*;

import org.kd.server.beans.vo.UserVo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.Date;
/**
 * The persistent class for the message database table.
 * 
 */
@Entity
@Table(name="MESSAGE")
@JsonIgnoreProperties(value={"messageTalk","hibernateLazyInitializer","handler","fieldHandler"})
public class Message implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="ID")
	private Long id;

	@Column(name="CONTENT")
	private String content;

	@Column(name="EXISTS_READ")
	private boolean existsRead;

	@Column(name="SEND_SHOW")
	private boolean sendShow;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="SEND_TIME")
	private Date sendTime;

	@JsonIgnore
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="SEND_USER")
	private User sendUser;

	@Column(name="TAKE_SHOW")
	private boolean takeShow;

	@JsonIgnore
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="TAKE_USER")
	private User takeUser;
	
	@Transient
	private UserVo takeUserVo;
	
	@Transient
	private UserVo sendUserVo;

	//bi-directional many-to-one association to MessageTalk
	@JsonIgnore
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="MESSAGE_TALK_ID")
	private MessageTalk messageTalk;

	public Message() {
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

	public boolean getExistsRead() {
		return this.existsRead;
	}

	public void setExistsRead(boolean existsRead) {
		this.existsRead = existsRead;
	}

	public boolean getSendShow() {
		return this.sendShow;
	}

	public void setSendShow(boolean sendShow) {
		this.sendShow = sendShow;
	}

	public Date getSendTime() {
		return this.sendTime;
	}

	public void setSendTime(Date sendTime) {
		this.sendTime = sendTime;
	}

	public boolean getTakeShow() {
		return this.takeShow;
	}

	public void setTakeShow(boolean takeShow) {
		this.takeShow = takeShow;
	}

	public MessageTalk getMessageTalk() {
		return this.messageTalk;
	}

	public void setMessageTalk(MessageTalk messageTalk) {
		this.messageTalk = messageTalk;
	}

	public User getSendUser() {
		return sendUser;
	}

	public void setSendUser(User sendUser) {
		this.sendUser = sendUser;
	}

	public User getTakeUser() {
		return takeUser;
	}

	public void setTakeUser(User takeUser) {
		this.takeUser = takeUser;
	}

	public UserVo getTakeUserVo() {
		return takeUserVo;
	}

	public void setTakeUserVo(UserVo takeUserVo) {
		this.takeUserVo = takeUserVo;
	}

	public UserVo getSendUserVo() {
		return sendUserVo;
	}

	public void setSendUserVo(UserVo sendUserVo) {
		this.sendUserVo = sendUserVo;
	}

}