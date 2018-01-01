package Entity.WorldObjects.Objects;

import java.util.ArrayList;
import java.util.Random;

import com.Engine.Util.Vectors.Vector2f;

import Entity.FreeMoving.Entity;
import Entity.FreeMoving.Entity.Living;
import Entity.FreeMoving.AI.Action.Action;
import Entity.FreeMoving.AI.Action.MultiAction;
import Entity.FreeMoving.AI.Action.Human.EatHeldItemAction;
import Entity.FreeMoving.AI.Action.Human.FindPlaceToSitAction;
import Entity.FreeMoving.AI.Action.Human.MoveToAction;
import Entity.WorldObjects.MultiTileObject;
import Entity.WorldObjects.Items.Cereal;
import Entity.WorldObjects.Items.FoodItem;
import Entity.WorldObjects.Items.Item;
import Entity.WorldObjects.Lot.Lot;
import Main.Assets;
import Main.Handler;

public class Fridge extends MultiTileObject {
	public static final ArrayList<FoodItem> quickMeals = generateQuickMeals();
	public static boolean magical = true;

	private static ArrayList<FoodItem> generateQuickMeals() {
		ArrayList<FoodItem> temp = new ArrayList<>();
		temp.add(new Cereal());
		
		return temp;
	}
	
	public Fridge(Handler handler, Lot lot) {
		super(handler, lot, Assets.fridgeModel, Assets.fridgeTexture);

		inventory.add(new Cereal());
		needs.put(Living.Hunger, 100);
	}
	
	@Override
	public void update(float delta) {

	}

	@Override
	public Action getAction(Entity entity, Living reason) {
		if(reason == Living.Hunger) {
			return new GetQuickMealAction(handler, entity, this);
		}
		
		return null;
	}

	@Override
	public Item searchForItem(Entity entity, Living reason) {
		if(reason == Living.Hunger && !inventory.isEmpty())
			return inventory.remove(0);
		return null;
	}

	@Override
	protected void initSkillsAndNeeds() {
		needs.put(Living.Hunger, 100);
		skills.put(Living.Cooking, 100);
	}

	@Override
	protected void initInventory() {
		
	}
	
	@Override
	public Fridge clone() { return new Fridge(handler, lot); }

	@Override
	public Vector2f[] getApplianceLocations() {
		return null;
	}
}

class GetLeftOversAction extends MultiAction {
	private Entity entity;
	private Fridge fridge;
	
	public GetLeftOversAction(Handler handler, Entity entity, Fridge fridge) {
		super(handler);
		
		this.entity = entity;
		this.fridge = fridge;
	}
	
	@Override
	public void start() {
		super.start();
		
		if(fridge.getInventory().isEmpty()) {
			complete = true;
			return;
		}
		
		subActions.add(new MoveToAction(handler, fridge, entity));
		subActions.add(new SearchForLeftOversAction(handler, entity, fridge));
		subActions.add(new FindPlaceToSitAction(handler, entity));
		subActions.add(new EatHeldItemAction(handler, entity));
	}
}

class SearchForLeftOversAction extends Action {
	private Entity entity;
	private Fridge fridge;
	
	public SearchForLeftOversAction(Handler handler, Entity entity, Fridge fridge) {
		super(handler);
		
		this.entity = entity;
		this.fridge = fridge;
	}
	
	@Override
	public void start() {
		if(!fridge.getInventory().isEmpty()) 
			entity.getInventory().setHeldItem(fridge.getInventory().remove(0));
		complete = true;
	}
	
	@Override
	public void update(float delta) {
		
	}
}

class GetQuickMealAction extends MultiAction {
	private Entity entity;
	private Fridge fridge;
	
	public GetQuickMealAction(Handler handler, Entity entity, Fridge fridge) {
		super(handler);

		this.entity = entity;
		this.fridge = fridge;
	}
	
	@Override
	public void start() {
		super.start();
		
		subActions.add(new MoveToAction(handler, fridge, entity));
		subActions.add(new GetQuickMealSubAction(handler, entity));
		subActions.add(new FindPlaceToSitAction(handler, entity));
		subActions.add(new EatHeldItemAction(handler, entity));
	}
}

class GetQuickMealSubAction extends Action {
	private Entity entity;

	public GetQuickMealSubAction(Handler handler, Entity entity) {
		super(handler);
		
		this.entity = entity;
	}

	@Override
	public void start() {
		super.start();
		
		entity.getInventory().setHeldItem(Fridge.quickMeals.get(new Random().nextInt(Fridge.quickMeals.size())).clone());
		complete = true;
	}
	
	@Override
	public void update(float delta) {

	}
}

class MakeMealAction extends MultiAction {

	public MakeMealAction(Handler handler) {
		super(handler);
		
	}
	
	@Override
	public void start() {
		super.start();
		
	}
}