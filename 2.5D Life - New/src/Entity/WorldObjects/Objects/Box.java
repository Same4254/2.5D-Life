package Entity.WorldObjects.Objects;

import com.Engine.Util.Vectors.Vector2f;

import Entity.FreeMoving.Entity;
import Entity.FreeMoving.Entity.Living;
import Entity.FreeMoving.AI.Action.Action;
import Entity.WorldObjects.MultiTileObject;
import Entity.WorldObjects.Items.Item;
import Entity.WorldObjects.Lot.Lot;
import Main.Assets;
import Main.Handler;

public class Box extends MultiTileObject {

//	private SphereRender sphereRender;
	
	public Box(Handler handler, Lot lot) {
		super(handler, lot, Assets.boxModel, Assets.boxTexture);

//		sphereRender = new SphereRender(new CollisionEllipse(new Vector3f(2, .1, 2)), 30);
//		sphereRender.setShader(Game.physicsShader);
	}
	
	@Override
	public void update(float delta) {
		
	}

	@Override
	public Action getAction(Entity entity, Living reason) {
		return searchApplianceForAction(entity, reason);
	}

	@Override
	public Item searchForItem(Entity entity, Living reason) {
		return null;
	}

	@Override
	protected void initSkillsAndNeeds() {
		
	}

	@Override
	protected void initInventory() {
		
	}
	
	@Override
	public void render() {
		body.render();
		applianceManager.render();
		
//		Transform transform = body.getRenderProperties().getTransform().clone();
//		transform.translate(new Vector3f(0, 2, 0));
//		sphereRender.render(new PhysicsRenderProperties(transform, new Vector3f(0, 1, 0), false));
	}
	
	@Override
	public Box clone() { return new Box(handler, lot); }

	@Override
	public Vector2f[] getApplianceLocations() {
		return new Vector2f[] {
				new Vector2f(1)
		};
	}
}
