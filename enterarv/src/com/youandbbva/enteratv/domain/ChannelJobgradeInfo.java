package com.youandbbva.enteratv.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.json.JSONObject;

/**
 * bbva_channel_jobgrade Table.
 * 
 * @author CJH
 *
 */

@Entity
@Table(name="bbva_channel_jobgrade")
public class ChannelJobgradeInfo {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="id")
	private Long id;

	@Column(name="channel_id")
	private Long channelID;

	@Column(name="jobgrade_id")
	private String jobgradeID;


	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}

	public Long getChannelID() {
		return channelID;
	}
	public void setChannelID(Long channelID) {
		this.channelID = channelID;
	}

	public String getJobgradeID() {
		return jobgradeID;
	}
	public void setJobgradeID(String jobgradeID) {
		this.jobgradeID = jobgradeID;
	}
	
	public JSONObject toJSONObject(){
		JSONObject result = new JSONObject();
		try{
			result.put("id", id);
			result.put("jobgrade_id", jobgradeID);
			result.put("channel_id", channelID);
		}catch (Exception e){}
		return result;
	}
	
}
