package Entity.WorldObjects.Lot.Edit;

import java.util.ArrayList;

import org.lwjgl.input.Keyboard;

import Entity.WorldObjects.Lot.Lot;
import Main.Handler;

public class LotEdit {
	private Handler handler;
	
	private ArrayList<EditMode> editModes;
	private int index;
	
	private int floorLevel = 0;
	private boolean enabled;
	
	public LotEdit(Handler handler, Lot lot) {
		this.handler = handler;
		
		editModes = new ArrayList<>();
		editModes.add(new WorldObjectEdit(handler, lot));
		editModes.add(new TileTextureEdit(handler, lot));
		editModes.add(new TilePlacementEdit(handler, lot));
	}
	
	public void update(float delta) { 
		if(enabled) {
			if(handler.getKeyManager().keyJustPressed(Keyboard.KEY_RIGHT)) 
				changeMode(index + 1);
			else if(handler.getKeyManager().keyJustPressed(Keyboard.KEY_LEFT)) 
				changeMode(index - 1);
			else if(handler.getKeyManager().keyJustPressed(Keyboard.KEY_UP)) {
				floorLevel++;
				
				if(floorLevel == Lot.MAX_FLOOR)
					floorLevel = 0;
				
				for(EditMode editMode : editModes)
					editMode.setFloorLevel(floorLevel);
			} else if(handler.getKeyManager().keyJustPressed(Keyboard.KEY_DOWN)) {
				floorLevel--;
				
				if(floorLevel == -1)
					floorLevel = Lot.MAX_FLOOR - 1;
				
				for(EditMode editMode : editModes)
					editMode.setFloorLevel(floorLevel);
			}
			
			editModes.get(index).update(delta);
		}
	}
	
	public void render() {
		if(enabled) {
			editModes.get(index).render();
		}
	}

	private void changeMode(int newIndex) {
		if(newIndex < 0)
			newIndex = editModes.size() - 1;
		else if(newIndex >= editModes.size())
			newIndex = 0;
		
		editModes.get(index).clear();
		this.index = newIndex;
	}
	
	public boolean isEnabled() { return enabled; }
	public void setEnabled(boolean enabled) { 
		this.enabled = enabled;
		
		if(!enabled) 
			editModes.get(index).clear();
	} 
}
