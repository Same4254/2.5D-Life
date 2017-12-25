package Entity.WorldObjects.Items;

public class Cereal extends FoodItem {

	public Cereal() {
		super(40);
	}
	
	@Override
	public boolean update(float delta) {
		return super.update(delta);
	}
	
	@Override
	public Cereal clone() {
		return new Cereal();
	}
}
