package Utils;

import com.Engine.Util.Vectors.Vector2f;
import com.Engine.Util.Vectors.Vector3f;

public class Util {
	public static float roundNearestMultiple(float number, float roundTo) {
		return Math.round(number / roundTo) * roundTo;
	}
	
	public static Vector2f roundNearestMultiple(Vector2f number, float roundTo) {
		return new Vector2f(roundNearestMultiple(number.x, roundTo), roundNearestMultiple(number.y, roundTo));
	}
	
	public static Vector3f roundNearestMultiple(Vector3f number, float roundTo) {
		return new Vector3f(roundNearestMultiple(number.x, roundTo), roundNearestMultiple(number.y, roundTo), roundNearestMultiple(number.z, roundTo));
	}
}
