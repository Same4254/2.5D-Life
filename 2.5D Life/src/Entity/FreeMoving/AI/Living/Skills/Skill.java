package Entity.FreeMoving.AI.Living.Skills;

import Entity.FreeMoving.Entity.Living;

public abstract class Skill {
	public static final int[] XP_LEVELS = { 25, 50, 75, 100 };
	
	protected float value;
	protected int level;
	
	public Skill() {
		value = 0;
		level = 0;
	}
	
	public abstract Living asEnum();
	
	public float getValue() { return value; }
	public void add(float amount) { 
		value += amount;
		checkForLevelUp();
	}
	
	private void checkForLevelUp() {
		if(level < XP_LEVELS.length && value >= XP_LEVELS[level]) {
			value = 0;
			level++;
		}
	}
}
