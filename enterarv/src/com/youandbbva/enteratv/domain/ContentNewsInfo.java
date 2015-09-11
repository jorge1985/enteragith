package com.youandbbva.enteratv.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.json.JSONObject;

/**
 * bbva_content_news Table.
 * 
 * @author CJH
 *
 */

@Entity
@Table(name="bbva_content_news")
public class ContentNewsInfo {
	
	@Id
	@Column(name="content_id")
	private Long contentID;

	@Column(name="content")
	private String content;

	@Column(name="image")
	private String image;

	public ContentNewsInfo() {
		// TODO Auto-generated constructor stub
		contentID=(long)0;
		content="";
		image="";
	}
	public Long getContentID() {
		return contentID;
	}
	public void setContentID(Long contentID) {
		this.contentID = contentID;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	
	public JSONObject toJSONObject(){
		JSONObject result = new JSONObject();
		try{
			result.put("content_id", contentID);
			result.put("content", content);
			result.put("image", image);
		}catch (Exception e){}
		return result;
	}
	
}
