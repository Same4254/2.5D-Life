package Entity.WorldObjects.Lot.Edit;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import com.Engine.Util.Vectors.Vector2f;
import com.Engine.Util.Vectors.Vector3f;

import Entity.WorldObjects.Lot.Lot;
import Input.MousePicker;
import Main.Handler;
import Utils.Util;
import World.Tiles.Tile;

public class TilePlacementEdit extends EditMode {
	private Tile tileToPlace;
	private TilePlacementDragList dragList;
	
	public TilePlacementEdit(Handler handler, Lot lot) {
		super(handler, lot);
		
		dragList = new TilePlacementDragList(handler, lot);
	}

	@Override
	public void update(float delta) {
		if(handler.getKeyManager().keyJustPressed(Keyboard.KEY_1)) {
			tileToPlace = new Tile(handler, lot.getFloor(floorLevel), new Vector3f(0));
			tileToPlace.setTextureIndex(2);
		}
		
		if(handler.getKeyManager().keyJustPressed(Keyboard.KEY_DELETE)) {
			tileToPlace = null;
			tileToPlace.cleanUp();
		}
		
		if(handler.getKeyManager().keyJustPressed(Keyboard.KEY_UP) || handler.getKeyManager().keyJustPressed(Keyboard.KEY_DOWN)) {
			dragList.clear();
		}
		
		if(tileToPlace != null) {
			if(handler.getMouseManager().keyJustReleased(0)) 
				place();
			else { 
				Vector3f location = MousePicker.calculateHitPosition(floorLevel * Lot.floorHeight);
				location = location.capMax(lot.getPosition().x + lot.getWidth() - 1, location.y, lot.getPosition().z + lot.getHeight() - 1);
				location = location.capMin(lot.getPosition().x, location.y, lot.getPosition().z);
				
				if(Mouse.isButtonDown(0)) {// Dragging
					dragList.fill(tileToPlace, Util.to2D(location));
				} else {// Free Moving
					tileToPlace.setPosition3D(location);
				}
			}
		}
	}

	private void place() {
		if(lot.getFloorTiles(tileToPlace.getPosition3D())[(int) tileToPlace.getPosition2D().x * 2][(int) tileToPlace.getPosition2D().y * 2] == null) 
			lot.getFloorTiles(tileToPlace.getPosition3D())[(int) tileToPlace.getPosition2D().x * 2][(int) tileToPlace.getPosition2D().y * 2] = tileToPlace;
		else 
			tileToPlace.cleanUp();
		
		tileToPlace = null;
		dragList.place();
	}
	
	@Override
	public void render() {
		if(tileToPlace != null)
			tileToPlace.render(Lot.tileInstanceModel);
		dragList.render();
	}

	@Override
	public void clear() {
		if(tileToPlace != null) {
			tileToPlace.cleanUp();
			tileToPlace = null;
		}
		
		dragList.clear();	
	}
}
