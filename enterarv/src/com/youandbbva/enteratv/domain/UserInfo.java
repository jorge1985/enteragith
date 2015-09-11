package com.youandbbva.enteratv.domain;

import java.io.Serializable;

import org.json.JSONObject;

/**
 * user Table.
 * 
 * @author CJH
 *
 */

public class UserInfo implements Serializable {

	private static final long serialVersionUID = 1L;

	private int userId;
	private String userRol;
	private String userName;
	private String userFirstName;
	private String userLastName;
	private String userEmployeeNumber;
	private int userAccessLevel;
	private int userToken;
	private String userKeyJob;
	private int userKeyDeparment;
	private String userDateOfBirth;
	private String userGender;
	private String userLocation;
	private String userAppoiment;
	private String userEmail;
	private String userAdmisionDate;
	private String userEntered;
	private String userHorary;
	private int userHierech;
	private int userStatus;
	private int mainDirection;
	private String userMuser;
	private int city;
	private int company;

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getUserRol() {
		return userRol;
	}

	public void setUserRol(String userRol) {
		this.userRol = userRol;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserFirstName() {
		return userFirstName;
	}

	public void setUserFirstName(String userFirstName) {
		this.userFirstName = userFirstName;
	}

	public String getUserLastName() {
		return userLastName;
	}

	public void setUserLastName(String userLastName) {
		this.userLastName = userLastName;
	}

	public String getUserEmployeeNumber() {
		return userEmployeeNumber;
	}

	public void setUserEmployeeNumber(String userEmployeeNumber) {
		this.userEmployeeNumber = userEmployeeNumber;
	}

	public int getUserAccessLevel() {
		return userAccessLevel;
	}

	public void setUserAccessLevel(int userAccessLevel) {
		this.userAccessLevel = userAccessLevel;
	}

	public int getUserToken() {
		return userToken;
	}

	public void setUserToken(int userToken) {
		this.userToken = userToken;
	}

	public String getUserKeyJob() {
		return userKeyJob;
	}

	public void setUserKeyJob(String userKeyJob) {
		this.userKeyJob = userKeyJob;
	}

	public int getUserKeyDeparment() {
		return userKeyDeparment;
	}

	public void setUserKeyDeparment(int userKeyDeparment) {
		this.userKeyDeparment = userKeyDeparment;
	}

	public String getUserDateOfBirth() {
		return userDateOfBirth;
	}

	public void setUserDateOfBirth(String userDateOfBirth) {
		this.userDateOfBirth = userDateOfBirth;
	}

	public String getUserGender() {
		return userGender;
	}

	public void setUserGender(String userGender) {
		this.userGender = userGender;
	}

	public String getUserLocation() {
		return userLocation;
	}

	public void setUserLocation(String userLocation) {
		this.userLocation = userLocation;
	}

	public String getUserAppoiment() {
		return userAppoiment;
	}

	public void setUserAppoiment(String userAppoiment) {
		this.userAppoiment = userAppoiment;
	}

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	public String getUserAdmisionDate() {
		return userAdmisionDate;
	}

	public void setUserAdmisionDate(String userAdmisionDate) {
		this.userAdmisionDate = userAdmisionDate;
	}

	public String getUserEntered() {
		return userEntered;
	}

	public void setUserEntered(String userEntered) {
		this.userEntered = userEntered;
	}

	public String getUserHorary() {
		return userHorary;
	}

	public void setUserHorary(String userHorary) {
		this.userHorary = userHorary;
	}

	public int getUserHierech() {
		return userHierech;
	}

	public void setUserHierech(int userHierech) {
		this.userHierech = userHierech;
	}

	public int getUserStatus() {
		return userStatus;
	}

	public void setUserStatus(int userStatus) {
		this.userStatus = userStatus;
	}

	public int getMainDirection() {
		return mainDirection;
	}

	public void setMainDirection(int mainDirection) {
		this.mainDirection = mainDirection;
	}

	public String getUserMuser() {
		return userMuser;
	}

	public void setUserMuser(String userMuser) {
		this.userMuser = userMuser;
	}

	public int getCity() {
		return city;
	}

	public void setCity(int city) {
		this.city = city;
	}

	public int getCompany() {
		return company;
	}

	public void setCompany(int company) {
		this.company = company;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public JSONObject toJSONObject() {
		JSONObject result = new JSONObject();
		try {
			result.put("id", userId);
			result.put("name", userName);
			result.put("firstname", userFirstName);
			result.put("lastname", userLastName);
			result.put("employeeNumber", userEmployeeNumber);
			result.put("email", userEmail);
			result.put("level", userRol);
			result.put("active", userStatus);
			result.put("security_level", userAccessLevel);
			result.put("empresa", company);
			result.put("ciudad", city);
			result.put("direccion", mainDirection);
			result.put("gender", userGender);
			result.put("employment_code", userKeyJob);
			result.put("token", userToken);
			result.put("jobarea_code", userKeyDeparment);
			result.put("birthday", userDateOfBirth);
			result.put("location", userLocation);
			result.put("nombramiento", userAppoiment);
			result.put("admission", userAdmisionDate);
			result.put("entered", userEntered);
			result.put("horario", userHorary);
			result.put("arbol", userHierech);
			result.put("musuario", userMuser);

		} catch (Exception e) {
		}
		return result;
	}

}
