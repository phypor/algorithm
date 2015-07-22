package org.kd.server.beans.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the initial_info database table.
 * 
 */
@Entity
@Table(name="INITIAL_INFO")
public class InitialInfo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="ID")
	private Long id;

	@Column(name="DPI")
	private String dpi;

	@Column(name="IMEI")
	private String imei;

	@Column(name="IMSI")
	private String imsi;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="LAST_ONLINE_TIME")
	private Date lastOnlineTime;

	@Column(name="PHONE_BRAND")
	private String phoneBrand;

	@Column(name="PHONE_VERSION")
	private String phoneVersion;

	@Column(name="SCREAM_HEIGHT")
	private int screamHeight;

	@Column(name="SCREAM_WIDTH")
	private int screamWidth;

	@Column(name="SESSION_ID")
	private String sessionId;

	public InitialInfo() {
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDpi() {
		return this.dpi;
	}

	public void setDpi(String dpi) {
		this.dpi = dpi;
	}

	public String getImei() {
		return this.imei;
	}

	public void setImei(String imei) {
		this.imei = imei;
	}

	public String getImsi() {
		return this.imsi;
	}

	public void setImsi(String imsi) {
		this.imsi = imsi;
	}

	public Date getLastOnlineTime() {
		return this.lastOnlineTime;
	}

	public void setLastOnlineTime(Date lastOnlineTime) {
		this.lastOnlineTime = lastOnlineTime;
	}

	public String getPhoneBrand() {
		return this.phoneBrand;
	}

	public void setPhoneBrand(String phoneBrand) {
		this.phoneBrand = phoneBrand;
	}

	public String getPhoneVersion() {
		return this.phoneVersion;
	}

	public void setPhoneVersion(String phoneVersion) {
		this.phoneVersion = phoneVersion;
	}

	public int getScreamHeight() {
		return this.screamHeight;
	}

	public void setScreamHeight(int screamHeight) {
		this.screamHeight = screamHeight;
	}

	public int getScreamWidth() {
		return this.screamWidth;
	}

	public void setScreamWidth(int screamWidth) {
		this.screamWidth = screamWidth;
	}

	public String getSessionId() {
		return this.sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

}