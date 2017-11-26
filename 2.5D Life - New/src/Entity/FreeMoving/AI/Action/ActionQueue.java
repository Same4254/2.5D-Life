package Entity.FreeMoving.AI.Action;

import java.util.ArrayList;

public class ActionQueue {
	private ArrayList<Action> actions;
	
	public ActionQueue() {
		actions = new ArrayList<>();
	}

	public void update(float delta) {
		if(!actions.isEmpty()) {
			if(actions.get(0).isComplete()) {
				actions.remove(0);
			} else if(!actions.get(0).hasStarted()) {
				actions.get(0).start();
			} else
				actions.get(0).update(delta);
		}
	}
	
	public ArrayList<Action> getActions() { return actions; }
	public boolean add(Action a) { return actions.add(a); }
}
