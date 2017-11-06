package Entity.WorldObjects;

import com.Engine.RenderEngine.Shaders.Shader;
import com.Engine.RenderEngine.Util.Camera;
import com.Engine.Util.Vectors.Vector2f;

import Entity.WrapperBodies.WrapperStaticBody;
import World.Tiles.Tile;

public abstract class WorldObject {
	protected WrapperStaticBody body;
	protected Tile tile;
	
	public WorldObject(Vector2f twoDDimension, String name, Shader modelShader) {
		body = new WrapperStaticBody(new Vector2f(), twoDDimension, name, modelShader);
	}
	
	public void render(Camera camera) {
		body.render(camera);
	}
	
	public void update() {
		
	}
	
	public abstract void rotateLeft();
	public abstract void rotateRight();

	public WrapperStaticBody getBody() { return body; }
	public void setTile(Tile tile) { this.tile = tile; }
}
