package Entity.FreeMoving.AI.Action;

import Main.Handler;

public abstract class Action {
	protected Handler handler;
	
	protected boolean started;
	protected boolean complete;
	
	public Action(Handler handler) {
		this.handler = handler;
	}

	public void start() {
		started = true;
	}
	
	public abstract void update(float delta);
	
	public boolean isComplete() { return complete; }
	public boolean hasStarted() { return started; }
}
