package Entity.FreeMoving.AI.Action;

import Main.Handler;

public abstract class MultiAction extends Action {
	protected ActionQueue subActions;
	
	public MultiAction(Handler handler) {
		super(handler);
		
		subActions = new ActionQueue();
	}

	@Override
	public void update(float delta) {
		if(started && subActions.getActions().isEmpty()) 
			complete = true;
		else
			subActions.update(delta);
	}
}
