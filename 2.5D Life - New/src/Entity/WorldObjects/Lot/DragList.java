package Entity.WorldObjects.Lot;

import java.util.ArrayList;
import java.util.Comparator;

import com.Engine.Util.Vectors.Vector2f;

import Entity.WorldObjects.WorldObject;

public class DragList {
	public static enum AXIS {
		X_AXIS, Z_AXIS, BOTH
	}
	
	private Comparator<WorldObject> xAxisComparator = (o1, o2) -> {
		if(o1.getX() < o2.getX())
			return -1;
		return 1;
	};
	
	private Comparator<WorldObject> yAxisComparator = (o1, o2) -> {
		if(o1.getZ() < o2.getZ())
			return -1;
		return 1;
	};
	
	private WorldObject original;
	private ArrayList<WorldObject> dragList;
	
	public DragList() {
		dragList = new ArrayList<>();
	}
	
	public void fill(AXIS axisToFill, Vector2f position) {
		if(original != null) {
			if(axisToFill == AXIS.X_AXIS || axisToFill == AXIS.BOTH) {// Least -> Greatest = Remove-- Add__
				dragList.sort(xAxisComparator);
				
				for(int i = 0; i < Math.abs(position.x - original.getX()); i++) {
					if(i >= dragList.size()) {
						WorldObject temp = add(0);
						if(position.x < original.getX()) 
							temp.getBody().setPosition2D(original.getX() - original.getWidth(), original.getZ());
						else
							temp.getBody().setPosition2D(original.getX() + original.getWidth(), original.getZ());
						continue;
					}
					
					// X-------O
					if(position.x < original.getX()) {
						WorldObject object = dragList.get(i);
						
						if(axisToFill != AXIS.BOTH && dragList.get(i).getZ() != original.getZ()) {
							remove(object);
							i--;
							continue;
						}
						
						if(object.getX() >= original.getX() || object.getX() < position.x) {
							remove(object);
							i--;
							continue;
						}
						
						float predictedLocation = original.getX() - (original.getWidth() * (i + 1));
						
						if(object.getX() != predictedLocation) {
							WorldObject add = add(i);
							add.getBody().setPosition2D(new Vector2f(predictedLocation, original.getZ()));
						}
					} else {// O-------X
						WorldObject object = dragList.get(dragList.size() - i - 1);
						
						if(axisToFill != AXIS.BOTH && dragList.get(i).getZ() != original.getZ()) {
							remove(object);
							i--;
							continue;
						}
						
						if(object.getX() <= original.getX() || object.getX() > position.x) {
							remove(object);
							i--;
							continue;
						}
						
						float predictedLocation = original.getX() + (original.getWidth() * (i + 1));
						
						if(object.getX() != predictedLocation) {
							WorldObject add = add(i);
							add.getBody().setPosition2D(new Vector2f(predictedLocation, original.getZ()));
						}
					}
				}
			}
			
			if(axisToFill == AXIS.Z_AXIS || axisToFill == AXIS.BOTH) {
				dragList.sort(yAxisComparator);
				
				for(int i = 0; i < Math.abs(position.y - original.getZ()); i++) {
					if(i >= dragList.size()) {
						WorldObject temp = add(0);
						if(position.y < original.getZ()) 
							temp.getBody().setPosition2D(original.getX(), original.getZ() - original.getHeight());
						else
							temp.getBody().setPosition2D(original.getX(), original.getZ() + original.getHeight());
						continue;
					}
					
					// X-------O
					if(position.y < original.getZ()) {
						WorldObject object = dragList.get(i);
						
						if(axisToFill != AXIS.BOTH && dragList.get(i).getX() != original.getX()) {
							remove(object);
							i--;
							continue;
						}
						
						if(object.getZ() >= original.getZ() || object.getZ() < position.y) {
							remove(object);
							i--;
							continue;
						}
						
						float predictedLocation = original.getZ() - (original.getHeight() * (i + 1));
						
						if(object.getZ() != predictedLocation) {
							WorldObject add = add(i);
							add.getBody().setPosition2D(new Vector2f(original.getX(), predictedLocation));
						}
					} else {// O-------X
						WorldObject object = dragList.get(dragList.size() - i - 1);
						
						if(axisToFill != AXIS.BOTH && dragList.get(i).getX() != original.getX()) {
							remove(object);
							i--;
							continue;
						}
						
						if(object.getZ() <= original.getZ() || object.getZ() > position.y) {
							remove(object);
							i--;
							continue;
						}
						
						float predictedLocation = original.getZ() + (original.getHeight() * (i + 1));
						
						if(object.getZ() != predictedLocation) {
							WorldObject add = add(i);
							add.getBody().setPosition2D(new Vector2f(original.getX(), predictedLocation));
						}
					}
				}
			}
		}
	}
	
	public void render() {
		for(WorldObject object : dragList)
			object.render();
	}
	
	public WorldObject remove(int index) {
		WorldObject temp = dragList.get(index);
		remove(temp);
		return temp;
	}
	
	public void remove(WorldObject object) {
		object.cleanUp();
		dragList.remove(object);
	}
	
	public WorldObject add(int index) {
		WorldObject temp = original.clone();
		dragList.add(index, temp);
		return temp;
	}
	
	public void clear() {
		for(WorldObject object : dragList)
			object.cleanUp();
		dragList.clear();
	}
	
	public void setOriginal(WorldObject original) { this.original = original; }
	public ArrayList<WorldObject> getList() { return dragList; }
}
