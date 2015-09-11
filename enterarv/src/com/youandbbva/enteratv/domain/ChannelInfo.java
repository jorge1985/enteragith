package com.youandbbva.enteratv.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.json.JSONObject;

/**
 * bbva_channel Table.
 * 
 * @author CJH
 *
 */

@Entity
@Table(name="bbva_channel")
public class ChannelInfo {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="id")
	private Long id;
	
	@Column(name="family_id")
	private Long familyID;

	@Column(name="parent_id")
	private Long parentID;

	@Column(name="name")
	private String name;

	@Column(name="email")
	private String email;
	
	@Column(name="password")
	private String password;

	@Column(name="access_level")
	private int accessLevel;
	
	@Column(name="security_level")
	private String securityLevel;

	@Column(name="people_manager")
	private String peopleManager;
	
	@Column(name="newhire")
	private String newHire;
	
	@Column(name="pos")
	private Long pos;
	
	private String fullName;
	private String securityLevelName;
	
	private int no;
	
	public ChannelInfo() {
		// TODO Auto-generated constructor stub
		id = (long)0;
	}
	
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

	public Long getFamilyID() {
		return familyID;
	}

	public void setFamilyID(Long familyID) {
		this.familyID = familyID;
	}

	public Long getParentID() {
		return parentID;
	}

	public void setParentID(Long parentID) {
		this.parentID = parentID;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getAccessLevel() {
		return accessLevel;
	}

	public void setAccessLevel(int i) {
		this.accessLevel = i;
	}

	public String getSecurityLevel() {
		return securityLevel;
	}

	public void setSecurityLevel(String securityLevel) {
		this.securityLevel = securityLevel;
	}

	public String getPeopleManager() {
		return peopleManager;
	}

	public void setPeopleManager(String peopleManager) {
		this.peopleManager = peopleManager;
	}

	public String getNewHire() {
		return newHire;
	}

	public void setNewHire(String newHire) {
		this.newHire = newHire;
	}

	public Long getPos() {
		return pos;
	}

	public void setPos(Long pos) {
		this.pos = pos;
	}
	
	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}	
	public String getSecurityLevelName() {
		return securityLevelName;
	}

	public void setSecurityLevelName(String securityLevelName) {
		this.securityLevelName = securityLevelName;
	}
	
	public int getNo() {
		return no;
	}

	public void setNo(int no) {
		this.no = no;
	}

	public JSONObject toJSONObject(){
		JSONObject result = new JSONObject();
		try{
			result.put("id", id);
			result.put("parentID", parentID);
			result.put("familyID", familyID);
			result.put("name", name);
			result.put("pos", pos);
		}catch (Exception e){}
		return result;
	}
	
	public JSONObject toJSON(){
		JSONObject result = new JSONObject();
		try{
			result.put("id", id);
			result.put("parentID", parentID);
			result.put("familyID", familyID);
			result.put("name", name);
			result.put("email", email);
			result.put("password", password);
			result.put("password", password);
			result.put("access_level", accessLevel);
			result.put("security_level", securityLevel);
			result.put("people_manager", peopleManager);
			result.put("newhire", newHire);
			result.put("pos", pos);
			result.put("full_name", fullName);
			result.put("security_name", securityLevelName);
		}catch (Exception e){}
		return result;
	}
}
