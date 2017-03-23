package com.vormadal.turborocket.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

import com.vormadal.turborocket.models.Map;

public class PropertiesUtil {
	
	private static final String DEFAULT_PATH = "props.properties"; 
	private static String PROP_FILE_PATH = DEFAULT_PATH;
	private static final HashMap<String, String> cache = new HashMap<>();
	
	/**
	 * Sets the path for the file which this util use for reading and writing
	 * @param path
	 */
	public static void setPath(String path){
		PROP_FILE_PATH = path;
	}
	
	/**
	 * sets the path for the file which this util uses to "props.properties"
	 */
	public static void resetPath(){
		PROP_FILE_PATH = DEFAULT_PATH;
	}
	
	private static File getFile(String path){
		File file = new File(path);
		try {
			file.createNewFile();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return file;
	}
	public static void removeProp(String key){
		cache.remove(key);
		Properties prop = new Properties();
		OutputStream output = null;
		try {
			FileInputStream in = new FileInputStream(getFile(PROP_FILE_PATH));
			
			prop.load(in);
			in.close();
			
			output = new FileOutputStream(PROP_FILE_PATH);

			// set the properties value
			prop.remove(key);

			// save properties to project root folder
			prop.store(output, null);

		} catch (IOException io) {
			io.printStackTrace();
		} finally {
			if (output != null) {
				try {
					output.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

		}
	}
	
	public static void saveProp(String key, Object value){
		cache.put(key, value.toString());
		Properties prop = new Properties();
		OutputStream output = null;
		try {
			FileInputStream in = new FileInputStream(getFile(PROP_FILE_PATH));
			prop.load(in);
			in.close();
			
			output = new FileOutputStream(PROP_FILE_PATH);

			// set the properties value
			prop.setProperty(key, value.toString());

			// save properties to project root folder
			prop.store(output, null);

		} catch (IOException io) {
			io.printStackTrace();
		} finally {
			if (output != null) {
				try {
					output.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

		}
	}
	
	
	private static String readProp(String key){
		
		String value = cache.get(key);
		if(value != null) return value;
		Properties prop = new Properties();
		FileInputStream in = null;
		try{
		in = new FileInputStream(getFile(PROP_FILE_PATH));
		prop.load(in);
		
		value = prop.getProperty(key);
		}catch(IOException e){
			e.printStackTrace();
		}finally {
			if(in != null)
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}
		return value;
		
	}
	
	public static double readDouble(String key){
		String value = readProp(key);
		return value != null ? Float.valueOf(value) : 0f;
	}
	
	public static long readLong(String key){
		String value = readProp(key);
		return value != null ? Long.valueOf(value) : 0;
	}
	
	public static String readString(String key){
		return readProp(key);
	}
	
	public static float readFloat(String key){
		String value = readProp(key);
		return value != null ? Float.valueOf(value) : 0f;
	}
	
	public static int readInt(String key){
		String value = readProp(key);
		return value != null ? Integer.valueOf(value) : 0;
	}
	
	public static boolean readBoolean(String key){
		String value = readProp(key);
		return value != null ? Boolean.valueOf(value) : false;
	}	

}