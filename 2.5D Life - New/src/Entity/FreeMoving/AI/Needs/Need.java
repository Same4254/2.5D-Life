package Entity.FreeMoving.AI.Needs;

public abstract class Need {
	protected float value;
	
	protected boolean buffing;
	protected long buffStart;
	protected long buffDuration;
	protected float defaultBuff;
	protected float alternateBuff;
	
	public Need() {
		value = 100;
	}
	
	public void update() {
		if(buffing) {
			value -= alternateBuff;
			if(System.currentTimeMillis() > buffStart + buffDuration)
				buffing = false;
		} else
			value -= defaultBuff;
		
	}
	
	public void buff(float seconds, float factor) {
		buffDuration = (long) (seconds * 1000);
		alternateBuff = defaultBuff * factor;
	}
	
	public float getValue() { return value; }
}
