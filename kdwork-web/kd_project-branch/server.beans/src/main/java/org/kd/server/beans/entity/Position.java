package org.kd.server.beans.entity;

import java.io.Serializable;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

/**
 * The persistent class for the position database table.
 * 
 */
@Entity
@Table(name = "POSITION")
@JsonIgnoreProperties(value = { "hibernateLazyInitializer", "handler" })
public class Position implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Long id;

	@Column(name = "NAME")
	private String name;

	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PARENT_ID")
	private Position position;

	// bi-directional many-to-one association to RecruitInfo
	@JsonIgnore
	@OneToMany(mappedBy = "position")
	private List<RecruitInfo> recruitInfos;

	@JsonIgnore
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "SECTION_ID")
	private Section section;

	public Section getSection() {
		return section;
	}

	public void setSection(Section section) {
		this.section = section;
	}

	public Position getPosition() {
		return position;
	}

	public void setPosition(Position position) {
		this.position = position;
	}

	public Position() {
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

	public List<RecruitInfo> getRecruitInfos() {
		return this.recruitInfos;
	}

	public void setRecruitInfos(List<RecruitInfo> recruitInfos) {
		this.recruitInfos = recruitInfos;
	}

}