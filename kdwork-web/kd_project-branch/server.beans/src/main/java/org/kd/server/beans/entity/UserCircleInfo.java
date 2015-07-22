package org.kd.server.beans.entity;


import java.io.Serializable;

import javax.persistence.*;

import org.kd.server.beans.entity.CircleInfoClickLikeLog;
import org.kd.server.beans.entity.CircleInfoDiscuss;
import org.kd.server.beans.entity.FriendCircle;
import org.kd.server.beans.entity.User;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.kd.server.beans.vo.UserVo;

import java.util.Date;
import java.util.List;
/**
 * The persistent class for the user_circle_info database table.
 * 
 */
@Entity
@Table(name="USER_CIRCLE_INFO")
@JsonIgnoreProperties(value={"hibernateLazyInitializer","handler"})
public class UserCircleInfo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="ID")
	private Long id;

	@Column(name="CLICK_LIKE_NUM")
	private int clickLikeNum;

	@Column(name="CONTENT")
	private String content;
	
	@Column(name="IMAGES_JSON")
	private String imagesJson;

	@Column(name="DISCUSS_NUM")
	private int discussNum;

	@Column(name="PUBLISH_TIME")
	private Date publishTime;

	@Column(name="PUBLISH_TYPE")
	private int publishType;
	
	@Column(name="CONTENT_TYPE")
	private int contentType;   

	@Column(name="REPEAT_NUM")
	private int repeatNum; 
	
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name="ORIGINAL_USER")
	private User originalUser;
	
	@Transient
	private UserVo originalUserVo;
	
	@Transient
	private boolean isSelfClickLike;
	
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name="FRIEND_CIRCLE")
	private FriendCircle friendCircle;
	

	//bi-directional many-to-one association to CircleInfoDiscuss
	@JsonIgnore
	@OneToMany(cascade = CascadeType.REMOVE,mappedBy="userCircleInfo")
	private List<CircleInfoDiscuss> circleInfoDiscusses;
	
	@JsonIgnore
	@OneToMany(cascade = CascadeType.REMOVE,mappedBy="userCircleInfo")
	private List<CircleInfoClickLikeLog> circleInfoClickLikeLog;

	//bi-directional many-to-one association to User
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name="PUBLISH_USER")
	private User publishUser;
	
	@Transient
	private UserVo publishUserVo;

	public UserCircleInfo() {
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getClickLikeNum() {
		return this.clickLikeNum;
	}

	public void setClickLikeNum(int clickLikeNum) {
		this.clickLikeNum = clickLikeNum;
	}

	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public int getDiscussNum() {
		return this.discussNum;
	}

	public void setDiscussNum(int discussNum) {
		this.discussNum = discussNum;
	}

	public Date getPublishTime() {
		return this.publishTime;
	}

	public void setPublishTime(Date publishTime) {
		this.publishTime = publishTime;
	}

	public int getPublishType() {
		return this.publishType;
	}

	public void setPublishType(int publishType) {
		this.publishType = publishType;
	}

	public int getRepeatNum() {
		return this.repeatNum;
	}

	public void setRepeatNum(int repeatNum) {
		this.repeatNum = repeatNum;
	}

	public List<CircleInfoDiscuss> getCircleInfoDiscusses() {
		return this.circleInfoDiscusses;
	}

	public void setCircleInfoDiscusses(List<CircleInfoDiscuss> circleInfoDiscusses) {
		this.circleInfoDiscusses = circleInfoDiscusses;
	}

	public User getOriginalUser() {
		return originalUser;
	}

	public void setOriginalUser(User originalUser) {
		this.originalUser = originalUser;
	}

	public User getPublishUser() {
		return publishUser;
	}

	public void setPublishUser(User publishUser) {
		this.publishUser = publishUser;
	}

	public FriendCircle getFriendCircle() {
		return friendCircle;
	}

	public void setFriendCircle(FriendCircle friendCircle) {
		this.friendCircle = friendCircle;
	}

	public int getContentType() {
		return contentType;
	}

	public void setContentType(int contentType) {
		this.contentType = contentType;
	}

	public String getImagesJson() {
		return imagesJson;
	}

	public void setImagesJson(String imagesJson) {
		this.imagesJson = imagesJson;
	}

	public boolean getIsSelfClickLike() {
		return isSelfClickLike;
	}

	public void setIsSelfClickLike(boolean isSelfClickLike) {
		this.isSelfClickLike = isSelfClickLike;
	}

	public void setSelfClickLike(boolean isSelfClickLike) {
		this.isSelfClickLike = isSelfClickLike;
	}

	public UserVo getOriginalUserVo() {
		return originalUserVo;
	}

	public void setOriginalUserVo(UserVo originalUserVo) {
		this.originalUserVo = originalUserVo;
	}

	public UserVo getPublishUserVo() {
		return publishUserVo;
	}

	public void setPublishUserVo(UserVo publishUserVo) {
		this.publishUserVo = publishUserVo;
	}

	public List<CircleInfoClickLikeLog> getCircleInfoClickLikeLog() {
		return circleInfoClickLikeLog;
	}

	public void setCircleInfoClickLikeLog(
			List<CircleInfoClickLikeLog> circleInfoClickLikeLog) {
		this.circleInfoClickLikeLog = circleInfoClickLikeLog;
	}

}