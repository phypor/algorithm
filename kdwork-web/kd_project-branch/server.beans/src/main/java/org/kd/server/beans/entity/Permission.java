package org.kd.server.beans.entity;

import java.io.Serializable;
import javax.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;

/**
 * The persistent class for the permission database table.
 * 
 */
@Entity
@Table(name="PERMISSION")
@JsonIgnoreProperties(value={"roles"})
public class Permission implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="ID")
	private Long id;

	@Column(name="DESCRIPTION")
	private String description;

	@Column(name="NAME")
	private String name;

	@Column(name="PARENT_ID")
	private Long parentId;

	@Column(name="PERMISSION")
	private String permission;

	@ManyToMany(cascade = {CascadeType.PERSIST }, fetch = FetchType.LAZY)
	@JoinTable(name = "PERMISSION_ROLE", joinColumns = { @JoinColumn(name = "PERMISSION_ID", referencedColumnName = "ID") }, 
		inverseJoinColumns = { @JoinColumn(name = "ROLE_ID", referencedColumnName = "ID") })
	private List<Role> roles;

	public Permission() {
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getParentId() {
		return this.parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public String getPermission() {
		return this.permission;
	}

	public void setPermission(String permission) {
		this.permission = permission;
	}

	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

}