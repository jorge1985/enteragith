package com.youandbbva.enteratv.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.json.JSONObject;

/**
 * bbva_validation Table.
 * 
 * @author CJH
 *
 */

@Entity
@Table(name="bbva_validation")
public class ValidationInfo {
	
	@Id
	@Column(name="id")
	private Long id;

	@Column(name="user_id")
	private String userID;

	@Column(name="session_id")
	private String sessionID;
	
	@Column(name="created_at")
	private String createdAt;


	public ValidationInfo() {
		// TODO Auto-generated constructor stub
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}


	public JSONObject toJSONObject(){
		JSONObject result = new JSONObject();
		try{
			result.put("id", id);
			result.put("session_id", sessionID);
			result.put("created_at", createdAt);
			result.put("user_id", userID);
		}catch (Exception e){}
		return result;
	}
}
