package Main;

import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.glClear;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;

import com.Engine.PhysicsEngine.PhysicsEngine;
import com.Engine.PhysicsEngine.Detection.Intersection.Tests.MovingEllipsoidMeshIntersectionTest;
import com.Engine.RenderEngine.Shaders.Shader;
import com.Engine.RenderEngine.Window.Window;
import com.Engine.Util.Vectors.Vector3f;

import Input.KeyManager;
import World.World;

public class Game {
	private Window window;
	private PhysicsEngine physicsEngine;
	
	private Handler handler;
	private World world;
	
	private KeyManager keyManager;
	
	public void init() throws LWJGLException {
		window = new Window();
		window.setTitle("Life 2.5D");
		window.setFPS(60);
		window.initDisplay(800, 600);
		
		physicsEngine = new PhysicsEngine();
		
		keyManager = new KeyManager();
		
		handler = new Handler(this);
		world = new World(handler);
		world.init();
		
		physicsEngine.addIntersectionTest(new MovingEllipsoidMeshIntersectionTest());
		
		Shader.unbind(); 
		Shader.setSkyColor(new Vector3f());
	}
	
	public void loop() {
		while(!window.isCloseRequested()) {
			keyManager.update();
			
			world.update((float) window.getFrameTime());
			world.render();
			
			//Rendering
			Shader.setProjectionMatrix(world.getCamera().getPorjectionMatrix());
			Shader.setViewMatrix(world.getCamera().getViewMatrix());
			glClear((Keyboard.isKeyDown(Keyboard.KEY_E) ? 0 : GL_COLOR_BUFFER_BIT) | GL_DEPTH_BUFFER_BIT);
			
			world.getShader().getRenderer().render();
			world.getShader().getRenderer().clear();
			
			//Last
			window.update();
			
			if(window.wasResized()) {
				world.getCamera().setAspect(window.getAspectRatio());
			    world.getCamera().recalculate();
			    
				Shader.unbind(); 
			}
		}
	}
	
	public void cleanUp() {
		window.destroy();
		System.exit(0);
	}
	
	public Window getWindow() { return window; }
	public KeyManager getKeyManager() { return keyManager; }
	public PhysicsEngine getPhysicsEngine() { return physicsEngine; }
}
