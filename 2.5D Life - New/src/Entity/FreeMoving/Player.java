package Entity.FreeMoving;

import java.util.Random;

import com.Engine.RenderEngine.Textures.Texture2D;

import Entity.FreeMoving.AI.Action.GoToAction;
import Entity.WorldObjects.Lot.Lot;
import Entity.WrapperBodies.WrapperModel;
import Main.Handler;

public class Player extends Human {
//	private Model model;
	
	public Player(Handler handler, WrapperModel wrapperModel, Texture2D texture, String name) {
		super(handler, wrapperModel, texture, name);
		
//		ModelData modelData = new ModelData();
//		model = new Model(modelData);
//		
//		modelData.storeDataInAttributeList(Shader.ATTRIBUTE_LOC_POSITIONS, 3, new float[] {
//				0, 0, 0,					body.getWidth(), 0, 0,
//				0, 0, body.getHeight(),		body.getWidth(), 0, body.getHeight()	     
//		}, false);
//		
//		modelData.loadIndicies(new int[] {
//				1,0,2,	
//				1,2,3,
//		});
//		
//		model.setShader(Game.physicsShader);
	}

	@Override
	public void update(float delta) {
		super.update(delta);
		
		if(actionQueue.getActions().isEmpty()) {
			Random random = new Random();
			
			Lot lot = handler.getWorld().getLot(getLocation());
			addAction(new GoToAction(lot, this, random.nextInt(lot.getWidth()), random.nextInt(lot.getHeight())));
		}
		
		if(handler.getMouseManager().keyJustReleased(1)) {
			handler.getMouseManager().updatePicker(s -> {
				addAction(new GoToAction(handler.getWorld().getLot(getLocation()), this, (int) s.getPosition().x, (int) s.getPosition().z));
			}, delta);
		}
		
//		if(Keyboard.isKeyDown(Keyboard.KEY_UP)) 
//			move(new Vector2f(0, -movementSpeed.y), delta);
//		
//		if(Keyboard.isKeyDown(Keyboard.KEY_LEFT)) 
//			move(new Vector2f(-movementSpeed.x, 0), delta);
//		
//		if(Keyboard.isKeyDown(Keyboard.KEY_DOWN)) 
//			move(new Vector2f(0, movementSpeed.y), delta);
//		
//		if(Keyboard.isKeyDown(Keyboard.KEY_RIGHT)) 
//			move(new Vector2f(movementSpeed.x, 0), delta);
	}
	
	@Override
	public void render() {
		body.render();
		
//		model.render(new PhysicsRenderProperties(new Transform(body.getPosition3D().subtract(new Vector3f(body.getWidth() / 2, 0, body.getHeight() / 2)), new Vector3f(0), new Vector3f(1)), new Vector3f(1, 0, 0), true));
	}
}
