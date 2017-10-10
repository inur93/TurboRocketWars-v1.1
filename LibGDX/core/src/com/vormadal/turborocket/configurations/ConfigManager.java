package com.vormadal.turborocket.configurations;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import com.vormadal.turborocket.exceptions.NoConfigException;
import com.vormadal.turborocket.models.configs.MapConfig;
import com.vormadal.turborocket.models.configs.ShipConfig;

public class ConfigManager {

	private XMLReader configReader;
	private List<SkinConfig> skins;
	private List<MapConfig> maps;
	private List<ShipConfig> ships;
	private SkinConfig skin;
	private boolean useFullscreen;
	private int width, height;
	private String defaultFont;
	private String titleFont;

	private String skinsPath;
	
	private HashMap<String, Setting> settings = new HashMap<>();

	//config files path ids
	private static final String SETUP_CONFIG = "setup.config";
	private static final String SCREEN_SETTINGS = "screen-settings";
	private static final String GENERAL_SETTINGS = "general-settings";
	private static final String MAPS_SETTINGS = "maps-settings";
	private static final String SHIP_SETTINGS = "ships-settings";
	private static final String SKINS = "skins";
	private static final String DEFAULT_SKIN_ID = "default-skin-id";
	private static final String DEFAULT_SETTINGS = "default-settings.config";
	
	//minimum config ids
	private static final String SCREEN_WIDTH = "screen-width";
	private static final String SCREEN_HEIGHT = "screen-height";
	private static final String USE_FULLSCREEN = "use-fullscreen";
	
	
	private static ConfigManager instance;
	public static ConfigManager instance(){
		if(instance == null) instance = new ConfigManager();
		return instance;
	}
	
	private ConfigManager(){
		configReader = new XMLReader();
		
		SettingsFile setup = null;
		try{
		setup = configReader.loadSettings(SETUP_CONFIG);
		}catch(NoConfigException e){
			System.err.println("setup config not found");
			e.printStackTrace();
			System.exit(0);
		}
		settings.putAll(setup.settings);
		
		defaultFont = getSettingValue(Styles.Const.FONT_DEFAULT);
		titleFont = getSettingValue(Styles.Const.FONT_TITLE);
				
		String width = getSettingValue(SCREEN_WIDTH);
		if(width != null) this.width = Integer.valueOf(width);

		String height = getSettingValue(SCREEN_HEIGHT);
		if(height != null) this.height = Integer.valueOf(height);

		String useFullscreen = getSettingValue(USE_FULLSCREEN);
		if(useFullscreen != null) this.useFullscreen = Boolean.valueOf(useFullscreen);

		this.skinsPath = getSettingValue(SKINS);

	}
	public void initialize(){
		try{
		loadSkinConfigs(skinsPath);
		SettingsFile generalSettings = loadSettings(getSettingValue(GENERAL_SETTINGS));
		this.settings.putAll(generalSettings.settings);
		SettingsFile screenSettings = loadSettings(getSettingValue(SCREEN_SETTINGS));
		this.settings.putAll(screenSettings.settings);
		List<SkinConfig> skinConfigs = configReader.loadSkins(getSettingValue(SKINS));
		
		
		this.maps = configReader.loadMaps(getSettingValue(MAPS_SETTINGS));
		this.ships = configReader.loadShips(getSettingValue(SHIP_SETTINGS));
		
		Iterator<String> keys = this.settings.keySet().iterator();
		while(keys.hasNext()){
			String key = keys.next();
			System.out.println("key: " + key + "=" + this.settings.get(key));
		}
		}catch(NoConfigException e){
			//TODO log
			e.printStackTrace();
			// we can't continue if some of these configs are not available
			System.exit(0);
		}
	}
	public void loadDefaultSettings(){
		try {
			loadSettings(DEFAULT_SETTINGS);
		} catch (NoConfigException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private SettingsFile loadSettings(String path) throws NoConfigException{

		SettingsFile file = configReader.loadSettings(path);
		this.settings.putAll(file.settings);
		return file;
	}

	public void loadSkinConfigs(String path) throws NoConfigException{
		skins = configReader.loadSkins(path);
		
		String defaultSkinId = getSettingValue(DEFAULT_SKIN_ID);
		
		for(SkinConfig s : skins){
			if(s.id.equals(defaultSkinId)){
				this.skin = s;
				this.settings.putAll(this.skin.settings.settings);
				break;
			}
		}
		if(this.skin == null){
			System.err.println("no skin found with id: " + defaultSkinId + "\n"
					+ "will use first skin available");
			this.skin = skins.get(0);
			
		}
		Styles.init(this.skin);
	}
	
	public void saveSettings(HashMap<String, String> values){
		values.forEach((k,v)->{
			settings.get(k).value = v;
		});
	}
	
	public void saveSetting(String id, String value){
		
	}

	public String getDefaultFontPath(){
		return this.defaultFont;
	}

	public String getTitleFontPath(){
		return this.titleFont;
	}

	public boolean useFullscreen(){
		return this.useFullscreen;
	}

	public int getScreenWidth(){
		return width;
	}

	public int getScreenHeight(){
		return height;
	}
	
	public List<MapConfig> getMaps(){
		return this.maps;
	}
	
	public List<ShipConfig> getShips(){
		return this.ships;
	}
	
	private Setting getSetting(String id){
		return this.settings.get(id);
	}
	
	public String getSettingValue(String id){
		Setting setting = getSetting(id);
		if(setting == null) {
			System.err.println("No setting for: " + id);
			return "";
		}
		return setting.value;
	}

	
	public HashMap<String, Setting> getAllSettings(){
		return this.settings;
	}
	
	
}
