package org.kd.server.beans.entity;

import java.io.Serializable;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.Date;
import java.util.List;

/**
 * The persistent class for the infomation database table.
 * 
 */
@Entity
@Table(name = "INFOMATION")
@JsonIgnoreProperties(value = { "hibernateLazyInitializer", "handler" })
public class Infomation implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Long id;

	@Column(name = "CLICK_LIKE_NUM")
	private int clickLikeNum;

	@Column(name = "CONTENT")
	private String content;

	@Column(name = "URL")
	private String url;

	@ManyToOne
	@JoinColumn(name = "INFO_PLATFORM_TYPE")
	private InfoPlatformType infoPlatformType;

	public InfoPlatformType getInfoPlatformType() {
		return infoPlatformType;
	}

	public void setInfoPlatformType(InfoPlatformType infoPlatformType) {
		this.infoPlatformType = infoPlatformType;
	}

	@Column(name = "CREATE_TIME")
	private Date createTime;

	@Column(name = "DISCUSS_NUM")
	private int discussNum;

	@Column(name = "MEDIA_PLATFORM")
	private String mediaPlatform;

	@Column(name = "READ_NUM")
	private int readNum;

	@Column(name = "TITLE")
	private String title;

	@Column(name = "TITLE_IMAGE")
	private String titleImage;

	@Column(name = "NEWS_TYPE")
	private int newsType;

	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CREATE_USER")
	private User createUser;

	// bi-directional many-to-one association to InfomationDiscuss
	@JsonIgnore
	@OneToMany(mappedBy = "infomation")
	private List<InfomationDiscuss> infomationDiscusses;

	public Infomation() {
	}

	public Infomation(int clickLikeNum, String content, String url,
			Date createTime, int discussNum, String mediaPlatform, int readNum,
			String title, String titleImage, int newsType, User createUser,
			List<InfomationDiscuss> infomationDiscusses) {
		super();
		this.clickLikeNum = clickLikeNum;
		this.content = content;
		this.url = url;
		this.createTime = createTime;
		this.discussNum = discussNum;
		this.mediaPlatform = mediaPlatform;
		this.readNum = readNum;
		this.title = title;
		this.titleImage = titleImage;
		this.newsType = newsType;
		this.createUser = createUser;
		this.infomationDiscusses = infomationDiscusses;
	}

	public Infomation(Long id) {
		super();
		this.id = id;
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

	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public int getDiscussNum() {
		return this.discussNum;
	}

	public void setDiscussNum(int discussNum) {
		this.discussNum = discussNum;
	}

	public String getMediaPlatform() {
		return this.mediaPlatform;
	}

	public void setMediaPlatform(String mediaPlatform) {
		this.mediaPlatform = mediaPlatform;
	}

	public int getReadNum() {
		return this.readNum;
	}

	public void setReadNum(int readNum) {
		this.readNum = readNum;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public List<InfomationDiscuss> getInfomationDiscusses() {
		return this.infomationDiscusses;
	}

	public void setInfomationDiscusses(
			List<InfomationDiscuss> infomationDiscusses) {
		this.infomationDiscusses = infomationDiscusses;
	}

	public User getCreateUser() {
		return createUser;
	}

	public void setCreateUser(User createUser) {
		this.createUser = createUser;
	}

	public String getTitleImage() {
		return titleImage;
	}

	public void setTitleImage(String titleImage) {
		this.titleImage = titleImage;
	}

	public int getNewsType() {
		return newsType;
	}

	public void setNewsType(int newsType) {
		this.newsType = newsType;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

}