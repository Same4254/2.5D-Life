package Entity.FreeMoving.AI.Living.Viewer;

import java.awt.Graphics2D;

public class BarGraph {
	private DataSeries[] series;
	
	private int screenHeight = 450;
	private int screenWidth = 400;
	
	private int translateX = 40, graphHeight = 400;
	private int scale = graphHeight / 100;
	private int xAxisGap = 20;
	private int barWidth = 30;
	private int barGap = 20;
	
	public BarGraph(DataSeries... series) {
		this.series = series;
	}
	
	public void update() {
		for(DataSeries s : series)
			s.update();
	}
	
	public void render(Graphics2D g2D) {
		for(int i = 0; i < series.length; i++) {
			g2D.drawRect(translateX, 0, screenWidth - translateX - 20, graphHeight);
			g2D.fillRect((i * barWidth) + (barGap * i) + translateX, (int) (graphHeight - (series[i].getValue() * scale)), barWidth, (int) (series[i].getValue() * scale));
			g2D.drawString(series[i].getName(), (i * barWidth) + (barGap * i) + translateX, graphHeight + xAxisGap);
		}
		
		for(int i = 0; i <= graphHeight / scale; i += 5) {
			int y = i * scale;
			g2D.drawLine(translateX, y, screenWidth - translateX + 20, y);
			g2D.drawString(String.valueOf((graphHeight / scale) - i), translateX - 25, y + 10);
		}
	}
}
