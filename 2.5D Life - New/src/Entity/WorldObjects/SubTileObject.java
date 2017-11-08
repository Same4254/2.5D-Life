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

//		Rectangle2D box = new Rectangle2D.Float(subX, subY, subWidth, subHeight);
//		AffineTransform transformer = new AffineTransform();
//		transformer.rotate(Math.PI / 2, box.getX(), box.getY());
//		box = transformer.createTransformedShape(box).getBounds2D();
//
//		subX = (int) Math.max(box.getX(), 0);
//		subY = (int) Math.max(box.getY(), 0);
		
//		Vector2f result = new Vector2f(subX, subY).add(subWidth, 0).subtract(subWidth / 2f, subHeight / 2f);
//		
//		float x = result.x;
//		result.x = -result.y;
//		result.y = x;
//		
//		result = result.add(subWidth / 2, subHeight / 2).capMin(0); 
//		
		subWidth ^= subHeight;
		subHeight ^= subWidth;
		subWidth ^= subHeight;
		
		subX = 0;
		subY = 0;
//		
////		Vector3f result = new Vector3f(new Vector2f(subX, subY).add(subWidth, 0).subtract(subWidth / 2f, subHeight / 2f), 0).rotate(new Vector3f(0, 0, 90)).add(subWidth / 2f, subHeight / 2f, 0).capMin(0);
//		subX = (int) result.x;
//		subY = (int) result.y;
	}

	@Override
	public void rotateRight() {
		body.getRenderProperties().rotate(new Vector3f(0, -90, 0));
		body.getRenderProperties().getTransform().setRotation(body.getRenderProperties().getTransform().getRotation().add(360).mod(180));
		
//		Vector2f result = new Vector2f(subX, subY).add(0, subHeight).subtract(subWidth / 2f, subHeight / 2f);
//		
		subWidth ^= subHeight;
		subHeight ^= subWidth;
		subWidth ^= subHeight;
		
		subX = 0;
		subY = 0;
//		
//		float x = result.x;
//		result.x = result.y;
//		result.y = -x;
//		
//		result = result.add(subWidth / 2, subHeight / 2).capMin(0); 
		
//		Vector3f result = new Vector3f(new Vector2f(subX, subY).add(subWidth, 0).subtract(subWidth / 2f, subHeight / 2f), 0).rotate(new Vector3f(0, 0, 90)).add(subWidth / 2f, subHeight / 2f, 0).capMin(0);
//		subX = (int) result.x;
//		subY = (int) result.y;
	}
	
	public void render(Camera camera) {
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
