package Entity.FreeMoving.AI.Living.Needs;

public class Need {
	private String name;
	private float value;
	private float deteriation;
	
	public Need(String name) {
		value = 100;
		deteriation = -1;
	}
	
	public void update(float delta) {
		float newValue = value + (deteriation * delta);
		
		if(newValue <= 100 && newValue >= 0)
			value = newValue;
	}
	
	public String getName() { return name; }
	public float getValue() { return value; }
}
