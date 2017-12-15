package Entity.FreeMoving.AI.Living.Needs;

public class Need {
	private float value;
	private float deteriation;
	
	public Need() {
		deteriation = -.1f;
	}
	
	public void update(float delta) {
		value += deteriation; //* delta;
	}
	
	public float getValue() { return value; }
}
