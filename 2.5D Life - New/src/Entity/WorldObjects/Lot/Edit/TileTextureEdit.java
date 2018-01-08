package Entity.WorldObjects.Lot.Edit;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import com.Engine.Util.Vectors.Vector3f;

import Entity.WorldObjects.Lot.Floor;
import Entity.WorldObjects.Lot.Lot;
import Input.MousePicker;
import Main.Handler;
import Utils.Util;
import World.Tiles.Tile;

public class TileTextureEdit extends EditMode {
	private Tile currentTile;
	private int originalTextureIndex;
	private int tempTextureIndex;
	
	private TileTextureDragList dragList;
	
	public TileTextureEdit(Handler handler, Lot lot) {
		super(handler, lot);
		
		dragList = new TileTextureDragList(lot);
		tempTextureIndex = -1;
	}

	@Override
	public void update(float delta) {
		if(handler.getKeyManager().keyJustPressed(Keyboard.KEY_DELETE)) 
			clear();
		else if(handler.getKeyManager().keyJustPressed(Keyboard.KEY_1)) 
			setTempIndex(0);
		else if(handler.getKeyManager().keyJustPressed(Keyboard.KEY_2)) 
			setTempIndex(1);
		else if(handler.getKeyManager().keyJustPressed(Keyboard.KEY_3)) 
			setTempIndex(2);
		else if(handler.getKeyManager().keyJustPressed(Keyboard.KEY_4)) 
			setTempIndex(3);
		else if(handler.getKeyManager().keyJustPressed(Keyboard.KEY_5)) 
			setTempIndex(4);
		else if(handler.getKeyManager().keyJustPressed(Keyboard.KEY_6)) 
			setTempIndex(5);
		else if(handler.getKeyManager().keyJustPressed(Keyboard.KEY_7)) 
			setTempIndex(6);
		
		if(handler.getMouseManager().keyJustReleased(0)) {//Placing the tile texture
			place();
		} else {
			Vector3f location = MousePicker.calculateHitPosition(floorLevel * Lot.FLOOR_HEIGHT);
			location = location.capMax(lot.getPosition().x + lot.getWidth() - 1, location.y, lot.getPosition().z + lot.getHeight() - 1);
			location = location.capMin(lot.getPosition().x, location.y, lot.getPosition().z);
			
			if(Mouse.isButtonDown(0)) {
				dragList.fill(currentTile, Util.to2D(location));
			} else {
				Floor floor = lot.getFloor(location);
				
				if(tempTextureIndex != -1) {
					if(currentTile == null) {
						currentTile = floor.getTiles()[(int) location.x][(int) location.z];
						originalTextureIndex = currentTile.getTextureIndex();
						currentTile.setTextureIndex(tempTextureIndex);
					} else if(!Util.to2D(location).equals(currentTile.getBody().getPosition2D())) {
						currentTile.setTextureIndex(originalTextureIndex);
						
						currentTile = floor.getTiles()[(int) location.x][(int) location.z];
						originalTextureIndex = currentTile.getTextureIndex();
						currentTile.setTextureIndex(tempTextureIndex);
						
//						dragList.clear();
//						dragList.setOriginalTile(currentTile);
					}
				}
			}
		}
	}

	@Override
	public void render() {
		
	}

	@Override
	public void clear() {
		if(currentTile != null) {
			currentTile.setTextureIndex(originalTextureIndex);
			
			tempTextureIndex = -1;
			currentTile = null;
			dragList.clear();
		}
	}
	
	private void place() {
		if(currentTile != null) {
			tempTextureIndex = -1;
			currentTile = null;
			dragList.place();
		}
	}
	
	private void setTempIndex(int index) {
		tempTextureIndex = index;
		
		if(currentTile != null)
			currentTile.setTextureIndex(tempTextureIndex);
	}
}
