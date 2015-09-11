package com.youandbbva.enteratv.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.json.JSONObject;

/**
 * bbva_folder Table.
 * 
 * @author CJH
 *
 */

@Entity
@Table(name="bbva_folder")
public class FolderInfo {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="id")
	private Long id;

	@Column(name="type")
	private String type;

	@Column(name="parent_id")
	private Long parentID;

	@Column(name="name")
	private String name;

	@Column(name="create_time")
	private String createTime;

	@Column(name="modify_time")
	private String modifyTime;

	@Column(name="status")
	private String status;

	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Long getParentID() {
		return parentID;
	}
	public void setParentID(Long parentID) {
		this.parentID = parentID;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getModifyTime() {
		return modifyTime;
	}
	public void setModifyTime(String modifyTime) {
		this.modifyTime = modifyTime;
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
			result.put("parent_id", parentID);
			result.put("type", type);
			result.put("name", name);
			result.put("create_time", createTime);
			result.put("modify_time", modifyTime);
			result.put("status", status);
		}catch (Exception e){}
		return result;
	}
	
}
