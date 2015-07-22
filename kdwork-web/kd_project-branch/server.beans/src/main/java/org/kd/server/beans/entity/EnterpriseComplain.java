package org.kd.server.beans.entity;

import java.io.Serializable;

import javax.persistence.*;

/**
 * The persistent class for the enterprise_complain database table. 企业投诉
 */
@Entity
@Table(name = "ENTERPRISE_COMPLAIN")
public class EnterpriseComplain implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Long id;

	@Column(name = "COMPLAIN_CONTENT")
	private String complainContent;

	// bi-directional many-to-one association to ComplainType
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "COMPLAIN_TYPE_ID")
	private ComplainType complainType;

	// bi-directional many-to-one association to Enterprise
	@ManyToOne(fetch = FetchType.LAZY)
	private Enterprise enterprise;

	public EnterpriseComplain() {
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getComplainContent() {
		return this.complainContent;
	}

	public void setComplainContent(String complainContent) {
		this.complainContent = complainContent;
	}

	public ComplainType getComplainType() {
		return this.complainType;
	}

	public void setComplainType(ComplainType complainType) {
		this.complainType = complainType;
	}

	public Enterprise getEnterprise() {
		return this.enterprise;
	}

	public void setEnterprise(Enterprise enterprise) {
		this.enterprise = enterprise;
	}

}