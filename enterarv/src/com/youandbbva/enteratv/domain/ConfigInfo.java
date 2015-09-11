package com.youandbbva.enteratv.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.json.JSONObject;

/**
 * bbva_config Table.
 * 
 * @author CJH
 *
 */

@Entity
@Table(name="bbva_config")
public class ConfigInfo {
	
	@Column(name="key")
	private String key ;

	@Column(name="value")
	private String value ;

	
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}

	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}

	public JSONObject toJSONObject(){
		JSONObject result = new JSONObject();
		try{
			result.put("key", key);
			result.put("value", value);
		}catch (Exception e){}
		return result;
	}
	
}
