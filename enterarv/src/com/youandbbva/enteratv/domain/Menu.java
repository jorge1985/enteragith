package com.youandbbva.enteratv.domain;

import java.util.ArrayList;

import org.json.JSONArray;

public class Menu {

	
	JSONArray datos = new JSONArray();
	ArrayList<?> list = new ArrayList(); 
	
	public ArrayList<?> getList() {
		return list;
	}

	public void setList(ArrayList<?> list) {
		this.list = list;
	}

	public JSONArray getDatos() {
		return datos;
	}

	public void setDatos(JSONArray datos) {
		this.datos = datos;
	}
	
	
}
