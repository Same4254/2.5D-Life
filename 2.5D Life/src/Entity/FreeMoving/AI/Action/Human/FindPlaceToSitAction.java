package Entity.FreeMoving.AI.Action.Human;

import java.util.ArrayList;
import java.util.Iterator;

import com.Engine.Util.Vectors.Vector2f;

import Entity.FreeMoving.Entity;
import Entity.FreeMoving.AI.Action.MultiAction;
import Entity.WorldObjects.WorldObject;
import Entity.WorldObjects.Lot.Floor;
import Entity.WorldObjects.Lot.Lot;
import Entity.WorldObjects.Objects.Chair;
import Main.Handler;

public class FindPlaceToSitAction extends MultiAction {
	private Entity entity;
	private ArrayList<Vector2f> points;
	
	public FindPlaceToSitAction(Handler handler, Entity entity) {
		super(handler);
		this.entity = entity;
	}
	
	public FindPlaceToSitAction(Handler handler, Entity entity, ArrayList<Vector2f> points) {
		this(handler, entity);
		this.points = points;
	}
	
	@Override
	public void start() {
		super.start();
		
		Lot lot = handler.getWorld().getLot(entity.getPosition2D());
		Floor floor = lot.getFloor(entity.getPosition3D());
		
		ArrayList<WorldObject> chairs = new ArrayList<>();
		for(int x = 0; x < lot.getWidth(); x++) {
			for(int y = 0; y < lot.getHeight(); y++) {
				WorldObject object = floor.getTiles()[x][y].getObject();
				if(object != null && object instanceof Chair) {
					chairs.add(object);
				}
			}
		}
		
		chairs.sort((o1, o2) -> {
			if(o1.getPosition2D().distance(entity.getPosition2D()) < o2.getPosition2D().distance(entity.getPosition2D()))
				return -1;
			return 1;
		});
		
		if(!chairs.isEmpty()) {
			if(points != null) {
				Iterator<WorldObject> iterator = chairs.iterator();
				WorldObject worldObject = null;
				
				while(iterator.hasNext()) {
					WorldObject temp = iterator.next();
					if(points.contains(temp.getPosition2D())) {
						worldObject = temp;
						break;
					}
				}
				
				if(worldObject != null)
					subActions.add(new MoveToAction(handler, worldObject, entity));
				else { 
					complete = true;
					return;
				}
			} else 
				subActions.add(new MoveToAction(handler, chairs.get(0), entity));
		} else 
			complete = true;
	}
}
