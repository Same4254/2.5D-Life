package Entity.WorldObjects.Objects;

import Entity.FreeMoving.Entity;
import Entity.FreeMoving.Entity.Living;
import Entity.FreeMoving.AI.Action.Action;
import Entity.WorldObjects.MultiTileObject;
import Entity.WorldObjects.Items.Cereal;
import Entity.WorldObjects.Items.Item;
import Main.Assets;
import Main.Handler;

public class Fridge extends MultiTileObject {
	public static boolean magical = true;
	
	public Fridge(Handler handler) {
		super(handler, Assets.fridgeModel, Assets.fridgeTexture);

		inventory.add(new Cereal());
		needs.put(Living.Hunger, 100);
	}
	
	@Override
	public void update(float delta) {

	}

	@Override
	public Action getAction(Entity entity, Living reason) {
		if(reason == Living.Hunger) {
			
		}
		
		return null;
	}

	@Override
	public Item searchForItem(Entity entity, Living reason) {
		if(reason == Living.Hunger) {
			if(magical)
				return inventory.get(0).clone();
			else if(!inventory.isEmpty()){
				return inventory.remove(0);
			}
		}
		
		return null;
	}

	@Override
	protected void initSkillsAndNeeds() {
		
	}

	@Override
	protected void initInventory() {
		
	}
	
	@Override
	public Fridge clone() { return new Fridge(handler); }
}
