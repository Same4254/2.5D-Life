package Entity.WorldObjects.Lot.Edit;

import org.lwjgl.input.Keyboard;

import Entity.WorldObjects.Lot.Lot;
import Main.Handler;
import Utils.Util;
import World.Tiles.Tile;

public class TileTextureEdit extends EditMode {
	private Tile currentTile;
	private int originalTextureIndex;
	private int tempTextureIndex;
	
	public TileTextureEdit(Handler handler, Lot lot) {
		super(handler, lot);
		
		tempTextureIndex = -1;
	}

	@Override
	public void update(float delta) {
		System.out.println("Tile Update");
		
		if(handler.getKeyManager().keyJustPressed(Keyboard.KEY_DELETE)) 
			clear();
		else if(handler.getKeyManager().keyJustPressed(Keyboard.KEY_1)) 
			tempTextureIndex = 0;
		else if(handler.getKeyManager().keyJustPressed(Keyboard.KEY_2)) 
			tempTextureIndex = 1;
		else if(handler.getKeyManager().keyJustPressed(Keyboard.KEY_3)) 
			tempTextureIndex = 2;
		else if(handler.getKeyManager().keyJustPressed(Keyboard.KEY_4)) 
			tempTextureIndex = 3;
		else if(handler.getKeyManager().keyJustPressed(Keyboard.KEY_5)) 
			tempTextureIndex = 4;
		
		handler.getMouseManager().updatePickerForTile(s -> {
			if(s != null) { //&& tempTextureIndex != -1) {
//				if(currentTile == null) {
//					currentTile = lot.getTiles()[(int) s.getPosition().x][(int) s.getPosition().z];
//					originalTextureIndex = currentTile.getTextureIndex();
//				} else if(!Util.to2D(s.getPosition()).equals(currentTile.getBody().getPosition2D())) {
//					currentTile.setTextureIndex(originalTextureIndex);
//					currentTile = lot.getTiles()[(int) s.getPosition().x][(int) s.getPosition().z];
//					originalTextureIndex = currentTile.getTextureIndex();
//				}
			}
		}, lot, delta);
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
		}
	}
}
