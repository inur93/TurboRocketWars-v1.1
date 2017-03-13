package com.vormadal.turborocket.models.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class ActorBackground extends Actor{
	private Texture tex;
	private Sprite sprite;
	
	public ActorBackground(String imgPath) {
		tex = new Texture(Gdx.files.internal(imgPath));
		 sprite = new Sprite(tex);
		 sprite.setBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
	}
	@Override
	public void draw(Batch batch, float parentAlpha) {
		super.draw(batch, parentAlpha);
		sprite.draw(batch);
	}
}
