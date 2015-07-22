package org.kd.server.beans.entity;

import java.io.Serializable;
import javax.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;
/**
 * The persistent class for the role database table.
 * 
 */
@Entity
@Table(name="ROLE")
public class Role implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="ID")
	@JsonIgnoreProperties(value={"users"})
	private Long id;
	
	@Column(name="ROLE")
	private String role;

	@Column(name="DESCRIPTION")
	private String description;

	@Column(name="NAME")
	private String name;

	@ManyToMany(cascade = {CascadeType.ALL}, fetch = FetchType.LAZY)
	@JoinTable(name = "PERMISSION_ROLE", joinColumns = { @JoinColumn(name = "ROLE_ID", referencedColumnName = "ID") }, 
		inverseJoinColumns = { @JoinColumn(name = "PERMISSION_ID", referencedColumnName = "ID") })
	private List<Permission> permissions;
	
	@ManyToMany(cascade = {CascadeType.PERSIST }, fetch = FetchType.LAZY)
	@JoinTable(name = "ROLE_USER", joinColumns = { @JoinColumn(name = "ROLE_ID", referencedColumnName = "ID") }, 
		inverseJoinColumns = { @JoinColumn(name = "USER_ID", referencedColumnName = "ID") })
	private List<User> users;

	public Role() {
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

	public List<Permission> getPermissions() {
		return permissions;
	}

	public void setPermissions(List<Permission> permissions) {
		this.permissions = permissions;
	}

	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

}