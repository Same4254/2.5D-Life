package Entity.WorldObjects.Objects;

import com.Engine.PhysicsEngine.Render.PhysicsRenderProperties;
import com.Engine.RenderEngine.Models.ModelData.ModelData;
import com.Engine.RenderEngine.Shaders.Shader;
import com.Engine.RenderEngine.Shaders.Default.Model;
import com.Engine.RenderEngine.Util.RenderStructs.Transform;
import com.Engine.Util.Vectors.Vector3f;

import Entity.WorldObjects.MultiTileObject;
import Main.Assets;
import Main.Game;
import Main.Handler;

public class Fridge extends MultiTileObject {
	private Model model;
	private Model collision;
	
	public Fridge(Handler handler) {
		super(handler, Assets.fridgeModel, Assets.fridgeTexture);

		collision = new Model(Assets.fridgeModel.getModel().getModelData());
		collision.setShader(Game.physicsShader);
		
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
	public void render() {
		body.getRenderProperties().getTransform().setTranslation(body.getPosition3D().add(0, .25, 0));
		body.render();
		collision.render(new PhysicsRenderProperties(new Transform(body.getStaticBody().getPosition(), body.getStaticBody().getRotation(), body.getStaticBody().getScale()), new Vector3f(0, 1, 0), false));
		model.render(new PhysicsRenderProperties(new Transform(body.getPosition3D().subtract(new Vector3f(body.getWidth() / 2, 0, body.getHeight() / 2)), new Vector3f(0), new Vector3f(1)), new Vector3f(0, 1, 0), true));
	}
	
	@Override
	public Fridge clone() {
		return new Fridge(handler);
	}
}
