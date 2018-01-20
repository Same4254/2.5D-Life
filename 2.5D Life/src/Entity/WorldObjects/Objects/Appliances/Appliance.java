package Entity.WorldObjects.Objects.Appliances;

import com.Engine.RenderEngine.Textures.Texture2D;
import com.Engine.Util.Vectors.Vector2f;

import Entity.WorldObjects.WorldObject;
import Entity.WorldObjects.Lot.Lot;
import Entity.WrapperBodies.WrapperModel;
import Main.Handler;
import World.Tiles.Tile;

public abstract class Appliance extends WorldObject {

	public Appliance(Handler handler, Lot lot, WrapperModel wrapperModel, Texture2D texture) {
		super(handler, lot, wrapperModel, texture);
		
	}
	
	@Override
	public boolean addToTile(Tile tile) {
		if(tile.containsAnything() && tile.getObject().getApplicationPosition() != null) {
			tiles.add(tile);
			
			tile.getObject().addAppliance(this);
			return true;
		}
		
		return false;
	}

	@Override
	public Vector2f[] getApplianceLocations() {
		return null;
	}
}
