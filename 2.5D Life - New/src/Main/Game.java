package Main;

import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.glClear;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;

import com.Engine.PhysicsEngine.PhysicsEngine;
import com.Engine.PhysicsEngine.Detection.Intersection.Tests.MovingEllipsoidMeshIntersectionTest;
import com.Engine.RenderEngine.GLFunctions.CullFace;
import com.Engine.RenderEngine.Shaders.Shader;
import com.Engine.RenderEngine.Window.Window;
import com.Engine.Util.Vectors.Vector3f;

import Input.KeyManager;
import World.World;


import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL12.*;
import static org.lwjgl.opengl.GL13.*;
import static org.lwjgl.opengl.GL14.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL21.*;
import static org.lwjgl.opengl.GL30.*;
import static org.lwjgl.opengl.GL31.*;
import static org.lwjgl.opengl.GL32.*;
import static org.lwjgl.opengl.GL33.*;
import static org.lwjgl.opengl.GL40.*;
import static org.lwjgl.opengl.GL41.*;
import static org.lwjgl.opengl.GL42.*;
import static org.lwjgl.opengl.GL43.*;
import static org.lwjgl.opengl.GL44.*;

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
		handler.init();
		world = handler.getWorld();
		
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
			
			glEnable(GL_CULL_FACE);
			glFrontFace(GL_CCW);
			glCullFace(GL_BACK);
			glEnable(GL_DEPTH_TEST);
			
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
