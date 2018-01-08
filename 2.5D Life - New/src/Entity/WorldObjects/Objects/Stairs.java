package Entity.WorldObjects.Objects;

import com.Engine.Util.Vectors.Vector2f;

import Entity.FreeMoving.Entity;
import Entity.FreeMoving.Entity.Living;
import Entity.FreeMoving.AI.Action.Action;
import Entity.WorldObjects.MultiTileObject;
import Entity.WorldObjects.WorldObject;
import Entity.WorldObjects.Items.Item;
import Entity.WorldObjects.Lot.Lot;
import Main.Assets;
import Main.Handler;

public class Stairs extends MultiTileObject {

	public Stairs(Handler handler, Lot lot) {
		super(handler, lot, Assets.stairModel, Assets.stairTexture);
		
	}

	@Override
	public void update(float delta) {
		
	}

	@Override
	public Action getAction(Entity entity, Living reason) {
		return null;
	}

	@Override
	public Item searchForItem(Entity entity, Living reason) {
		return null;
	}

	@Override
	public WorldObject clone() {
		return new Stairs(handler, lot);
	}

	@Override
	protected void initSkillsAndNeeds() {
		
	}

	@Override
	protected void initInventory() {
		
	}

	@Override
	public Vector2f[] getApplianceLocations() {
		return null;
	}
}
