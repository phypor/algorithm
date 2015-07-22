package org.kd.server.beans.entity;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name="FRIEND_CIRCLE")
@JsonIgnoreProperties(value={"hibernateLazyInitializer","handler"})
public class FriendCircle  implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="ID")
     private Long id;
	
	@JsonIgnore
	@ManyToMany(cascade = {CascadeType.MERGE },fetch=FetchType.LAZY)
	@JoinTable(name = "USER_CIRCLE", joinColumns = { @JoinColumn(name = "FRIEND_CIRCLE_ID", referencedColumnName = "ID") }, 
		inverseJoinColumns = { @JoinColumn(name = "USER_ID", referencedColumnName = "ID") })
	private Set<User> users;
	
	@JsonIgnore
	@OneToMany(mappedBy="friendCircle",fetch=FetchType.LAZY)
	private List<UserCircleInfo> userCircleInfos;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Set<User> getUsers() {
		return users;
	}

	public void setUsers(Set<User> users) {
		this.users = users;
	}

	public List<UserCircleInfo> getUserCircleInfos() {
		return userCircleInfos;
	}

	public void setUserCircleInfos(List<UserCircleInfo> userCircleInfos) {
		this.userCircleInfos = userCircleInfos;
	}
     
	@Override
	 public boolean equals(Object obj) {
	  if (obj instanceof FriendCircle) {
		  FriendCircle t = (FriendCircle) obj;
	   return this.getId().equals(t.getId());
	  }
	  return super.equals(obj);
	 }
	
	public int hashCode(){
		return this.getId()!=null?this.getId().intValue():0;
	}
}
