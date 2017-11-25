package Entity.FreeMoving.AI.Skills;

import java.util.HashMap;

public abstract class Skill {
	public static final HashMap<Integer, Integer> xpPerLevel = createSkillTable();
	
	private static HashMap<Integer, Integer> createSkillTable() {
		HashMap<Integer, Integer> skillLevels = new HashMap<>();
		skillLevels.put(1, 100);
		skillLevels.put(2, 200);
		skillLevels.put(3, 300);
		skillLevels.put(4, 400);
		skillLevels.put(5, 500);
		skillLevels.put(6, 600);
		skillLevels.put(7, 700);
		skillLevels.put(8, 800);
		skillLevels.put(9, 900);
		skillLevels.put(10, 1000);
		
		return skillLevels;
	}
	
	private int level;
	private float exp;
	private float factor;
	
	public Skill(float traitFactor) {
		this.factor = traitFactor;
		this.level = 1;
	}
	
	public int getLevel() { return level; }
	public float getExp() { return exp; }
}
