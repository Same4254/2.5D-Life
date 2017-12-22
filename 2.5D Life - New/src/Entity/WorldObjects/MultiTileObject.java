package Entity.WorldObjects;

import java.util.ArrayList;

import com.Engine.RenderEngine.Textures.Texture2D;
import com.Engine.Util.Vectors.Vector3f;

import Entity.WorldObjects.Lot.Lot;
import Entity.WrapperBodies.WrapperModel;
import Main.Handler;
import Utils.Util;
import World.Tiles.Tile;

public abstract class MultiTileObject extends WorldObject {

	protected ArrayList<Tile> tiles;
	
	public MultiTileObject(Handler handler, WrapperModel wrapperModel, Texture2D texture) {
		super(handler, wrapperModel, texture);
		
		tiles = new ArrayList<>();
	}

	@Override
	public boolean addToTile(Tile tile) {
		//TODO check if the object is in the lot's bounds
		
		Lot lot = tile.getLot();
		
		boolean noCollide = true;
		
		for(int x = (int) tile.getBody().getPosition2D().x; x < (int) tile.getBody().getPosition2D().x + body.getWidth(); x++) {
		for(int y = (int) tile.getBody().getPosition2D().y; y < (int) tile.getBody().getPosition2D().y + body.getHeight(); y++) {
			if(lot.getTiles()[x][y].containsAnything()) {// TODO Check for collision in all the tiles
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
				tiles.add(lot.getTiles()[x][y]);
				lot.getTiles()[x][y].add(this);
			}}
			
			setPosition2D(tile.getBody().getPosition2D());
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
	public void render() {
		body.render();
	}
	
	@Override
	public void rotateLeft() {
		body.getRenderProperties().rotate(new Vector3f(0, 90, 0));
		body.getStaticBody().setRotation(body.getStaticBody().getRotation().add(new Vector3f(0, 90, 0)));
		
//		body.getHitBox().rotateLeft();
		
		float width = body.getWidth();
		float height = body.getHeight();
		
		body.setWidth(height);
		body.setHeight(width);
		
		rotateFront(90);
	}

	@Override
	public void rotateRight() {
		body.getRenderProperties().rotate(new Vector3f(0, -90, 0));
		body.getStaticBody().setRotation(body.getStaticBody().getRotation().add(new Vector3f(0, -90, 0)));

		body.getHitBox().rotateRight();
		
		float width = body.getWidth();
		float height = body.getHeight();
		
		body.setWidth(height);
		body.setHeight(width);
		
		rotateFront(-90);
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
	
//	@Override
//	public void setPosition2D(Vector2f position) {
//		body.setPosition2D(position);
//		body.getStaticBody().setPosition(new Vector3f(position.x + .5f, 0, position.y + .5f));
//	}
//	
//	@Override
//	public void setPosition2D(float x, float z) {
//		setPosition2D(new Vector2f(x, z));
//	}
//	
	@Override
	public void clearTile() {
		tiles.clear();
	}
	
	public ArrayList<Tile> getTiles() { return tiles; }
}
