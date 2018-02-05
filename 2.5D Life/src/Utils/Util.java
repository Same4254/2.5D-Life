package Utils;

import java.util.ArrayList;

import com.Engine.Util.Vectors.Vector2f;
import com.Engine.Util.Vectors.Vector3f;

import World.Tiles.Tile;

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
	
	public static float roundNearestMultipleFloor(float number, float roundTo) {
		return (float) (roundTo * (Math.floor(number / roundTo)));
	}
	
	public static Vector2f roundNearestMultipleFloor(Vector2f number, float roundTo) {
		return new Vector2f(roundNearestMultipleFloor(number.x, roundTo), roundNearestMultipleFloor(number.y, roundTo));
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
	
	public static boolean isInBetween(float number, Vector2f vector) {
		if(vector.x > vector.y) {
			if(number > vector.y && number < vector.x)
				return true;
			return false;
		} else if(vector.x < vector.y) {
			if(number < vector.y && number > vector.x)
				return true;
			return false;
		}
		
		return false;
	}

	/****** Within Range ******/
	public static Vector2f closest(Vector2f vector, ArrayList<Vector2f> vectors) {
		Vector2f closestVector = null;
		float closestDistance = Float.MAX_VALUE;
		for(Vector2f v : vectors) {
			float distance = v.distance(vector); 
			
			if(distance < closestDistance) {
				closestDistance = distance;
				closestVector = v;
			}
		}
		
		return closestVector;
	}
	
	public static Vector2f swap(Vector2f vector) {
		float x = vector.x;
		float y = vector.y;
		return new Vector2f(y, x);
	}
	
	/****** Rotate ******/
	public static Vector2f rotate(Vector2f vector, float angle) {
		Vector2f rotated = vector.rotate(angle);
		if(Math.abs(rotated.x) < .001)
			rotated.x = 0;
		if(Math.abs(rotated.y) < .001)
			rotated.y = 0;
		return rotated;
	}
	
	/****** Angle ******/
	public static float getAngle(Vector2f velocity) {
		float angle = (float) Math.toDegrees(Math.atan2(velocity.x, velocity.y));
		angle -= 90;
		return angle;
	}
	
	public static float getAngle(Vector2f current, Vector2f target) {
		float angle = (float) Math.toDegrees(Math.atan2(target.x - current.x, target.y - current.y));
		angle -= 90;
		return angle;
	}
	
	public static float getPosAngle(Vector2f current, Vector2f target) {
		float angle = (float) Math.toDegrees(Math.atan2(target.x - current.x, target.y - current.y));
		angle -= 90;
		
		if(angle < 0)
			angle += 360;
		return angle;
	}
	
	/****** Vectors ******/
	public static Vector2f to2D(Vector3f vector) {
		return new Vector2f(vector.x, vector.z);
	}
	
	public static Vector3f to3D(Vector2f vector, float y) {
		return new Vector3f(vector.x, y, vector.y);
	}
	
	/****** Tiles ******/
	public static Vector2f toGrid(Vector2f vector) {
		return roundNearestMultiple(vector, .5f);
	}
	
	public static int getSubCoord(float pos) {
		return (int) (roundNearestMultiple(pos - (int) pos, (float) (1.0 / Tile.TILE_RESOLUTION)) * Tile.TILE_RESOLUTION);
	}
}
