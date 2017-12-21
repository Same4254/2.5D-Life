package Entity.WorldObjects.Objects;

import com.Engine.PhysicsEngine.Render.PhysicsRenderProperties;
import com.Engine.RenderEngine.Models.ModelData.ModelData;
import com.Engine.RenderEngine.Shaders.Shader;
import com.Engine.RenderEngine.Shaders.Default.Model;
import com.Engine.RenderEngine.Util.RenderStructs.Transform;
import com.Engine.Util.Vectors.Vector3f;

import Entity.WorldObjects.MultiTileObject;
import Entity.WorldObjects.WorldObject;
import Main.Assets;
import Main.Game;
import Main.Handler;

public class TV extends MultiTileObject {
	private Model model;
	
	public TV(Handler handler) {//Need to rotate the position
		super(handler, Assets.tvModel, Assets.tvTexture);

		ModelData modelData = new ModelData();
		model = new Model(modelData);
		
		modelData.storeDataInAttributeList(Shader.ATTRIBUTE_LOC_POSITIONS, 3, new float[] {
				0, 0, 0,					body.getWidth(), 0, 0,
				0, 0, body.getHeight(),		body.getWidth(), 0, body.getHeight()	     
		}, false);
		
		modelData.loadIndicies(new int[] {
				1,0,2,	
				1,2,3,
		});
		
		model.setShader(Game.physicsShader);
	}

	@Override
	public WorldObject clone() {
		return new TV(handler);
	}
	
	@Override
	public void render() {
		body.render();
		model.render(new PhysicsRenderProperties(new Transform(body.getPosition3D(), new Vector3f(0), new Vector3f(1)), new Vector3f(1, 0, 0), true));
	}
}
