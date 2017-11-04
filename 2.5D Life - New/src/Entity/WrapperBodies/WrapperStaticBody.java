package Entity.WrapperBodies;

import com.Engine.PhysicsEngine.Bodies.StaticBody;
import com.Engine.PhysicsEngine.Detection.Colliders.CollisionMesh;
import com.Engine.RenderEngine.Shaders.RenderProperties;
import com.Engine.Util.Vectors.Vector2f;
import com.Engine.Util.Vectors.Vector3f;

public class WrapperStaticBody {
	private RenderProperties renderProperties;
	private StaticBody staticBody;
	
	public WrapperStaticBody(RenderProperties renderProperties, Vector2f twoDLocation, Vector2f twoDDimensions, StaticBody staticBody) {
		this.renderProperties = renderProperties;
		this.staticBody = staticBody;
	}
	
	public WrapperStaticBody(RenderProperties renderProperties, Vector2f twoDLocation, Vector2f twoDDimensions, CollisionMesh mesh) {
		this(renderProperties, twoDLocation, twoDDimensions, new StaticBody(mesh));
	}
	
	public Vector3f getPosition3D() { return renderProperties.getTransform().getTranslation(); }
	public Vector2f getPosition2D() { return new Vector2f(getX(), getZ()); }
	
	public void setPosition(Vector3f position) {
		staticBody.setPosition(position);
		renderProperties.getTransform().setTranslation(position);
	}
	
	public float getX() { return renderProperties.getTransform().getTranslation().x; }
	
	public void setX(float x) {
		staticBody.setPosition(new Vector3f(x, staticBody.getPosition().y, staticBody.getPosition().z));
		renderProperties.getTransform().setTranslation(new Vector3f(x, renderProperties.getTransform().getTranslation().y, renderProperties.getTransform().getTranslation().z));
	}
	
	public float getY() { return renderProperties.getTransform().getTranslation().y; }
	
	public void setY(float y) {
		staticBody.setPosition(new Vector3f(staticBody.getPosition().x, y, staticBody.getPosition().z));
		renderProperties.getTransform().setTranslation(new Vector3f(renderProperties.getTransform().getTranslation().x, y, renderProperties.getTransform().getTranslation().z));
	}
	
	public float getZ() { return renderProperties.getTransform().getTranslation().z; }
	
	public void setZ(float z) {
		staticBody.setPosition(new Vector3f(staticBody.getPosition().x, staticBody.getPosition().y, z));
		renderProperties.getTransform().setTranslation(new Vector3f(renderProperties.getTransform().getTranslation().x, renderProperties.getTransform().getTranslation().y, z));
	}
	
	public StaticBody getStaticBody() { return staticBody; }
	public RenderProperties getRenderProperties() { return renderProperties; }
}
