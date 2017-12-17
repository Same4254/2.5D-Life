package Entity.WrapperBodies;

import java.awt.geom.Rectangle2D;
import java.awt.geom.Rectangle2D.Float;

import com.Engine.PhysicsEngine.Bodies.StaticBody;
import com.Engine.PhysicsEngine.Detection.Colliders.CollisionMesh;
import com.Engine.PhysicsEngine.Detection.Colliders.CollisionMeshLoader;
import com.Engine.RenderEngine.Shaders.RenderProperties;
import com.Engine.RenderEngine.Shaders.Default.DefaultRenderProperties;
import com.Engine.RenderEngine.Shaders.Default.Model;
import com.Engine.RenderEngine.Textures.Texture2D;
import com.Engine.RenderEngine.Util.Camera;
import com.Engine.Util.Vectors.Vector2f;
import com.Engine.Util.Vectors.Vector3f;

import Utils.Util;
import World.Tiles.Tile;

public class WrapperStaticBody {
	private WrapperModel wrapperModel;
	private Model model;
	private RenderProperties renderProperties;
	private StaticBody staticBody;
	private Rectangle2D.Float hitBox;
	
	public WrapperStaticBody(WrapperModel wrapperModel, Texture2D texture2D, RenderProperties renderProperties) {
		this.wrapperModel = wrapperModel;
		this.model = wrapperModel.getModel();
		this.renderProperties = renderProperties;
		
		model.setTexture(texture2D);
		
		CollisionMesh col = wrapperModel.getCollisionMesh();
		staticBody = new StaticBody(col);
		
		Vector3f radius = col.getOctree().getRoot().getBounds().getRadius().multiply(2);
		
		Vector2f dimensions = new Vector2f(radius.x, radius.z);
		dimensions = Util.roundNearestMultiple(dimensions, 1f / Tile.TILE_RESOLUTION);
		
//		dimensions = dimensions.divide(1 / Tile.TILE_RESOLUTION).round().multiply(1 / Tile.TILE_RESOLUTION);
//		Math.round(number / roundTo) * roundTo
		
		hitBox = new Rectangle2D.Float(0, 0, dimensions.x, dimensions.y);
		setPosition2D(0, 0);
	}
	
	public WrapperStaticBody(WrapperModel wrapperModel, Texture2D texture2D) {
		this(wrapperModel, texture2D, new DefaultRenderProperties());
	}
	
	private WrapperStaticBody(Model model, CollisionMesh col, Rectangle2D.Float hitBox, RenderProperties renderProperties) {
		this.model = new Model(model.getModelData());
		this.model.setTexture(model.getTexture());
		this.model.setShader(model.getShader());
		
		this.staticBody = new StaticBody(col);
		this.hitBox = (Float) hitBox.clone();
		this.renderProperties = renderProperties.clone();
	}

	public WrapperStaticBody clone() {
		return new WrapperStaticBody(model, wrapperModel.getCollisionMesh(), hitBox, renderProperties);
	}
	
	public Vector3f getPosition3D() { return staticBody.getPosition(); }
	public Vector2f getPosition2D() { return new Vector2f(getX(), getZ()); }
	
	public void setPosition3D(Vector3f position) {
		staticBody.setPosition(position);
		renderProperties.getTransform().setTranslation(position);
		setHitBoxCoords(position.getX(), position.getZ());
	}
	
	public void setPosition2D(Vector2f position) {
		staticBody.setPosition(new Vector3f(position.getX(), getY(), position.getY()));
		renderProperties.getTransform().setTranslation(new Vector3f(position.getX(), getY(), position.getY()));
		setHitBoxCoords(position);
	}

	public void setPosition2D(float x, float z) { setPosition2D(new Vector2f(x, z)); }
	public Vector2f roundPosToGrid() { return Util.roundNearestMultiple(getPosition2D(), (float) (1.0 / Tile.TILE_RESOLUTION)); }
	
	private void setHitBoxCoords(float x, float z) {
		hitBox.x = x;
		hitBox.y = z;
	}
	
	private void setHitBoxCoords(Vector2f position) { setHitBoxCoords(position.x, position.y); }
	
	public float getWidth() { return hitBox.width; }
	public void setWidth(float width) { hitBox.width = width; } 
	
	public float getHeight() { return hitBox.height; } 
	public void setHeight(float height) { hitBox.height = height; }
	
	public void setDimensions(Vector2f dimensions) { setWidth(dimensions.x); setHeight(dimensions.y); }
	public Vector2f getDimensions() { return new Vector2f(getWidth(), getHeight()); }
	
	public float getX() { return (float) hitBox.getX(); }
	public void addX(float amount) { setX(getX() + amount); }
	
	public void setX(float x) {
		staticBody.setPosition(new Vector3f(x, staticBody.getPosition().y, staticBody.getPosition().z));
		renderProperties.getTransform().setTranslation(new Vector3f(x, renderProperties.getTransform().getTranslation().y, renderProperties.getTransform().getTranslation().z));
		hitBox.x = x;
	}
	
	public float getY() { return staticBody.getPosition().y; }
	
	public void setY(float y) {
		staticBody.setPosition(new Vector3f(staticBody.getPosition().x, y, staticBody.getPosition().z));
		renderProperties.getTransform().setTranslation(new Vector3f(renderProperties.getTransform().getTranslation().x, y, renderProperties.getTransform().getTranslation().z));
	}
	
	public float getZ() { return (float) hitBox.getY(); }
	public void addZ(float amount) { setZ(getZ() + amount); }
	
	public void setZ(float z) {
		staticBody.setPosition(new Vector3f(staticBody.getPosition().x, staticBody.getPosition().y, z));
		renderProperties.getTransform().setTranslation(new Vector3f(renderProperties.getTransform().getTranslation().x, renderProperties.getTransform().getTranslation().y, z));
		hitBox.y = z;
	}
	
	public void add(float x, float z) { addX(x); addZ(z); }
	public void add(Vector2f amount) { addX(amount.x); addZ(amount.y); }
	
	public void render() { model.render(renderProperties); }
	
	public Model getModel() { return model; }
	public StaticBody getStaticBody() { return staticBody; }
	public RenderProperties getRenderProperties() { return renderProperties; }
	public Rectangle2D.Float getHitBox() { return hitBox; }
}
