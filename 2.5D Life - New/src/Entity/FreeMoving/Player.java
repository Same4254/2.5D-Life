package Entity.FreeMoving;

import org.lwjgl.input.Keyboard;

import com.Engine.PhysicsEngine.Render.PhysicsRenderProperties;
import com.Engine.RenderEngine.Models.ModelData.ModelData;
import com.Engine.RenderEngine.Shaders.Shader;
import com.Engine.RenderEngine.Shaders.Default.Model;
import com.Engine.RenderEngine.Textures.Texture2D;
import com.Engine.RenderEngine.Util.Camera;
import com.Engine.RenderEngine.Util.RenderStructs.Transform;
import com.Engine.Util.Vectors.Vector2f;
import com.Engine.Util.Vectors.Vector3f;

import Entity.FreeMoving.AI.Action.GoToAction;
import Entity.WrapperBodies.WrapperModel;
import Main.Game;
import Main.Handler;

public class Player extends Human {

	private Model model;
	
	public Player(Handler handler, WrapperModel wrapperModel, Texture2D texture) {
		super(handler, wrapperModel, texture);
		
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
	public void update(float delta) {
		super.update(delta);
		
		if(handler.getMouseManager().keyJustReleased(1)) {
			handler.getMouseManager().updatePicker(s -> {
				addAction(new GoToAction(handler.getWorld().getTestLot(), this, (int) s.getPosition().x, (int) s.getPosition().z));
			}, delta);
		}
		
		if(Keyboard.isKeyDown(Keyboard.KEY_UP)) 
			move(new Vector2f(0, -movementSpeed.y), delta);
		
		if(Keyboard.isKeyDown(Keyboard.KEY_LEFT)) 
			move(new Vector2f(-movementSpeed.x, 0), delta);
		
		if(Keyboard.isKeyDown(Keyboard.KEY_DOWN)) 
			move(new Vector2f(0, movementSpeed.y), delta);
		
		if(Keyboard.isKeyDown(Keyboard.KEY_RIGHT)) 
			move(new Vector2f(movementSpeed.x, 0), delta);
	}
	
	@Override
	public void render() {
		body.render();
		
		model.render(new PhysicsRenderProperties(new Transform(body.getPosition3D().subtract(new Vector3f(body.getWidth() / 2, 0, body.getHeight() / 2)), new Vector3f(0), new Vector3f(1)), new Vector3f(1, 0, 0), true));
	}
}
