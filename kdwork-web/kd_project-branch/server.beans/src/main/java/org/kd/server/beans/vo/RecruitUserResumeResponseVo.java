package org.kd.server.beans.vo;

import java.util.Date;

import org.kd.server.beans.entity.RecruitUserResume;

public class RecruitUserResumeResponseVo {
    private RecruitUserResume recruitUserResume;
    private Date sendTime;
    
	public RecruitUserResume getRecruitUserResume() {
		return recruitUserResume;
	}
	public void setRecruitUserResume(RecruitUserResume recruitUserResume) {
		this.recruitUserResume = recruitUserResume;
	}
	public Date getSendTime() {
		return sendTime;
	}
	public void setSendTime(Date sendTime) {
		this.sendTime = sendTime;
	}
    
}
