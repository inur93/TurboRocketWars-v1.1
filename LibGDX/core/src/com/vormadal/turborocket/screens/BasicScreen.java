package com.vormadal.turborocket.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.vormadal.turborocket.configurations.ConfigManager;
import com.vormadal.turborocket.configurations.LabelStyles;
import com.vormadal.turborocket.models.actors.ActorBackground;
import com.vormadal.turborocket.utils.InputConfiguration;
import com.vormadal.turborocket.utils.InputListenerAdapter;

public class BasicScreen extends InputListenerAdapter implements Screen{
	
	private static final float LEFT_OFFSET = 10;
	private static final float BOT_OFFSET = 10;
	private static final float TOP_OFFSET = 10;
	private static final float RIGHT_OFFSET = 10;
	
	protected Stage stage;
	
	public enum Position {
		TOP_LEFT, TOP_MID, TOP_RIGHT,
		MID_LEFT, MID_MID, MID_RIGHT,
		BOT_LEFT, BOT_MID, BOT_RIGHT
	}
	
	public BasicScreen(String title, String backgroundId, InputConfiguration config) {
		super(config);
		stage = new Stage();
		stage.addActor(new ActorBackground(ConfigManager.instance().getSettingValue(backgroundId)));
		Label headerLabel = new Label(title, LabelStyles.getTitleStyle());
		placeWidget(headerLabel, Position.TOP_MID);
		stage.addActor(headerLabel);
	}
	@Override
	public void show() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void render(float delta) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}
	public void placeWidget(Actor actor, Position pos){
		placeWidget(actor, pos, 0,0);
	}
	public void placeWidget(Actor actor, Position pos, float xOffset, float yOffset){
	
		actor.setPosition(0, 0);
		actor.getWidth();
		float x = 0, y = 0;
		float sw = Gdx.graphics.getWidth();
		float sh = Gdx.graphics.getHeight();
		float aw = actor.getWidth();
		float ah = actor.getHeight();
		switch(pos){
		case BOT_LEFT:
			x = LEFT_OFFSET;
			y = BOT_OFFSET;
			break;
		case BOT_MID:
			x = sw/2-aw/2;
			y = BOT_OFFSET;
			break;
		case BOT_RIGHT:
			x = sw-aw-LEFT_OFFSET;
			y = BOT_OFFSET;
			break;
		case MID_LEFT:
			break;
		case MID_MID:
			break;
		case MID_RIGHT:
			break;
		case TOP_LEFT:
			x = LEFT_OFFSET;
			y = sh-ah-TOP_OFFSET;
			break;
		case TOP_MID:
			x = sw/2-aw/2;
			y = sh-ah-TOP_OFFSET;
			break;
		case TOP_RIGHT:
			x = sw-aw-LEFT_OFFSET;
			y = sh-ah-TOP_OFFSET;
			break;
		default:
			break;
		}
		actor.setPosition(x+xOffset, y+yOffset);
		stage.addActor(actor);
	}
	

}
