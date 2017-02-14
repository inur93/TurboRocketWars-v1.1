package com.vormadal.turborocket.models.ammo;

import com.badlogic.gdx.math.Matrix3;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

public class Cannon<T extends Ammo> {

	private World world;
	private AmmoFactory<T> shotFactory;

	private Vector2 cannonDirection = new Vector2(0,5);
	private Vector2[] cannonPos;
	
	private long reloadTime = 100; //msec default
	private long timeOfLastShot = -1;
	
	public Cannon(AmmoFactory<T> shotFactory,
			int cannonNumber, 
			boolean cannonPointForward, 
			long reloadTime, 
			World world) {

		this.shotFactory = shotFactory;	
		this.world = world;
		if(reloadTime > 0) this.reloadTime = reloadTime;
		if(!cannonPointForward) cannonDirection = cannonDirection.scl(-1);

		cannonPos = new Vector2[cannonNumber];
		switch(cannonNumber){
		case 1 : 
			cannonPos = new Vector2[]{new Vector2(0, 7)};
			break;
		case 2:
			cannonPos = new Vector2[]{new Vector2(2, 5), new Vector2(-2,5)};
			break;
		case 3:
		default: 
			cannonPos = new Vector2[]{new Vector2(2.5f, 7), new Vector2(0,10), new Vector2(-2.5f, 7)};
			break;
		}

		if(!cannonPointForward){
			for(int i = 0; i < cannonPos.length; i++) cannonPos[i] = cannonPos[i].scl(-1);
		}
	}

	public synchronized boolean fire(Vector2 p0, Vector2 v0, float angle){
		if(!isLoaded()) return false;
		
		Vector2 dir = new Vector2(1,0).rotate(angle);
		for(int i = 0; i < cannonPos.length; i++){
			Vector2 pos = p0.add(cannonPos[i].rotate(angle));
			Ammo s = shotFactory.factory(v0, pos, dir.scl(cannonDirection), world);
			//TODO call s.create() when appropriate
		}

		return true;
	}
	
	private boolean isLoaded() {
		long newTime = System.currentTimeMillis();
		if (newTime - timeOfLastShot > this.reloadTime) {
			timeOfLastShot = newTime;
			return true;
		}
		return false;
	}

	public long getReloadTime(){
		return this.reloadTime;
	}



}

