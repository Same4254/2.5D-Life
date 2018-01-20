package Entity.FreeMoving.AI.Living.Needs;

import Entity.FreeMoving.Entity.Living;

public class Sleep extends Need {

	public Sleep() {
		value = 91;
	}
	
	@Override
	public Living asEnum() {
		return Living.Sleep;
	}

}
