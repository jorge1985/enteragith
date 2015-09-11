package com.youandbbva.enteratv.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.json.JSONObject;
import org.json.JSONArray;

/**
 *.
 * 
 * @author CJH
 *
 */

@Entity
@Table(name="Content")
public class ContentCountInfo {
	
	@Column(name="records")
	private Long  records;

	@Column(name="contentquery")
	private JSONArray contentquery;

		
	public Long getRecords() {
		return records;
	}
	public void setRecords(Long records) {
		this.records = records;
	}

	public JSONArray getJSONArray() {
		return contentquery;
	}
	public void setJSONArray(JSONArray contentquery) {
		this.contentquery = contentquery;
	}



}
