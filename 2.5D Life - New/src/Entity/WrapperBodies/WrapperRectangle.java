package Entity.WrapperBodies;

import java.awt.geom.Rectangle2D;

public class WrapperRectangle extends Rectangle2D.Float {
	private static final long serialVersionUID = 1L;

	private int index;
	
	public WrapperRectangle(float x, float y, float width, float height) {
		super(x, y, width, height);
		
	}
	
	public void rotateRight() {
		float[] tx = new float[] { x, x + (int) (width / 2), x, x }; 
		float[] ty = new float[] { y, y, y + (int) (height / 2), y};
		
		index++;
		if(index == 4)
			index = 0;
		
		x = tx[index];
		y = ty[index];
	}
	
	public void rotateLeft() {
		float[] tx = new float[] { x, x + (int) (width / 2), x, x }; 
		float[] ty = new float[] { y, y, y + (int) (height / 2), y};
		
		index--;
		if(index == -1)
			index = 3;
		
		x = tx[index];
		y = ty[index];
	}
	
	private void switchDimension() {
		float width = (float) getWidth();
		this.width = height;
		this.height = width;
	}
	
	public WrapperRectangle clone() {
		WrapperRectangle temp = new WrapperRectangle(x, y, width, height);
		temp.index = index;
		return temp;
	}
}
