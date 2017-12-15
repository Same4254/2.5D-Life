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
	
	private Comparator<WorldObject> zAxisComparator = (o1, o2) -> {
		if(o1.getZ() < o2.getZ())
			return -1;
		return 1;
	};
	
	private WorldObject original;
//	private ArrayList<WorldObject> dragList;
	private ArrayList<WorldObject> xAxis;
	private ArrayList<WorldObject> zAxis;
	
	public DragList() {
		xAxis = new ArrayList<>();
		zAxis = new ArrayList<>();
	}
	
	public void fill(AXIS axisToFill, Vector2f position) {
		if(original != null) {
			if(original.getBody().getPosition2D().equals(position)) {
				clear(AXIS.X_AXIS);
				clear(AXIS.Z_AXIS);
				return;
			}
			
			if(axisToFill == AXIS.X_AXIS || axisToFill == AXIS.BOTH) {// Least -> Greatest = Remove-- Add__
				if(axisToFill != AXIS.BOTH)
					clear(AXIS.Z_AXIS);
				
				xAxis.sort(xAxisComparator);
				
				for(int i = 0; i < Math.abs(position.x - original.getX()) / original.getWidth(); i++) {
					if(i >= xAxis.size()) {
						WorldObject temp = add(AXIS.X_AXIS, i);
						if(position.x < original.getX()) 
							temp.getBody().setPosition2D(original.getX() - (original.getWidth() * (i + 1)), original.getZ());
						else
							temp.getBody().setPosition2D(original.getX() + (original.getWidth() * (i + 1)), original.getZ());
						continue;
					}
					
					// X-------O
					if(position.x < original.getX()) {
						WorldObject object = xAxis.get(i);
						
						if(object.getX() >= original.getX() || object.getX() < position.x) {
							remove(AXIS.X_AXIS, object);
							i--;
							continue;
						}
						
						float predictedLocation = original.getX() - (original.getWidth() * (xAxis.size() - i));
						
						if(object.getX() != predictedLocation) {
							WorldObject add = add(AXIS.X_AXIS, i);
							add.getBody().setPosition2D(new Vector2f(predictedLocation, original.getZ()));
						}
					} else {// O-------X
						WorldObject object = xAxis.get(xAxis.size() - i - 1);
						
						if(object.getX() <= original.getX() || object.getX() > position.x) {
							remove(AXIS.X_AXIS, object);
							i--;
							continue;
						}
						
						float predictedLocation = original.getX() + (original.getWidth() * (xAxis.size() - i));
						
						if(object.getX() != predictedLocation) {
							WorldObject add = add(AXIS.X_AXIS, i);
							add.getBody().setPosition2D(new Vector2f(predictedLocation, original.getZ()));
						}
					}
				}
			}
			
			if(axisToFill == AXIS.Z_AXIS || axisToFill == AXIS.BOTH) {// Least -> Greatest = Remove-- Add__
				if(axisToFill != AXIS.BOTH)
					clear(AXIS.X_AXIS);
				
				zAxis.sort(zAxisComparator);
				
				for(int i = 0; i < Math.abs(position.y - original.getZ()) / original.getHeight(); i++) {
					if(i >= zAxis.size()) {
						WorldObject temp = add(AXIS.Z_AXIS, i);
						if(position.y < original.getZ()) 
							temp.getBody().setPosition2D(original.getX(), original.getZ() - (original.getHeight() * (i + 1)));
						else
							temp.getBody().setPosition2D(original.getX(), original.getZ() + (original.getHeight() * (i + 1)));
						continue;
					}
					
					// X-------O
					if(position.y > original.getZ()) {
						WorldObject object = zAxis.get(i);
						
						if(object.getZ() >= original.getZ() || object.getZ() < position.y) {
							remove(AXIS.Z_AXIS, object);
							i--;
							continue;
						}
						
						float predictedLocation = original.getZ() - (original.getHeight() * (zAxis.size() - i));
						
						if(object.getX() != predictedLocation) {
							WorldObject add = add(AXIS.Z_AXIS, i);
							add.getBody().setPosition2D(new Vector2f(original.getX(), predictedLocation));
						}
					} else {// O-------X
						WorldObject object = zAxis.get(zAxis.size() - i - 1);
						
						if(object.getZ() <= original.getZ() || object.getZ() > position.y) {
							remove(AXIS.Z_AXIS, object);
							i--;
							continue;
						}
						
						float predictedLocation = original.getZ() + (original.getHeight() * (zAxis.size() - i));
						
						if(object.getX() != predictedLocation) {
							WorldObject add = add(AXIS.Z_AXIS, i);
							add.getBody().setPosition2D(new Vector2f(original.getX(), predictedLocation));
						}
					}
				}
			}
		}
	}
	
	public void render() {
		for(WorldObject object : getList(AXIS.BOTH))
			object.render();
	}
	
	public WorldObject remove(AXIS axis, int index) {
		WorldObject temp = null;
		
		temp = getList(axis).get(index);
		
		remove(axis, temp);
		return temp;
	}
	
	public void remove(AXIS axis, WorldObject object) {
		object.cleanUp();
		getList(axis).remove(object);
	}
	
	public WorldObject add(AXIS axis, int index) {
		WorldObject temp = original.clone();
		getList(axis).add(index, temp);
		return temp;
	}
	
	public void clear(AXIS axis) {
		if(axis == AXIS.X_AXIS || axis == AXIS.BOTH) {
			xAxis.clear();
		}
		
		if(axis == AXIS.Z_AXIS || axis == AXIS.BOTH) {
			zAxis.clear();
		}
		
//		if(axis == AXIS.X_AXIS || axis == AXIS.BOTH) {
//			if(!xAxis.isEmpty()) {
//				for(WorldObject object : xAxis)
//					object.cleanUp();
//				xAxis.clear();
//			}
//		}
//		
//		if(axis == AXIS.Z_AXIS || axis == AXIS.BOTH) {
//			if(!zAxis.isEmpty()) {
//				for(WorldObject object : zAxis)
//					object.cleanUp();
//				zAxis.clear();
//			}
//		}
	}
	
	public void setOriginal(WorldObject original) { this.original = original; }
	
	public ArrayList<WorldObject> getList(AXIS axis) {
		if(axis == AXIS.X_AXIS)
			return xAxis;
		else if(axis == AXIS.Z_AXIS)
			return zAxis;
		else {
			ArrayList<WorldObject> temp = new ArrayList<>();
			
			temp.addAll(xAxis);
			temp.addAll(zAxis);
			return temp;
		}
	}
}
