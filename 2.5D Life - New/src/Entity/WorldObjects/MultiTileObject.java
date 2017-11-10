package Entity.WorldObjects;

import java.util.ArrayList;

import com.Engine.RenderEngine.Shaders.Shader;
import com.Engine.Util.Vectors.Vector2f;
import com.Engine.Util.Vectors.Vector3f;

import Entity.WorldObjects.Lot.Lot;
import Main.Handler;
import World.Tiles.Tile;

public class MultiTileObject extends WorldObject {

	protected ArrayList<Tile> tiles;
	
	public MultiTileObject(Handler handler, Vector2f twoDDimension, String name, Shader modelShader) {
		super(handler, twoDDimension, name, modelShader);
		
		tiles = new ArrayList<>();
	}

	@Override
	public void addToTile(Lot lot, Tile tile) {
		//TODO check if the object is in the lot's bounds
		
		boolean noCollide = true;
		
		for(int x = (int) tile.getBody().getPosition2D().x; x < (int) tile.getBody().getPosition2D().x + body.getWidth(); x++) {
		for(int y = (int) tile.getBody().getPosition2D().y; y < (int) tile.getBody().getPosition2D().y + body.getHeight(); y++) {
			if(lot.getTiles()[x][y].collide(this)) {// TODO Check for collision in all the tiles
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
				tiles.add(lot.getTiles()[y][x]);
				lot.getTiles()[y][x].add(this);
			}}
		}
	}
	
	@Override
	public void rotateLeft() {
		body.getRenderProperties().rotate(new Vector3f(0, 90, 0));
		
		int x = (int) body.getX();
		int y = (int) body.getX();
		
		float width = body.getWidth();
		float height = body.getHeight();
		
		body.setWidth(height);
		body.setHeight(width);
	}

	@Override
	public void rotateRight() {
		body.getRenderProperties().rotate(new Vector3f(0, -90, 0));
		
		int x = (int) body.getX();
		int y = (int) body.getX();
		
		float width = body.getWidth();
		float height = body.getHeight();
		
		body.setWidth(height);
		body.setHeight(width);
	}
	
	public ArrayList<Tile> getTiles() { return tiles; }
}
