package Input;

import com.Engine.PhysicsEngine.Bodies.PhysicsBody;

@FunctionalInterface
public interface OnPicked {
	public void pick(PhysicsBody picked);
}
