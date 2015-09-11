package com.youandbbva.enteratv;

/**
 * Define all constant.
 *  
 * @author CJH
 *
 * PHP Demo
 * 		http://50.23.231.18/tmpinstall/secure/
 * 		login: 4294967295
 * 		password: tmpaccess000

Storage Access
Google Cloud Storage provides a REST API, command line tool, and online browser to create, share, and manage your data.
Browse data
You can view your project's data in a web browser through the Google Cloud Storage Manager.
Identifying your project
When creating new buckets, or listing the buckets owned by a project, you must specify the x-goog-project-id HTTP header in order to identify which project you are using.
x-goog-project-id: 576973778880
Google Cloud Storage IDs
By default, members of your project can access Google Cloud Storage data as defined by their project roles. Each role is identified by the following group IDs that you can use to modify permissions for your objects. Learn more
You:	00b4903a9743ecda8efc83c5fa4a30d4d8c31b18dbe9e00c461f5439c04b9496
Owners:	00b4903a979e9f393df26c25e6fbb9775812ea7b36e9abd64925394ac10d91cf
Editors:	00b4903a978cc6b8004e7761c3e3412bc0c527ecd6a2754ef6badbe27cf329a4
Team:	00b4903a976041b86982dcffbc8de346ef53af8e02aa50993bb12a53f0dd599e

Label	youandbbva
Project ID	576973778880
Project Name	
youandbbva
Google+ Page	
Request connection
Owners	kxtrm2@gmail.com, jinhechen124@gmail.com - you
Current charges	None


youandbbva@appspot.gserviceaccount.com
App Engine service account 
 
576973778880-compute@developer.gserviceaccount.com
Google APIs service account 
 
576973778880@cloudservices.gserviceaccount.com
Google APIs service account 
 
576973778880@repo.gserviceaccount.com
Google APIs service account 

 *
 */

public class Constants {
	
	private static Registry registry = Registry.getInstance();
	
	/**
	 * version code.
	 */
	public static final float VERSION = registry.getFloatOfApplication("version");
	public static final String SERVICE_ACCOUNT = "youandbbva@appspot.gserviceaccount.com";
	public static final String BASEPATH = registry.getStringOfApplication("application.path");
		
	public static final String CSV_SEPARATOR = ",";
	public static final String CSV_QUOTE= "\"";
	
	/**
	 * password hash.
	 */
	public static final String PASSWORD_HASH = registry.getStringOfApplication("password.hash");
	public static final String SESSION_HASH = "enteratv_opportunities";
	
	/**
	 * Configuration Code
	 */
	public static final String DEFAULT_ACTIVE = "1";
	public static final String CODE_USER_ADMINISTRATOR = "1";
	public static final String DEFAULT_YES = "1";
	
	public static final String OUTSIDE_USER_ID = "outside_user";
	public static final String OUTSIDE_USER_NAME = "Outside User";
	
	public static final long EXPIRED_PASSWORD_TIME = 300;		// 5 min
	public static final int EXPIRED_COOKIE_TIME = 7 * 24 * 60 * 60;
	
	/**
	 * File Upload Format
	 */
	public static final String[] FILE_EXTENTION = {
		".zip", ".tgz", ".far", 
		".jpg", ".jpeg", ".png", ".gif", 
		".pdf", 
		".docx", ".doc", ".xls", ".xlsx", ".ppt", ".pptx", 
		".rtf", ".txt",
		".mp4", ".avi", ".m4v", ".flv", ".f4v", ".mov"
	};
	
	public static final String[] CONTENT_TYPE = {
		"application/zip",	"application/octet-stream", 
		"image/jpeg", "image/gif", "image/png", 
		"application/pdf",
		"application/vnd.openxmlformats-officedocument.wordprocessingml.document", "application/msword",
		"application/vnd.openxmlformats-officedocument.spreadsheetml.sheet", "application/vnd.ms-excel",
		"application/vnd.openxmlformats-officedocument.presentationml.presentation", "application/vnd.ms-powerpoint",
		"application/rtf", "text/plain",
		"video/x-msvideo", "video/x-flv", "video/x-m4v", "video/x-f4v", "video/mp4", "application/mp4", "video/quicktime", 
		"application/x-vlc-plugin"
	};
	
	
	/**
	 * Thumbnail Size
	 */
	public static final int THUMBNAIL_WIDTH = registry.getIntOfApplication("thumbnail.width");
	public static final int THUMBNAIL_HEIGHT = registry.getIntOfApplication("thumbnail.height");
	
	/**
	 * Widgets
	 */
	public static final int BANNER_COUNT = 8;
	public static final int FEATURES_COUNT = 5;
	
	/**
	 * Message.
	 */
	public static final String ERROR_CODE = "errCode";
	public static final String ERROR_MSG = "errMsg";
	public static final int ACTION_SUCCESS = 0;
	public static final int ACTION_FAILED = 1;

	/**
	 * System Options
	 */
	public static final String OPTION_ONLINE = "online";
	public static final String OPTION_ALLOW_IP = "ip";
	public static final String OPTION_ALLOW_HOST = "host";
	
	public static final String OPTION_BANNER = "banner";
	public static final String OPTION_BANNER_HTML = "banner_html";

	public static final String OPTION_BANNER_HOME_SIDEBAR_HTML = "banner_home_sidebar";
	public static final String OPTION_BANNER_HOME_BOTTOM_HTML = "banner_home_bottom";

	public static final String OPTION_BANNER_INTERNAL_SIDEBAR_HTML = "banner_internal_sidebar";
	public static final String OPTION_BANNER_INTERNAL_BOTTOM_HTML = "banner_internal_bottom";

	public static final String OPTION_FEATURES = "features";
	public static final String OPTION_FEATURES_HTML = "features_html";
	
	public static final String OPTION_SLIDER_NAV_HTML = "slider_nav_html";
	public static final String OPTION_SLIDER_FOR_HTML = "slider_for_html";
	
	/**
	 * Language
	 * 
	 * @author CJH
	 *
	 */
	public enum Language {
		SPANISH("me"),
		ENGLISH("en"),
		RUSSIAN("ru"),
		CHINESE("ch"),
		JAPANESE("jp"),
		KOREAN("ko"),
		ARABIAN("ar"),
		PORTUGUAL("pt"),
		DEFAULT("");
		
		private String code;
		
		private Language(String lang){
			code = lang;
		}
		
		public String getCode(){
			return code;
		}
	}
	
	/**
	 * Log Type.
	 * 
	 * @author CJH
	 *
	 */
	public enum LogType {
		LOGIN("1"),
		ACCESS("2"),
		CHANNEL("3"),
		CONTENT("4"),
		DEFAULT("9");
		
		private String code;
		
		private LogType(String lang){
			code = lang;
		}
		
		public String getCode(){
			return code;
		}
	}
	
	/**
	 * Content Type.
	 * 
	 * @author CJH
	 *
	 */
	public enum ContentType {
		VIDEO("001"),
		NEWS("002"),
		POLL("003"),
		FAQS("004"),
		GALLERY("005"),
		FILES("006"),
		DEFAULT("000");
		
		private String code;
		
		private ContentType(String c){
			code = c;
		}
		
		public String getCode(){
			return code;
		}
	}
	
	
}
