package Entity.FreeMoving.AI.Action.Human;

import Entity.FreeMoving.Human;
import Entity.FreeMoving.AI.Action.MultiAction;
import Entity.WorldObjects.Objects.TV;
import Main.Handler;

public class WatchTV extends MultiAction {
	private Human human;
	private TV tv;
	
	public WatchTV(Handler handler, Human human, TV tv) {
		super(handler);
		
		this.human = human;
		this.tv = tv;
	}

	@Override
	public void start() {
		super.start();
		
		
	}
}
