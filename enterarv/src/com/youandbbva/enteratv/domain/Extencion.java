package com.youandbbva.enteratv.domain;


/**
 * Fileextencion Table.
 * 
 * @author JRM
 *
 */

public class Extencion {

	private int fileExtencionId;
	private int fileTypeId;
	private String fileExtencionName;
	private boolean fileExtencionActive;
	
	public int getFileExtencionId() {
		return fileExtencionId;
	}
	public void setFileExtencionId(int fileExtencionId) {
		this.fileExtencionId = fileExtencionId;
	}
	public int getFileTypeId() {
		return fileTypeId;
	}
	public void setFileTypeId(int fileTypeId) {
		this.fileTypeId = fileTypeId;
	}
	public String getFileExtencionName() {
		return fileExtencionName;
	}
	public void setFileExtencionName(String fileExtencionName) {
		this.fileExtencionName = fileExtencionName;
	}
	public boolean getFileExtencionActive() {
		return fileExtencionActive;
	}
	public void setFileExtencionActive(boolean fileExtencionActive) {
		this.fileExtencionActive = fileExtencionActive;
	}

}
