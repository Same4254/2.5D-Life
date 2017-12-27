package Entity.FreeMoving.AI.Living.Needs;

import Entity.FreeMoving.Entity.Living;

public class Entertainment extends Need {

	public Entertainment() {
		
	}
	
	@Override
	public Living asEnum() {
		return Living.Entertainment;
	}
}
