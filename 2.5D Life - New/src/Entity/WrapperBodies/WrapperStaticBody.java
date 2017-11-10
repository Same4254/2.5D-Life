package Entity.WrapperBodies;

import java.awt.geom.Rectangle2D;

import com.Engine.PhysicsEngine.Bodies.StaticBody;
import com.Engine.PhysicsEngine.Detection.Colliders.CollisionMeshLoader;
import com.Engine.RenderEngine.Models.ModelLoader;
import com.Engine.RenderEngine.Shaders.RenderProperties;
import com.Engine.RenderEngine.Shaders.Shader;
import com.Engine.RenderEngine.Shaders.Default.DefaultRenderProperties;
import com.Engine.RenderEngine.Shaders.Default.Model;
import com.Engine.RenderEngine.Util.Camera;
import com.Engine.Util.Vectors.Vector2f;
import com.Engine.Util.Vectors.Vector3f;

import Utils.ImageLoader;

public class WrapperStaticBody {
	private Model model;
	private RenderProperties renderProperties;
	private StaticBody staticBody;
	private Rectangle2D.Float hitBox;
	
	public WrapperStaticBody(Model model, RenderProperties renderProperties, StaticBody staticBody, Vector2f twoDLocation, Vector2f twoDDimensions) {
		this.model = model;
		this.renderProperties = renderProperties;
		this.staticBody = staticBody;
		
		hitBox = new Rectangle2D.Float(twoDLocation.x, twoDLocation.y, twoDDimensions.x, twoDDimensions.y);
		
		setPosition2D(twoDLocation);
	}
	
	public WrapperStaticBody(RenderProperties renderProperties, Vector2f twoDLocation, Vector2f twoDDimensions, String objPath, String modelTexturePath, Shader modelShader) {
		this(new Model(ModelLoader.loadOBJ(objPath)), renderProperties, new StaticBody(CollisionMeshLoader.loadObj(objPath)), twoDLocation, twoDDimensions);
		
		model.setTexture(ImageLoader.loadTexture(modelTexturePath));
		model.setShader(modelShader);
	}
	
	public WrapperStaticBody(Vector2f twoDLocation, Vector2f twoDDimensions, String objPath, String modelTexturePath, Shader modelShader) {
		this(new DefaultRenderProperties(), twoDLocation, twoDDimensions, objPath, modelTexturePath, modelShader);
	}
	
	/**
	 * This Will likely be removed
	 */
	public WrapperStaticBody(Vector2f twoDLocation, Vector2f twoDDimensions, String name, Shader modelShader) {
		this(twoDLocation, twoDDimensions, ImageLoader.MODEL_PATH + name + ".obj", ImageLoader.TEXTURE_PATH + name + ".png", modelShader);
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
	
	public void setZ(float z) {
		staticBody.setPosition(new Vector3f(staticBody.getPosition().x, staticBody.getPosition().y, z));
		renderProperties.getTransform().setTranslation(new Vector3f(renderProperties.getTransform().getTranslation().x, renderProperties.getTransform().getTranslation().y, z));
		hitBox.y = z;
	}
	
	public void render(Camera camera) { model.render(renderProperties, camera); }
	
	public Model getModel() { return model; }
	public StaticBody getStaticBody() { return staticBody; }
	public RenderProperties getRenderProperties() { return renderProperties; }
	public Rectangle2D.Float getHitBox() { return hitBox; }
}
