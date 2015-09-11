package com.youandbbva.enteratv.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.json.JSONObject;

/**
 * bbva_content_gallery Table.
 * 
 * @author CJH
 *
 */

@Entity
@Table(name="bbva_content_gallery")
public class ContentGalleryInfo {
	
	@Id
	@Column(name="content_id")
	private Long contentID;

	@Column(name="detail")
	private String detail;

	public Long getContentID() {
		return contentID;
	}
	public void setContentID(Long contentID) {
		this.contentID = contentID;
	}
	public String getDetail() {
		return detail;
	}
	public void setDetail(String detail) {
		this.detail = detail;
	}
	
	public JSONObject toJSONObject(){
		JSONObject result = new JSONObject();
		try{
			result.put("content_id", contentID);
			result.put("detail", detail);
		}catch (Exception e){}
		return result;
	}
	
}
