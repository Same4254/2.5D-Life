package World.Tiles.Render;

import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_INT;
import static org.lwjgl.opengl.GL31.glDrawElementsInstanced;

import com.Engine.RenderEngine.Instancing.InstanceRenderer;
import com.Engine.RenderEngine.Instancing.InstanceVBO;
import com.Engine.RenderEngine.Shaders.Shader;
import com.Engine.RenderEngine.Shaders.Default.DefaultRenderProperties;
import com.Engine.RenderEngine.Textures.Texture2D;
import com.Engine.Util.Vectors.Vector2f;
import com.Engine.Util.Vectors.Vector3f;

import Main.Assets;

public class TileRenderer extends InstanceRenderer<TileInstanceModel, DefaultRenderProperties, TileShader> {

	public TileRenderer(Shader shader) {
		super(shader);
		
	}

	@Override
	public void prepInstanceVBO(InstanceVBO instanceVBO, TileInstanceModel model, DefaultRenderProperties property) {
		Vector3f translation = property.getTransform().getTranslation();
		instanceVBO.putAll(new Vector2f(property.getShineDamper(), property.getReflectivity()), new Vector2f(translation.x, translation.z));
	}

	protected void prepareOpenGL() {
		shader.bind();
		shader.loadProjectionMatrix(Shader.getProjectionMatrix());
		shader.loadViewMatrix(Shader.getViewMatrix());
		
		shader.loadFogValues(0.007f, 1.5f);
		shader.loadSkyColor(Shader.getSkyColor());
	}
	
	public void bindModel(TileInstanceModel model) {
		model.bind(); 

		TileShader.ATTRIBUTE_LOC_LIGHT_INFO.enable();
		TileShader.ATTRIBUTE_LOC_TRANSLATION.enable();
	}

	public void renderInstance(InstanceVBO vbo, TileInstanceModel model) {
		Assets.goldTexture.bind(0);
		glDrawElementsInstanced(GL_TRIANGLES, model.getIndiceCount(), GL_UNSIGNED_INT, 0, vbo.getRenderCount());
	}
	
	public void unbindModel(TileInstanceModel model) { 
		TileShader.ATTRIBUTE_LOC_LIGHT_INFO.disable();
		TileShader.ATTRIBUTE_LOC_TRANSLATION.disable();
		
		model.unbind(); 
	}
	
	protected void revertOpenGL() {
		Texture2D.unbind_2D(0);
		Shader.unbind();
	}
	
//	public void clear() {
//		super.clear();
//		super.instanceVBOs.clear();
//	}
}