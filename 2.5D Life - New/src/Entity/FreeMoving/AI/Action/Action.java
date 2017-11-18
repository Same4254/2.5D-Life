package Entity.FreeMoving.AI.Action;

public abstract class Action {
	protected boolean started;
	protected boolean complete;
	
	public Action() {
		
	}

	public void start() {
		started = true;
	}
	
	public abstract void update(float delta);
	
	public boolean isComplete() { return complete; }
	public boolean hasStarted() { return started; }
}
