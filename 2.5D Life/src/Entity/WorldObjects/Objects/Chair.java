package Entity.WorldObjects.Objects;

import com.Engine.Util.Vectors.Vector2f;

import Entity.FreeMoving.Entity;
import Entity.FreeMoving.Entity.Living;
import Entity.FreeMoving.AI.Action.Action;
import Entity.WorldObjects.WorldObject;
import Entity.WorldObjects.Items.Item;
import Entity.WorldObjects.Lot.Lot;
import Main.Assets;
import Main.Handler;

public class Chair extends WorldObject {

	public Chair(Handler handler, Lot lot) {
		super(handler, lot, Assets.chairModel, Assets.chairTexture);
		
	}
	
	@Override 
	public void update(float delta) {
		
	}

	@Override
	public WorldObject clone() {
		return new Chair(handler, lot);
	}

	@Override
	public void initSkillsAndNeeds() {
		
	}

	@Override
	public void initInventory() {
		
	}

	@Override
	public Vector2f[] getApplianceLocations() {
		return null;
	}

	@Override
	public Action getAction(Entity entity, Living reason) {
		return null;
	}

	@Override
	public Item searchForItem(Entity entity, Living reason) {
		return null;
	}
}
