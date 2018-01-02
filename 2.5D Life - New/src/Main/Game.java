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
import com.Engine.RenderEngine.New_Pipeline.FBO.FBO_Types.Attachment;
import com.Engine.RenderEngine.New_Pipeline.FBO.RenderBuffer;
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
	
	private FBO antiAliasing;
	
	public static PhysicsShader physicsShader;
	
	/** TODO
	 * Systems: 
	 * 	✔ Frustrum Culling -> Wrapper Bodies 
	 * 	✔ Anti Aliasing
	 * 	✔ Instance rendering tiles -> Textures
	 * 		- ✔ Each Tile have different texture
	 * 		- ✔ Implement Into Assets
	 * 	
	 * 	  Rotation Glitch
	 * 
	 * Acutal Game:
	 * 	Make actual textures for the models
	 * 
	 * 	TV effective radius improvments
	 * 		- Can't watch TV through a wall
	 * 		- Recursive algorithm can't go into an empty room next to the TV
	 * 		- Ray Tracing -> Look at mouse picking
	 * 
	 * 	Appliances
	 * 		- ✔ Pick them off of host object
	 * 		- ✔ update them
	 * 		- ✔ translate them with the host
	 * 		- ✔ rotate with host
	 * 		- host will look at appliances for actions AND needs
	 * 
	 * 	Multiple Floors
	 * 		- Lot can have floor levels that are all at different levels, and have different tiles
	 * 		- Stairs need to be implemented to get between the different floors
	 *  
	 *  Lot to Lot travel 
	 *  	- Roads will act as a median
	 *  		- Sidewalk
	 *  		- Cars
	 *  			- Traffic system <- Oh Boy that's going to be fun <- Keep that simple
	 *  
	 * 	Getting on an object
	 * 		- Rotate and translate the player to fit (Ex. Bed)
	 * 		- Animations?
	 * 
	 * 	Actions
	 * 		- Make a meal <- Cooking Skill <- Fridge Class 
	 * 
	 * 		- Multiple Actions At Once
	 * 			- No conflictions
	 * 				- Can't walk in two different directions at the same time
	 * 			- Effectiveness
	 * 				- Homework and TV at the same time results in homework taking longer
	 * 		- Interupts
	 * 			- A Very low need level may take priority over the current action, and interupt the current to take care of that low level need
	 * 		- Re-Order them (Slight UI)
	 * 			- Be able to reorder the actions by sliding them (UI)
	 * 		- Timed actions 
	 * 			- Food takes time to eat, not an instant
	 * 			- Watching TV give entertainment over time
	 * 		- Synced actions between two entities
	 * 			- Two people talking
	 * 			- Back burner action list?
	 * 		- Within a certian distance (effective radius) -> see TV Effective radius
	 * 			- Example: walking and talking becomes not possible when the party's involved are far away
	 * 			- Stop the action when a certain distance away
	 *		- More models to go with new Actions
	 * 	
	 * 	Edit Mode
	 * 		- ✔ Tile Texture 
	 * 			- ✔ choosing and setting
	 * 			- ✔ dragging in a rectangle
	 * 
	 * 	Relationships
	 * 		- Remembering the other people 
	 * 		- Get to know their skills and traits
	 * 		- Use traits to determine how much they could get along or dislike each other
	 * 
	 * 	Object Inventory
	 * 		- Render the inventory in some way?
	 * 		- Each object can have a render method to organize how the inventory can be rendered
	 * 			- Table can render a certain amount of items, from the inventory, on top of its model
	 * 
	 * 	Character Customization
	 * 		- Separate Models
	 * 			- Example: Hat, watch, glasses
	 * 			- Dynamically change the locations of those models based on the coordinates of the player model lie	
	 * 				- Increased fatness or getting taller or shorter
	 * 			- Change the textures
	 * 				- Different color t-shirt
	 * 			- Add function to wrapper body to add other models, and sync with offset to original model
	 */
	public void init() throws LWJGLException {
		window = new Window();
		window.setTitle("Life 2.5D");
		window.setFPS(60);
		window.initDisplay(800, 600);
		
		antiAliasing = new FBO(window.getWidth(), window.getHeight(), 4);
		antiAliasing.attach(new RenderBuffer(antiAliasing), Attachment.ColourBuffer);
		antiAliasing.attach(new RenderBuffer(antiAliasing), Attachment.DepthBuffer);
		FBO.SCREEN_FBO.screenResized(window);
		
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
			antiAliasing.bind();
			antiAliasing.clear();
			
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

			FBO.unbind();
			antiAliasing.resolve();
			
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
