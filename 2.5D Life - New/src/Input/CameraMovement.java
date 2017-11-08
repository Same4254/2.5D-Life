package Input;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import com.Engine.PhysicsEngine.Bodies.PhysicsBody;
import com.Engine.RenderEngine.Util.Camera;
import com.Engine.Util.Vectors.Vector2f;
import com.Engine.Util.Vectors.Vector3f;

import Main.Handler;

public class CameraMovement {
	private Camera camera;
	private Vector3f spawn;
	private Vector3f rotationToWorld;
	
	private Handler handler;
	
	private float movementSpeed;
	private float jumpSpeed;
	private float mouseSensetivity;
	private float angleToWorld;
	
	private boolean escapeLastFrame, rLastFrame;
	
	public CameraMovement(Handler handler, Camera camera, Vector3f spawn, float movementSpeed, float jumpSpeed, float mouseSensitivity) {
		this.handler = handler;
		this.camera = camera;
		this.spawn = spawn;
		this.movementSpeed = movementSpeed;
		this.jumpSpeed = jumpSpeed;
		this.mouseSensetivity = mouseSensitivity;

		angleToWorld = 45;
		
		camera.setPosition(spawn);
		
		rotationToWorld = new Vector3f(angleToWorld, 0, 0);
		point(rotationToWorld);
	}
	
	public void point(Vector3f angle){
		camera.setRotation(angle);
	}
	
//	public void centerOnEntity(Entity e) {
//		camera.setZ((float) (e.getY() + (camera.getY() / Math.tan(angleToWorld))));
//		camera.setX(e.getX());
//		point(rotationToWorld);
//	}
//	
	public void update(float delta) {
		Vector3f caped = camera.getRotation().capMax(100).capMin(-100);
		camera.setRotX(caped.x);
		
		if(handler.getKeyManager().keyJustPressed(Keyboard.KEY_ESCAPE)) {
			Mouse.setGrabbed(!Mouse.isGrabbed());
		}
		
//		if(Keyboard.isKeyDown(Keyboard.KEY_ESCAPE) && !escapeLastFrame) {
//			Mouse.setGrabbed(!Mouse.isGrabbed());
//			escapeLastFrame = true;
//		} else if(!Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)) 
//			escapeLastFrame = false;
		
		if(handler.getKeyManager().keyJustPressed(Keyboard.KEY_R)) {
			point(rotationToWorld);
			camera.setPosition(spawn);
		}
		
//		if(Keyboard.isKeyDown(Keyboard.KEY_R) && !rLastFrame) {
//			point(rotationToWorld);
//			
//			camera.setPosition(spawn);
//			
//			rLastFrame = true;
//		} else if(!Keyboard.isKeyDown(Keyboard.KEY_R)) 
//			rLastFrame = false;
		
//		if(handler.getKeyManager().keyJustPressed(Keyboard.KEY_RETURN)){
//			centerOnEntity(handler.getWorld().getEntityManager().getEntities().get(0));
//		}
		
		if(Keyboard.isKeyDown(Keyboard.KEY_W)) 
			camera.moveForward(movementSpeed * delta);
		if(Keyboard.isKeyDown(Keyboard.KEY_A)) 
			camera.moveRight(-movementSpeed * delta);
		if(Keyboard.isKeyDown(Keyboard.KEY_S)) 
			camera.moveForward(-movementSpeed * delta);
		if(Keyboard.isKeyDown(Keyboard.KEY_D)) 
			camera.moveRight(movementSpeed * delta);
		if(Keyboard.isKeyDown(Keyboard.KEY_SPACE)) 
			camera.moveUp(jumpSpeed * delta);
		if(Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)) 
			camera.moveUp(-jumpSpeed * delta);
		
		if(Mouse.isGrabbed()) {
			if(Math.abs(camera.getRotX()) <= 100)
				camera.rotateX(-Mouse.getDY() * mouseSensetivity);
			camera.rotateY(Mouse.getDX() * mouseSensetivity);
		}
	}

	public float getMovementSpeed(){return movementSpeed;}
	public void setMovementSpeed(float movementSpeed){this.movementSpeed = movementSpeed;}
	public float getJumpSpeed(){return jumpSpeed;}
	public void setJumpSpeed(float jumpSpeed){this.jumpSpeed = jumpSpeed;}
	public float getMouseSensetivity(){return mouseSensetivity;}
	public void setMouseSensetivity(float mouseSensetivity){this.mouseSensetivity = mouseSensetivity;}
}