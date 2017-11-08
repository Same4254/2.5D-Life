package World.Tiles;

import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

import com.Engine.PhysicsEngine.Bodies.PhysicsBody;
import com.Engine.RenderEngine.Shaders.Shader;
import com.Engine.RenderEngine.Util.Camera;
import com.Engine.Util.Vectors.Vector2f;
import com.Engine.Util.Vectors.Vector3f;

import Entity.Entity;
import Entity.WorldObjects.FullTileObject;
import Entity.WorldObjects.SubTileObject;
import Entity.WorldObjects.WorldObject;
import Entity.WorldObjects.Lot.Lot;
import Entity.WrapperBodies.WrapperStaticBody;
import Main.Handler;

public class Tile {
	public static final int TILE_RESOLUTION = 4;
	private static final Rectangle2D[][] rectangles = new Rectangle2D[TILE_RESOLUTION][TILE_RESOLUTION];
	
	static {
		float unit = 1f / TILE_RESOLUTION;
		
		for(int x = 0; x < TILE_RESOLUTION; x++) { 
		for(int y = 0; y < TILE_RESOLUTION; y++) {
			rectangles[x][y] = new Rectangle2D.Float(x * unit, y * unit, unit, unit); 
		}}
	}
	
	private Handler handler;
	private Lot lot;
	
	private FullTileObject fullObject;
	private WrapperStaticBody body;

	private SubTileObject[][] subTileObjects; 
	
	public Tile(Handler handler, Lot lot, Vector2f position, String objPath, String modelTexturePath, Shader modelShader) {
		this.handler = handler;
		this.lot = lot;
		
		body = new WrapperStaticBody(position, new Vector2f(1, 1), objPath, modelTexturePath, modelShader);
		subTileObjects = new SubTileObject[TILE_RESOLUTION][TILE_RESOLUTION];
		
		handler.getGame().getPhysicsEngine().add(body.getStaticBody());
	}
	
	public void render(Camera camera) {
		Vector2f temp = body.getPosition2D().add(lot.getPosition());
		
		body.getRenderProperties().getTransform().setTranslation(new Vector3f(temp.x, 0, temp.y));
		body.render(camera);
		
		if(fullObject != null) {
			fullObject.getBody().getRenderProperties().getTransform().setTranslation(new Vector3f(temp.x, 0, temp.y));
			fullObject.render(camera);
		}
		
		ArrayList<SubTileObject> objects = new ArrayList<>();
		
		for(SubTileObject[] subTiles : subTileObjects) { 
		for(SubTileObject o : subTiles) {
			if(o != null && !objects.contains(o)) {
//				o.getBody().getRenderProperties().getTransform().translate(new Vector3f(temp.x, 0, temp.y));
				o.render(camera);
				objects.add(o);
			}
		}}
	}
	
	public void update() {
		if(fullObject != null)
			fullObject.update();
	}
	
	public boolean add(WorldObject obj) {
		if(!collide(obj)) {
			if(obj instanceof FullTileObject) {
				fullObject = (FullTileObject) obj;
				obj.getBody().setPosition2D(body.getPosition2D());
			}
			else {
				SubTileObject temp = (SubTileObject) obj;
				for(int x = temp.getSubX(); x < temp.getSubX() + temp.getSubWidth(); x++) { 
				for(int y = temp.getSubY(); y < temp.getSubY() + temp.getSubHeight(); y++) {
					subTileObjects[x][y] = temp;
				}}
				obj.getBody().setPosition2D(body.getPosition2D().add(new Vector2f(temp.getSubX() , temp.getSubY()).divide(Tile.TILE_RESOLUTION)));
			}
			obj.setTile(this);
			return true;
		}
		return false;
	}
	
	public boolean collide(Entity e) {
		Rectangle2D collisionRect = new Rectangle2D.Float(e.getBody().getX() - body.getX(), e.getBody().getZ() - body.getZ(), e.getBody().getWidth(), e.getBody().getHeight());
		
		for(int x = 0; x < TILE_RESOLUTION; x++) { 
		for(int y = 0; y < TILE_RESOLUTION; y++) {
			if((subTileObjects[x][y] != null || fullObject != null) && collisionRect.intersects(rectangles[x][y])) return true;
		}}
		return false;
	}
	
	public boolean collide(WorldObject obj) {
		if(fullObject == null) {
			if(obj instanceof FullTileObject) {
				for(SubTileObject[] subTiles : subTileObjects) { 
				for(SubTileObject o : subTiles) {
					if(o != null) return true;
				}}
			} else {
				SubTileObject temp = (SubTileObject) obj;
				
				if(temp.getSubX() + temp.getSubWidth() > TILE_RESOLUTION)
					return true;
				if(temp.getSubY() + temp.getSubHeight() > TILE_RESOLUTION)
					return true;
				
				for(int x = temp.getSubX(); x < temp.getSubX() + temp.getSubWidth(); x++) { 
				for(int y = temp.getSubY(); y < temp.getSubY() + temp.getSubHeight(); y++) {
					if(subTileObjects[x][y] != null && subTileObjects[x][y].getClass().equals(obj.getClass())) continue;
					if(subTileObjects[x][y] != null) return true;
				}}
			}
		} else return true;
		return false;
	}
	
	public WorldObject remove(WorldObject object) {
		if(fullObject == object) {
			fullObject = null;
			return object;
		} else {
			boolean removed = false;
			for(int i = 0; i < subTileObjects.length; i++) { 
			for(int j = 0; j < subTileObjects[i].length; j++) {
				if(subTileObjects[i][j] == object) {
					subTileObjects[i][j] = null;
					removed = true;
				}
			}}
			
			if(removed)
				return object;
			else
				return null;
		}
	}
	
	public WorldObject findObject(PhysicsBody body) {
		if(fullObject != null && fullObject.getBody().getStaticBody() == body)
			return fullObject;

		System.out.println(body);
		for(int i = 0; i < subTileObjects.length; i++) {
		for(int j = 0; j < subTileObjects[i].length; j++) {
			if(subTileObjects[i][j] != null) 
				System.out.println(subTileObjects[i][j].getBody().getStaticBody());
			if(subTileObjects[i][j] != null && subTileObjects[i][j].getBody().getStaticBody() == body)
				return subTileObjects[i][j];
		}}
		
		return null;
	}

	public FullTileObject getFullObject() { return fullObject; }
	public WrapperStaticBody getBody() { return body; }
	public SubTileObject[][] getSubTileObjects() { return subTileObjects; }
}