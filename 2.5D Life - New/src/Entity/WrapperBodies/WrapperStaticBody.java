package Entity.WrapperBodies;

import java.awt.geom.Rectangle2D;

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

public class WrapperStaticBody {
	private Model model;
	private RenderProperties renderProperties;
	private StaticBody staticBody;
	private Rectangle2D.Float hitBox;
	
	public WrapperStaticBody(WrapperModel wrapperModel, Texture2D texture2D, RenderProperties renderProperties) {
		this.model = wrapperModel.getModel();
		this.renderProperties = renderProperties;
		
		CollisionMesh col = wrapperModel.getCollisionMesh();
		staticBody = new StaticBody(col);
		
		Vector3f radius = col.getOctree().getRoot().getBounds().getRadius();
		hitBox = new Rectangle2D.Float(0, 0, radius.x, radius.z);
		setPosition2D(0, 0);
	}
	
	public WrapperStaticBody(WrapperModel wrapperModel, Texture2D texture2D) {
		this(wrapperModel, texture2D, new DefaultRenderProperties());
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

	public void setPosition2D(float x, float y) {
		setPosition2D(new Vector2f(x, y));
	}
	
	private void setHitBoxCoords(float x, float y) {
		hitBox.x = x;
		hitBox.y = y;
	}
	
	private void setHitBoxCoords(Vector2f position) { setHitBoxCoords(position.x, position.y); }
	
	public float getWidth() { return hitBox.width; }
	public void setWidth(float width) { hitBox.width = width; } 
	
	public float getHeight() { return hitBox.height; } 
	public void setHeight(float height) { hitBox.height = height; }
	
	public Vector2f getDimensions() { return new Vector2f(getWidth(), getHeight()); }
	
	public float getX() { return staticBody.getPosition().x; }
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
	
	public float getZ() { return staticBody.getPosition().z; }
	public void addZ(float amount) { setZ(getZ() + amount); }
	
	public void setZ(float z) {
		staticBody.setPosition(new Vector3f(staticBody.getPosition().x, staticBody.getPosition().y, z));
		renderProperties.getTransform().setTranslation(new Vector3f(renderProperties.getTransform().getTranslation().x, renderProperties.getTransform().getTranslation().y, z));
		hitBox.y = z;
	}
	
	public void add(float x, float z) { addX(x); addZ(z); }
	public void add(Vector2f amount) { addX(amount.x); addZ(amount.y); }
	
	public void render(Camera camera) { model.render(renderProperties, camera); }
	
	public Model getModel() { return model; }
	public StaticBody getStaticBody() { return staticBody; }
	public RenderProperties getRenderProperties() { return renderProperties; }
	public Rectangle2D.Float getHitBox() { return hitBox; }
}
