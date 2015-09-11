package com.youandbbva.enteratv.domain;

import org.json.JSONObject;

import com.youandbbva.enteratv.Utils;

/**
 * bbva_file Table.
 * 
 * @author CJH
 *
 */

public class FileInfo {
	
	private Long id;
	private Long folderID;
	private String type;
	private String name;
	private Long size;
	private String createTime;
	private String modifyTime;
	private String status;
	private String cloudKey;
	private String thumbKey;

	
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
	public String getName() {
		return name;
	}
	public Long getFolderID() {
		return folderID;
	}
	public void setFolderID(Long folderID) {
		this.folderID = folderID;
	}
	public Long getSize() {
		return size;
	}
	public void setSize(Long size) {
		this.size = size;
	}
	public String getCloudKey() {
		return cloudKey;
	}
	public void setCloudKey(String cloud_key) {
		this.cloudKey = cloud_key;
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
	public String getThumbKey() {
		return thumbKey;
	}
	public void setThumbKey(String thumbKey) {
		this.thumbKey = thumbKey;
	}
	
	public JSONObject toJSONObject(){
		JSONObject result = new JSONObject();
		try{
			result.put("id", id);
			result.put("folder_id", folderID);
			result.put("type", type);
			result.put("name", name);
			result.put("size",  Utils.getDouble(String.valueOf((double) size/1000.0), 2) );
			
			result.put("create_time", createTime);
			result.put("modify_time", modifyTime);
			result.put("status", status);
			result.put("cloud_key", cloudKey);
			result.put("thumb_key", thumbKey);
		}catch (Exception e){}
		return result;
	}
	
}
