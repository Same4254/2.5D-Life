package Entity.FreeMoving.AI.Living.Needs;

import Entity.FreeMoving.Entity.Living;

public class Hunger extends Need {

	public Hunger() {
		value = 50;
	}

	@Override
	public Living asEnum() {
		return Living.Hunger;
	}
}
