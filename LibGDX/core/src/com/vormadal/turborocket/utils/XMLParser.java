package com.vormadal.turborocket.utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Iterator;

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

public class XMLParser {

	static final String BACKGROUND_PATH = "background";
	static final String MAP = "map";
	static final String WIDTH = "width";
	static final String HEIGHT = "height";
	static final String PLATFORM = "platform";
	static final String POINT_A = "pointA";
	static final String POINT_B = "pointB";
	static final String POINT = "point";
	static final String MAP_OBJECT = "mapObject";


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

	private Vector2 getVector2(String rawVal){
		String[] xy = rawVal.split(";");
		return new Vector2(Float.valueOf(xy[0]), Float.valueOf(xy[1]));
	}
	public static void main(String[] args){
		MapConfig config = new XMLParser().readMapConfig("C:/Workspaces/TurboRocketWars/LibGDX/core/assets/maps/map1.config");
		System.out.println(config.toString());
	}
}
