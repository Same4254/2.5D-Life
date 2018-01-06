package Input;

import java.util.ArrayList;
import java.util.LinkedList;

import org.lwjgl.input.Mouse;
import org.lwjgl.util.vector.Matrix4f;

import com.Engine.PhysicsEngine.Bodies.MovingBody;
import com.Engine.PhysicsEngine.Bodies.PhysicsBody;
import com.Engine.PhysicsEngine.Detection.Intersection.IntersectionResult;
import com.Engine.RenderEngine.Shaders.Shader;
import com.Engine.Util.Vectors.Vector2f;
import com.Engine.Util.Vectors.Vector3f;
import com.Engine.Util.Vectors.Vector4f;

import Main.Game;

public class MousePicker extends MovingBody{
	private PhysicsBody target;
	private ArrayList<PhysicsBody> allTargets;

	public MousePicker() {
		super(new Vector3f(0.1f));
		
		Vector2f clickLocation = new Vector2f((float) Mouse.getX() / Game.screenWidth, (float) Mouse.getY() / Game.screenHeight);
		
		Vector3f endPos = project(clickLocation.x, clickLocation.y, 1);
		Vector3f startPos = project(clickLocation.x, clickLocation.y, 0);

		setPosition(startPos);
		setVelocity(endPos.subtract(startPos));
		
		allTargets = new ArrayList<>();
	}

	public static Vector3f calculateHitPosition(float yLimit) {
		Vector2f clickLocation = new Vector2f((float) Mouse.getX() / Game.screenWidth, (float) Mouse.getY() / Game.screenHeight);
		
		Vector3f endPos = project(clickLocation.x, clickLocation.y, 1);
		Vector3f startPos = project(clickLocation.x, clickLocation.y, 0);

		float xAngle = (float) Math.toDegrees(Math.atan((startPos.x - endPos.x) / (startPos.y - endPos.y)));
		float zAngle = (float) Math.toDegrees(Math.atan((startPos.z - endPos.z) / (startPos.y - endPos.y)));
		
		float x = (float) (Math.tan(Math.toRadians(xAngle)) * (startPos.y - yLimit));
		float z = (float) (Math.tan(Math.toRadians(zAngle)) * (startPos.y - yLimit)); 
		
		x -= startPos.x;
		z -= startPos.z;
		
		z = (int) (Math.abs(z) + .5);
		x = (int) (Math.abs(x) + .5);
		
		return new Vector3f(x, yLimit, z);
	}
	
	private static Vector3f project(float x, float y, float z){
		Vector4f in = new Vector4f(x * 2 - 1, y * 2 - 1, z *2 - 1, 1);
		Matrix4f inverse = Matrix4f.mul(Shader.getProjectionMatrix(), Shader.getViewMatrix(), null);
		inverse = (Matrix4f) inverse.invert();
		org.lwjgl.util.vector.Vector4f lwjglOut = Matrix4f.transform(inverse, in.toLWJGL(), null);
		Vector4f out = new Vector4f(lwjglOut);
		out.w = 1/out.w;
		return new Vector3f(out.x * out.w, out.y * out.w, out.z * out.w);
	}

	public void update(float delta) {
		LinkedList<IntersectionResult> results = engine.findClosestIntersection(this);
		
		if(results.size() > 0){
			IntersectionResult result = results.get(0);
			target = result.getIntersected();
			
			for(IntersectionResult r : results)
				allTargets.add(r.getIntersected());
		}
		
		engine.remove(this);
	}
	
	public PhysicsBody getTarget() { return target; }
	public ArrayList<PhysicsBody> getAllTargets() { return allTargets; } 
}