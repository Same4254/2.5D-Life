package Entity.WorldObjects.Lot.Edit;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

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
		if(handler.getKeyManager().keyJustPressed(Keyboard.KEY_1))
			tileToPlace = new Tile(handler, lot, new Vector3f(0));
		
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
			else if(Mouse.isButtonDown(0)) {// Dragging
				dragList.fill(tileToPlace, Util.to2D(MousePicker.calculateHitPosition(floorLevel * Lot.FLOOR_HEIGHT)));
			} else {//Free Moving
				Vector3f pos = MousePicker.calculateHitPosition(floorLevel * Lot.FLOOR_HEIGHT);
				tileToPlace.setPosition3D(pos);
			}
		}
	}

	private void place() {
		if(lot.getFloorTiles(tileToPlace.getPosition3D())[(int) tileToPlace.getPosition2D().x][(int) tileToPlace.getPosition2D().y] == null) 
			lot.getFloorTiles(tileToPlace.getPosition3D())[(int) tileToPlace.getPosition2D().x][(int) tileToPlace.getPosition2D().y] = tileToPlace;
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
