package com.vormadal.turborocket.models.actors;

import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.vormadal.turborocket.configurations.Setting;

public class ActorSettingList extends Actor {
	
//	private Skin skin;
	private Stage stage;
//	private SpriteBatch batch;
	private List<Setting> settings;
		
	public ActorSettingList(List<Setting> settings, Stage stage){
		this.settings = settings;
		this.stage = stage;
		create();
	}


	

	public void create(){
		Gdx.input.setInputProcessor(stage);
		setOrigin(0, 0);
		setSize(500, 1000);
		
		int yPos = 100;
		for(Setting s : settings){
			if(s.editable){
				new ActorSetting(stage, s, yPos);
				yPos += 25;
			}
		}

	}

	
//	@Override
//		public void draw(Batch batch, float parentAlpha) {
//			// TODO Auto-generated method stub
//		
//			super.draw(batch, parentAlpha);
//			Gdx.gl.glClearColor(0.2f, 0.2f, 0.2f, 1);
//			Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
//			getStage().act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
////			batch.begin();
//			getStage().draw();
////			batch.end();
//			
//			System.out.println("draw");
//		}
	


}
