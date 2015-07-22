package org.kd.server.beans.entity;

import java.io.Serializable;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;


/**
 * The persistent class for the quantity_scale database table.
 * 
 */
@Entity
@Table(name="QUANTITY_SCALE")
@JsonIgnoreProperties(value={"hibernateLazyInitializer","handler"})
public class QuantityScale implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="ID")
	private Long id;

	@Column(name="NAME")
	private String name;


	//bi-directional many-to-one association to Enterprise
	@JsonIgnore
	@OneToMany(mappedBy="quantityScale")
	private List<Enterprise> enterprises;

	public QuantityScale() {
	}

	public QuantityScale(Long id) {
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
		return name;
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

}