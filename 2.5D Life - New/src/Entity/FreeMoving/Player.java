package Entity.FreeMoving;

import com.Engine.RenderEngine.Textures.Texture2D;

import Entity.FreeMoving.AI.Action.Human.GoToAction;
import Entity.FreeMoving.AI.Action.Human.MoveToAction;
import Entity.WorldObjects.WorldObject;
import Entity.WorldObjects.Lot.Lot;
import Entity.WrapperBodies.WrapperModel;
import Main.Handler;
import Utils.Util;

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
//		super.update(delta);
		needManager.update(delta);
		actionQueue.update(delta);
		
//		if(actionQueue.getActions().isEmpty()) {
//			Random random = new Random();
//			
//			Lot lot = handler.getWorld().getLot(getLocation());
//			addAction(new GoToAction(lot, this, random.nextInt(lot.getWidth()), random.nextInt(lot.getHeight())));
//		}
		
		if(handler.getMouseManager().keyJustReleased(1)) {
			handler.getMouseManager().updatePicker(s -> {
				Lot lot = handler.getWorld().getLot(getPosition());
				WorldObject object = lot.getTiles()[(int) s.getPosition().x][(int) s.getPosition().z].getObject();
				if(object != null) {
					addAction(new MoveToAction(handler, handler.getWorld().getLot(getPosition()), object, this));//, object.getPosition2D().add(object.getFront()).truncate()));
// 					addAction(new GoToAction(handler, handler.getWorld().getLot(getPosition()), this, (int) (s.getPosition().x + object.getFront().x), (int) (s.getPosition().z + object.getFront().y)));
// 					addAction(new TurnToAction(this, object.getPosition2D()));
				} else {
					addAction(new GoToAction(handler, handler.getWorld().getLot(getPosition()), this, (int) s.getPosition().x, (int) s.getPosition().z));
				}
			}, delta);
		}
	}
	
	@Override
	public void render() {
		body.render();
		
//		model.render(new PhysicsRenderProperties(new Transform(body.getPosition3D().subtract(new Vector3f(body.getWidth() / 2, 0, body.getHeight() / 2)), new Vector3f(0), new Vector3f(1)), new Vector3f(1, 0, 0), true));
	}
}
