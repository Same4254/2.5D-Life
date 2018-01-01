package Entity.WrapperBodies;

import com.Engine.PhysicsEngine.Bodies.StaticBody;
import com.Engine.PhysicsEngine.Detection.Colliders.CollisionMesh;
import com.Engine.RenderEngine.Shaders.RenderProperties;
import com.Engine.RenderEngine.Shaders.Default.DefaultRenderProperties;
import com.Engine.RenderEngine.Shaders.Default.Model;
import com.Engine.RenderEngine.Textures.Texture2D;
import com.Engine.Util.Vectors.Vector2f;
import com.Engine.Util.Vectors.Vector3f;

import Utils.Util;
import World.Tiles.Tile;

public class WrapperStaticBody {
	private WrapperModel wrapperModel;
	private Model model;
	private DefaultRenderProperties renderProperties;
	private StaticBody staticBody;
	
	private Vector3f min, max;
	private Vector3f size, position;
	
	public WrapperStaticBody(WrapperModel wrapperModel) {
		this(wrapperModel, new DefaultRenderProperties());
	}
	
	public WrapperStaticBody(WrapperModel wrapperModel, Texture2D texture2D) {
		this(wrapperModel, texture2D, new DefaultRenderProperties());
	}
	
	private WrapperStaticBody(WrapperModel wrapperModel, Texture2D texture2D, DefaultRenderProperties renderProperties) {
		this(wrapperModel, renderProperties);
		
		model.setTexture(texture2D);
	}
	
	private WrapperStaticBody(WrapperModel wrapperModel, DefaultRenderProperties renderProperties) {
		this.wrapperModel = wrapperModel;
		this.model = wrapperModel.getModel();
		this.renderProperties = renderProperties;
		
		CollisionMesh col = wrapperModel.getCollisionMesh();
		staticBody = new StaticBody(col);
		
		Vector3f radius = col.getOctree().getRoot().getBounds().getRadius().multiply(2);
		
		Vector2f dimensions = new Vector2f(radius.x, radius.z);
		dimensions = Util.roundNearestMultiple(dimensions, 1f / Tile.TILE_RESOLUTION);
		
		min = new Vector3f(dimensions.x, 0, dimensions.y).subtract(1).divide(-2).truncate().subtract(.5).setY(0);
		max = min.add(dimensions.x, 0, dimensions.y);
		
		Vector3f tempMin = min.transform(renderProperties.getTransformMatrix());
		Vector3f tempMax = max.transform(renderProperties.getTransformMatrix());
		
		size = tempMin.difference(tempMax).round();
		size.y = radius.y;
		
		setPosition2D(0, 0);
	}
	
	private WrapperStaticBody(Model model, CollisionMesh col, Vector3f size, Vector3f position, DefaultRenderProperties renderProperties) {
		this.model = new Model(model.getModelData());
		this.model.setTexture(model.getTexture());
		this.model.setShader(model.getShader());
		
		this.staticBody = new StaticBody(col);
		this.renderProperties = renderProperties.clone();
	}

	public WrapperStaticBody clone() {
		return new WrapperStaticBody(model, wrapperModel.getCollisionMesh(), size, position, renderProperties);
	}
	
	public Vector3f getPosition3D() { return staticBody.getPosition(); }
	public Vector2f getPosition2D() { return new Vector2f(getX(), getZ()); }
	
	public void setRotationPosition2D(Vector2f position) {
		staticBody.setPosition(new Vector3f(position.getX(), staticBody.getPosition().y, position.getY()));
		renderProperties.getTransform().setTranslation(new Vector3f(position.getX(), renderProperties.getTransform().getTranslation().y, position.getY()));
		
		Vector3f tempMin = min.transform(renderProperties.getTransformMatrix());
		Vector3f tempMax = max.transform(renderProperties.getTransformMatrix());

		float height = size.y;
		this.size = tempMin.difference(tempMax).round();
		size.y = height;
		
		this.position = tempMin.capMax(tempMax).add(.5);
	}
	
	public void setPosition2D(Vector2f position) {
		staticBody.setPosition(new Vector3f(position.getX(), staticBody.getPosition().y, position.getY()));
		renderProperties.getTransform().setTranslation(new Vector3f(position.getX(), renderProperties.getTransform().getTranslation().y, position.getY()));
		this.position = new Vector3f(position.x, this.position == null ? 0 : this.position.y, position.y);
	}
	
	public void setPosition3D(Vector3f position) {
		setY(position.y);
		setPosition2D(Util.to2D(position));
	}
	
	public void setRotationPosition3D(Vector3f position) {
		setY(position.y);
		setRotationPosition2D(Util.to2D(position));
	}
	
	public void setY(float y) {
		staticBody.getPosition().y = y;
		renderProperties.getTransform().getTranslation().y = y;
	}
	
	public void setPosition2D(float x, float z) { setPosition2D(new Vector2f(x, z)); }
	public Vector2f roundPosToGrid() { return Util.roundNearestMultiple(getPosition2D(), (float) (1.0 / Tile.TILE_RESOLUTION)); }

	public Vector2f getDimensions() { return new Vector2f(size.x, size.z); }
	public Vector3f getDimensions3D() { return size; }
	
	public float getWidth() { return size.x; } 
	public float getHeight() { return size.z; } 
	public float getHeightY() { return size.y; }
	
	public float getX() { return position.x; }
	public void addX(float amount) { setX(getX() + amount); }
	public void setX(float x) { setPosition2D(x, getZ()); }
	
	public float getZ() { return position.z; }
	public void addZ(float amount) { setZ(getZ() + amount); }
	public void setZ(float z) { setPosition2D(getX(), z); }
	
	public void setAngle(float angle) {
		renderProperties.getTransform().setRotation(new Vector3f(0, angle, 0));
		staticBody.setRotation(new Vector3f(0, angle, 0));
		
		Vector3f tempMin = min.transform(renderProperties.getTransformMatrix());
		Vector3f tempMax = max.transform(renderProperties.getTransformMatrix());
		
		float height = size.y;
		size = tempMin.difference(tempMax).round();
		size.y = height;
		
		position = tempMin.capMax(tempMax).add(.5);
	}
	
	public void add(float x, float z) { addX(x); addZ(z); }
	public void add(Vector2f amount) { addX(amount.x); addZ(amount.y); }
	
	public void render() { model.render(renderProperties); }
	
	public Model getModel() { return model; }
	public StaticBody getStaticBody() { return staticBody; }
	public DefaultRenderProperties getRenderProperties() { return renderProperties; }
}
