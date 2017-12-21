package Main;

import static org.lwjgl.opengl.GL11.GL_BACK;
import static org.lwjgl.opengl.GL11.GL_CCW;
import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_CULL_FACE;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_TEST;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glCullFace;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glFrontFace;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;

import com.Engine.PhysicsEngine.PhysicsEngine;
import com.Engine.PhysicsEngine.Detection.Intersection.Tests.MovingEllipsoidMeshIntersectionTest;
import com.Engine.PhysicsEngine.Render.PhysicsShader;
import com.Engine.RenderEngine.New_Pipeline.FBO.FBO;
import com.Engine.RenderEngine.Shaders.Shader;
import com.Engine.RenderEngine.Window.Window;
import com.Engine.Util.Vectors.Vector3f;

import World.World;
import World.Tiles.Render.TileInstanceModel;

public class Game {
	private Window window;
	private PhysicsEngine physicsEngine;
	
	private Handler handler;
	private World world;
	
	public static PhysicsShader physicsShader;
	
	public void init() throws LWJGLException {
		window = new Window();
		window.setTitle("Life 2.5D");
		window.setFPS(60);
		window.initDisplay(800, 600);
		
		physicsEngine = new PhysicsEngine();
		
		physicsShader = new PhysicsShader();
		Assets.init();
		
		handler = new Handler(this);
		handler.init();
		world = handler.getWorld();
		
		physicsEngine.addIntersectionTest(new MovingEllipsoidMeshIntersectionTest());
		
		Shader.unbind(); 
		Shader.setSkyColor(new Vector3f());
	}
	
	public void loop() {
		double frameTimeAvg = 0.0;
		int frameAvgCounter = 0;
		
		while(!window.isCloseRequested()) {
			handler.getKeyManager().update();
			handler.getMouseManager().update();
//			physicsEngine.simulate((float) window.getFrameTime());
			
//			window.setTitle("2.5D Life: " + window.getFrameTime());
			float delta = (float) window.getFrameTime();
			if(delta < 2 && delta != 0)
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
			
			TileInstanceModel.TILE_SHADER.getRenderer().render(world.getCamera());
			TileInstanceModel.TILE_SHADER.getRenderer().clear();
			
			Assets.defaultShader.getRenderer().render(world.getCamera());
			Assets.defaultShader.getRenderer().clear();
			
			physicsShader.getRenderer().render(world.getCamera());
			physicsShader.getRenderer().clear();
			
//			Tile[][] tiles = world.getTestLot().getTiles();
			
//			for(Tile[] t : tiles)
//			for(Tile temp : t)
//				temp.getBody().getModel().setTexture(Assets.goldTexture);
			
			//Last
			window.update();
			
			if(window.wasResized()) {
			    FBO.SCREEN_FBO.screenResized(window);
				world.getCamera().setAspect(window.getAspectRatio());
			    world.getCamera().recalculate();
			    
				Shader.unbind(); 
			}
			
			if (frameAvgCounter >= 50) {
				frameTimeAvg /= (double) frameAvgCounter;
				window.setTitle("Life 2.5D | FPS: " + (int) (1.0 / frameTimeAvg) + "\t FrameTime: " + (float) frameTimeAvg);

				frameTimeAvg = 0.0;
				frameAvgCounter = 0;
			} else {
				frameTimeAvg += window.getFrameTime();
				frameAvgCounter++;
			}
		}
	}
	
	public void cleanUp() {
		window.destroy();
		System.exit(0);
	}
	
	public Window getWindow() { return window; }
	public PhysicsEngine getPhysicsEngine() { return physicsEngine; }
}
