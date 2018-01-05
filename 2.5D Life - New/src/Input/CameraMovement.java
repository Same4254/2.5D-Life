package Input;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import com.Engine.RenderEngine.Util.Camera;
import com.Engine.Util.Vectors.Vector3f;

import Audio.Sound;
import Entity.FreeMoving.Entity;
import Main.Handler;

public class CameraMovement {
	private Camera camera;
	private Vector3f spawn;
	private Vector3f rotationToWorld;
	
	private Handler handler;
	private boolean followPlayer;
	
	private float movementSpeed;
	private float jumpSpeed;
	private float mouseSensetivity;
	
	public CameraMovement(Handler handler, Camera camera, Vector3f spawn, float movementSpeed, float jumpSpeed, float mouseSensitivity) {
		this.handler = handler;
		this.camera = camera;
		this.spawn = spawn;
		this.movementSpeed = movementSpeed;
		this.jumpSpeed = jumpSpeed;
		this.mouseSensetivity = mouseSensitivity;

		setPosition(spawn);
		
		rotationToWorld = new Vector3f(50, 0, 0);
		setRotation(rotationToWorld);
	}
	
	public void centerOnEntity(Entity e) {
		setPosition(new Vector3f(e.getX(), 30, e.getZ() + 20));
//		camera.setZ(e.getZ() + 20);
//		camera.setX(e.getX());
//		camera.setY(30);
		setRotation(rotationToWorld);
	}
	
	public void update(float delta) {
		Vector3f caped = camera.getRotation().capMax(100).capMin(-100);
		setRotX(caped.x);
		
		if(handler.getKeyManager().keyJustPressed(Keyboard.KEY_ESCAPE)) {
			Mouse.setGrabbed(!Mouse.isGrabbed());
		}
		
		if(handler.getKeyManager().keyJustPressed(Keyboard.KEY_R)) {
			setRotation(rotationToWorld);
			setPosition(spawn);
		}
		
		if(handler.getKeyManager().keyJustPressed(Keyboard.KEY_TAB))
			followPlayer = !followPlayer;
		
		if(!followPlayer) {
			if(Mouse.isGrabbed()) {
				if(Math.abs(camera.getRotX()) <= 100)
					rotateX(-Mouse.getDY() * mouseSensetivity);
				rotateY(Mouse.getDX() * mouseSensetivity);
			}
			
			if(Keyboard.isKeyDown(Keyboard.KEY_W)) 
				moveForward(movementSpeed * delta);
			if(Keyboard.isKeyDown(Keyboard.KEY_A)) 
				moveRight(-movementSpeed * delta);
			if(Keyboard.isKeyDown(Keyboard.KEY_S)) 
				moveForward(-movementSpeed * delta);
			if(Keyboard.isKeyDown(Keyboard.KEY_D)) 
				moveRight(movementSpeed * delta);
			if(Keyboard.isKeyDown(Keyboard.KEY_SPACE)) 
				moveUp(jumpSpeed * delta);
			if(Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)) 
				moveUp(-jumpSpeed * delta);
		}
		
		Sound.updateListenerData(camera);
	}		
	
	private void setPosition(Vector3f position) {
		camera.setPosition(position);
	}
	
	private void setRotation(Vector3f angle) {
		camera.setRotation(angle);
	}

	private void rotateX(float amount) {
		camera.rotateX(amount);
	}
	
	private void rotateY(float amount) {
		camera.rotateY(amount);
	}
	
	private void setRotX(float rotX) {
		camera.setRotX(rotX);
	}
	
	private void moveForward(float amount) {
		camera.moveForward(amount);
	}

	private void moveRight(float amount) {
		camera.moveRight(amount);
	}
	
	private void moveUp(float amount) {
		camera.moveUp(amount);
	}
	
	public float getMovementSpeed(){return movementSpeed;}
	public void setMovementSpeed(float movementSpeed){this.movementSpeed = movementSpeed;}
	public float getJumpSpeed(){return jumpSpeed;}
	public void setJumpSpeed(float jumpSpeed){this.jumpSpeed = jumpSpeed;}
	public float getMouseSensetivity(){return mouseSensetivity;}
	public void setMouseSensetivity(float mouseSensetivity){this.mouseSensetivity = mouseSensetivity;}
}