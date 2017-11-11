package Entity.WorldObjects;

import com.Engine.RenderEngine.Textures.Texture2D;
import com.Engine.RenderEngine.Util.Camera;
import com.Engine.Util.Vectors.Vector2f;
import com.Engine.Util.Vectors.Vector3f;

import Entity.WrapperBodies.WrapperModel;
import Main.Handler;
import World.Tiles.Tile;

public class SubTileObject extends WorldObject {
	protected int subX, subY; 
	protected int subWidth, subHeight;
	
	public SubTileObject(Handler handler, WrapperModel wrapperModel, Texture2D texture) {
		super(handler, wrapperModel, texture);

		Vector2f dimensions = body.getDimensions();
		
		subWidth = (int) dimensions.multiply(Tile.TILE_RESOLUTION).x;
		subHeight = (int) dimensions.multiply(Tile.TILE_RESOLUTION).y;
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
