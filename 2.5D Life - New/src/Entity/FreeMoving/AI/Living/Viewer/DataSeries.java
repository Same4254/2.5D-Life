package Entity.FreeMoving.AI.Living.Viewer;

public class DataSeries {
	private DataUpdater updater;
	
	private String name;
	private float value;
	
	public DataSeries(String name, DataUpdater updater) {
		this.updater = updater;
		this.name = name;
	}
	
	public void update() { value = updater.getValue(); }
	public String getName() { return name; }
	public float getValue() { return value; }
}
