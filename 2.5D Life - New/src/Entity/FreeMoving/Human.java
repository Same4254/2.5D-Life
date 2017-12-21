package Entity.FreeMoving;

import com.Engine.RenderEngine.Textures.Texture2D;
import com.Engine.Util.Vectors.Vector2f;

import Entity.WrapperBodies.WrapperModel;
import Main.Handler;

public class Human extends Entity {
	private String name;
	
	public Human(Handler handler, WrapperModel wrapperModel, Texture2D texture, String name) {
		super(handler, wrapperModel, texture);
		this.name = name;
		
		movementSpeed = new Vector2f(8);
	}
	
	public void update(float delta) {
		super.update(delta);
		
//		if(actionQueue.getActions().isEmpty()) {
//			Need lowest = needManager.getLowest();
//			if(lowest.getValue() < 70) {
//				Lot lot = handler.getWorld().getLot(body.getPosition2D());
//				
//				ArrayList<WorldObject> objects = new ArrayList<>();
//				for(Tile[] tiles : lot.getTiles())
//					for(Tile t : tiles) {
//						if(t.getObject() != null && t.getObject().getNeeds().containsKey(lowest.getClass()) && !objects.contains(t.getObject())) {
//							objects.add(t.getObject());
//						}
//					}
//				
//				if(objects.size() > 0) {
//					if(objects.size() > 1) {
//						Collections.sort(objects, new Comparator<WorldObject>() {
//							@Override
//							public int compare(WorldObject o1, WorldObject o2) {
//								if(o1.getSkills().get(lowest.getClass()) > o2.getSkills().get(lowest.getClass()))
//									return -1;
//								return 1;
//							}
//						});
//					}
//					
//					System.out.println("ADD");
//					System.out.println(objects.get(0).getBody().getPosition2D());
//					actionQueue.add(new GoToAction(lot, this, objects.get(0).getBody().getPosition2D()));
//				}
//			}
//		}
	}
	
	public String toString() { return name; }
}
