package com.youandbbva.enteratv.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.json.JSONObject;

/**
 * bbva_content_file_media Table.
 * 
 * @author CJH
 *
 */

@Entity
@Table(name="bbva_content_file_media")
public class ContentFileMediaInfo {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="id")
	private Long id;

	@Column(name="content_id")
	private Long contentID;

	@Column(name="name")
	private String name;

	@Column(name="media")
	private String media;

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getContentID() {
		return contentID;
	}
	public void setContentID(Long contentID) {
		this.contentID = contentID;
	}
	public String getMedia() {
		return media;
	}
	public void setMedia(String media) {
		this.media = media;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public JSONObject toJSONObject(){
		JSONObject result = new JSONObject();
		try{
			result.put("id", id);
			result.put("content_id", contentID);
			result.put("name", name);
			result.put("media", media);
		}catch (Exception e){}
		return result;
	}
	
}
