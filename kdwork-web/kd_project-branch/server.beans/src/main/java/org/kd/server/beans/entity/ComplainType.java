package org.kd.server.beans.entity;

import java.io.Serializable;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.List;


/**
 * The persistent class for the complain_type database table.
 * 
 */
@Entity
@Table(name="COMPLAIN_TYPE")
public class ComplainType implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="ID")
	private Long id;

	@Column(name="NAME")
	private String name;

	//bi-directional many-to-one association to EnterpriseComplain
	@JsonIgnore
	@OneToMany(mappedBy="complainType")
	private List<EnterpriseComplain> enterpriseComplains;

	public ComplainType() {
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

	public List<EnterpriseComplain> getEnterpriseComplains() {
		return this.enterpriseComplains;
	}

	public void setEnterpriseComplains(List<EnterpriseComplain> enterpriseComplains) {
		this.enterpriseComplains = enterpriseComplains;
	}

}