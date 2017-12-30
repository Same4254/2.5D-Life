package Entity.WorldObjects.Lot.Edit;

import Entity.WorldObjects.Lot.Lot;
import Main.Handler;

public abstract class EditMode {
	protected Handler handler;
	protected Lot lot;
	
	public EditMode(Handler handler, Lot lot) {
		this.handler = handler;
		this.lot = lot;
	}
	
	public abstract void update(float delta);
	public abstract void render();
	public abstract void clear();
}
