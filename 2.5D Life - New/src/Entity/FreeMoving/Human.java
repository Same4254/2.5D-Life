package Entity.FreeMoving;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import com.Engine.RenderEngine.Textures.Texture2D;
import com.Engine.Util.Vectors.Vector2f;

import Entity.FreeMoving.AI.Action.Human.EatHeldItemAction;
import Entity.FreeMoving.AI.Action.Human.FindPlaceToSitAction;
import Entity.FreeMoving.AI.Action.Human.SearchAction;
import Entity.FreeMoving.AI.Living.Needs.Need;
import Entity.WorldObjects.WorldObject;
import Entity.WorldObjects.Lot.Lot;
import Entity.WrapperBodies.WrapperModel;
import Main.Handler;
import World.Tiles.Tile;

public class Human extends Entity {
	private String name;
	
	public Human(Handler handler, WrapperModel wrapperModel, Texture2D texture, String name) {
		super(handler, wrapperModel, texture);
		this.name = name;
		
		movementSpeed = new Vector2f(6);
	}
	
	public void update(float delta) {
		super.update(delta);
		
		if(actionQueue.getActions().isEmpty()) {
			Need lowest = needManager.getLowest();
			if(lowest.getValue() < 85) {
				Lot lot = handler.getWorld().getLot(body.getPosition2D());
				
				ArrayList<WorldObject> objects = new ArrayList<>();
				for(Tile[] tiles : lot.getTiles())
					for(Tile t : tiles) {
						if(t.getObject() != null && t.getObject().getNeeds().containsKey(lowest.asEnum()) && !objects.contains(t.getObject())) {
							objects.add(t.getObject());
						}
					}
				
				if(objects.size() > 0) {
					if(objects.size() > 1) {
						Collections.sort(objects, new Comparator<WorldObject>() {
							@Override
							public int compare(WorldObject o1, WorldObject o2) {
								if(o1.getNeeds().get(lowest.asEnum()) > o2.getNeeds().get(lowest.asEnum()))
									return -1;
								return 1;
							}
						});
					}

					addAction(objects.get(0).getAction(this, lowest.asEnum()));
					
//					if(lowest.asEnum() == Living.Hunger) {
//						if(objects.get(0).searchForItem(this, Living.Hunger) == null)
//							
//						
//						addAction(new SearchAction(handler, this, objects.get(0), Living.Hunger));
//						addAction(new FindPlaceToSitAction(handler, this));
//						addAction(new EatHeldItemAction(handler, this));
//					} else if(lowest.asEnum() == Living.Entertainment) {
//						addAction(objects.get(0).getAction(this, Living.Entertainment));
//					}
				}
			}
		}
	}
	
	public String toString() { return name; }
}
