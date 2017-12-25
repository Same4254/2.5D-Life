package Entity.FreeMoving.AI.Living.Needs;

import Entity.FreeMoving.Entity.Living;

public abstract class Need {
	private float value;
	private float deteriation;
	
	public Need() {
		value = 100;
		deteriation = -1;
	}
	
	public void update(float delta) {
		float newValue = value + (deteriation * delta);
		
		if(newValue <= 100 && newValue >= 0)
			value = newValue;
	}

	public void add(float amount) {
		value += amount;
		
		if(value > 100)
			value = 100;
		if(value < 0)
			value = 0;
	}
	
	public abstract Living asEnum();
	public float getValue() { return value; }
}
