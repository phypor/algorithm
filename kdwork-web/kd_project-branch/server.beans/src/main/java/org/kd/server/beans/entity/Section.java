package org.kd.server.beans.entity;

import java.io.Serializable;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
/**
 * The persistent class for the section database table.
 * 
 */
@Entity
@Table(name="SECTION")
@JsonIgnoreProperties(value={"hibernateLazyInitializer","handler"})
public class Section implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="ID")
	private Long id;

	@Column(name="NAME")
	private String name;

	public Section() {
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

}