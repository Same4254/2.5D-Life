package Entity.WorldObjects.Items;

public abstract class Item {
	private float integrity;
	
	public Item() {
		integrity = 100;
	}

	public void degrade(float amount) { integrity -= amount; }
	public float getIntegrity() { return integrity; }
	
	public boolean update(float delta) {
		if(integrity < 0)
			return false;
		return true;
	}
	
	public abstract Item clone();
}
