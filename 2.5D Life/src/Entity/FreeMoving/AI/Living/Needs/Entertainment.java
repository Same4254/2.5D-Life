package Entity.FreeMoving.AI.Living.Needs;

import Entity.FreeMoving.Entity.Living;

public class Entertainment extends Need {

	public Entertainment() {
		value = 80;
	}
	
	@Override
	public Living asEnum() {
		return Living.Entertainment;
	}
}
