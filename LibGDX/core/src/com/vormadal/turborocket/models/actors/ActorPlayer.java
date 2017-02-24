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
	private Texture lifeTex = new Texture(Gdx.files.internal("status-bars/life.png"));
	
	private Sprite barBackground = new Sprite(barBackgroundTex);
	private Sprite hitpoints = new Sprite(hitpointsTex);
	private Sprite ammo = new Sprite(ammoTex);
	private Sprite specialAmmo = new Sprite(specialAmmoTex);
	private Sprite life = new Sprite(lifeTex);
	
	private BitmapFont font = new BitmapFont();
	private int barLength = getHpBarLength();
	private int hpBarHeight = getHpBarHeight();
	
	private Player player;
	private float lineHeight = font.getLineHeight()+2;
	private float x0 = 10, x1 = 85, x2 = 200, x3 = 285;
	private float y0 = lineHeight, y1 = lineHeight*2;
	private float spriteYOffset = lineHeight/2;
	
	public ActorPlayer(Player player) {
		this.player = player;
		float xOffset = Gdx.graphics.getWidth()*player.getScreenX();
		x0 = x0+xOffset;
		x1 = x1+xOffset;
		x2 = x2+xOffset;
		x3 = x3+xOffset;
	}
	
	

	@Override
	public void draw(Batch batch, float parentAlpha) {
		Ship<?,?> ship = player.getShip();
		Cannon<?> cannonNormal = ship.getNormalCannon();
		Cannon<?> cannonSpecial = ship.getSpecialCannon();
				
		float hpPct = ship.getCurHitPoints()/ship.getMaxHitPoints();
		float ammoPct = cannonNormal.getAmmoCount()/cannonNormal.getMaxAmmoCount();
		float spcAmmoPct = cannonSpecial.getAmmoCount()/cannonSpecial.getMaxAmmoCount();
		
		int livesLeft = ship.getLives();
		float hpLeft = hpPct * barLength * getScaleX();
		float ammoLeft = ammoPct * barLength * getScaleX();
		float spcAmmoLeft = spcAmmoPct * barLength * getScaleX();		
		
		//column 1
		font.draw(batch, "HP:", x0, y0);
		font.draw(batch, "LIVES:", x0, y1);
		
		//column 2
		//hp bar
		float barWidth = barLength * getScaleX();
		float barHeight = hpBarHeight * getScaleY();
		batch.draw(barBackground, x1, y0-spriteYOffset, barWidth, barHeight);	
		batch.draw(hitpoints, x1, y0-spriteYOffset, hpLeft, barHeight);
		
		//lives
		for(int i = 0; i < livesLeft; i++){
			batch.draw(life, x1+i*lineHeight, y1-spriteYOffset, lineHeight-5, lineHeight-5);
		}
		
		//column 3
		font.draw(batch, "SPC AMMO:", x2, y0);
		font.draw(batch, "AMMO:", x2, y1);

		//column 4
		batch.draw(barBackground, x3, y0-spriteYOffset, barWidth, barHeight);
		batch.draw(specialAmmo, x3, y0-spriteYOffset, spcAmmoLeft, hpBarHeight * getScaleY());
	
		batch.draw(barBackground, x3, y1-spriteYOffset, barWidth, barHeight);
		batch.draw(ammo, x3, y1-spriteYOffset, ammoLeft, hpBarHeight * getScaleY());
	
//		hitpoints.setBounds(0, 0, hpLeft, hpBarHeight);


//		font.draw(batch, "Name: " + player.getShip().getId(), x, y+font.getLineHeight());
		// bars background
		
		
		
		
		//draw second column
		

		

	}
}
