package Entity.WorldObjects.Lot;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import com.Engine.Util.Vectors.Vector2f;

import Entity.WorldObjects.WorldObject;
import Entity.WorldObjects.Lot.DragList.AXIS;
import Entity.WorldObjects.Objects.Bed;
import Entity.WorldObjects.Objects.Box;
import Entity.WorldObjects.Objects.Chair;
import Entity.WorldObjects.Objects.Fridge;
import Entity.WorldObjects.Objects.TV;
import Entity.WorldObjects.Objects.Table;
import Entity.WorldObjects.Objects.Wall;
import Main.Handler;
import Utils.Util;

public class EditMode {
	private Handler handler;
	private Lot lot;
	
	private WorldObject heldObject;
	private DragList dragList;
	
	private boolean enabled;
	
	public EditMode(Handler handler, Lot lot) {
		this.handler = handler;
		this.lot = lot;
		
		dragList = new DragList();
	}
	
	public void update(float delta) { 
		if(enabled) {
			if(handler.getKeyManager().keyJustPressed(Keyboard.KEY_1)) {
				if(heldObject != null)
					heldObject.cleanUp();
				heldObject = new Wall(handler);
				dragList.setOriginal(heldObject);
			}
			
			if(handler.getKeyManager().keyJustPressed(Keyboard.KEY_2)) {
				if(heldObject != null)
					heldObject.cleanUp();
				heldObject = new Table(handler);
				dragList.setOriginal(heldObject);
			}
			
			if(handler.getKeyManager().keyJustPressed(Keyboard.KEY_3)) {
				if(heldObject != null)
					heldObject.cleanUp();
				heldObject = new Box(handler);
				dragList.setOriginal(heldObject);
			}
			
			if(handler.getKeyManager().keyJustPressed(Keyboard.KEY_4)) {
				if(heldObject != null)
					heldObject.cleanUp();
				heldObject = new TV(handler);
				dragList.setOriginal(heldObject);
			}
			
			if(handler.getKeyManager().keyJustPressed(Keyboard.KEY_5)) {
				if(heldObject != null)
					heldObject.cleanUp();
				heldObject = new Fridge(handler);
				dragList.setOriginal(heldObject);
			}
			
			if(handler.getKeyManager().keyJustPressed(Keyboard.KEY_6)) {
				if(heldObject != null)
					heldObject.cleanUp();
				heldObject = new Bed(handler);
				dragList.setOriginal(heldObject);
			}
			
			if(handler.getKeyManager().keyJustPressed(Keyboard.KEY_7)) {
				if(heldObject != null)
					heldObject.cleanUp();
				heldObject = new Chair(handler);
				dragList.setOriginal(heldObject);
			}
			
			
			if(handler.getKeyManager().keyJustPressed(Keyboard.KEY_DELETE)) {
				if(heldObject != null)
					heldObject.cleanUp();
				heldObject = null;
				dragList.setOriginal(heldObject);
				dragList.clear(AXIS.BOTH);
			}
			
			if(handler.getMouseManager().keyJustReleased(0)) { 
				if(heldObject != null) {
					place();
				} else {
					handler.getMouseManager().updatePicker(s -> {
						WorldObject temp = lot.getTiles()[(int) s.getPosition().getX()][(int) s.getPosition().getZ()].getObject(); 
						if(temp != null) {
							temp.removeFromTile();
							heldObject = temp;
							dragList.setOriginal(heldObject);
						}
					}, delta);
				}
			} else if(heldObject != null) { //Mouse moving around
				handler.getMouseManager().updatePickerForTile(s -> {
					if(s != null && !Mouse.isButtonDown(0) && !Mouse.isButtonDown(1)) {//Free Moving
						Vector2f location = new Vector2f(s.getPosition().x, s.getPosition().z);
						Vector2f truncated = location.truncate();
//						System.out.println(heldObject.getBody().getHitBox());
						heldObject.setPosition2D(truncated);
					} else if(s != null && Mouse.isButtonDown(1)) { 
						if(heldObject instanceof Wall) {//Dragging
							if(Math.abs(heldObject.getX() - s.getPosition().x) > Math.abs(heldObject.getZ() - s.getPosition().z))
								dragList.fill(DragList.AXIS.X_AXIS, new Vector2f(s.getPosition().x, s.getPosition().z));
							else
								dragList.fill(DragList.AXIS.Z_AXIS, new Vector2f(s.getPosition().x, s.getPosition().z));
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
	
	public void render() {
		if(enabled && heldObject != null) {
			heldObject.render();
			dragList.render();
		}
	}
	
	private void place() {
		if(heldObject != null) {
//			System.out.println("----------");
//			System.out.println("X: " + (int) heldObject.getX() + " Z: " + (int) heldObject.getZ() + " Width: " + heldObject.getWidth() + " Height: " + heldObject.getHeight());
			heldObject.addToTile(lot.getTiles()[(int) heldObject.getX()][(int) heldObject.getZ()]);
//			System.out.println(heldObject.getBody().getStaticBody());
			
			for(WorldObject object : dragList.getList(DragList.AXIS.BOTH)) {
 				object.addToTile(lot.getTiles()[(int) object.getX()][(int) object.getZ()]);
// 				System.out.println(object.getBody().getStaticBody().getBodies());
			}
			
			heldObject = null;
			dragList.setOriginal(heldObject);
			dragList.clear(AXIS.BOTH);
		}
	}
	
	public boolean isEnabled() { return enabled; }
	public void setEnabled(boolean enabled) { this.enabled = enabled; } 
}
