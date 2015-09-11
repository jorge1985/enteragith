package com.youandbbva.enteratv.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.json.JSONObject;

/**
 * bbva_content_video Table.
 * 
 * @author CJH
 *
 */

@Entity
@Table(name="bbva_content_video")
public class ContentVideoInfo {
	
	@Id
	@Column(name="content_id")
	private Long contentID;

	@Column(name="video")
	private String video;

	@Column(name="image")
	private String image;

	public ContentVideoInfo() {
		// TODO Auto-generated constructor stub
		contentID=(long)0;
		video="";
		image="";
	}
	public Long getContentID() {
		return contentID;
	}
	public void setContentID(Long contentID) {
		this.contentID = contentID;
	}
	public String getVideo() {
		return video;
	}
	public void setVideo(String video) {
		this.video = video;
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
			result.put("video", video);
			result.put("image", image);
		}catch (Exception e){}
		return result;
	}
	
}
