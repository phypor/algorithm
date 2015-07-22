package org.kd.server.beans.entity;

import java.io.Serializable;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

/**
 * The persistent class for the industry database table.
 * 
 */
@Entity
@Table(name = "INDUSTRY")
@JsonIgnoreProperties(value = { "hibernateLazyInitializer", "handler" })
public class Industry implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Long id;

	@Column(name = "NAME")
	private String name;

	@JsonIgnore
	@OneToMany(mappedBy = "industry")
	private List<Enterprise> enterprises;

	// bi-directional many-to-one association to Industry
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PARENT_ID")
	private Industry industry;

	// bi-directional many-to-one association to Industry
	@JsonIgnore
	@OneToMany(mappedBy = "industry")
	private List<Industry> industries;

	public Industry() {
	}

	public Industry(Long id) {
		super();
		this.id = id;
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Enterprise> getEnterprises() {
		return this.enterprises;
	}

	public void setEnterprises(List<Enterprise> enterprises) {
		this.enterprises = enterprises;
	}

	public Industry getIndustry() {
		return this.industry;
	}

	public void setIndustry(Industry industry) {
		this.industry = industry;
	}

	public List<Industry> getIndustries() {
		return this.industries;
	}

	public void setIndustries(List<Industry> industries) {
		this.industries = industries;
	}

}