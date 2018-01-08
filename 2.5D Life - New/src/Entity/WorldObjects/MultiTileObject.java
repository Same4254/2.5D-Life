package Entity.WorldObjects;

import java.util.ArrayList;

import com.Engine.RenderEngine.Textures.Texture2D;

import Entity.WorldObjects.Lot.Floor;
import Entity.WorldObjects.Lot.Lot;
import Entity.WrapperBodies.WrapperModel;
import Main.Handler;
import Utils.Util;
import World.Tiles.Tile;

public abstract class MultiTileObject extends WorldObject {

	protected ArrayList<Tile> tiles;
	
	public MultiTileObject(Handler handler, Lot lot, WrapperModel wrapperModel, Texture2D texture) {
		super(handler, lot, wrapperModel, texture);
		
		tiles = new ArrayList<>();
	}

	@Override
	public boolean addToTile(Tile tile) {
		//TODO check if the object is in the lot's bounds
		if(tile == null)
			return false;
		if(tile.getPosition2D().x  + getWidth() > lot.getWidth() || tile.getPosition2D().y + getHeight() > lot.getHeight())
			return false;
		
		Floor floor = tile.getLot().getFloor(getPosition3D());
		
		boolean noCollide = true;
		
		for(int x = (int) tile.getBody().getPosition2D().x; x < (int) tile.getBody().getPosition2D().x + body.getWidth(); x++) {
		for(int y = (int) tile.getBody().getPosition2D().y; y < (int) tile.getBody().getPosition2D().y + body.getHeight(); y++) {
			if(floor.getTiles()[x][y].containsAnything()) {// TODO Check for collision in all the tiles
				noCollide = false;
			}
		}}
		
		if(noCollide) {
			if(!tiles.isEmpty()) {
				for(Tile t : tiles)
					t.remove(this);
				tiles.clear();
			}

			for(int x = (int) tile.getBody().getPosition2D().x; x < (int) tile.getBody().getPosition2D().x + body.getWidth(); x++) {
			for(int y = (int) tile.getBody().getPosition2D().y; y < (int) tile.getBody().getPosition2D().y + body.getHeight(); y++) {
				tiles.add(floor.getTiles()[x][y]);
				floor.getTiles()[x][y].add(this);
			}}

			return true;
		} 
		
		return false;
	}
	
	@Override
	public WorldObject removeFromTile() {
		if(!tiles.isEmpty()) {
			for(int i = tiles.size() - 1; i >= 0; i--) 
				tiles.get(i).remove(this);
			tiles.clear();
			return this;
		}
		
		return null;
	}
	
	@Override
	public void rotateFront(float angle) {
		if(front.x > 1)
			front.x = 1;
		if(front.x < -1)
			front.x = -1;
		
		if(front.y > 1)
			front.y = 1;
		if(front.y < -1)
			front.y = -1;
		
		front = Util.rotate(front, angle);
	
		if(front.y == 1)
			front.y = body.getHeight();
		if(front.x == 1)
			front.x = body.getWidth();
	}

	@Override
	public void clearTile() {
		tiles.clear();
	}
	
	public ArrayList<Tile> getTiles() { return tiles; }
}
