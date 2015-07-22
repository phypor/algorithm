package org.kd.server.beans.entity;

import java.io.Serializable;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * The persistent class for the province_city_area database table.
 * 
 */
@Entity
@Table(name="PROVINCE_CITY_AREA")
@JsonIgnoreProperties(value={"hibernateLazyInitializer","handler"})
public class ProvinceCityArea implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="ID")
	private Long id;

	@Column(name="NAME")
	private String name;

	//bi-directional many-to-one association to ProvinceCityArea
	@JsonIgnore
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="PARENT_ID")
	private ProvinceCityArea provinceCityArea;


	public ProvinceCityArea() {
	}

	public ProvinceCityArea(Long id) {
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

	public ProvinceCityArea getProvinceCityArea() {
		return this.provinceCityArea;
	}

	public void setProvinceCityArea(ProvinceCityArea provinceCityArea) {
		this.provinceCityArea = provinceCityArea;
	}


}