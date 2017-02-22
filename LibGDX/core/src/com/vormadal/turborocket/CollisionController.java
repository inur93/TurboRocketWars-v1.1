package com.vormadal.turborocket;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.vormadal.turborocket.models.Platform;
import com.vormadal.turborocket.models.Ship;
import com.vormadal.turborocket.models.WorldEntity;
import com.vormadal.turborocket.models.WorldEntityData;
import com.vormadal.turborocket.models.WorldEntityData.EntityType;
import com.vormadal.turborocket.models.ammo.Ammo;

public class CollisionController implements ContactListener{

	@Override
	public void beginContact(Contact contact) {

		WorldEntityData dataA = (WorldEntityData) contact.getFixtureA().getBody().getUserData();
		WorldEntityData dataB = (WorldEntityData) contact.getFixtureB().getBody().getUserData();
		EntityType typeA = dataA.getType();
		EntityType typeB = dataB.getType();
		//		
		System.out.println("collision: " + typeA + "<->" + typeB);

		switch(typeA){
		case AMMO:
			switch(typeB){
			case AMMO:
				ammoSoloCollision((Ammo) dataB.getEntity());
			case MAP:
			case PLATFORM:
				ammoSoloCollision((Ammo) dataA.getEntity());
				break;
			case SHIP:
				ship2Ammo((Ship<?, ?>) dataB.getEntity(), (Ammo) dataA.getEntity());
				break;
			}
			break;
		case MAP:
			switch(typeB){
			case AMMO:
				ammoSoloCollision((Ammo) dataB.getEntity());
				break;
			case MAP:
			case PLATFORM:
				break;
			case SHIP:
				ship2Map((Ship<?, ?>) dataB.getEntity());
				break;
			}
			break;
		case PLATFORM:
			switch(typeB){
			case AMMO:
				ammoSoloCollision((Ammo)dataB.getEntity());
				break;
			case MAP:
			case PLATFORM:
				break;
			case SHIP:
				ship2Platform((Ship<?,?>) dataB.getEntity(), (Platform) dataA.getEntity());
				break;
			}
			break;
		case SHIP:
			switch(typeB){
			case AMMO:
				ship2Ammo((Ship<?, ?>) dataA.getEntity(), (Ammo) dataB.getEntity());
				break;
			case MAP:
				ship2Map((Ship<?, ?>) dataA.getEntity());
				break;
			case PLATFORM:
				ship2Platform((Ship<?,?>) dataA.getEntity(), (Platform) dataB.getEntity());
				break;
			case SHIP:
				ship2Ship((Ship<?, ?>) dataA.getEntity(), (Ship<?, ?>) dataB.getEntity());
				break;
			}
			break;
		}

	}

	private void ship2Platform(Ship<?, ?> ship, Platform platform) {
		double tol = 10; //degrees
		// get an angle between 0 and 360 degrees. 
		double angle = Math.abs(Math.toDegrees(Math.abs(ship.getBody().getAngle()%(Math.PI*2))));
		System.out.println("angle: " + angle);
		if(angle <= tol || angle > 360-5){
			ship.regenerateAmmo();
			ship.regenerateHP();
		}else{
			ship.die();
		}
	}

	private void ship2Map(Ship<?, ?> entity) {
		entity.die();
	}

	@Override
	public void endContact(Contact contact) {
		WorldEntityData dataA = (WorldEntityData) contact.getFixtureA().getBody().getUserData();
		WorldEntityData dataB = (WorldEntityData) contact.getFixtureB().getBody().getUserData();
		EntityType typeA = dataA.getType();
		EntityType typeB = dataB.getType();
		//		
		System.out.println("collision end: " + typeA + "<->" + typeB);

		switch(typeA){
		case PLATFORM:
			switch(typeB){
			case SHIP:
				ship2Platform((Ship<?,?>) dataB.getEntity(), (Platform) dataA.getEntity());
				break;
			default:
				System.out.println("dont care");
			}
			break;
		case SHIP:
			switch(typeB){
			case PLATFORM:
				ship2Platform((Ship<?,?>) dataA.getEntity(), (Platform) dataB.getEntity());
				break;
			default:
				System.out.println("dont care");
			}
			break;

		default:
			System.out.println("dont care");
		}
	}

	@Override
	public void preSolve(Contact contact, Manifold oldManifold) {
		// TODO Auto-generated method stub

	}

	@Override
	public void postSolve(Contact contact, ContactImpulse impulse) {
		/* TODO Auto-generated
		 *  method stub

		 */
	}


	private void ship2Ammo(Ship<?,?> ship, Ammo ammo){
		ship.attack(ammo.getDamage());
		ammo.collide();
	}

	private void ship2Ship(Ship<?,?> shipA, Ship<?,?> shipB){
		shipA.die();
		shipB.die();
	}

	private void ammoSoloCollision(Ammo ammo){
		ammo.collide();
	}

}
