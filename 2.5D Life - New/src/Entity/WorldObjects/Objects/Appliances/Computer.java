package Entity.WorldObjects.Objects.Appliances;

import Entity.FreeMoving.Entity;
import Entity.FreeMoving.Entity.Living;
import Entity.FreeMoving.AI.Action.Action;
import Entity.FreeMoving.AI.Action.MultiAction;
import Entity.WorldObjects.WorldObject;
import Entity.WorldObjects.Items.Item;
import Entity.WorldObjects.Lot.Lot;
import Main.Assets;
import Main.Handler;

public class Computer extends Appliance {

	public Computer(Handler handler, Lot lot) {
		super(handler, lot, Assets.computerModel, Assets.computerTexture);
		
	}

	@Override
	public void update(float delta) {

	}

	@Override
	public Action getAction(Entity entity, Living reason) {
		return new ProgramAction(handler, entity, this);
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
	public WorldObject clone() { return new Computer(handler, lot); }
}

class ProgramAction extends MultiAction {
	private Entity entity;
	private Computer computer;
	
	public ProgramAction(Handler handler, Entity entity, Computer computer) {
		super(handler);
		
		this.entity = entity;
		this.computer = computer;
	}
	
	@Override 
	public void start() {
		super.start();
		
	}
}

class ProgramSubAction extends Action {
	private Entity entity;
	private Computer computer;

	public ProgramSubAction(Handler handler, Entity entity, Computer computer) {
		super(handler);

		this.entity = entity;
		this.computer = computer;
	}

	@Override
	public void update(float delta) {
		
	}
}
