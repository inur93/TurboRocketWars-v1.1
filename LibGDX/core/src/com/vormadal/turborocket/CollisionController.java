package com.vormadal.turborocket;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;
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
//		WorldEntity e1 = typeA.equals(EntityType.AMMO) || typeA.equals(EntityType.SHIP) 
//				? dataA.getEntity() : dataB.getEntity();
//		WorldEntity e2 = e1.equals(dataA.getEntity()) 
//				? dataB.getEntity() : dataA.getEntity();
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
				break;
			case SHIP:
				ship2Ship((Ship<?, ?>) dataA.getEntity(), (Ship<?, ?>) dataB.getEntity());
				break;
			}
			break;
		}
		
	}

	private void ship2Map(Ship<?, ?> entity) {
		entity.die();
	}

	@Override
	public void endContact(Contact contact) {
		// TODO Auto-generated method stub
		
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
