package Utils;

import com.Engine.Util.Vectors.Vector4f;

public class Vector4I {
	public int x, y, z, w;
	
	public Vector4I(int x, int y, int z, int w) {
		this.x = x; 
		this.y = y;
		this.z = z;
		this.w = w;
	}
	
	public Vector4I(Vector4f vector) {
		this.x = (int) vector.x;
		this.y = (int) vector.y;
		this.z = (int) vector.z;
		this.w = (int) vector.w;
	}
	
	public Vector4I(int number) {
		this.x = number;
		this.y = number;
		this.z = number;
		this.w = number;
	}
	
	public Vector4I(float x, float y, float z, float w) {
		this.x = (int) x;
		this.y = (int) y;
		this.z = (int) z;
		this.w = (int) w;
	}
	
	public int getX() { return x; }
	public int getY() { return y; }
	public int getZ() { return z; }
	public int getW() { return w; }
}
