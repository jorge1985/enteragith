package com.youandbbva.enteratv.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.json.JSONObject;

/**
 * bbva_content_channel Table.
 * 
 * @author CJH
 *
 */

@Entity
@Table(name="bbva_content_channel")
public class ContentChannelInfo {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="id")
	private Long id;

	@Column(name="content_id")
	private Long contentID;

	@Column(name="channel_id")
	private Long channelID;

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
	public Long getContentID() {
		return contentID;
	}
	public void setContentID(Long contentID) {
		this.contentID = contentID;
	}
	
	public JSONObject toJSONObject(){
		JSONObject result = new JSONObject();
		try{
			result.put("id", id);
			result.put("content", contentID);
			result.put("channel_id", channelID);
		}catch (Exception e){}
		return result;
	}
	
}
