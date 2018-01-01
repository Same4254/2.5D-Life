package Entity.WorldObjects.Lot.Edit;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import com.Engine.Util.Vectors.Vector2f;
import com.Engine.Util.Vectors.Vector3f;

import Entity.WorldObjects.WorldObject;
import Entity.WorldObjects.Lot.Lot;
import Entity.WorldObjects.Lot.Edit.WorldObjectDragList.AXIS;
import Entity.WorldObjects.Objects.Bed;
import Entity.WorldObjects.Objects.Box;
import Entity.WorldObjects.Objects.Chair;
import Entity.WorldObjects.Objects.ComputerDesk;
import Entity.WorldObjects.Objects.Fridge;
import Entity.WorldObjects.Objects.Stove;
import Entity.WorldObjects.Objects.TV;
import Entity.WorldObjects.Objects.Table;
import Entity.WorldObjects.Objects.Wall;
import Entity.WorldObjects.Objects.Appliances.Appliance;
import Entity.WorldObjects.Objects.Appliances.Computer;
import Main.Handler;
import Utils.Util;

public class WorldObjectEdit extends EditMode {
	private WorldObject heldObject;
	private WorldObjectDragList dragList;
	
	public WorldObjectEdit(Handler handler, Lot lot) {
		super(handler, lot);
		
		dragList = new WorldObjectDragList();
	}
	
	@Override
	public void update(float delta) {
		if(handler.getKeyManager().keyJustPressed(Keyboard.KEY_1)) 
			setHeldObject(new Wall(handler, lot));
		
		else if(handler.getKeyManager().keyJustPressed(Keyboard.KEY_2)) 
			setHeldObject(new Table(handler, lot));
		
		else if(handler.getKeyManager().keyJustPressed(Keyboard.KEY_3)) 
			setHeldObject(new Box(handler, lot));
		
		else if(handler.getKeyManager().keyJustPressed(Keyboard.KEY_4)) 
			setHeldObject(new TV(handler, lot));
		
		else if(handler.getKeyManager().keyJustPressed(Keyboard.KEY_5)) 
			setHeldObject(new Fridge(handler, lot));
		
		else if(handler.getKeyManager().keyJustPressed(Keyboard.KEY_6)) 
			setHeldObject(new Bed(handler, lot));
		
		else if(handler.getKeyManager().keyJustPressed(Keyboard.KEY_7)) 
			setHeldObject(new Chair(handler, lot));
		
		else if(handler.getKeyManager().keyJustPressed(Keyboard.KEY_8)) 
			setHeldObject(new Stove(handler, lot));
		
		else if(handler.getKeyManager().keyJustPressed(Keyboard.KEY_9)) 
			setHeldObject(new ComputerDesk(handler, lot));
		
		else if(handler.getKeyManager().keyJustPressed(Keyboard.KEY_0)) 
			setHeldObject(new Computer(handler, lot));
		
		else if(handler.getKeyManager().keyJustPressed(Keyboard.KEY_DELETE)) 
			clear();
		//********************************
		
		if(handler.getMouseManager().keyJustReleased(0)) { 
			if(heldObject != null) {
				place();
			} else {
				handler.getMouseManager().updatePicker(s -> {
					WorldObject temp = lot.getTiles()[(int) s.getPosition().getX()][(int) s.getPosition().getZ()].findObject(s);
					if(temp != null) {
						if(temp instanceof Appliance) {
							lot.getTiles()[(int) s.getPosition().x][(int) s.getPosition().z].getObject().removeAppliance((Appliance) temp);
							heldObject = temp;
							dragList.setOriginal(temp);
						} else {
							temp.removeFromTile();
							this.heldObject = temp;
							dragList.setOriginal(heldObject);
						}
					}
				}, delta);
			}
		} else if(heldObject != null) { //Mouse moving around
			if(heldObject instanceof Appliance) {
				handler.getMouseManager().updatePicker(s -> {
					if(s != null && !Mouse.isButtonDown(0) && !Mouse.isButtonDown(1)) {//Free Moving
						int x = (int) s.getPosition().x, z = (int) s.getPosition().z;
	
						if(lot.getTiles()[x][z].containsAnything()) {
							Vector3f applianceLocation = lot.getTiles()[x][z].getObject().getApplicationPosition();
							if(applianceLocation != null) {
								heldObject.setPosition3D(applianceLocation);
								heldObject.setAngle(lot.getTiles()[x][z].getObject().getBody().getRenderProperties().getTransform().getRotation().y);
							} else {
								Vector2f location = new Vector2f(s.getPosition().x, s.getPosition().z);
								Vector2f truncated = location.truncate();
								heldObject.setPosition2D(truncated);
							}
						} else {
							Vector2f location = new Vector2f(s.getPosition().x, s.getPosition().z);
							Vector2f truncated = location.truncate();
							heldObject.getBody().setY(0);
							heldObject.setPosition2D(truncated); 
						}
					} else if(s != null && Mouse.isButtonDown(1)) { 
						if(s.getPosition().distance(heldObject.getPosition3D()) > 2) {//Rotation
							float angle = Util.getPosAngle(heldObject.getPosition2D(), Util.to2D(s.getPosition()));
							heldObject.setAngle(Util.roundNearestMultiple(angle, 90));
						}
					}
				}, delta);
			} else {
				handler.getMouseManager().updatePickerForTile(s -> {
					if(s != null && !Mouse.isButtonDown(0) && !Mouse.isButtonDown(1)) {//Free Moving
						int x = (int) s.getPosition().x, z = (int) s.getPosition().z;
	
						if(heldObject instanceof Appliance && lot.getTiles()[x][z].containsAnything()) {
							Vector3f applianceLocation = lot.getTiles()[x][z].getObject().getApplicationPosition();
							if(applianceLocation != null) {
								heldObject.setPosition3D(applianceLocation);
							} else {
								Vector2f location = new Vector2f(s.getPosition().x, s.getPosition().z);
								Vector2f truncated = location.truncate();
								heldObject.setPosition2D(truncated);
							}
						} else {
							Vector2f location = new Vector2f(s.getPosition().x, s.getPosition().z);
							Vector2f truncated = location.truncate();
							heldObject.getBody().setY(0);
							heldObject.setPosition2D(truncated); 
						}
					} else if(s != null && Mouse.isButtonDown(1)) { 
						if(heldObject instanceof Wall) {//Dragging
							if(Math.abs(heldObject.getX() - s.getPosition().x) > Math.abs(heldObject.getZ() - s.getPosition().z))
								dragList.fill(WorldObjectDragList.AXIS.X_AXIS, new Vector2f(s.getPosition().x, s.getPosition().z));
							else
								dragList.fill(WorldObjectDragList.AXIS.Z_AXIS, new Vector2f(s.getPosition().x, s.getPosition().z));
						} else {//Rotating
							if(s.getPosition().distance(heldObject.getPosition3D()) > 2) {
								float angle = Util.getPosAngle(heldObject.getPosition2D(), Util.to2D(s.getPosition()));
								heldObject.setAngle(Util.roundNearestMultiple(angle, 90));
							}
						}
					}
				}, lot, delta);
			}
		} 
	}

	@Override
	public void render() {
		if(heldObject != null) {
			heldObject.render();
			dragList.render();
		}
	}
	
	@Override
	public void clear() {
		if(heldObject != null)
			heldObject.cleanUp();
		heldObject = null;
		dragList.setOriginal(heldObject);
		dragList.clear(AXIS.BOTH);
	}
	
	private void place() {
		if(heldObject != null) {
			if(!heldObject.addToTile(lot.getTiles()[(int) heldObject.getX()][(int) heldObject.getZ()])) {
				heldObject.cleanUp();
			}
			
			for(WorldObject object : dragList.getList(WorldObjectDragList.AXIS.BOTH)) 
 				if(!object.addToTile(lot.getTiles()[(int) object.getX()][(int) object.getZ()]))
 					object.cleanUp();
			
			heldObject = null;
			dragList.setOriginal(heldObject);
			dragList.clear(AXIS.BOTH);
		}
	}
	
	private void setHeldObject(WorldObject worldObject) {
		if(heldObject != null)
			heldObject.cleanUp();
		heldObject = worldObject;
		dragList.setOriginal(heldObject);
	}
}
