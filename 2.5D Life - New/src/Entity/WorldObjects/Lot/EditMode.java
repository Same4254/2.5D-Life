package Entity.WorldObjects.Lot;

import org.lwjgl.input.Keyboard;

import com.Engine.RenderEngine.Util.Camera;
import com.Engine.Util.Vectors.Vector2f;

import Entity.WorldObjects.SubTileObject;
import Entity.WorldObjects.WorldObject;
import Entity.WorldObjects.FullObjects.Table;
import Entity.WorldObjects.MultiTileObjects.Box;
import Entity.WorldObjects.SubObjects.Wall;
import Main.Handler;
import World.Tiles.Tile;

public class EditMode {

	private Handler handler;
	private Lot lot;
	
	private WorldObject heldObject;

	private boolean enabled;
	
	public EditMode(Handler handler, Lot lot) {
		this.handler = handler;
		this.lot = lot;
	}
	
	public void update(float delta) {
		if(enabled) {
			if(handler.getKeyManager().keyJustPressed(Keyboard.KEY_1)) {
				heldObject = new Wall(handler);
			}
			
			if(handler.getKeyManager().keyJustPressed(Keyboard.KEY_2)) {
				heldObject = new Table(handler);
			}
			
			if(handler.getKeyManager().keyJustPressed(Keyboard.KEY_3)) {
				heldObject = new Box(handler);
			}
			
			if(handler.getKeyManager().keyJustPressed(Keyboard.KEY_DELETE)) {
				heldObject = null;
			}
			
			if(handler.getKeyManager().keyJustPressed(Keyboard.KEY_RETURN)) {
				heldObject.addToTile(lot.getTiles()[(int) heldObject.getBody().getX()][(int) heldObject.getBody().getZ()]);
				heldObject = null;
			}
			
			if(handler.getMouseManager().keyJustPressed(0)) {
				handler.getMouseManager().updatePicker(s -> {
					if(heldObject != null) {
						heldObject.addToTile(lot.getTiles()[(int) heldObject.getBody().getX()][(int) heldObject.getBody().getZ()]);
//						 lot.getTiles()[(int) heldObject.getBody().getX()][(int) heldObject.getBody().getZ()].add(heldObject);
						 heldObject = null;
						 
//						 System.out.println("--------------------");
						 
//						 for(int x = 0; x < (int) lot.getTiles().length; x++) {
//						 for(int y = 0; y < (int) lot.getTiles()[x].length; y++) {
//							 System.out.print((lot.getTiles()[y][x].getFullObject() == null ? 0 : 1) + "\t");
//						 } System.out.println(); }
//						 System.out.println("--------------------");
					} else {
						heldObject = lot.getTiles()[(int) s.getPosition().getX()][(int) s.getPosition().getZ()].findObject(s);
					}
				}, delta);
			} else if(heldObject != null) {
				handler.getMouseManager().updatePickerForTile(s -> {
					if(s != null) {
						Vector2f location = new Vector2f(s.getPosition().x, s.getPosition().z);
						Vector2f truncated = location.truncate();
						heldObject.getBody().setPosition2D(truncated);
					}
				}, lot, delta);
				
				if(heldObject instanceof SubTileObject) {
					SubTileObject temp = (SubTileObject) heldObject;
					
					if(handler.getKeyManager().keyJustPressed(Keyboard.KEY_I)) {
						if(temp.getSubY() - 1 >= 0)
							temp.setSubY(temp.getSubY() - 1);
					}
					
					if(handler.getKeyManager().keyJustPressed(Keyboard.KEY_K)) {
						if(temp.getSubY() + 1 + temp.getSubHeight() <= Tile.TILE_RESOLUTION)
							temp.setSubY(temp.getSubY() + 1);
					}
					
					if(handler.getKeyManager().keyJustPressed(Keyboard.KEY_J)) {
						if(temp.getSubX() - 1 >= 0)
							temp.setSubX(temp.getSubX() - 1);
					}
					
					if(handler.getKeyManager().keyJustPressed(Keyboard.KEY_L)) {
						if(temp.getSubX() + 1 + temp.getSubWidth() <= Tile.TILE_RESOLUTION)
							temp.setSubX(temp.getSubX() + 1);
					}
				}
				
				if(handler.getKeyManager().keyJustPressed(Keyboard.KEY_PERIOD)) {
					heldObject.rotateRight();
				}
				
				if(handler.getKeyManager().keyJustPressed(Keyboard.KEY_COMMA)) {
					heldObject.rotateLeft();
				}
			} 
		}
	}
	
	public void render(Camera camera) {
		if(enabled && heldObject != null)
			heldObject.render(camera);
	}
	
	public boolean isEnabled() { return enabled; }
	public void setEnabled(boolean enabled) { this.enabled = enabled; } 
}
