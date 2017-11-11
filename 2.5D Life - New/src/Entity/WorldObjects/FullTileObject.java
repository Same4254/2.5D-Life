package Entity.WorldObjects;

import com.Engine.RenderEngine.Shaders.Shader;
import com.Engine.Util.Vectors.Vector2f;
import com.Engine.Util.Vectors.Vector3f;

import Main.Handler;
import World.Tiles.Tile;

public class FullTileObject extends WorldObject {
	
	public FullTileObject(Handler handler, Vector2f twoDDimension, String name, Shader modelShader) {
		super(handler, twoDDimension, name, modelShader);
	}

	@Override
	public void rotateLeft() {
		body.getRenderProperties().rotate(new Vector3f(0, 90, 0));
	}

	@Override
	public void rotateRight() {
		body.getRenderProperties().rotate(new Vector3f(0, -90, 0));
	}
	
	@Override
	public void addToTile(Tile tile) {
		if(!tile.collide(this)) {
			if(this.tile != null) 
				this.tile.remove(this);
			tile.add(this);
			this.tile = tile;
			
			body.setPosition2D(tile.getBody().getPosition2D());
		}
	}

	@Override
	public void clearTile() {
		tile = null;
	}
}
