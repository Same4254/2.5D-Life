package Entity.FreeMoving;

import com.Engine.RenderEngine.Shaders.Shader;
import com.Engine.RenderEngine.Textures.Texture2D;
import com.Engine.RenderEngine.Util.Camera;
import com.Engine.Util.Vectors.Vector2f;

import Entity.WorldObjects.Lot.Lot;
import Entity.WrapperBodies.WrapperStaticBody;
import Main.Handler;
import Utils.ImageLoader;
import World.Tiles.Tile;

public abstract class Entity {
	protected Handler handler;
	protected WrapperStaticBody body;

	public static Texture2D red = ImageLoader.loadTexture(ImageLoader.TEXTURE_PATH + "Bullet.png");
	public static Texture2D gold = ImageLoader.loadTexture(ImageLoader.TEXTURE_PATH + "Gold.png");
	
	public Entity(Handler handler, Vector2f twoDLocation, Vector2f twoDDimension, String objPath, String modelTexturePath, Shader modelShader) {
		this.handler = handler;
		body = new WrapperStaticBody(twoDLocation, twoDDimension, objPath, modelTexturePath, modelShader);
	}
	
	public boolean collide(Lot lot, Vector2f velocity) {
		Tile[][] tiles = lot.getTiles();
		
//		System.out.println("OG Velocity: " + velocity);
		
//		velocity = velocity.multiply(Tile.TILE_RESOLUTION).add(.999f).truncate().divide(Tile.TILE_RESOLUTION);
		
		Vector2f currentLocation = body.getPosition2D();
		
//		System.out.println("Normalized: " + velocity.normalized());
		
		Vector2f step = velocity.normalized().divide(Tile.TILE_RESOLUTION);

		Vector2f position = body.getPosition2D();
		
		int maxWidth = (int) Math.ceil(body.getWidth());
		int maxHeight = (int) Math.ceil(body.getHeight());
		
//		System.out.println("Step: " + step);
//		System.out.println("Velocity: " + velocity);
		
		try {
//			System.out.println(velocity.length());
//			System.out.println((position.add(step)).subtract(currentLocation).length() < velocity.length());
			position = position.add(step);
			do {
				body.setPosition2D(position);
				Vector2f gridPosition = position.subtract(maxWidth / 2f, maxHeight / 2f).truncate();
				for(int x = (int) gridPosition.x; x < gridPosition.x + maxWidth + 1; x++) { 
				for(int y = (int) gridPosition.y; y < gridPosition.y + maxHeight + 1; y++) {
					tiles[x][y].getBody().getModel().setTexture(red);
					if(tiles[x][y].collide(this)) return true;
				}}
				position = position.add(step);
			} while(position.subtract(currentLocation).length() <= velocity.length());
			return false;
		} finally {
			body.setPosition2D(currentLocation);
		}
	}
	
	public abstract void update(float delta);
	public abstract void render(Camera camera);
	
	public WrapperStaticBody getBody() { return body; }
}
