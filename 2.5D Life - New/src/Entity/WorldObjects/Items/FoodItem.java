package Entity.WorldObjects.Items;

public abstract class FoodItem extends Item {
	private float value;
	
	public FoodItem(float value) {
		this.value = value;
	}
	
	public float getValue() { return value; }
}
