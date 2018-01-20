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
import Entity.WorldObjects.Objects.Stairs;
import Entity.WorldObjects.Objects.Stove;
import Entity.WorldObjects.Objects.TV;
import Entity.WorldObjects.Objects.Table;
import Entity.WorldObjects.Objects.Wall;
import Entity.WorldObjects.Objects.Appliances.Appliance;
import Entity.WorldObjects.Objects.Appliances.Computer;
import Input.MousePicker;
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
		
		else if(handler.getKeyManager().keyJustPressed(Keyboard.KEY_MINUS)) 
			setHeldObject(new Stairs(handler, lot));
		
		else if(handler.getKeyManager().keyJustPressed(Keyboard.KEY_DELETE)) 
			clear();
		//********************************
		
		if(handler.getMouseManager().keyJustReleased(0)) { 
			if(heldObject != null) {
				place();
			} else {
				handler.getMouseManager().updatePicker(s -> {
//					WorldObject temp = handler.getWorld().getTestLot().getTile(Util.toGrid((Util.to2D(s.getPosition())))).getObject();
					WorldObject temp = lot.getFloor(floorLevel).getTile(Util.to2D(s.getPosition())).getObject();
					
					if(temp != null) {
						temp.removeFromTile();
						this.heldObject = temp;
						dragList.setOriginal(heldObject);
					}
				}, delta);
			}
		} else if(heldObject != null) { //Mouse moving around
				Vector3f s = MousePicker.calculateHitPosition(Lot.floorHeight * floorLevel);
				s = s.capMax(lot.getPosition().x + lot.getWidth() - heldObject.getWidth(), s.y, lot.getPosition().z + lot.getHeight() - heldObject.getHeight());
				s = s.capMin(lot.getPosition().x, s.y, lot.getPosition().z);
				
//				System.out.println(Util.toGrid((Util.to2D(s))));
				
				if(!Mouse.isButtonDown(0) && !Mouse.isButtonDown(1)) {//Free Moving
					if(heldObject != null) {
						if(heldObject instanceof Appliance && lot.getFloor(floorLevel).getTile(Util.to2D(s)).getObject() != null) {
							WorldObject host = lot.getFloor(floorLevel).getTile(Util.to2D(s)).getObject();
							Vector3f pos = host.getApplicationPosition();
							System.out.println("Host: " + host.getPosition3D());
							System.out.println("Pos: " + pos);
							if(pos != null) 
								heldObject.setPosition3D(pos.subtract(heldObject.getWidth() / 2, 0, heldObject.getHeight() / 2));
						} else 
							heldObject.setPosition3D(s); 
					}
				} if(heldObject instanceof Wall && Mouse.isButtonDown(0)) {
					if(Math.abs(heldObject.getX() - s.x) > Math.abs(heldObject.getZ() - s.z)) {
						if(s.getX() + heldObject.getWidth() < lot.getPosition().x + lot.getWidth() && s.getX() - heldObject.getWidth() > lot.getPosition().x)
							dragList.fill(WorldObjectDragList.AXIS.X_AXIS, new Vector2f(s.x, s.z));
					} else if(s.getZ() + heldObject.getHeight() < lot.getPosition().z + lot.getHeight() && s.getZ() - heldObject.getHeight() > lot.getPosition().z)
							dragList.fill(WorldObjectDragList.AXIS.Z_AXIS, new Vector2f(s.x, s.z));
				} else if(s != null && Mouse.isButtonDown(1)) {  
					if(Util.to2D(s).distance(heldObject.getPosition2D()) > 2) {//Rotating
						float angle = Util.getPosAngle(heldObject.getPosition2D(), Util.to2D(s));
						if(Util.withinRange(angle, Util.roundNearestMultiple(angle, 90), 30))
							heldObject.setAngle(Util.roundNearestMultiple(angle, 90)); 
				}
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
			if(!handler.getWorld().place(heldObject)) 
				heldObject.cleanUp();
			
			for(WorldObject object : dragList.getList(WorldObjectDragList.AXIS.BOTH)) 
 				if(!handler.getWorld().place(object)) {
 					object.cleanUp();
 					System.err.println(heldObject.getPosition2D());
 					System.err.println(object.getPosition2D());
 				}
			
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
