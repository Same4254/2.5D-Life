package World.Tiles.Render;

import com.Engine.RenderEngine.Instancing.IRenderableInstance;
import com.Engine.RenderEngine.Instancing.InstanceVBO;
import com.Engine.RenderEngine.Models.ModelData.ModelData;
import com.Engine.RenderEngine.Shaders.Renderer;
import com.Engine.RenderEngine.Shaders.Shader;
import com.Engine.RenderEngine.Shaders.Default.DefaultRenderProperties;

public class TileInstanceModel implements IRenderableInstance<DefaultRenderProperties> {
	private static final int INSTANCE_COUNT = 40000;
	private static final int INSTANCE_SIZE = 4;
	public static final TileShader TILE_SHADER = new TileShader();
	private static ModelData modelData = new ModelData();
	
	static {
		TILE_SHADER.getRenderer().usingFrustumCulling(false);
		
		modelData.loadIndicies(new int[] {
				1, 0, 2,
				3, 1, 2
		});
		
		modelData.storeDataInAttributeList(Shader.ATTRIBUTE_LOC_POSITIONS, 2, new float[] {
				-.5f,-.5f,	.5f,-.5f,
				-.5f,.5f,	.5f,.5f
		}, false);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void render(DefaultRenderProperties properties) {
		((Renderer<TileInstanceModel, DefaultRenderProperties, ?>) TILE_SHADER.getRenderer()).addModel(this, properties);
	}

	@Override
	public Shader getShader() {
		return TILE_SHADER;
	}

	@Override
	public ModelData getModelData() {
		return modelData;
	}

	@Override
	public int getInstanceLength() {
		return INSTANCE_SIZE;
	}

	@Override
	public int getInstanceCount() {
		return INSTANCE_COUNT;
	}

	@Override
	public void addInstanceAttributes(InstanceVBO vbo) {
		int vao = modelData.getVAOId();
		
		vbo.nextAttribute(vao, TileShader.ATTRIBUTE_LOC_LIGHT_INFO, 2, 1);
		vbo.nextAttribute(vao, TileShader.ATTRIBUTE_LOC_TRANSLATION, 2, 1);
	}
}
