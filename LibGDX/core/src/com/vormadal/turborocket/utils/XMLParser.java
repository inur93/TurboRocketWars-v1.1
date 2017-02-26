package com.vormadal.turborocket.utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;

import com.badlogic.gdx.math.Vector2;
import com.vormadal.turborocket.models.Map;
import com.vormadal.turborocket.models.configs.MapConfig;
import com.vormadal.turborocket.models.configs.MapObjectConfig;
import com.vormadal.turborocket.models.configs.PlatformConfig;
import com.vormadal.turborocket.utils.Setting.SettingType;
import com.vormadal.turborocket.utils.Setting.ValueType;

public class XMLParser {

	//map fields
	private static final String BACKGROUND_PATH = "background";
	private static final String MAP = "map";
	private static final String WIDTH = "width";
	private static final String HEIGHT = "height";
	private static final String PLATFORM = "platform";
	private static final String POINT_A = "pointA";
	private static final String POINT_B = "pointB";
	private static final String POINT = "point";
	private static final String MAP_OBJECT = "mapObject";
	
	public MapConfig readMapConfig(String configFile){
		MapConfig mapConfig = new MapConfig();
		try {
			// First, create a new XMLInputFactory
			XMLInputFactory inputFactory = XMLInputFactory.newInstance();
			// Setup a new eventReader
			InputStream in = new FileInputStream(configFile);
			XMLEventReader eventReader = inputFactory.createXMLEventReader(in);
			// these will be created when the right element is reached in reader
			PlatformConfig platformConfig = null;
			MapObjectConfig mapObjectConfig = null;

			while (eventReader.hasNext()) {
				XMLEvent event = eventReader.nextEvent();

				if (event.isStartElement()) {
					StartElement startElement = event.asStartElement();
					//we do not expect more than one map in file
					if (startElement.getName().getLocalPart().equals(MAP)) {
						@SuppressWarnings("unchecked")
						Iterator<Attribute> attributes = startElement.getAttributes();
						while (attributes.hasNext()) {
							Attribute attribute = attributes.next();
							switch(attribute.getName().toString()){
							case WIDTH:
								mapConfig.width = Float.valueOf(attribute.getValue());
								break;
							case HEIGHT:
								mapConfig.height = Float.valueOf(attribute.getValue());
								break;
							case BACKGROUND_PATH:
								mapConfig.backgroundPath = attribute.getValue();
								break;
							}
						}
					}
					
					String localPart = event.asStartElement().getName().getLocalPart();
					if (event.isStartElement()) {	
						if (localPart.equals(PLATFORM)) {
							event = eventReader.nextEvent();
							platformConfig = new PlatformConfig();
							continue;
						}
						if(localPart.equals(MAP_OBJECT)){
							event = eventReader.nextEvent();
							mapObjectConfig = new MapObjectConfig();
							continue;
						}
					}
					
					if (localPart.equals(POINT_A)) {
						event = eventReader.nextEvent();
						platformConfig.pointA = getVector2(event.asCharacters().getData());
						continue;
					}

					if (localPart.equals(POINT_B)) {
						event = eventReader.nextEvent();
						platformConfig.pointB = getVector2(event.asCharacters().getData());
						continue;
					}
					
					if(localPart.equals(POINT)){
						event = eventReader.nextEvent();
						mapObjectConfig.vertices.add(getVector2(event.asCharacters().getData()));
						continue;
					}
				}
				// If we reach the end of an element, we have to add it to the appropriate list
				if (event.isEndElement()) {
					String endElementLocalPart = event.asEndElement().getName().getLocalPart();
					if (endElementLocalPart.equals(MAP)) {
						return mapConfig; // we expect only one map. therefore return when we are done with first map element
					}
					if(endElementLocalPart.equals(PLATFORM)){
						mapConfig.platforms.add(platformConfig);
					}
					if(endElementLocalPart.equals(MAP_OBJECT)){
						mapConfig.mapObjects.add(mapObjectConfig);
					}
				}

			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (XMLStreamException e) {
			e.printStackTrace();
		}
		return mapConfig;
	}
	
	private static final String SETTING = "setting";
	private static final String SETTING_TYPE = "settingType";
	private static final String VALUE_TYPE = "valueType";
	private static final String VALUE = "value";
	private static final String NAME = "name";
	private static final String DESCRIPTION = "description";
	private static final String VALIDATION = "validation";
	private static final String MIN_VALUE = "minValue";
	private static final String MAX_VALUE = "maxValue";
	private static final String REGEX = "regEx";
	
	
	

	public List<Setting> readSettingsConfig(String configFile){
		List<Setting> settings = new ArrayList();
		try {
			// First, create a new XMLInputFactory
			XMLInputFactory inputFactory = XMLInputFactory.newInstance();
			// Setup a new eventReader
			InputStream in = new FileInputStream(configFile);
			XMLEventReader eventReader = inputFactory.createXMLEventReader(in);
			// these will be created when the right element is reached in reader
			PlatformConfig platformConfig = null;
			MapObjectConfig mapObjectConfig = null;
			Setting setting = null;

			while (eventReader.hasNext()) {
				XMLEvent event = eventReader.nextEvent();
				if (event.isStartElement()) {
					StartElement startElement = event.asStartElement();
					// first setting found create new object
					if (startElement.getName().getLocalPart().equals(SETTING)) {
						setting = new Setting();
						@SuppressWarnings("unchecked")
						Iterator<Attribute> attributes = startElement.getAttributes();
						while (attributes.hasNext()) {
							Attribute attribute = attributes.next();
							String value = attribute.getValue();
							switch(attribute.getName().toString()){
							case NAME:
								setting.name = value;
								break;
							case SETTING_TYPE:
								//TODO throw invalid value exception
								setting.settingType = SettingType.valueOf(value);
								break;
							case VALUE_TYPE:
								//TODO throw invalid value exception
								setting.valueType = ValueType.valueOf(value);
								break;
							case VALUE:
								setting.value = value;
								break;
							case DESCRIPTION:
								setting.description = value;
								break;
							}
						}
					}
					
					if (startElement.getName().getLocalPart().equals(VALIDATION)) {
						@SuppressWarnings("unchecked")
						Iterator<Attribute> attributes = startElement.getAttributes();
						while (attributes.hasNext()) {
							Attribute attribute = attributes.next();
							String value = attribute.getValue();
							switch(attribute.getName().toString()){
							case MIN_VALUE:
								setting.validation.minValue = value;
								break;
							case MAX_VALUE:
								setting.validation.maxValue = value;
								break;
							case REGEX:
								setting.validation.regEx = value;
								break;
							}
						}
					}
					
				}
				// If we reach the end of an element, we have to add it to the appropriate list
				if (event.isEndElement()) {
					String endElementLocalPart = event.asEndElement().getName().getLocalPart();
					if (endElementLocalPart.equals(SETTING)) {
						settings.add(setting);
					}
				}

			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (XMLStreamException e) {
			e.printStackTrace();
		}
		return settings;
	}
	private Vector2 getVector2(String rawVal){
		String[] xy = rawVal.split(";");
		return new Vector2(Float.valueOf(xy[0]), Float.valueOf(xy[1]));
	}
	
	// test
	public static void main(String[] args){
//		MapConfig config = new XMLParser().readMapConfig("C:/Workspaces/TurboRocketWars/LibGDX/core/assets/maps/map1.config");
//		System.out.println(config.toString());
		
		List<Setting> settings = new XMLParser().readSettingsConfig("C:/workspace/TurboRocketWars-v1.1/LibGDX/core/assets/default-settings.config");
		
		for(Setting s : settings){
			System.out.println(s);
			boolean valid = s.validate();
			if(!valid){
				System.out.println(s.getError());
			}
		}
		
		
	}
}
