package Entity.FreeMoving.AI.Living.Skills;

public class SkillManager {
	private Skill[] skills;
	
	public SkillManager() {
		skills = new Skill[2];
		
		skills[0] = new Programming();
		skills[1] = new Cooking();
	}
	
	public Programming getProgrammingSkill() { return (Programming) skills[0]; }
	public Cooking getCookingSkill() { return (Cooking) skills[1]; }
}
