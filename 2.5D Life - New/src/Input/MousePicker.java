package Input;

import java.util.LinkedList;

import org.lwjgl.util.vector.Matrix4f;

import com.Engine.PhysicsEngine.Bodies.MovingBody;
import com.Engine.PhysicsEngine.Bodies.PhysicsBody;
import com.Engine.PhysicsEngine.Detection.Intersection.IntersectionResult;
import com.Engine.RenderEngine.Shaders.Shader;
import com.Engine.Util.Vectors.Vector2f;
import com.Engine.Util.Vectors.Vector3f;
import com.Engine.Util.Vectors.Vector4f;

public class MousePicker extends MovingBody{

	private PhysicsBody target;
	
	public MousePicker(Vector3f cameraPos, Vector3f cameraRot, Vector2f clickLocation) {
		super(new Vector3f(0.1f));
		
		Vector3f endPos = project(clickLocation.x, clickLocation.y, 1);
		Vector3f startPos = project(clickLocation.x, clickLocation.y, 0);

		setPosition(startPos);
		setVelocity(endPos.subtract(startPos));
	}
	
	private Vector3f project(float x, float y, float z){
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
		}
		
		engine.remove(this);
	}

	public PhysicsBody getTarget(){return target;}
}