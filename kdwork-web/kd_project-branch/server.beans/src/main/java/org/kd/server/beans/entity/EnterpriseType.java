package org.kd.server.beans.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="ENTERPRISE_TYPE")
public class EnterpriseType  implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="ID")
     private Long id;
	
	@Column(name="NAME")
     private String name;
	
	@Column(name="CODE")
     private String code;
	
	@OneToMany(fetch = FetchType.LAZY)
	private List<Enterprise> enterprises;
     
	public Long getId() {
		return id;
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
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public List<Enterprise> getEnterprises() {
		return enterprises;
	}
	public void setEnterprises(List<Enterprise> enterprises) {
		this.enterprises = enterprises;
	}
    
}
