package Input;

import org.lwjgl.input.Keyboard;

public class KeyManager {
	private boolean[] keys, justPressed, cantPress;
	
	public KeyManager() {
		keys = new boolean[256];
		justPressed = new boolean[keys.length];
		cantPress = new boolean[keys.length];
	}
	
	public void update() {
		while(Keyboard.next()) {
			if(!Keyboard.getEventKeyState()) 
				keyReleased(Keyboard.getEventKey());
			else 
				keyPressed(Keyboard.getEventKey());
		}
		
		for(int i = 0; i < keys.length; i++) {
			if(cantPress[i] && !keys[i])
				cantPress[i] = false;
			else if(justPressed[i]) { 
				cantPress[i] = true;
				justPressed[i] = false;
			}
			if(!cantPress[i] && keys[i]) 
				justPressed[i] = true;
		}
	}
	
	public void clearKeys() {
		for(int i = 0; i < keys.length; i++)
			keys[i] = false;
	}
	
	public boolean isPressed(int keyCode) {
		if(keyCode < 0 || keyCode >= keys.length)
			return false;
		return keys[keyCode];
	}
	
	public boolean keyJustPressed(int keyCode) {
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
