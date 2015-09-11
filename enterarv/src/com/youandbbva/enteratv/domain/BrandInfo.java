package com.youandbbva.enteratv.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.json.JSONObject;

/**
 * bbva_brand Table.
 * 
 * @author CJH
 *
 */

@Entity
@Table(name="bbva_brand")
public class BrandInfo {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="id")
	private Long id;

	@Column(name="name")
	private String name;

	@Column(name="status")
	private String status;
	
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
			result.put("name", name);
			result.put("status", status);
		}catch (Exception e){}
		return result;
	}
	
}
