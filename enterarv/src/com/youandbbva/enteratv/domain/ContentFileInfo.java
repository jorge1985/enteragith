package com.youandbbva.enteratv.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.json.JSONObject;

/**
 * bbva_content_file Table.
 * 
 * @author CJH
 *
 */

@Entity
@Table(name="bbva_content_file")
public class ContentFileInfo {
	
	@Id
	@Column(name="content_id")
	private Long contentID;

	@Column(name="description")
	private String description;

	public Long getContentID() {
		return contentID;
	}
	public void setContentID(Long contentID) {
		this.contentID = contentID;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	public JSONObject toJSONObject(){
		JSONObject result = new JSONObject();
		try{
			result.put("content_id", contentID);
			result.put("description", description);
		}catch (Exception e){}
		return result;
	}
	
}
