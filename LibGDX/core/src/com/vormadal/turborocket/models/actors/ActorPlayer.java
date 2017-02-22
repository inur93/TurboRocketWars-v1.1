package com.vormadal.turborocket.models.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.vormadal.turborocket.models.Player;
import com.vormadal.turborocket.models.Ship;
import com.vormadal.turborocket.models.ammo.Cannon;

import static com.vormadal.turborocket.utils.ConfigUtil.*;
import static com.vormadal.turborocket.utils.PropKeys.*;

public class ActorPlayer extends Actor{

	private Texture barBackgroundTex = new Texture(Gdx.files.internal("status-bars/background.png"));
	private Texture hitpointsTex = new Texture(Gdx.files.internal("status-bars/hp.png"));	
	private Texture ammoTex = new Texture(Gdx.files.internal("status-bars/ammo.png"));	
	private Texture specialAmmoTex = new Texture(Gdx.files.internal("status-bars/special-ammo.png"));	
	
	private Sprite barBackground = new Sprite(barBackgroundTex);
	private Sprite hitpoints = new Sprite(hitpointsTex);
	private Sprite ammo = new Sprite(ammoTex);
	private Sprite specialAmmo = new Sprite(specialAmmoTex);
	
	private BitmapFont font = new BitmapFont();
	private int hpBarLength = getHpBarLength();
	private int hpBarHeight = getHpBarHeight();
	
	private Player player;
	
	public ActorPlayer(Player player) {
		this.player = player;
//		hitpoints.setBounds(0, 0, 100, 50);
	}
	@Override
	public void draw(Batch batch, float parentAlpha) {
		Ship<?,?> ship = player.getShip();
		Cannon<?> cannonNormal = ship.getNormalCannon();
		Cannon<?> cannonSpecial = ship.getSpecialCannon();
		
		float x = player.getScreenX()*Gdx.graphics.getWidth();
		float y = 10;
		float hpPct = ship.getCurHitPoints()/ship.getMaxHitPoints();
		float ammoPct = cannonNormal.getAmmoCount()/cannonNormal.getMaxAmmoCount();
		float spcAmmoPct = cannonSpecial.getAmmoCount()/cannonSpecial.getMaxAmmoCount();
		
		float hpLeft = hpPct * hpBarLength * getScaleX();
		float ammoLeft = ammoPct * hpBarLength * getScaleX();
		float spcAmmoLeft = spcAmmoPct * hpBarLength * getScaleX();		
		
		font.draw(batch, "SPC AMMO:", x, y);
		font.draw(batch, "AMMO:", x, y+font.getLineHeight());
		font.draw(batch, "HP:", x, y+font.getLineHeight()*2);
		int fontWidth = 85;

		hitpoints.setBounds(0, 0, hpLeft, hpBarHeight);
//		System.out.println("x,y=" + x + "," + y);
//		System.out.println("nrm ammo: " + cannonNormal.getAmmoCount() + "/" + cannonNormal.getMaxAmmoCount());
		
//		font.draw(batch, "Name: " + player.getShip().getId(), x, y+font.getLineHeight());
		// bars background
		float barWidth = hpBarLength * getScaleX();
		float barHeight = hpBarHeight * getScaleY();
		float barY = 0;
		
		batch.draw(barBackground, x+fontWidth, barY, barWidth, barHeight);
		batch.draw(specialAmmo, x+fontWidth, barY, spcAmmoLeft, hpBarHeight * getScaleY());
		barY += font.getLineHeight();
		batch.draw(barBackground, x+fontWidth, barY, barWidth, barHeight);
		batch.draw(ammo, x+fontWidth, barY, ammoLeft, hpBarHeight * getScaleY());
		barY += font.getLineHeight();
		batch.draw(barBackground, x+fontWidth, barY, barWidth, barHeight);	
		batch.draw(hitpoints, x+fontWidth, barY, hpLeft, hpBarHeight * getScaleY());

	}
}
