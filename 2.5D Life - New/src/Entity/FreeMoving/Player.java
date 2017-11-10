package Entity.FreeMoving;

import org.lwjgl.input.Keyboard;

import com.Engine.RenderEngine.Shaders.Shader;
import com.Engine.Util.Vectors.Vector2f;

import Main.Handler;

public class Player extends Human {

	private Vector2f movementSpeed;
	
	public Player(Handler handler, Vector2f twoDLocation, Vector2f twoDDimension, String objPath, String modelTexturePath, Shader modelShader) {
		super(handler, twoDLocation, twoDDimension, objPath, modelTexturePath, modelShader);
		
		movementSpeed = new Vector2f(1);
	}

	@Override
	public void update(float delta) {
		if(handler.getKeyManager().keyJustPressed(Keyboard.KEY_W)) {
			
		}
		
		if(handler.getKeyManager().keyJustPressed(Keyboard.KEY_A)) {
			
		}
		
		if(handler.getKeyManager().keyJustPressed(Keyboard.KEY_S)) {
					
		}
		
		if(handler.getKeyManager().keyJustPressed(Keyboard.KEY_D)) {
			
		}
	}
}
