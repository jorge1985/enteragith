package com.youandbbva.enteratv.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.json.JSONObject;

/**
 * bbva_channel_payonwer Table.
 * 
 * @author CJH
 *
 */

@Entity
@Table(name="bbva_channel_payonwer")
public class ChannelPayownerInfo {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="id")
	private Long id;

	@Column(name="channel_id")
	private Long channelID;

	@Column(name="payowner_id")
	private String payownerID;


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

	public String getPayownerID() {
		return payownerID;
	}
	public void setPayownerID(String payownerID) {
		this.payownerID = payownerID;
	}
	
	public JSONObject toJSONObject(){
		JSONObject result = new JSONObject();
		try{
			result.put("id", id);
			result.put("payowner_id", payownerID);
			result.put("channel_id", channelID);
		}catch (Exception e){}
		return result;
	}
	
}
