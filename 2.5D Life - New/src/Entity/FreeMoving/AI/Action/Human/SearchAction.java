package Entity.FreeMoving.AI.Action.Human;

import Entity.FreeMoving.Entity.Living;
import Entity.FreeMoving.Human;
import Entity.FreeMoving.AI.Action.Action;
import Entity.FreeMoving.AI.Action.ActionQueue;
import Entity.FreeMoving.AI.Action.MultiAction;
import Entity.WorldObjects.WorldObject;
import Entity.WorldObjects.Items.Item;
import Main.Handler;

public class SearchAction extends MultiAction {
	private Human human;
	private WorldObject worldObject;
	private Living reason;
	
	public SearchAction(Handler handler, Human human, WorldObject worldObject, Living reason) {
		super(handler);

		this.human = human;
		this.worldObject = worldObject;
		this.reason = reason;
	}

	@Override
	public void start() {
		super.start();
		
		subActions.add(human.getInventory().storeHeldItemAction());
		subActions.add(new MoveToAction(handler, handler.getWorld().getLot(human.getPosition()), worldObject, human));
		subActions.add(new Search(handler, human, worldObject, reason));
	}
}

class Search extends Action {
	private Human human; 
	private WorldObject worldObject;
	private Living reason;
	
	public Search(Handler handler, Human human, WorldObject worldObject, Living reason) {
		super(handler);
		
		this.human = human;
		this.worldObject = worldObject;
		this.reason = reason;
	}

	@Override
	public void start() {
		super.start();
		
		Item item = worldObject.searchForItem(human, reason);
		if(item != null) {
			human.getInventory().setHeldItem(item);
			System.out.println("Added: " + item);
		}
	}
	
	@Override
	public void update(float delta) {
		complete = true;
	}
}
