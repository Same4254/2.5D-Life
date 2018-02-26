package Entity.FreeMoving;

import org.lwjgl.input.Keyboard;

import com.Engine.RenderEngine.Textures.Texture2D;
import com.Engine.Util.Vectors.Vector3f;

import Entity.FreeMoving.AI.PathFinding;
import Entity.FreeMoving.AI.PathFinding2;
import Entity.FreeMoving.AI.Action.Action;
import Entity.FreeMoving.AI.Action.Human.GoToAction;
import Entity.FreeMoving.AI.Action.Human.GoToBelowFloorAction;
import Entity.FreeMoving.AI.Action.Human.GoToNextFloorAction;
import Entity.FreeMoving.AI.Action.Human.MoveToAction;
import Entity.FreeMoving.AI.Action.Human.MovementFunction.MovementFunction;
import Entity.WorldObjects.WorldObject;
import Entity.WorldObjects.Lot.Lot;
import Entity.WrapperBodies.WrapperModel;
import Main.Handler;
import Utils.Util;
import World.Tiles.Tile;

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
//		body.getSoundSource().setVolume(100000000);
//		body.getSoundSource().setLooping(true);
//		body.getSoundSource().play(Assets.bounceSoundBuffer);
//		
//		SoundSource source = new SoundSource();
//		source.setVolume(110000000);
//		source.setLooping(true);
//		source.play(Assets.bounceSoundBuffer);
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
		
		if(handler.getKeyManager().keyJustPressed(Keyboard.KEY_O)) {
			actionQueue.getActions().clear();
			body.setPosition3D(new Vector3f());
		}
		
		if(handler.getKeyManager().keyJustPressed(Keyboard.KEY_J))
			actionQueue.add(new MovementFunction(handler, this, MovementFunction.stairMovement, 0, 4, true, false, false));
		
		if(handler.getKeyManager().keyJustPressed(Keyboard.KEY_K))
			actionQueue.add(new MovementFunction(handler, this, MovementFunction.stairMovement, 0, 4, false, true, false));

		if(handler.getKeyManager().keyJustPressed(Keyboard.KEY_L))
			actionQueue.add(new MovementFunction(handler, this, MovementFunction.stairMovement, 0, 4, false, false, false));
		
		if(handler.getKeyManager().keyJustPressed(Keyboard.KEY_I))
			actionQueue.add(new MovementFunction(handler, this, MovementFunction.stairMovement, 0, 4, true, true, false));
		
		
		if(handler.getKeyManager().keyJustPressed(Keyboard.KEY_NUMPAD4))
			actionQueue.add(new MovementFunction(handler, this, MovementFunction.stairMovement, 0, 4, true, false, true));
		
		if(handler.getKeyManager().keyJustPressed(Keyboard.KEY_NUMPAD5))
			actionQueue.add(new MovementFunction(handler, this, MovementFunction.stairMovement, 0, 4, false, true, true));

		if(handler.getKeyManager().keyJustPressed(Keyboard.KEY_NUMPAD6))
			actionQueue.add(new MovementFunction(handler, this, MovementFunction.stairMovement, 0, 4, false, false, true));
		
		if(handler.getKeyManager().keyJustPressed(Keyboard.KEY_NUMPAD8))
			actionQueue.add(new MovementFunction(handler, this, MovementFunction.stairMovement, 0, 4, true, true, true));
		
		if(handler.getKeyManager().keyJustPressed(Keyboard.KEY_RETURN))
			actionQueue.add(new GoToNextFloorAction(handler, this));
		if(handler.getKeyManager().keyJustPressed(Keyboard.KEY_APOSTROPHE))
			actionQueue.add(new GoToBelowFloorAction(handler, this));
		
		if(handler.getMouseManager().keyJustReleased(1)) {
			handler.getMouseManager().updatePicker(s -> {
				Lot lot = handler.getWorld().getLot(getPosition2D());
				Tile tile = lot.getFloor(s.getPosition()).getTile(Util.to2D(s.getPosition()));
//				WorldObject object = tile.getObject();
//				if(object != null) {
//					addAction(new MoveToAction(handler, object, this));//, object.getPosition2D().add(object.getFront()).truncate()));
//// 					addAction(new GoToAction(handler, handler.getWorld().getLot(getPosition()), this, (int) (s.getPosition().x + object.getFront().x), (int) (s.getPosition().z + object.getFront().y)));
//// 					addAction(new TurnToAction(this, object.getPosition2D()));
//				} else {
//					addAction(new GoToAction(handler, lot, this, tile.getPosition2D()));
//				}
				
//				System.out.println(PathFinding2.findPath(handler, this, lot, s.getPosition()));
				
				for(Action a : PathFinding2.findPath(handler, this, lot, s.getPosition())) {
					System.out.println(a);
					addAction(a);
//					System.out.println(a);
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
