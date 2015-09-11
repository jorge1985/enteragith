package com.youandbbva.enteratv.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.json.JSONObject;

/**
 * bbva_content Table.
 * 
 * @author CJH
 *
 */

@Entity
@Table(name="bbva_content")
public class ContentInfo {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="id")
	private Long id;
	
	@Column(name="name")
	private String name;

	@Column(name="type")
	private String type;

	@Column(name="show_in")
	private String showIn;
	
	@Column(name="validity_start")
	private String validityStart;
	
	@Column(name="validity_end")
	private String validityEnd;
	
	@Column(name="active")
	private String active;
	
	@Column(name="status")
	private String status;

	@Column(name="created_at")
	private String createdAt;

	@Column(name="updated_at")
	private String updatedAt;
	
	@Column(name="html")
	private String html;
	
	@Column(name="html_mobile")
	private String htmlMobile;
	
	@Column(name="blog")
	private String blog;

	private String image;
	private String content;
	
	public ContentInfo() {
		// TODO Auto-generated constructor stub
		id = (long)0;
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getShowIn() {
		return showIn;
	}

	public void setShowIn(String showIn) {
		this.showIn = showIn;
	}

	public String getValidityStart() {
		return validityStart;
	}

	public void setValidityStart(String validityStart) {
		this.validityStart = validityStart;
	}

	public String getValidityEnd() {
		return validityEnd;
	}

	public void setValidityEnd(String validityEnd) {
		this.validityEnd = validityEnd;
	}

	public String getActive() {
		return active;
	}

	public void setActive(String active) {
		this.active = active;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getHtml() {
		return html;
	}

	public void setHtml(String html) {
		this.html = html;
	}

	public String getHtmlMobile() {
		return htmlMobile;
	}

	public void setHtmlMobile(String htmlMobile) {
		this.htmlMobile = htmlMobile;
	}

	public String getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}

	public String getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(String updatedAt) {
		this.updatedAt = updatedAt;
	}
	
	public String getBlog() {
		return blog;
	}

	public void setBlog(String blog) {
		this.blog = blog;
	}

	public JSONObject toJSONObject(){
		JSONObject result = new JSONObject();
		try{
			result.put("id", id);
			result.put("name", name);
			result.put("type", type);
			result.put("show_in", showIn);
			result.put("validity_start", validityStart);
			result.put("validity_end", validityEnd);
			result.put("active", active);
			result.put("status", status);
			result.put("blog", blog);
			result.put("updated_at", updatedAt);
			result.put("html", html);
		}catch (Exception e){}
		return result;
	}

	public void setContent(String string) {
		// TODO Auto-generated method stub
		this.content = string;
	}

	public void setImage(String string) {
		// TODO Auto-generated method stub
		this.image = string;
	}

	public String getImage() {
		// TODO Auto-generated method stub
		return image;
	}
	
	public String getContent(){
		// TODO Auto-generated method stub
		return content;
	}
}
