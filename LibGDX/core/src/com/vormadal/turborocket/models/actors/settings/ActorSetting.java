package com.vormadal.turborocket.models.actors.settings;

import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.vormadal.turborocket.configurations.Setting;

public abstract class ActorSetting {

	protected Setting setting;
	protected static final float PADDING = 3;
	public ActorSetting(Setting setting){
		this.setting = setting;
	}
	public abstract void addToTable(Table table);
	public abstract void setValue(String value);
	public abstract String getValue();
	public abstract void resetValue();
	public abstract void saveValue();
	public String getId(){
		return setting.id;
	}
}
