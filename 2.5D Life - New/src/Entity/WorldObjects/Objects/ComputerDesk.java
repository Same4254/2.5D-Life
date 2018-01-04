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

public class ComputerDesk extends MultiTileObject {

	public ComputerDesk(Handler handler, Lot lot) {
		super(handler, lot, Assets.computerDeskModel, Assets.computerDeskTexture);
		
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
		skills.put(Living.Programming, 100);
	}

	@Override
	protected void initInventory() {
		
	}
	
	@Override
	public WorldObject clone() { return new ComputerDesk(handler, lot); }

	@Override
	public Vector2f[] getApplianceLocations() {
		return new Vector2f[] {
				new Vector2f()
		};
	}
}
