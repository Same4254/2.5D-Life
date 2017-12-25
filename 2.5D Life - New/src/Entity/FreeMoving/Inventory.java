package Entity.FreeMoving;

import java.util.ArrayList;

import Entity.FreeMoving.AI.Action.Action;
import Entity.WorldObjects.WorldObject;
import Entity.WorldObjects.Items.Item;
import Main.Handler;

public class Inventory {
	private Handler handler;
	private Entity entity;
	private Item heldItem;
	private ArrayList<Item> inventory;
	
	public Inventory(Handler handler, Entity entity) {
		this.handler = handler;
		this.entity = entity;
		inventory = new ArrayList<>();
	}
	
	public Item getHeldItem() { return heldItem; }
	public void setHeldItem(Item item) {
		if(heldItem != null) 
			addItem(heldItem);
		heldItem = item;
	}
	
	public void storeHeldItemInInventory() {
		if(heldItem != null) 
			addItem(heldItem);
		heldItem = null;
	}
	
	public Action storeHeldItemAction() {
		return new StoreHeldItem(handler, entity);
	}
	
	public void addItem(Item item) { inventory.add(item); }
	
	public Item removeItem(int index) { return inventory.remove(index); }
	public boolean removeItem(Item item) { return inventory.remove(item); }
	
	public Item removeHeldObject() { 
		Item held = heldItem.clone();
		heldItem = null;
		return held;
	}
}

class StoreHeldItem extends Action {
	private Entity entity;
	
	public StoreHeldItem(Handler handler, Entity entity) {
		super(handler);
		
		this.entity = entity;
	}

	@Override
	public void start() {
		super.start();
		
		entity.getInventory().storeHeldItemInInventory();;
	}
	
	@Override
	public void update(float delta) {
		complete = true;
	}
}