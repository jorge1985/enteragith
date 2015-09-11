package com.youandbbva.enteratv.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * bbva_opportunities Table.
 * 
 * @author CJH
 *
 */

@Entity
@Table(name="bbva_opportunities")
public class OpportunitiesInfo {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="id")
	private Long id;

	@Column(name="type")
	private String type;

	@Column(name="brand_id")
	private Long brandID;

	@Column(name="state_id")
	private Long stateID;
	
	@Column(name="city_id")
	private Long cityID;

	@Column(name="model")
	private String model;
	
	@Column(name="price")
	private String price;

	@Column(name="today")
	private String today;

	@Column(name="mileage")
	private String mileage;

	@Column(name="transmission")
	private String transmission;
	
	@Column(name="employee_num")
	private String employeeNum;
	
	@Column(name="public_date")
	private String publicDate;
	
	@Column(name="obs")
	private String obs;
	
	@Column(name="color")
	private String color;
	
	@Column(name="doors")
	private String doors;
	
	@Column(name="telephone")
	private String telephone;
	
	@Column(name="mobilephone")
	private String mobilephone;
	
	@Column(name="file")
	private String file;
	
	@Column(name="created_at")
	private String createdAt;
	
	@Column(name="updated_at")
	private String updatedAt;
	
	@Column(name="user_id")
	private String userID;
	
	@Column(name="status")
	private String status;

	@Column(name="serve_type")
	private String serveType;
	
	@Column(name="property")
	private String property;

	@Column(name="plants")
	private String plants;

	@Column(name="rooms")
	private String rooms;

	@Column(name="amueblado")
	private String amueblado;
	
	@Column(name="varios")
	private String varios;

	
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
	public Long getBrandID() {
		return brandID;
	}
	public void setBrandID(Long brandID) {
		this.brandID = brandID;
	}
	public Long getStateID() {
		return stateID;
	}
	public void setStateID(Long stateID) {
		this.stateID = stateID;
	}
	public Long getCityID() {
		return cityID;
	}
	public void setCityID(Long cityID) {
		this.cityID = cityID;
	}
	public String getModel() {
		return model;
	}
	public void setModel(String model) {
		this.model = model;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public String getToday() {
		return today;
	}
	public void setToday(String today) {
		this.today = today;
	}
	public String getMileage() {
		return mileage;
	}
	public void setMileage(String mileage) {
		this.mileage = mileage;
	}
	public String getTransmission() {
		return transmission;
	}
	public void setTransmission(String transmission) {
		this.transmission = transmission;
	}
	public String getEmployeeNum() {
		return employeeNum;
	}
	public void setEmployeeNum(String employeeNum) {
		this.employeeNum = employeeNum;
	}
	public String getPublicDate() {
		return publicDate;
	}
	public void setPublicDate(String publicDate) {
		this.publicDate = publicDate;
	}
	public String getObs() {
		return obs;
	}
	public void setObs(String obs) {
		this.obs = obs;
	}
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	public String getDoors() {
		return doors;
	}
	public void setDoors(String doors) {
		this.doors = doors;
	}
	public String getTelephone() {
		return telephone;
	}
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	public String getMobilephone() {
		return mobilephone;
	}
	public void setMobilephone(String mobilephone) {
		this.mobilephone = mobilephone;
	}
	public String getFile() {
		return file;
	}
	public void setFile(String file) {
		this.file = file;
	}
	public String getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}
	public String getUpdatedAt() {
		return updatedAt;
	}
	public void setUpdatedAt(String updatedAt) {
		this.updatedAt = updatedAt;
	}
	public String getUserID() {
		return userID;
	}
	public void setUserID(String userID) {
		this.userID = userID;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getServeType() {
		return serveType;
	}
	public void setServeType(String serveType) {
		this.serveType = serveType;
	}
	public String getProperty() {
		return property;
	}
	public void setProperty(String property) {
		this.property = property;
	}
	public String getPlants() {
		return plants;
	}
	public void setPlants(String plants) {
		this.plants = plants;
	}
	public String getRooms() {
		return rooms;
	}
	public void setRooms(String rooms) {
		this.rooms = rooms;
	}
	public String getAmueblado() {
		return amueblado;
	}
	public void setAmueblado(String amueblado) {
		this.amueblado = amueblado;
	}
	public String getVarios() {
		return varios;
	}
	public void setVarios(String varios) {
		this.varios = varios;
	}
}
