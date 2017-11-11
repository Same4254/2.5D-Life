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

import Entity.WrapperBodies.WrapperModel;
import Main.Game;
import Main.Handler;
import Utils.ImageLoader;

public class Player extends Human {

	private Vector2f movementSpeed;
	
	private Model model;
	
	public Player(Handler handler, WrapperModel wrapperModel, Texture2D texture) {
		super(handler, wrapperModel, texture);
		
		movementSpeed = new Vector2f(8);
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
		if(Keyboard.isKeyDown(Keyboard.KEY_UP)) {
			if(!collide(handler.getWorld().getTestLot(), new Vector2f(0, -movementSpeed.y * delta)))
				body.add(0, -movementSpeed.y * delta);
		}
		
		if(Keyboard.isKeyDown(Keyboard.KEY_LEFT)) {
			if(!collide(handler.getWorld().getTestLot(), new Vector2f(-movementSpeed.x * delta, 0)))
				body.add(-movementSpeed.x * delta, 0);
		}
		
		if(Keyboard.isKeyDown(Keyboard.KEY_DOWN)) {
			if(!collide(handler.getWorld().getTestLot(), new Vector2f(0, movementSpeed.y * delta)))
				body.add(0, movementSpeed.y * delta);
		}
		
		if(Keyboard.isKeyDown(Keyboard.KEY_RIGHT)) {
			if(!collide(handler.getWorld().getTestLot(), new Vector2f(movementSpeed.x * delta, 0)))
				body.add(movementSpeed.x * delta, 0);
		}
	}
	
	@Override
	public void render(Camera camera) {
		body.render(camera);
		
		model.render(new PhysicsRenderProperties(new Transform(body.getPosition3D().subtract(new Vector3f(body.getWidth() / 2, 0, body.getHeight() / 2)), new Vector3f(0), new Vector3f(1)), new Vector3f(1, 0, 0), true), camera);
	}
}
