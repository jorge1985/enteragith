package com.youandbbva.enteratv.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.json.JSONObject;

/**
 * bbva_channel_division Table.
 * 
 * @author CJH
 *
 */

@Entity
@Table(name="bbva_channel_division")
public class ChannelDivisionInfo {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="id")
	private Long id;

	@Column(name="type")
	private String type;

	@Column(name="channel_id")
	private Long channelID;

	@Column(name="division_id")
	private Long divisionID;


	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}

	public Long getChannelID() {
		return channelID;
	}
	public void setChannelID(Long channelID) {
		this.channelID = channelID;
	}

	public Long getDivisionID() {
		return divisionID;
	}
	public void setDivisionID(Long divisionID) {
		this.divisionID = divisionID;
	}
	
	
	public JSONObject toJSONObject(){
		JSONObject result = new JSONObject();
		try{
			result.put("id", id);
			result.put("type", type);
			result.put("channel_id", channelID);
			result.put("division_id", divisionID);
		}catch (Exception e){}
		return result;
	}
	
}
