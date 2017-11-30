package Utils;

import com.Engine.Util.Vectors.Vector2f;
import com.Engine.Util.Vectors.Vector3f;

public class Util {
	
	/****** Nearest Multiple ******/
	public static float roundNearestMultiple(float number, float roundTo) {
		return Math.round(number / roundTo) * roundTo;
	}
	
	public static Vector2f roundNearestMultiple(Vector2f number, float roundTo) {
		return new Vector2f(roundNearestMultiple(number.x, roundTo), roundNearestMultiple(number.y, roundTo));
	}
	
	public static Vector3f roundNearestMultiple(Vector3f number, float roundTo) {
		return new Vector3f(roundNearestMultiple(number.x, roundTo), roundNearestMultiple(number.y, roundTo), roundNearestMultiple(number.z, roundTo));
	}

	/****** Nearest Tenth ******/
	public static float roundNearestTenth(float number) {
		return roundNearestMultiple(number, .1f);
	}
	
	public static Vector2f roundNearestTenth(Vector2f number) {
		return new Vector2f(roundNearestTenth(number.x), roundNearestTenth(number.y));
	}
	
	public static Vector3f roundNearestTenth(Vector3f number) {
		return new Vector3f(roundNearestTenth(number.x), roundNearestTenth(number.y), roundNearestTenth(number.z));
	}
	
	/****** Nearest Hundredth ******/
	public static float roundNearestHundredth(float number) {
		return roundNearestMultiple(number, .01f);
	}
	
	public static Vector2f roundNearestHundredth(Vector2f number) {
		return new Vector2f(roundNearestHundredth(number.x), roundNearestHundredth(number.y));
	}
	
	public static Vector3f roundNearestHundredth(Vector3f number) {
		return new Vector3f(roundNearestHundredth(number.x), roundNearestHundredth(number.y), roundNearestHundredth(number.z));
	}
	
	/****** Within Range ******/
	public static boolean withinRange(float number, float point, float range) {
		if(number < point - range || number > point + range) return false;
		return true;
	}
	
	public static boolean withinRange(Vector2f number, Vector2f point, float range) {
		if(number.greaterThenOrEqual(point.subtract(range)) && number.lessThenOrEqual(point.add(range))) return true;
		return false;
	}
	
	public static boolean withinRange(Vector3f number, Vector3f point, float range) {
		if(number.lessThen(point.subtract(range)) || number.greaterThen(point.add(range))) return false;
		return true;
	}
	
//	/****** Sub Tile Value ******/
//	public int getSubX(float x) {
//		
//	}
	
	public static void main(String[] args) {
		Vector2f velocity = new Vector2f(1, -1);
		
		float angle = (float) Math.toDegrees(Math.acos((velocity.dot(new Vector2f(1, 0)) / velocity.length())));
		
		if(velocity.y < 0)
			angle += 180;
		
		System.out.println(angle);
	}
}
