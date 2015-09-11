package com.youandbbva.enteratv.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.json.JSONObject;

/**
 * bbva_state_city Table.
 * 
 * @author CJH
 *
 */

@Entity
@Table(name="bbva_state_city")
public class OpportCityInfo {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="id")
	private Long id;

	@Column(name="state_id")
	private Long stateID;

	@Column(name="name")
	private String name;

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
	public Long getStateID() {
		return stateID;
	}
	public void setStateID(Long stateID) {
		this.stateID = stateID;
	}
	
	public JSONObject toJSONObject(){
		JSONObject result = new JSONObject();
		try{
			result.put("id", id);
			result.put("state_id", stateID);
			result.put("name", name);
		}catch (Exception e){}
		return result;
	}
	
}
