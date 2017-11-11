package Entity.WorldObjects;

import com.Engine.RenderEngine.Shaders.Shader;
import com.Engine.RenderEngine.Util.Camera;
import com.Engine.Util.Vectors.Vector2f;
import com.Engine.Util.Vectors.Vector3f;

import Main.Handler;
import World.Tiles.Tile;

public class SubTileObject extends WorldObject {
	protected int subX, subY; 
	protected int subWidth, subHeight;
	
	public SubTileObject(Handler handler, int subX, int subY, Vector2f twoDDimension, String name, Shader modelShader) {
		super(handler, twoDDimension, name, modelShader);

		this.subX = subX;
		this.subY = subY;
		
		subWidth = (int) twoDDimension.multiply(Tile.TILE_RESOLUTION).x;
		subHeight = (int) twoDDimension.multiply(Tile.TILE_RESOLUTION).y;
	}
	
	@Override
	public void rotateLeft() {
		body.getRenderProperties().rotate(new Vector3f(0, 90, 0));
		body.getRenderProperties().getTransform().setRotation(body.getRenderProperties().getTransform().getRotation().add(360).mod(180));

		subWidth ^= subHeight;
		subHeight ^= subWidth;
		subWidth ^= subHeight;
		
		subX = 0;
		subY = 0;
	}

	@Override
	public void rotateRight() {
		body.getRenderProperties().rotate(new Vector3f(0, -90, 0));
		body.getRenderProperties().getTransform().setRotation(body.getRenderProperties().getTransform().getRotation().add(360).mod(180));
		
		subWidth ^= subHeight;
		subHeight ^= subWidth;
		subWidth ^= subHeight;
		
		subX = 0;
		subY = 0;
	}
	
	@Override
	public void addToTile(Tile tile) {
		if(!tile.collide(this)) {
			if(this.tile != null) 
				this.tile.remove(this);
			tile.add(this);
			this.tile = tile;
		
			body.setPosition2D(body.getPosition2D().add(new Vector2f(subX, subY).divide(Tile.TILE_RESOLUTION)));
		}
	}
	
	@Override
	public void clearTile() {
		tile = null;
	}
	
	public void render(Camera camera) {
		if(tile == null)
			body.getRenderProperties().getTransform().setTranslation(body.getPosition3D().add((float) subX / Tile.TILE_RESOLUTION, 0, (float) subY / Tile.TILE_RESOLUTION));
		body.render(camera);
	}
	
	public int getSubX() { return subX; }
	public int getSubY() { return subY; }
	public int getSubWidth() { return subWidth; }
	public int getSubHeight() { return subHeight; }

	public void setSubX(int subX) { this.subX = subX; }
	public void setSubY(int subY) { this.subY = subY; }
}
