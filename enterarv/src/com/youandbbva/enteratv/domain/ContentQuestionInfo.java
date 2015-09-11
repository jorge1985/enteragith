package com.youandbbva.enteratv.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.json.JSONObject;

/**
 * bbva_content_question Table.
 * 
 * @author CJH
 *
 */

@Entity
@Table(name="bbva_content_question")
public class ContentQuestionInfo {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="id")
	private Long id;

	@Column(name="content_id")
	private Long contentID;

	@Column(name="question")
	private String question;

	@Column(name="status")
	private String status;
	
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
	public String getQuestion() {
		return question;
	}
	public void setQuestion(String question) {
		this.question = question;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	public JSONObject toJSONObject(){
		JSONObject result = new JSONObject();
		try{
			result.put("id", id);
			result.put("content_id", contentID);
			result.put("question", question);
			result.put("status", status);
		}catch (Exception e){}
		return result;
	}
	
}
