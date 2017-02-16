package com.vormadal.turborocket;

import java.util.ArrayList;
import java.util.LinkedList;

import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.vormadal.turborocket.models.WorldEntity;

public class WorldEntitiesController {

	private LinkedList<WorldEntity> createQueue = new LinkedList<>();
	private LinkedList<WorldEntity> destroyQueue = new LinkedList<>();
	private ArrayList<WorldEntity> entities = new ArrayList<>();
	
	public enum EntityType {SHIP, AMMO}
	private boolean isLocked = false;
	private World world;
	private Stage stage;
	public WorldEntitiesController(World world, Stage stage){
		this.world = world;
		this.stage = stage;
	}
	
	public <T> ArrayList<T> getEntities(Class<T> type){
		ArrayList<T> matches = new ArrayList<>();
		for(WorldEntity e : entities){
			try{
				T match = type.cast(e);
				matches.add(match);
			}catch(Exception ex){
				
			}
		}
		return matches;
	}
	
	public void createWhenReady(WorldEntity entity){
		entities.add(entity);
		createQueue.add(entity);
		executeQueueElements();
	}
	
	public void destroyWhenReady(WorldEntity entity){
		entities.remove(entity);
		 entity.destroy(world); // test to see if it works
		
	}
	
	private void executeQueueElements(){
		while(!createQueue.isEmpty() && !isLocked){
			stage.addActor(createQueue.pop().create(world));
		}
	}

	public void lockQueue() {
		isLocked = true;
	}
	
	public void unlockQueue(){
		isLocked = false;
		executeQueueElements();
	}
	
	
}
