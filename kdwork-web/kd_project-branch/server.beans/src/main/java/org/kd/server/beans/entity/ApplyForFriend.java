package org.kd.server.beans.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name="APPLY_FOR_FRIEND")
@JsonIgnoreProperties(value={"hibernateLazyInitializer","handler"})
public class ApplyForFriend implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="ID")
	private Long id;
	
	@ManyToOne
	@JoinColumn(name="APPLY_USER_ID")
	private User applyUser;

	@ManyToOne
	@JoinColumn(name="EXAMINATION_USER_ID")
	private User examinationUser;
	
	@Column(name="APPROVAL")
	private int approval;    //审批结果    0  未审批   1  申请通过  2  拒绝添加  3  删除   4  已读
	
	@Column(name="APPLY_TIME")
	private Date applyTime;
	
	@Column(name="APPLY_DESC")
	private String applyDesc;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public User getApplyUser() {
		return applyUser;
	}

	public void setApplyUser(User applyUser) {
		this.applyUser = applyUser;
	}

	public User getExaminationUser() {
		return examinationUser;
	}

	public void setExaminationUser(User examinationUser) {
		this.examinationUser = examinationUser;
	}

	public int getApproval() {
		return approval;
	}

	public void setApproval(int approval) {
		this.approval = approval;
	}

	public Date getApplyTime() {
		return applyTime;
	}

	public void setApplyTime(Date applyTime) {
		this.applyTime = applyTime;
	}

	public String getApplyDesc() {
		return applyDesc;
	}

	public void setApplyDesc(String applyDesc) {
		this.applyDesc = applyDesc;
	}
	
	
}
