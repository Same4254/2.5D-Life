package Entity.WorldObjects;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import com.Engine.Util.Vectors.Vector2f;
import com.Engine.Util.Vectors.Vector3f;

import Entity.WorldObjects.Objects.Appliances.Appliance;

public class ApplianceManager {
	private WorldObject hostObject;
	private HashMap<Vector2f, Appliance> locations;
	
	public ApplianceManager(WorldObject hostObject, Vector2f... positions) {
		this.hostObject = hostObject;
		
		locations = new HashMap<>();
		
		if(positions != null)
			for(Vector2f vector : positions) 
				locations.put(vector, null);
	}
	
	public boolean addAppliance(Appliance appliance) {
		for(Vector2f vector : locations.keySet()) {
			if(locations.get(vector) == null) {
				locations.put(vector, appliance);
				appliance.setAngle(hostObject.getBody().getRenderProperties().getTransform().getRotation().y);
				updatePosition(vector);
				return true;
			}
		}
		return false;
	}
	
	public Vector3f getAnAvailablePosition() {
		for(Vector2f vector : locations.keySet()) {
			if(locations.get(vector) == null) {
				return getPosition(vector);
			}
		}
		return null;
	}
	
	public boolean removeAppliance(Appliance appliance) {
		for(Vector2f vector : locations.keySet()) {
			if(locations.get(vector) == appliance) {
				locations.put(vector, null);
				return true;
			}
		}
		return false;
	}
	
	public void rotate(float angle, float angleDifference) {
		Collection<Vector2f> vectors = locations.keySet();
		for(Vector2f vector : vectors) {
			Appliance appliance = locations.get(vector);
			locations.remove(vector);
			
			Vector2f tempVector = vector.rotate(angleDifference).round();
			locations.put(tempVector, appliance);
			
			if(appliance != null) {
				updatePosition(tempVector);
				appliance.setAngle(angle);
			}
		}
	}
	
	public void syncPosition() {
		locations.forEach((vector, appliance) -> {
			if(appliance != null)
				updatePosition(vector);
		});
	}
	
	public void render() {
		for(Appliance appliance : locations.values()) {
			if(appliance != null) 
				appliance.render();
		}
	}
	
	public void update(float delta) {
		for(Appliance appliance : locations.values()) {
			if(appliance != null) 
				appliance.update(delta);
		}
	}
	
	private void updatePosition(Vector2f position) {
		locations.get(position).setPosition3D(getPosition(position).subtract(locations.get(position).getWidth() / 2, 0, locations.get(position).getHeight() / 2));
	}
	
	private Vector3f getPosition(Vector2f position) {
		return hostObject.getPosition3D().add(position.x, hostObject.getHeightY(), position.y);
	}
	
	public ArrayList<Appliance> getAppliances() {
		ArrayList<Appliance> temp = new ArrayList<Appliance>();
		for(Appliance appliance : locations.values()) {
			if(appliance != null)
				temp.add(appliance);
		}
		return temp;
	}
}
