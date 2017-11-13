package Entity.WorldObjects.Lot;

import java.util.ArrayList;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

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
	
	private ArrayList<WorldObject> heldObjects;
	private int x, y;
	
	private boolean enabled;
	
	public EditMode(Handler handler, Lot lot) {
		this.handler = handler;
		this.lot = lot;
		
		heldObjects = new ArrayList<>();
	}
	
	public void update(float delta) {
		if(enabled) {
			if(handler.getKeyManager().keyJustPressed(Keyboard.KEY_1)) {
				for(WorldObject heldObject : heldObjects) 
					heldObject.cleanUp();
				
				heldObjects.clear();
				heldObjects.add(new Wall(handler));
			}
			
			if(handler.getKeyManager().keyJustPressed(Keyboard.KEY_2)) {
				for(WorldObject heldObject : heldObjects) 
					heldObject.cleanUp();
				
				heldObjects.clear();
				heldObjects.add(new Table(handler));
			}
			
			if(handler.getKeyManager().keyJustPressed(Keyboard.KEY_3)) {
				for(WorldObject heldObject : heldObjects) 
					heldObject.cleanUp();
				
				heldObjects.clear();
				heldObjects.add(new Box(handler));
			}
			
			if(handler.getKeyManager().keyJustPressed(Keyboard.KEY_DELETE)) {
				for(WorldObject heldObject : heldObjects) 
					heldObject.cleanUp();
				
				heldObjects.clear();
			}
			
			if(handler.getKeyManager().keyJustPressed(Keyboard.KEY_RETURN)) {
				if(!heldObjects.isEmpty()) {
					for(WorldObject heldObject : heldObjects) {
						heldObject.addToTile(lot.getTiles()[(int) heldObject.getBody().getX()][(int) heldObject.getBody().getZ()]);
					}
					
					heldObjects.clear();
				}
			}

			if(handler.getMouseManager().keyJustPressed(0)) {
				handler.getMouseManager().updatePicker(s -> {
					x = (int) s.getPosition().x;
					y = (int) s.getPosition().z;
				}, delta);
			}

			if(!heldObjects.isEmpty() && Mouse.isButtonDown(0) && (Keyboard.isKeyDown(Keyboard.KEY_Z) || Keyboard.isKeyDown(Keyboard.KEY_X))) {
				WorldObject original = heldObjects.get(0);
				
				if(Keyboard.isKeyDown(Keyboard.KEY_Z)) {
					handler.getMouseManager().updatePickerForTile(s -> {
						ArrayList<WorldObject> toAdd = new ArrayList<>();
						int tempX = (int) s.getPosition().x;
						
						if(tempX > x) {
							for(int i = heldObjects.size() - 1; i >= 0; i--) {
								WorldObject o = heldObjects.get(i);
								if(o.getBody().getPosition2D().x < x || o.getBody().getPosition2D().x > tempX) { o.cleanUp(); heldObjects.remove(o); }
							}
							
							for(float i = x; i <= tempX; i += original.getBody().getDimensions().x) {
								boolean alreadyContained = false;
								for(WorldObject o : heldObjects) {
									if(o.getBody().getPosition2D().x == i) {
										alreadyContained = true;
										break;
									}
								}
								
								if(!alreadyContained) {
									WorldObject temp = original.clone();
									temp.getBody().setPosition2D(i, y);
									toAdd.add(temp);
								}					
							}
						} else if(tempX < x) {
							for(int i = heldObjects.size() - 1; i >= 0; i--) {
								WorldObject o = heldObjects.get(i);
								if(o.getBody().getPosition2D().x > x || o.getBody().getPosition2D().x < tempX) { o.cleanUp(); heldObjects.remove(o); }
							}
							
							for(float i = x; i >= tempX; i -= original.getBody().getDimensions().x) {
								boolean alreadyContained = false;
								for(WorldObject o : heldObjects) {
									if(o.getBody().getPosition2D().x == i) {
										alreadyContained = true;
										break;
									}
								}
								
								if(!alreadyContained) {
									WorldObject temp = original.clone();
									temp.getBody().setPosition2D(i, y);
									toAdd.add(temp);
								}		
							}
						}
						
						heldObjects.addAll(toAdd);
						
					}, lot, delta);
				} if(Keyboard.isKeyDown(Keyboard.KEY_X)) {
					handler.getMouseManager().updatePickerForTile(s -> {
						ArrayList<WorldObject> toAdd = new ArrayList<>();
						int tempY = (int) s.getPosition().z;
						
						if(tempY > y) {
							for(int i = heldObjects.size() - 1; i >= 0; i--) {
								WorldObject o = heldObjects.get(i);
								if(o.getBody().getPosition2D().y < y || o.getBody().getPosition2D().y > tempY) { o.cleanUp(); heldObjects.remove(o); }
							}

							for(float i = y; i <= tempY; i += original.getBody().getDimensions().y) {
								boolean alreadyContained = false;
								for(WorldObject o : heldObjects) {
									if(o.getBody().getPosition2D().y == i) {
										alreadyContained = true;
										break;
									}
								}
								
								if(!alreadyContained) {
									WorldObject temp = original.clone();
									temp.getBody().setPosition2D(x, i);
									toAdd.add(temp);
								}					
							}
						} else if(tempY < y) {
							for(int i = heldObjects.size() - 1; i >= 0; i--) {
								WorldObject o = heldObjects.get(i);
								if(o.getBody().getPosition2D().y > y || o.getBody().getPosition2D().y < tempY) { o.cleanUp(); heldObjects.remove(o); }
							}
							
							for(float i = y; i >= tempY; i -= original.getBody().getDimensions().y) {
								System.out.println(i);
								boolean alreadyContained = false;
								for(WorldObject o : heldObjects) {
									if(o.getBody().getPosition2D().y == i) {
										alreadyContained = true;
										break;
									}
								}
								
								if(!alreadyContained) {
									WorldObject temp = original.clone();
									temp.getBody().setPosition2D(x, i);
									toAdd.add(temp);
								}		
							}
						}
						
						heldObjects.addAll(toAdd);
						
					}, lot, delta);
				}
			} else if(handler.getMouseManager().keyJustReleased(0)) {
				handler.getMouseManager().updatePicker(s -> {
					if(!heldObjects.isEmpty()) {
						for(WorldObject heldObject : heldObjects) {
							heldObject.addToTile(lot.getTiles()[(int) heldObject.getBody().getX()][(int) heldObject.getBody().getZ()]);
						}
						
						heldObjects.clear();
					} else {
						WorldObject temp = lot.getTiles()[(int) s.getPosition().getX()][(int) s.getPosition().getZ()].findObject(s); 
						if(temp != null) {
							temp.removeFromTile();
							heldObjects.add(temp);
						}
					}
				}, delta);
			} else if(heldObjects.size() == 1) {
				WorldObject heldObject = heldObjects.get(0);
				
				handler.getMouseManager().updatePickerForTile(s -> {
					if(s != null && !Mouse.isButtonDown(0)) {
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
		if(enabled && !heldObjects.isEmpty())
			for(WorldObject o : heldObjects)
				o.render(camera);
	}
	
	public boolean isEnabled() { return enabled; }
	public void setEnabled(boolean enabled) { this.enabled = enabled; } 
}
