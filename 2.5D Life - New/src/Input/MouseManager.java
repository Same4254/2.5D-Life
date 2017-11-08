package Input;

import org.lwjgl.input.Mouse;

import com.Engine.PhysicsEngine.Bodies.PhysicsBody;
import com.Engine.RenderEngine.Util.Camera;
import com.Engine.Util.Vectors.Vector2f;

import Main.Handler;

public class MouseManager {
	private Handler handler;
	private Camera camera;
	
	private boolean[] keys, justPressed, cantPress;
	
	public MouseManager(Handler handler) {
		this.handler = handler;
		camera = handler.getWorld().getCamera();
		
		keys = new boolean[3];
		justPressed = new boolean[keys.length];
		cantPress = new boolean[keys.length];
	}
	
	public void update() {
		while(Mouse.next()) {
			if(!Mouse.getEventButtonState()) {
				keyReleased(Mouse.getEventButton());
			} else {
				keyPressed(Mouse.getEventButton());
			}
		}
		
		for(int i = 0;i < keys.length; i++){
			if(cantPress[i] && !keys[i]) {
				cantPress[i] = false;
			}
			else if(justPressed[i]) {
				cantPress[i] = true;
				justPressed[i] = false;
			}
			if(!cantPress[i] && keys[i]) {
				justPressed[i] = true;
			}
		}
	}
	
	public void updatePicker(OnPicked picked, float delta) {
//		System.out.println("MouseX: " + Mouse.getX() + ", MouseY: " + Mouse.getY());
		MousePicker picker = new MousePicker(camera.getPosition(), camera.getRotation(), new Vector2f((float)Mouse.getX() / handler.getWidth(), (float) Mouse.getY() / handler.getHeight()));
		handler.getGame().getPhysicsEngine().add(picker);
		picker.update(delta);
		PhysicsBody selected = picker.getTarget();
		
		if(selected != null)
			picked.pick(selected);
	}
	
	public void clearKeys(){
		for(int i = 0; i < keys.length; i++){
			keys[i] = false;
		}
	}
	
	public boolean isPressed(int keyCode){
		if(keyCode < 0 || keyCode >= keys.length)
			return false;
		return keys[keyCode];
	}
	
	public boolean keyJustPressed(int keyCode){
		if(keyCode < 0 || keyCode >= keys.length)
			return false;
		return justPressed[keyCode];
	}
	
	private void keyPressed(int keyCode) {
		if(keyCode < 0 || keyCode >= keys.length)
			return;
		keys[keyCode] = true;
	}

	private void keyReleased(int keyCode) {
		if(keyCode < 0 || keyCode >= keys.length)
			return;
		keys[keyCode] = false;
	}
}
