package com.youandbbva.enteratv.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.json.JSONObject;

/**
 * bbva_banner Table.
 * 
 * @author CJH
 *
 */

@Entity
@Table(name="bbva_banner")
public class BannerInfo {
	
	@Column(name="id")
	private Long id;

	@Column(name="image")
	private String image;

	@Column(name="target")
	private String target;

	@Column(name="url")
	private String url;

	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}

	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}

	public String getTarget() {
		return target;
	}
	public void setTarget(String target) {
		this.target = target;
	}

	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}

	public JSONObject toJSONObject(){
		JSONObject result = new JSONObject();
		try{
			result.put("id", id);
			result.put("image", image);
			result.put("target", target);
			result.put("url", url);
		}catch (Exception e){}
		return result;
	}
}
