package org.kd.server.beans.entity;

import java.io.Serializable;

import javax.persistence.*;

import org.kd.server.beans.vo.UserVo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;


/**
 * The persistent class for the message_talk database table.
 * 
 */
@Entity
@Table(name="MESSAGE_TALK")
@JsonIgnoreProperties(value={"hibernateLazyInitializer","handler"})
public class MessageTalk implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="ID")
	private Long id;

	//bi-directional many-to-one association to Message
	@JsonIgnore
	@OneToMany(mappedBy="messageTalk")
	private List<Message> messages;
	
	@Column(name="USER1_UNREAD_COUNT")
	private int user1UnreadCount;
	
	@Column(name="USER2_UNREAD_COUNT")
	private int user2UnreadCount;

	//bi-directional many-to-one association to Message
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="LAST_MESSAGE")
	private Message message;

	//bi-directional many-to-one association to User
	@JsonIgnore
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="USER2")
	private User user1Bean;

	//bi-directional many-to-one association to User
	@JsonIgnore
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="USER1")
	private User user2Bean;
	
	@Transient
	private UserVo user1Vo;
	
	@Transient
	private UserVo user2Vo;

	public MessageTalk() {
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public List<Message> getMessages() {
		return this.messages;
	}

	public void setMessages(List<Message> messages) {
		this.messages = messages;
	}

	public Message addMessage(Message message) {
		getMessages().add(message);
		message.setMessageTalk(this);

		return message;
	}

	public Message removeMessage(Message message) {
		getMessages().remove(message);
		message.setMessageTalk(null);

		return message;
	}

	public Message getMessage() {
		return this.message;
	}

	public void setMessage(Message message) {
		this.message = message;
	}

	public User getUser1Bean() {
		return this.user1Bean;
	}

	public void setUser1Bean(User user1Bean) {
		this.user1Bean = user1Bean;
	}

	public User getUser2Bean() {
		return this.user2Bean;
	}

	public void setUser2Bean(User user2Bean) {
		this.user2Bean = user2Bean;
	}

	public int getUser1UnreadCount() {
		return user1UnreadCount;
	}

	public void setUser1UnreadCount(int user1UnreadCount) {
		this.user1UnreadCount = user1UnreadCount;
	}

	public int getUser2UnreadCount() {
		return user2UnreadCount;
	}

	public void setUser2UnreadCount(int user2UnreadCount) {
		this.user2UnreadCount = user2UnreadCount;
	}

	public UserVo getUser1Vo() {
		return user1Vo;
	}

	public void setUser1Vo(UserVo user1Vo) {
		this.user1Vo = user1Vo;
	}

	public UserVo getUser2Vo() {
		return user2Vo;
	}

	public void setUser2Vo(UserVo user2Vo) {
		this.user2Vo = user2Vo;
	}

}