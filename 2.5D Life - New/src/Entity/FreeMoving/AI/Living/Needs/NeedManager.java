package Entity.FreeMoving.AI.Living.Needs;

public class NeedManager {
	private Need[] needs;
	
	public NeedManager() {
		needs = new Need[2];
		needs[0] = new Hunger();
		needs[1] = new Entertainment();
	}
	
	public void update(float delta) {
		for(Need n : needs)
			n.update(delta);
	}
	
	public Need getLowest() {
		Need lowest = needs[0];
		
		for(Need n : needs)
			if(n.getValue() < lowest.getValue())
				lowest = n;
			
		return lowest;
	}
	
	public Need[] getNeeds() { return needs; }
	public Hunger getHunger() { return (Hunger) needs[0]; }
	public Entertainment getEntertainment() { return (Entertainment) needs[1]; }
}
