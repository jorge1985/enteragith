package com.youandbbva.enteratv.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.json.JSONObject;

/**
 * bbva_visitor Table.
 * 		type=0 => Login (user_id='outside_user' => Outside user) 				
 * 		type=1 => Channel Visits ( access_id means channel_id )
 * 		type=2 => Content Visits ( access_id means content_id )
 * 
 * @author CJH
 *
 */

@Entity
@Table(name="bbva_visitor")
public class VisitorInfo {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="id")
	private Long id;

	@Column(name="type")
	private String type;

	@Column(name="user_id")
	private String userID;

	@Column(name="session_id")
	private String sessionID;

	@Column(name="date")
	private String date;

	@Column(name="time")
	private String time;
	
	@Column(name="ip_addr")
	private String ipAddr;

	@Column(name="access_id")
	private Long accessID;
	
	@Column(name="access_comment")
	private String accessComment;
	
	
	private String access_name;

	
	public String getAccess_name() {
		return access_name;
	}

	public void setAccess_name(String access_name) {
		this.access_name = access_name;
	}

	public VisitorInfo() {
		// TODO Auto-generated constructor stub
		id = (long)0;
	}

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}

	public String getUserID() {
		return userID;
	}
	public void setUserID(String userID) {
		this.userID = userID;
	}

	public String getSessionID() {
		return sessionID;
	}
	public void setSessionID(String sessionID) {
		this.sessionID = sessionID;
	}

	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}

	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}

	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}

	public String getIpAddr() {
		return ipAddr;
	}

	public void setIpAddr(String ipAddr) {
		this.ipAddr = ipAddr;
	}

	public Long getAccessID() {
		return accessID;
	}

	public void setAccessID(Long accessID) {
		this.accessID = accessID;
	}

	public String getAccessComment() {
		return accessComment;
	}

	public void setAccessComment(String accessComment) {
		this.accessComment = accessComment;
	}

	
	public JSONObject toJSONObject(){
		JSONObject result = new JSONObject();
		try{
			result.put("id", id);
			result.put("type", type);
			result.put("user_id", userID);
			result.put("session_id", sessionID);
			result.put("date", date);
			result.put("time", time);
			result.put("ip_addr", ipAddr);
			result.put("access_id", accessID);
			result.put("access_comment", accessComment);
			result.put("access_name", access_name);
			
		}catch (Exception e){}
		return result;
	}
	
}
