package com.youandbbva.enteratv;

import java.io.IOException;
import java.io.InputStream;
import java.text.MessageFormat;
import java.util.Properties;

/**
 * Load all property files. 
 * 		(application.properties, message.properties) 
 *
 * @author CJH
 *  
 */

public class Registry {
	
	// Constant
	private static final String APPLICATION_PROPERTIES = "/application.properties";
	private static final String MESSAGE_PROPERTIES = "/message.properties";
	private static final String LANGUAGE_PROPERTIES = "/language.properties";
	private static final String ARRAY_SPLIT = "\\|";

	// Property
	private static Properties propertiesApplication;
	private static Properties propertiesMessage;
	private static Properties propertiesLanguage;

	// Instance
	private static Registry instance = new Registry();
	
	
	/**
	 * Constructor.
	 * 		create property object to save property files.
	 * 		load property files.
	 */
	private Registry() {
		propertiesApplication = load(APPLICATION_PROPERTIES);
		propertiesMessage = load(MESSAGE_PROPERTIES);
		propertiesLanguage = load(LANGUAGE_PROPERTIES);
	}
	
	/**
	 * Load data from property file.
	 * 
	 * @param filename Property File
	 * @return Property
	 */
	private Properties load(String filename){
		InputStream input = null;
		Properties properties = new Properties();
		try {
			 
			input = getClass().getResourceAsStream(filename);
			properties.load(input);
	 
		} catch (IOException ex) {
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
				}
			}
		}
		
		return properties;
	}

	/**
	 * Get value from Application properties.
	 * 
	 * @param name
	 * @return value
	 */
	public String getStringOfApplication(String name) {
		String n=null;
		try{
			n=new String(propertiesApplication.getProperty(name));
		}catch (Exception e){
			n="";
		}
		return n;
	}

	/**
	 * Get value from Application properties.
	 * 
	 * @param name
	 * @return value
	 */
	public int getIntOfApplication(String name) {
		try{
			return Integer.parseInt(getStringOfApplication(name));
		}catch (Exception e){}
		
		return -1;
	}
	
	/**
	 * Get value from Application properties.
	 * 
	 * @param name
	 * @return value
	 */
	public int getHexIntOfApplication(String name) {
		return Integer.parseInt(getStringOfApplication(name), 16);
	}

	/**
	 * Get value from Application properties.
	 * 
	 * @param name
	 * @return value
	 */
	public float getFloatOfApplication(String name) {
		return Float.parseFloat(getStringOfApplication(name));
	}
	
	/**
	 * Get value from Application properties.
	 * 
	 * @param name
	 * @return value
	 */
	public long getLongOfApplication(String name) {
		return Long.parseLong(getStringOfApplication(name));
	}

	/**
	 * Get value from Application properties.
	 * 
	 * @param name
	 * @return value
	 */
	public boolean isTrueOfApplication(String name) {
		return Boolean.valueOf(getStringOfApplication(name)).booleanValue();
	}

	/**
	 * Get value from Application properties.
	 * 
	 * @param name
	 * @return value
	 */
	public String[] getStringArrayOfApplication(String name) {
		return getStringOfApplication(name).split(ARRAY_SPLIT);
	}

	/**
	 * Get value from Application properties.
	 * 
	 * @param name
	 * @return value
	 */
	public int[] getIntArrayOfApplication(String name) {
		int length = 0;
		int[] target = null;
		String[] source = null;
		source = getStringOfApplication(name).split(ARRAY_SPLIT);
		length = source.length;
		target = new int[length];
		for (int i = 0; i < length; i++)
			target[i] = Integer.parseInt(source[i]);
		return target;
	}

	/**
	 * Get value from Application properties.
	 * 
	 * @param name
	 * @return value
	 */
	public boolean[] getBooleanArrayOfApplication(String name) {
		int length = 0;
		boolean[] target = null;
		String[] source = null;
		source = getStringOfApplication(name).split(ARRAY_SPLIT);
		length = source.length;
		target = new boolean[length];
		for (int i = 0; i < length; i++)
			target[i] = Boolean.valueOf(source[i]).booleanValue();
		return target;
	}

	/**
	 * Get value from Application properties.
	 * 
	 * @param name
	 * @param language
	 * @return value
	 */
	public String getStringOfApplication(String name, String language) {
		String n=null;
		try{
			n=new String(propertiesApplication.getProperty(name+"."+language));
		}catch (Exception e){
			n=getStringOfApplication(name);
		}
		return n;
	}

	/**
	 * Get value from Application properties.
	 * 
	 * @param name
	 * @param language
	 * @return value
	 */
	public int getIntOfApplication(String name, String language) {
		try{
			return Integer.parseInt(getStringOfApplication(name+"."+language));
		}catch (Exception e){
			return Integer.parseInt(getStringOfApplication(name));
		}
	}
	
	/**
	 * Get value from Application properties.
	 * 
	 * @param name
	 * @param language
	 * @return value
	 */
	public int getHexIntOfApplication(String name, String language) {
		try{
			return Integer.parseInt(getStringOfApplication(name+"."+language), 16);
		}catch (Exception e){
			return Integer.parseInt(getStringOfApplication(name), 16);
		}
	}

	/**
	 * Get value from Application properties.
	 * 
	 * @param name
	 * @param language
	 * @return value
	 */
	public float getFloatOfApplication(String name, String language) {
		try{
			return Float.parseFloat(getStringOfApplication(name+"."+language));
		}catch (Exception e){
			return Float.parseFloat(getStringOfApplication(name));
		}
	}
	
	/**
	 * Get value from Application properties.
	 * 
	 * @param name
	 * @param language
	 * @return value
	 */
	public long getLongOfApplication(String name, String language) {
		try{
			return Long.parseLong(getStringOfApplication(name+"."+language));
		}catch (Exception e){
			return Long.parseLong(getStringOfApplication(name));
		}
	}

	/**
	 * Get value from Application properties.
	 * 
	 * @param name
	 * @param language
	 * @return value
	 */
	public boolean isTrueOfApplication(String name, String language) {
		try{
			return Boolean.valueOf(getStringOfApplication(name+"."+language)).booleanValue();
		}catch (Exception e){
			return Boolean.valueOf(getStringOfApplication(name)).booleanValue();
		}
	}

	/**
	 * Get value from Application properties.
	 * 
	 * @param name
	 * @param language
	 * @return value
	 */
	public String[] getStringArrayOfApplication(String name, String language) {
		try{
			return getStringOfApplication(name).split(ARRAY_SPLIT);
		}catch (Exception e){
			return getStringOfApplication(name).split(ARRAY_SPLIT);
		}
	}

	/**
	 * Get value from Application properties.
	 * 
	 * @param name
	 * @param language
	 * @return value
	 */
	public int[] getIntArrayOfApplication(String name, String language) {
		try{
			return getIntArrayOfApplication(name+"."+language);
		}catch (Exception e){
			return getIntArrayOfApplication(name);
		}
	}

	/**
	 * Get value from Application properties.
	 * 
	 * @param name
	 * @param language
	 * @return value
	 */
	public boolean[] getBooleanArrayOfApplication(String name, String language) {
		try{
			return getBooleanArrayOfApplication(name+"."+language);
		}catch (Exception e){
			return getBooleanArrayOfApplication(name);
		}
	}

	/**
	 * Get value from Message properties.
	 * 
	 * @param name
	 * @return value
	 */
	public String getStringOfMessage(String name) {
		String n=null;
		try{
			n=new String(propertiesMessage.getProperty(name));
		}catch (Exception e){
			n="";
		}
		return n;
	}
	
	/**
	 * Get value from Message properties.
	 * 
	 * @param name
	 * @param language
	 * @return value
	 */
	public String getStringOfMessage(String name, String language) {
		String n=null;
		try{
			n=new String(propertiesMessage.getProperty(name+"."+language));
		}catch (Exception e){
			n=getStringOfMessage(name);
		}
		return n;
	}
	
	/**
	 * Get message from Message properties.
	 * 
	 * @param msgid The Type of Message
	 * @param language
	 * @param type The type of Bracket
	 * @param param1 Argument1
	 * @param param2 Argument2
	 * @param param3 Argument3
	 * @return message
	 */
	public String getMessage(String msgid, String language, int type, String param1, String param2, String param3){
		String result = "";
		String format = getStringOfMessage(msgid, language);
		if (format==null || format.length()==0)
			return result;
		
		param1 = Utils.bracket(type, param1);
		param2 = Utils.bracket(type, param2);
		param3 = Utils.bracket(type, param3);
		
		try{
			Object args[] = {"", param1, param2, param3};
			result = MessageFormat.format(format, args);
		}catch (Exception e){}
		
		return result;
	}
	
	/**
	 * Get message from Message properties.
	 * 
	 * @param msgid The Type of Message
	 * @param language
	 * @param type The type of Bracket
	 * @param param1 Argument1
	 * @param param2 Argument2
	 * @return message
	 */
	public String getMessage(String msgid, String language, int type, String param1, String param2){
		String result = "";
		String format = getStringOfMessage(msgid, language);
		if (format==null || format.length()==0)
			return result;
		
		param1 = Utils.bracket(type, param1);
		param2 = Utils.bracket(type, param2);
		
		try{
			Object args[] = {"", param1, param2};
			result = MessageFormat.format(format, args);
		}catch (Exception e){}
		
		return result;
	}
	
	/**
	 * Get message from Message properties.
	 * 
	 * @param msgid The Type of Message
	 * @param language
	 * @param type The type of Bracket
	 * @param param Argument
	 * @return message
	 */
	public String getMessage(String msgid, String language, int type, String param){
		String result = "";
		String format = getStringOfMessage(msgid, language);
		if (format==null || format.length()==0)
			return result;
		
		param = Utils.bracket(type, param);
		
		try{
			Object args[] = {"", param};
			result = MessageFormat.format(format, args);
		}catch (Exception e){}
		
		return result;
	}
	
	/**
	 * Get message from Message properties.
	 * 
	 * @param msgid The Type of Message
	 * @param language
	 * @return message
	 */
	public String getMessage(String msgid, String language){
		String result = "";
		String format = getStringOfMessage(msgid, language);
		if (format==null || format.length()==0)
			return result;
		result = format;
		return result;
	}

	/**
	 * Get message from Message properties.
	 * 
	 * @param msgid The Type of Message
	 * @param type The type of Bracket
	 * @param param1 Argument1
	 * @param param2 Argument2
	 * @param param3 Argument3
	 * @return message
	 */
	public String getMessage(String msgid, int type, String param1, String param2, String param3){
		String result = "";
		String format = getStringOfMessage(msgid);
		if (format==null || format.length()==0)
			return result;
		
		param1 = Utils.bracket(type, param1);
		param2 = Utils.bracket(type, param2);
		param3 = Utils.bracket(type, param3);
		
		try{
			Object args[] = {"", param1, param2, param3};
			result = MessageFormat.format(format, args);
		}catch (Exception e){}
		
		return result;
	}
	
	/**
	 * Get message from Message properties.
	 * 
	 * @param msgid The Type of Message
	 * @param type The type of Bracket
	 * @param param1 Argument1
	 * @param param2 Argument2
	 * @return message
	 */
	public String getMessage(String msgid, int type, String param1, String param2){
		String result = "";
		String format = getStringOfMessage(msgid);
		if (format==null || format.length()==0)
			return result;
		
		param1 = Utils.bracket(type, param1);
		param2 = Utils.bracket(type, param2);
		
		try{
			Object args[] = {"", param1, param2};
			result = MessageFormat.format(format, args);
		}catch (Exception e){}
		
		return result;
	}
	
	/**
	 * Get message from Message properties.
	 * 
	 * @param msgid The Type of Message
	 * @param type The type of Bracket
	 * @param param Argument
	 * @return message
	 */
	public String getMessage(String msgid, int type, String param){
		String result = "";
		String format = getStringOfMessage(msgid);
		if (format==null || format.length()==0)
			return result;
		
		param = Utils.bracket(type, param);
		
		try{
			Object args[] = {"", param};
			result = MessageFormat.format(format, args);
		}catch (Exception e){}
		
		return result;
	}
	
	/**
	 * Get message from Message properties.
	 * 
	 * @param msgid The Type of Message
	 * @return message
	 */
	public String getMessage(String msgid){
		String result = "";
		String format = getStringOfMessage(msgid);
		if (format==null || format.length()==0)
			return result;
		result = format;
		return result;
	}

	/**
	 * Get value from Language properties.
	 * 
	 * @param name
	 * @return value
	 */
	public String getStringOfLanguage(String name) {
		String n=null;
		try{
			n=new String(propertiesLanguage.getProperty(name));
		}catch (Exception e){
			n="";
		}
		return n;
	}

	/**
	 * Get value from Language properties.
	 * 
	 * @param name
	 * @return value
	 */
	public int getIntOfLanguage(String name) {
		try{
			return Integer.parseInt(getStringOfLanguage(name));
		}catch (Exception e){}
		
		return -1;
	}
	
	/**
	 * Get value from Language properties.
	 * 
	 * @param name
	 * @return value
	 */
	public int getHexIntOfLanguage(String name) {
		return Integer.parseInt(getStringOfLanguage(name), 16);
	}

	/**
	 * Get value from Language properties.
	 * 
	 * @param name
	 * @return value
	 */
	public float getFloatOfLanguage(String name) {
		return Float.parseFloat(getStringOfLanguage(name));
	}
	
	/**
	 * Get value from Language properties.
	 * 
	 * @param name
	 * @return value
	 */
	public long getLongOfLanguage(String name) {
		return Long.parseLong(getStringOfLanguage(name));
	}

	/**
	 * Get value from Language properties.
	 * 
	 * @param name
	 * @return value
	 */
	public boolean isTrueOfLanguage(String name) {
		return Boolean.valueOf(getStringOfLanguage(name)).booleanValue();
	}

	/**
	 * Get value from Language properties.
	 * 
	 * @param name
	 * @return value
	 */
	public String[] getStringArrayOfLanguage(String name) {
		return getStringOfLanguage(name).split(ARRAY_SPLIT);
	}

	/**
	 * Get value from Language properties.
	 * 
	 * @param name
	 * @return value
	 */
	public int[] getIntArrayOfLanguage(String name) {
		int length = 0;
		int[] target = null;
		String[] source = null;
		source = getStringOfLanguage(name).split(ARRAY_SPLIT);
		length = source.length;
		target = new int[length];
		for (int i = 0; i < length; i++)
			target[i] = Integer.parseInt(source[i]);
		return target;
	}

	/**
	 * Get value from Language properties.
	 * 
	 * @param name
	 * @return value
	 */
	public boolean[] getBooleanArrayOfLanguage(String name) {
		int length = 0;
		boolean[] target = null;
		String[] source = null;
		source = getStringOfLanguage(name).split(ARRAY_SPLIT);
		length = source.length;
		target = new boolean[length];
		for (int i = 0; i < length; i++)
			target[i] = Boolean.valueOf(source[i]).booleanValue();
		return target;
	}

	/**
	 * Get value from Language properties.
	 * 
	 * @param name
	 * @param language
	 * @return value
	 */
	public String getStringOfLanguage(String name, String language) {
		String n=null;
		try{
			n=new String(propertiesLanguage.getProperty(name+"."+language));
		}catch (Exception e){
			n=getStringOfLanguage(name);
		}
		return n;
	}

	/**
	 * Get value from Language properties.
	 * 
	 * @param name
	 * @param language
	 * @return value
	 */
	public int getIntOfLanguage(String name, String language) {
		try{
			return Integer.parseInt(getStringOfLanguage(name+"."+language));
		}catch (Exception e){
			return Integer.parseInt(getStringOfLanguage(name));
		}
	}
	
	/**
	 * Get value from Language properties.
	 * 
	 * @param name
	 * @param language
	 * @return value
	 */
	public int getHexIntOfLanguage(String name, String language) {
		try{
			return Integer.parseInt(getStringOfLanguage(name+"."+language), 16);
		}catch (Exception e){
			return Integer.parseInt(getStringOfLanguage(name), 16);
		}
	}

	/**
	 * Get value from Language properties.
	 * 
	 * @param name
	 * @param language
	 * @return value
	 */
	public float getFloatOfLanguage(String name, String language) {
		try{
			return Float.parseFloat(getStringOfLanguage(name+"."+language));
		}catch (Exception e){
			return Float.parseFloat(getStringOfLanguage(name));
		}
	}
	
	/**
	 * Get value from Language properties.
	 * 
	 * @param name
	 * @param language
	 * @return value
	 */
	public long getLongOfLanguage(String name, String language) {
		try{
			return Long.parseLong(getStringOfLanguage(name+"."+language));
		}catch (Exception e){
			return Long.parseLong(getStringOfLanguage(name));
		}
	}

	/**
	 * Get value from Language properties.
	 * 
	 * @param name
	 * @param language
	 * @return value
	 */
	public boolean isTrueOfLanguage(String name, String language) {
		try{
			return Boolean.valueOf(getStringOfLanguage(name+"."+language)).booleanValue();
		}catch (Exception e){
			return Boolean.valueOf(getStringOfLanguage(name)).booleanValue();
		}
	}

	/**
	 * Get value from Language properties.
	 * 
	 * @param name
	 * @param language
	 * @return value
	 */
	public String[] getStringArrayOfLanguage(String name, String language) {
		try{
			return getStringOfLanguage(name).split(ARRAY_SPLIT);
		}catch (Exception e){
			return getStringOfLanguage(name).split(ARRAY_SPLIT);
		}
	}

	/**
	 * Get value from Language properties.
	 * 
	 * @param name
	 * @param language
	 * @return value
	 */
	public int[] getIntArrayOfLanguage(String name, String language) {
		try{
			return getIntArrayOfLanguage(name+"."+language);
		}catch (Exception e){
			return getIntArrayOfLanguage(name);
		}
	}

	/**
	 * Get value from Language properties.
	 * 
	 * @param name
	 * @param language
	 * @return value
	 */
	public boolean[] getBooleanArrayOfLanguage(String name, String language) {
		try{
			return getBooleanArrayOfLanguage(name+"."+language);
		}catch (Exception e){
			return getBooleanArrayOfLanguage(name);
		}
	}

	/**
	 * Return Registry Instance.
	 * 
	 * @return instance
	 */
	public static Registry getInstance() {
		return instance;
	}

	/**
	 * Destroy Registry Instance.
	 */
	public static void destroy(){
		propertiesApplication.clear();
		propertiesApplication = null;

		propertiesMessage.clear();
		propertiesMessage = null;
		instance = null;
	}
	
}