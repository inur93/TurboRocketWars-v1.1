package com.vormadal.turborocket;

import java.util.ArrayList;
import java.util.LinkedList;

import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.vormadal.turborocket.models.WorldEntity;

public class WorldEntitiesController {

	private LinkedList<ListItem> queue = new LinkedList<>();
	private ArrayList<WorldEntity> entities = new ArrayList<>();

	private boolean isLocked = false;
	private World world;
	private Stage stage;

	private class ListItem{
		public final WorldEntity entity;
		public final boolean create;
		public ListItem(WorldEntity entity, boolean create){
			this.entity = entity;
			this.create = create;
		}
	}
	public WorldEntitiesController(World world, Stage stage) {
		this.world = world;
		this.stage = stage;
	}

	public <T> ArrayList<T> getEntities(Class<T> type) {
		ArrayList<T> matches = new ArrayList<>();
		for (WorldEntity e : entities) {
			try {
				T match = type.cast(e);
				matches.add(match);
			} catch (Exception ex) {

			}
		}
		return matches;
	}

	public void createWhenReady(WorldEntity entity) {		
		queue.add(new ListItem(entity, true));
	}

	public void destroyWhenReady(WorldEntity entity) {
		queue.add(new ListItem(entity, false));

	}

	/**
	 * It is very important that we destroy bodies before creating. 
	 */
	private void executeQueueElements() {

		while (!queue.isEmpty() && !isLocked) {
			ListItem item = queue.pop();
			System.out.println("item: " + item.entity);
			if(item.create){
				entities.add(item.entity);
				Actor actor = item.entity.create(world);
				if(actor != null)
					stage.addActor(actor);
			}else{
				entities.remove(item.entity);
				item.entity.destroy(world);
			}
			System.out.println("item created/deleted");
		}

	}

	public void lockQueue() {
		isLocked = true;
	}

	public void unlockQueue() {
		isLocked = false;
		executeQueueElements();
	}

}
