package World.Tiles.Render;

import java.util.List;

import org.lwjgl.util.vector.Matrix4f;

import com.Engine.RenderEngine.Lights.Light;
import com.Engine.RenderEngine.Models.ModelData.Attribute;
import com.Engine.RenderEngine.Shaders.Shader;
import com.Engine.RenderEngine.Shaders.Uniforms.Uniform;
import com.Engine.RenderEngine.Shaders.Uniforms.UniformFloat;
import com.Engine.RenderEngine.Shaders.Uniforms.UniformMat4;
import com.Engine.RenderEngine.Shaders.Uniforms.UniformTexture;
import com.Engine.RenderEngine.Shaders.Uniforms.UniformVec3;
import com.Engine.Util.Vectors.Vector3f;

public class TileShader extends Shader {
	private static final String VS_PATH = "TileShader.vsh", FS_PATH = "TileShader.fsh"; 
	
	public static final Attribute ATTRIBUTE_LOC_LIGHT_INFO = nextAttribId(TileShader.class);
	public static final Attribute ATTRIBUTE_LOC_TRANSLATION  = nextAttribId(TileShader.class);
	public static final Attribute ATTRIBUTE_LOC_OFFSET = nextAttribId(TileShader.class);
	
	private static final int LIGHT_COUNT = 4;
	
	@Uniform private UniformVec3 skyColor;
	@Uniform private UniformFloat fogDensity;
	@Uniform private UniformFloat fogGradient;

	@Uniform(size=LIGHT_COUNT) private UniformVec3 lightPosition[];
	@Uniform(size=LIGHT_COUNT) private UniformVec3 lightColor[];
	@Uniform(size=LIGHT_COUNT) private UniformVec3 lightAttenuation[];
//	@Uniform private UniformFloat shineDamper;
//	@Uniform private UniformFloat reflectivity;

	@Uniform private UniformFloat numberOfRows;
//	@Uniform private UniformVec2 offset;
	
	@Uniform private UniformMat4 projectionMatrix;
	@Uniform private UniformMat4 viewMatrix;
	
	@Uniform private UniformTexture texture0;
	
	public TileShader() {
		super(VS_PATH, FS_PATH, TileRenderer.class);

	}

	@Override
	protected void bindAttributies() {
		super.bindAttribute(ATTRIBUTE_LOC_POSITIONS, "position");

		super.bindAttribute(ATTRIBUTE_LOC_TRANSLATION, "translation");
		super.bindAttribute(ATTRIBUTE_LOC_LIGHT_INFO, "lightInfo");
		super.bindAttribute(ATTRIBUTE_LOC_OFFSET, "offset");
	}
	
	public void loadNumberOfRows(int numberOfRows) {
		this.numberOfRows.load((float) numberOfRows);
	}
	
	public void loadProjectionMatrix(Matrix4f projectionMatrix) {
		this.projectionMatrix.load(projectionMatrix);
	}
	
	public void loadViewMatrix(Matrix4f viewMatrix) {
		this.viewMatrix.load(viewMatrix);
	}
	
	public void loadFogValues(float density, float gradient) {
		this.fogDensity.load(density);
		this.fogGradient.load(gradient);
	}
	
	public void loadSkyColor(Vector3f skyColor) {
		this.skyColor.load(skyColor);
	}
	
	public void loadLights(List<Light> lights) {	
		for(int i = 0; i < LIGHT_COUNT; i ++) {
			if(i < lights.size()) {
				this.lightPosition[i].load(lights.get(i).getPosition());
				this.lightColor[i].load(lights.get(i).getColor());
				this.lightAttenuation[i].load(lights.get(i).getAttenuation());
			} else {
				this.lightPosition[i].load(new Vector3f());
				this.lightColor[i].load(new Vector3f());		
				this.lightAttenuation[i].load(new Vector3f(1, 0, 0));		
			}
		}
	}
	
	public void loadLights(Light... lights) {	
		for(int i = 0; i < LIGHT_COUNT; i ++) {
			if(i < lights.length) {
				this.lightPosition[i].load(lights[i].getPosition());
				this.lightColor[i].load(lights[i].getColor());
				this.lightAttenuation[i].load(lights[i].getAttenuation());
			} else {
				this.lightPosition[i].load(new Vector3f());
				this.lightColor[i].load(new Vector3f());		
				this.lightAttenuation[i].load(new Vector3f(1, 0, 0));		
			}
		}
	}
	
//	public void loadShineVariables(float damper, float reflectivity) {
//		this.shineDamper.load(damper);
//		this.reflectivity.load(reflectivity);
//	}
	
//	public void loadTextureAtlasIndex(float numberOfRows, Vector2f offset) {
//		this.numberOfRows.load(numberOfRows);
//		this.offset.load(offset);
//	}
}
