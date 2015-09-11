package com.youandbbva.enteratv.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.json.JSONObject;

/**
 * bbva_channel_promote Table.
 * 
 * @author CJH
 *
 */

@Entity
@Table(name="bbva_channel_promote")
public class ChannelPromoteInfo {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="id")
	private Long id;

	@Column(name="channel_id")
	private Long channelID;

	@Column(name="promote_id")
	private String promoteID;


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

	public String getPromoteID() {
		return promoteID;
	}
	public void setPromoteID(String promoteID) {
		this.promoteID = promoteID;
	}
	
	
	public JSONObject toJSONObject(){
		JSONObject result = new JSONObject();
		try{
			result.put("id", id);
			result.put("channel_id", channelID);
			result.put("promote_id", promoteID);
		}catch (Exception e){}
		return result;
	}
	
}
