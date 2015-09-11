package com.youandbbva.enteratv.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.json.JSONObject;

/**
 * bbva_code Table.
 * 		usr1/	Kind of User(administrator, user).
 * 
 * 		chn1/	Security Level(Level1 ~ Level4)
 * 		chn2/	Access Level(Public, Private)
 * 		chn3/	PROMOSTAT (STD)
 * 		chn4/	PEOPLEMANAGER(Manager only, All)
 * 		chn5/	NEWHIRE(Yes, No)
 * 
 * 		cnt1/	Show In(Featured Only, Channels Only, Both)
 * 		cnt2/	Featured(Video, News, Poll, FAQs, Image Gallery, Files Download)
 * 		
 * 		cfg1/	Online(Yes, No)
 * 
 * 		trgt/	Target(Blank, Self)
 * 
 * @author CJH
 *
 */

@Entity
@Table(name="bbva_code")
public class CodeInfo {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="id")
	private Long id;

	@Column(name="div")
	private String div;

	@Column(name="code")
	private String code;

	@Column(name="value")
	private String value;
	
	@Column(name="value_en")
	private String valueEnglish;

	@Column(name="value_me")
	private String valueMexico;

	
	public CodeInfo(){
		code = "";
		value = "";
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDiv() {
		return div;
	}

	public void setDiv(String div) {
		this.div = div;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getValueEnglish() {
		return valueEnglish;
	}

	public void setValueEnglish(String valueEnghlish) {
		this.valueEnglish = valueEnghlish;
	}

	public String getValueMexico() {
		return valueMexico;
	}

	public void setValueMexico(String valueMexico) {
		this.valueMexico = valueMexico;
	}
	
	public JSONObject toJSONObject(){
		JSONObject result = new JSONObject();
		try{
			result.put("code", code);
			result.put("value", value);
		}catch (Exception e){}
		return result;
	}
	
}
