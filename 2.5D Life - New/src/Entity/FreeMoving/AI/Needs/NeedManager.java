package Entity.FreeMoving.AI.Needs;

public class NeedManager {
	private Hunger hunger;
	private Sleep sleep;
	
	public NeedManager() {
		hunger = new Hunger();
		sleep = new Sleep();
	}
	
	public void update() {
		hunger.update();
		sleep.update();
	}

	public Hunger getHunger() { return hunger; }
	public Sleep getSleep() { return sleep; }
}
