package com.youandbbva.enteratv.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.json.JSONObject;

/**
 * bbva_content_question_answer Table.
 * 
 * @author CJH
 *
 */

@Entity
@Table(name="bbva_content_question_answer")
public class ContentAnswerInfo {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="id")
	private Long id;

	@Column(name="content_id")
	private Long contentID;

	@Column(name="question_id")
	private Long questionID;

	@Column(name="answer_id")
	private Long answerID;

	@Column(name="user_id")
	private String userID;
	
	@Column(name="answer")
	private String answer;

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
	public Long getQuestionID() {
		return questionID;
	}
	public void setQuestionID(Long questionID) {
		this.questionID = questionID;
	}
	public String getAnswer() {
		return answer;
	}
	public void setAnswer(String answer) {
		this.answer = answer;
	}
	public String getUserID() {
		return userID;
	}
	public void setUserID(String userID) {
		this.userID = userID;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Long getAnswerID() {
		return answerID;
	}
	public void setAnswerID(Long answerID) {
		this.answerID = answerID;
	}
	
	public JSONObject toJSONObject(){
		JSONObject result = new JSONObject();
		try{
			result.put("id", id);
			result.put("content_id", contentID);
			result.put("question_id", questionID);
			result.put("answer_id", answerID);
			result.put("user_id", userID);
			result.put("answer", answer);
			result.put("status", status);
		}catch (Exception e){}
		return result;
	}
	
}
