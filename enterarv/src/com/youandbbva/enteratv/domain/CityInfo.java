package com.youandbbva.enteratv.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.json.JSONObject;

/**
 * bbva_city Table.
 * 
 * @author CJH
 *
 */

@Entity
@Table(name="bbva_city")
public class CityInfo {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="id")
	private Long id;

	@Column(name="parent_id")
	private Long parentID;

	@Column(name="dorder")
	private Long order;

	@Column(name="name")
	private String name;
	
	@Column(name="name_en")
	private String nameEnglish;

	@Column(name="name_me")
	private String nameMexico;

	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getParentID() {
		return parentID;
	}

	public void setParentID(Long parentID) {
		this.parentID = parentID;
	}

	public Long getOrder() {
		return order;
	}

	public void setOrder(Long order) {
		this.order = order;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNameEnglish() {
		return nameEnglish;
	}

	public void setNameEnglish(String nameEnghlish) {
		this.nameEnglish = nameEnghlish;
	}

	public String getNameMexico() {
		return nameMexico;
	}

	public void setNameMexico(String nameMexico) {
		this.nameMexico = nameMexico;
	}
	
	public JSONObject toJSONObject(){
		JSONObject result = new JSONObject();
		try{
			result.put("id", id);
			result.put("parent_id", parentID);
			result.put("name", name);
		}catch (Exception e){}
		return result;
	}
	
}
