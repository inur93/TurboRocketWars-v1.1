package test;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.badlogic.gdx.math.Vector2;
import com.vormadal.turborocket.models.Map;
import com.vormadal.turborocket.models.Platform;
import com.vormadal.turborocket.models.configs.MapConfig;
import com.vormadal.turborocket.models.configs.PlatformConfig;
import com.vormadal.turborocket.utils.Setting;
import com.vormadal.turborocket.utils.Setting.SettingType;
import com.vormadal.turborocket.utils.Setting.ValueType;
public class XMLReader {
	
	public Element getDocElement(String xml) {
       
        Document dom;
        // Make an  instance of the DocumentBuilderFactory
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        try {
            // use the factory to take an instance of the document builder
            DocumentBuilder db = dbf.newDocumentBuilder();
            // parse using the builder to get the DOM mapping of the    
            // XML file
            dom = db.parse(xml);

            return dom.getDocumentElement();

        } catch (ParserConfigurationException pce) {
            System.out.println(pce.getMessage());
        } catch (SAXException se) {
            System.out.println(se.getMessage());
        } catch (IOException ioe) {
            System.err.println(ioe.getMessage());
        }

        return null;
    }
	private static final String SETTING = "setting";
	private static final String ID = "id";
	private static final String SETTING_TYPE = "settingType";
	private static final String VALUE_TYPE = "valueType";
	private static final String VALUE = "value";
	private static final String NAME = "name";
	private static final String DESCRIPTION = "description";
	private static final String VALIDATION = "validation";
	private static final String MIN_VALUE = "minValue";
	private static final String MAX_VALUE = "maxValue";
	private static final String REGEX = "regEx";
	private static final String EDITABLE = "editable";
	
	private String getAttribute(NamedNodeMap attributes, String name){
		
		if(attributes == null){ 
			System.out.println("getVal: NamedNodeMap null for attribute: " + name);
			return null;
		}
		Node item;
		if((item = attributes.getNamedItem(name)) != null){
			return item.getNodeValue();
		}else{
			System.out.println("getVal: no value for: " + name);
		}
		return null;
	}
	
	private Node getNodeByName(Node parentNode, String name){
		NodeList children = parentNode.getChildNodes();
		for(int i = 0; i < children.getLength(); i++){
			Node node = children.item(i);
			if(node.getNodeName().equals(name)){
				return node;
			}
		}
		return null;
	}
	
	private List<Node> getNodeListByName(Node parent, String name){
		List<Node> nodes = new ArrayList<>();
		NodeList children = parent.getChildNodes();
		for(int i = 0; i < children.getLength(); i++){
			Node node = children.item(i);
			if(node.getNodeName().equals(name)){
				nodes.add(node);
			}
		}
		return nodes;
	}
	public boolean saveSettings(List<Setting> settings, String filename){
		if(!filename.endsWith(".config")){
			filename += ".config";
		}
		
		 Document dom;
		    Element e = null;

		    // instance of a DocumentBuilderFactory
		    DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		    try {
		        // use factory to get an instance of document builder
		        DocumentBuilder db = dbf.newDocumentBuilder();
		        // create instance of DOM
		        dom = db.newDocument();

		        // create the root element
		        Element rootEle = dom.createElement("config");
		        
		        for(Setting s : settings){
		        	e = dom.createElement(SETTING);
		        	e.setAttribute(ID, s.id);
		        	e.setAttribute(SETTING_TYPE, s.settingType.toString());
		        	e.setAttribute(VALUE_TYPE, s.valueType.toString());
		        	e.setAttribute(VALUE, s.value);
		        	e.setAttribute(NAME, s.name);
		        	e.setAttribute(DESCRIPTION, s.description);
		        	e.setAttribute(EDITABLE, String.valueOf(s.editable));
		        	Element v = dom.createElement(VALIDATION);
		        	v.setAttribute(MIN_VALUE, s.validation.minValue);
		        	v.setAttribute(MAX_VALUE, s.validation.maxValue);
		        	v.setAttribute(REGEX, s.validation.regEx);
		        	e.appendChild(v);
		        	rootEle.appendChild(e);
		        }

		        dom.appendChild(rootEle);

		        try {
		            Transformer tr = TransformerFactory.newInstance().newTransformer();
		            tr.setOutputProperty(OutputKeys.INDENT, "yes");
		            tr.setOutputProperty(OutputKeys.METHOD, "xml");
		            tr.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
//		            tr.setOutputProperty(OutputKeys.DOCTYPE_SYSTEM, "roles.dtd");
		            tr.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");

		            // send DOM to file
		            tr.transform(new DOMSource(dom), 
		                                 new StreamResult(new FileOutputStream(filename)));
		            return true;

		        } catch (TransformerException te) {
		            System.out.println(te.getMessage());
		        } catch (IOException ioe) {
		            System.out.println(ioe.getMessage());
		        }
		    } catch (ParserConfigurationException pce) {
		        System.out.println("UsersXML: Error trying to instantiate DocumentBuilder " + pce);
		    }
		return false;
	}
	public List<Setting> loadSettings(String configPath) {
		List<Setting> settings = new ArrayList<>();
	    NodeList settingNodes;
	    settingNodes = getDocElement(configPath).getElementsByTagName(SETTING);
	    for(int i = 0; i < settingNodes.getLength(); i++){
	    	Node settingNode = settingNodes.item(i);
	    	NamedNodeMap settingAttributes = settingNode.getAttributes();
	    	Setting setting = new Setting();
	    	Node validationNode = null;
	    	NodeList settingChildren = settingNode.getChildNodes();
	    	for(int j = 0; j < settingChildren.getLength(); j++){
	    		Node child = settingChildren.item(j);
	    		if(child.getNodeName().equalsIgnoreCase(VALIDATION)){
	    			validationNode = child;
	    			break;
	    		}
	    	}
	    	//setting id
	    	String id = getAttribute(settingAttributes, ID);
	    	if(id != null) setting.id = id;
	    	
	    	//setting type
	    	String settingType = getAttribute(settingAttributes, SETTING_TYPE);
	    	if(settingType != null) setting.settingType = SettingType.valueOf(settingType);
	    	
	    	//value type
	    	String valueType = getAttribute(settingAttributes, VALUE_TYPE);
	    	if(valueType != null) setting.valueType = ValueType.valueOf(valueType);
	    	
	    	String value = getAttribute(settingAttributes, VALUE);
	    	if(value != null) setting.value = value;
	    	
	    	String name = getAttribute(settingAttributes, NAME);
	    	if(name != null) setting.name = name;
	    	
	    	String description = getAttribute(settingAttributes, DESCRIPTION);
	    	if(description != null) setting.description = description;
	    	
	    	String editable = getAttribute(settingAttributes, EDITABLE);
	    	if(editable != null) setting.editable = Boolean.valueOf(editable);
	    	
	    	if(validationNode != null){
	    		NamedNodeMap validationAttributes = validationNode.getAttributes();
	    		String minVal = getAttribute(validationAttributes, MIN_VALUE);
	    		if(minVal != null) setting.validation.minValue = minVal;
	    		
	    		String maxVal = getAttribute(validationAttributes, MAX_VALUE);
	    		if(maxVal != null) setting.validation.maxValue = maxVal;
	    		
	    		String regEx = getAttribute(validationAttributes, REGEX);
	    		if(regEx != null) setting.validation.regEx = regEx;
	    	}
	    	settings.add(setting);
//	    	System.out.println(setting.toString());

	    }
	    return settings;
	}
	
	private static final String BACKGROUND_PATH = "background";
//	private static final String NAME = "name";
//	private static final String DESCRIPTION = "description";
	private static final String MAP = "map";
	private static final String WIDTH = "width";
	private static final String HEIGHT = "height";
	private static final String PLATFORM = "platform";
	private static final String POINT_A = "pointA";
	private static final String POINT_B = "pointB";
	private static final String POINT = "point";
	private static final String X = "X";
	private static final String Y = "Y";
	
	private static final String MAP_OBJECT = "mapObject";
	
	public MapConfig loadMap(String configPath){
		MapConfig map = new MapConfig();
	    Node mapNode;
	    NodeList nodeList = getDocElement(configPath).getElementsByTagName(MAP);
	    if(nodeList.getLength() <= 0) return null;
	    
	    mapNode = nodeList.item(0);
	    
	    map.width = Float.valueOf(getAttribute(mapNode.getAttributes(), WIDTH));
	    map.height= Float.valueOf(getAttribute(mapNode.getAttributes(), HEIGHT));
	    map.backgroundPath = getAttribute(mapNode.getAttributes(), BACKGROUND_PATH);
	    map.name = getAttribute(mapNode.getAttributes(), NAME);
	    map.description = getAttribute(mapNode.getAttributes(), DESCRIPTION);
	    
	    NodeList children = mapNode.getChildNodes();
	    for(int i = 0; i < children.getLength(); i++){
	    	Node child = children.item(i);
	    	String childName = child.getNodeName();
	    	if(childName.equals(PLATFORM)){
	    		Node pointA = getNodeByName(child, POINT_A);
	    		Node pointB = getNodeByName(child, POINT_B);
	    		PlatformConfig platform = new PlatformConfig();
	    		String aX = getAttribute(pointA.getAttributes(), X);
	    		String aY = getAttribute(pointA.getAttributes(), Y);
	    		String bX = getAttribute(pointB.getAttributes(), X);
	    		String bY = getAttribute(pointB.getAttributes(), Y);
	    		
	    		platform.pointA = new Vector2(Float.valueOf(aX), Float.valueOf(aY));
	    		platform.pointB = new Vector2(Float.valueOf(bX), Float.valueOf(bY));
	    		map.platforms.add(platform);
	    	}else if(childName.equals(MAP_OBJECT)){
	    		
	    	}
	    }
	    System.out.println("mapConfig: " + map.toString());
	    return map;
	    
	}
	public static void main(String[] args) {
		List<Setting> settings = new XMLReader().loadSettings("assets/default-settings.config");
		new XMLReader().saveSettings(settings, "assets/my-settings.config");
		
	}
}
