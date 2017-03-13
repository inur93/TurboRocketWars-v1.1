package com.vormadal.turborocket.utils;

public class Setting {

	public enum SettingType {TEXT_FIELD, SLIDER, TOGGLE};
	public enum ValueType {STRING, INT, FLOAT, BOOLEAN};

	public String id;
	public String name;
	public String value;

	public SettingType settingType;
	public ValueType valueType;
	public String description;
	public SettingValidation validation = new SettingValidation();

	public boolean isValid(String value){
		String curValue = this.value;
		this.value = value;
		boolean valid = validate();
		errorMessage = "";
		errors = 0;
		this.value = curValue;
		return valid;
	}
	
	public boolean validate(){
		switch (valueType) {
		case BOOLEAN:
			tryBoolCast("VALUE", value);
			tryBoolCast("MIN VALUE", validation.minValue);
			tryBoolCast("MAX VALUE", validation.maxValue);
			if(!this.settingType.equals(SettingType.TOGGLE)){
				addError("Setting type for boolean cannot be " + this.settingType + ". It must be TOGGLE");
			}
			break;
		case FLOAT:
			tryFloatCast("VALUE", value);
			tryFloatCast("MIN VALUE", validation.minValue);
			tryFloatCast("MAX VALUE", validation.maxValue);
			if(this.settingType.equals(SettingType.TOGGLE)){
				addError("Setting type for float cannot be TOGGLE");
			}
			break;
		case INT:
			tryIntCast("VALUE", value);
			tryIntCast("MIN VALUE", validation.minValue);
			tryIntCast("MAX VALUE", validation.maxValue);
			if(this.settingType.equals(SettingType.TOGGLE)){
				addError("Setting type for int cannot be TOGGLE");
			}
			break;
		case STRING:
			break;
		default:
			break;
		}
		if(errors > 0) return false;
		return true;
	}
	private int errors = 0;
	private String errorMessage = "";
	public boolean editable = true;

	private void addError(String error){
		this.errors++;

	}
	private String tryBoolCast(String name, String value){
		try{
			if(value != null && value.length() > 0){
				Boolean.valueOf(value);
			}
		}catch(Exception e){
			errors++;
			errorMessage += "Attribute " + name + ": " + value + " can not be cast to boolean\n";
			return e.getMessage();
		}
		return "OK";
	}

	private String tryFloatCast(String name, String value){
		try{
			if(value != null && value.length() > 0){
				Float.valueOf(value);
			}
		}catch(Exception e){
			errors++;
			errorMessage += "Attribute " + name + ": " + value + " can not be cast to float\n";
			return e.getMessage();
		}
		return "OK";
	}
	
	private String tryIntCast(String name, String value){
		try{
			if(value != null && value.length() > 0){
				Integer.valueOf(value);
			}
		}catch(Exception e){
			errors++;
			errorMessage += "Attribute " + name + ": " + value + " can not be cast to int\n";
			return e.getMessage();
		}
		return "OK";
	}
	
	public String getError(){
		if(errors > 0){
			return this.errorMessage;
		}
		return "ALL OK";
	}
	public String toString(){
		return "Setting{\n"
				+ "name: " + name + "\n"
				+ "value: " + value + "\n"
				+ "setting type: " + settingType.toString() + "\n"
				+ "value type: " + valueType + "\n"
				+ "description: " + description + "\n"
				+ "min value: " + validation.minValue + "\n"
				+ "max value: " + validation.maxValue + "\n"
				+ "regex: " + validation.regEx + "\n"
				+ "}\n";
	}

	public class SettingValidation{
		public String minValue;
		public String maxValue;
		public String regEx;
	}
}
