package org.kd.server.beans.entity;

import java.io.Serializable;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.Date;
/**
 * The persistent class for the image database table.
 * 
 */
@Entity
@Table(name="IMAGE")
@JsonIgnoreProperties(value={"hibernateLazyInitializer","handler"})
public class Image implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="ID")
	private Long id;

	@Column(name="ALT")
	private String alt;
	
	@JsonIgnore
	@Column(name="BEGIN_TIME")
	private Date beginTime;

	@Column(name="DESCRIPT")
	private String descript;

	@JsonIgnore
	@Column(name="END_TIME")
	private Date endTime;

	@Column(name="NAME")
	private String name;

	@Column(name="TITLE")
	private String title;

	@ManyToOne(fetch=FetchType.LAZY)
	@JsonIgnore
	@JoinColumn(name="TYPE_ID")
	private ImageType imageType;

	@Column(name="URL")
	private String url;

	@JsonIgnore
	@Column(name="ENABLE")
	private boolean enable;
	
	@Column(name="INTERLINKING_URL")
	private String interlinkingUrl;

	public Image() {
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAlt() {
		return this.alt;
	}

	public void setAlt(String alt) {
		this.alt = alt;
	}

	public Date getBeginTime() {
		return this.beginTime;
	}

	public void setBeginTime(Date beginTime) {
		this.beginTime = beginTime;
	}

	public String getDescript() {
		return this.descript;
	}

	public void setDescript(String descript) {
		this.descript = descript;
	}

	public Date getEndTime() {
		return this.endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public ImageType getImageType() {
		return imageType;
	}

	public void setImageType(ImageType imageType) {
		this.imageType = imageType;
	}

	public String getUrl() {
		return this.url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getInterlinkingUrl() {
		return interlinkingUrl;
	}

	public void setInterlinkingUrl(String interlinkingUrl) {
		this.interlinkingUrl = interlinkingUrl;
	}

	public boolean isEnable() {
		return enable;
	}

	public void setEnable(boolean enable) {
		this.enable = enable;
	}

}