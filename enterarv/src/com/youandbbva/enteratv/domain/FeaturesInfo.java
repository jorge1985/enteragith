package com.youandbbva.enteratv.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.json.JSONObject;

/**
 * bbva_features Table.
 * 
 * @author CJH
 *
 */

@Entity
@Table(name="bbva_features")
public class FeaturesInfo {
	
	@Column(name="id")
	private Long id;

	@Column(name="sub_id")
	private Long subID;
	
	@Column(name="image")
	private String image;

	@Column(name="title")
	private String title;

	@Column(name="date")
	private String date;

	@Column(name="content")
	private String content;

	@Column(name="content_id")
	private Long contentID;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}

	public Long getSubID() {
		return subID;
	}
	public void setSubID(Long subID) {
		this.subID = subID;
	}

	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}

	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
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
			result.put("sub_id", subID);
			result.put("image", image);
			result.put("title", title);
			result.put("date", date);
			result.put("content", content);
			result.put("content_id", contentID);
		}catch (Exception e){}
		return result;
	}
	
}
