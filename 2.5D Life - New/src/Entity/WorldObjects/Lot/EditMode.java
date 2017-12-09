package Entity.WorldObjects.Lot;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import com.Engine.Util.Vectors.Vector2f;

import Entity.WorldObjects.WorldObject;
import Entity.WorldObjects.FullObjects.Table;
import Entity.WorldObjects.Lot.DragList.AXIS;
import Entity.WorldObjects.MultiTileObjects.Box;
import Entity.WorldObjects.SubObjects.Wall;
import Main.Handler;

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
			
			if(handler.getKeyManager().keyJustPressed(Keyboard.KEY_DELETE)) {
				if(heldObject != null)
					heldObject.cleanUp();
				heldObject = null;
				dragList.setOriginal(heldObject);
				dragList.clear(AXIS.BOTH);
			}
			
			if(handler.getMouseManager().keyJustReleased(0)) { //In the physics engine there's a sort that I commented out <- fix that (causes tiles to be clicked rather than objects)
				handler.getMouseManager().updatePicker(s -> {
					if(heldObject != null) {
						place();
					} else {
						WorldObject temp = lot.getTiles()[(int) s.getPosition().getX()][(int) s.getPosition().getZ()].findObject(s); 
						if(temp != null) {
							temp.removeFromTile();
							heldObject = temp;
							dragList.setOriginal(heldObject);
						}
					}
				}, delta);
			} else if(heldObject != null) { //Mouse moving around
				handler.getMouseManager().updatePickerForTile(s -> {
					if(s != null && !Mouse.isButtonDown(0)) {//Free Moving
						Vector2f location = new Vector2f(s.getPosition().x, s.getPosition().z);
						Vector2f truncated = location.truncate();
						heldObject.getBody().setPosition2D(truncated);
					} else if(s != null && Mouse.isButtonDown(0)) { //Dragging
						if(handler.getKeyManager().isPressed(Keyboard.KEY_RSHIFT)) {
							if(Math.abs(heldObject.getX() - s.getPosition().x) > Math.abs(heldObject.getZ() - s.getPosition().z))
								dragList.fill(DragList.AXIS.X_AXIS, new Vector2f(s.getPosition().x, s.getPosition().z));
							else
								dragList.fill(DragList.AXIS.Z_AXIS, new Vector2f(s.getPosition().x, s.getPosition().z));
						} else 
							dragList.fill(DragList.AXIS.BOTH, new Vector2f(s.getPosition().x, s.getPosition().z));
					}
				}, lot, delta);
				
				if(handler.getKeyManager().keyJustPressed(Keyboard.KEY_PERIOD)) {
					heldObject.rotateRight();
				}
				
				if(handler.getKeyManager().keyJustPressed(Keyboard.KEY_COMMA)) {
					heldObject.rotateLeft();
				}
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
			heldObject.addToTile(lot.getTiles()[(int) heldObject.getBody().getX()][(int) heldObject.getBody().getZ()]);
			
			for(WorldObject object : dragList.getList(DragList.AXIS.BOTH)) {
 				object.addToTile(lot.getTiles()[(int) object.getBody().getX()][(int) object.getBody().getZ()]);
			}
			
			heldObject = null;
			dragList.setOriginal(heldObject);
			dragList.clear(AXIS.BOTH);
		}
	}
	
	public boolean isEnabled() { return enabled; }
	public void setEnabled(boolean enabled) { this.enabled = enabled; } 
}
