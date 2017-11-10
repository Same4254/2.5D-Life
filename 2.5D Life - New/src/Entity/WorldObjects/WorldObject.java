package Entity.WorldObjects;

import com.Engine.RenderEngine.Shaders.Shader;
import com.Engine.RenderEngine.Util.Camera;
import com.Engine.Util.Vectors.Vector2f;

import Entity.WorldObjects.Lot.Lot;
import Entity.WrapperBodies.WrapperStaticBody;
import Main.Handler;
import World.Tiles.Tile;

public abstract class WorldObject {
	protected Handler handler;
	protected WrapperStaticBody body;
	protected Tile tile;
	
	public WorldObject(Handler handler, Vector2f twoDDimension, String name, Shader modelShader) {
		this.handler = handler;
		
		body = new WrapperStaticBody(new Vector2f(), twoDDimension, name, modelShader);
		handler.getGame().getPhysicsEngine().add(body.getStaticBody());
	}
	
	public void render(Camera camera) {
		body.render(camera);
	}
	
	public void update() {
		
	}
	
	public abstract void addToTile(Lot lot, Tile tile);
	public abstract void rotateLeft();
	public abstract void rotateRight();

	public WrapperStaticBody getBody() { return body; }
	public void setTile(Tile tile) { this.tile = tile; }
}
