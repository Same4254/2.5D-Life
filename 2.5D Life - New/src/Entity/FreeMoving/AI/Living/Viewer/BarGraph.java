package Entity.FreeMoving.AI.Living.Viewer;

import java.awt.Graphics2D;

public class BarGraph {
	private String title;
	private DataSeries[] series;
	
	private int screenWidth = 400;
	
	private int translateX, translateY; 
	private int graphHeight = 400;
	private int scale = graphHeight / 100;
	private int xAxisGap = 20;
	private int barWidth = 30;
	private int barGap = 20;
	
	public BarGraph(String title, int translateX, int translateY, DataSeries... series) {
		this.series = series;
		
		this.title = title;
		this.translateX = translateX;
		this.translateY = translateY;
	}
	
	public void update() {
		for(DataSeries s : series)
			s.update();
	}
	
	public void render(Graphics2D g2D) {
		g2D.drawString(title, translateX + 100, translateY - graphHeight + 18);
		
		for(int i = 0; i < series.length; i++) {
			g2D.drawRect(translateX, translateY, screenWidth - translateX - 20, graphHeight);
			g2D.fillRect((i * barWidth) + (barGap * i) + translateX, (int) (graphHeight - (series[i].getValue() * scale)) + translateY, barWidth, (int) (series[i].getValue() * scale));
			g2D.drawString(series[i].getName(), (i * barWidth) + (barGap * i) + translateX, graphHeight + xAxisGap + translateY);
		}
		
		for(int i = 0; i <= graphHeight / scale; i += 5) {
			int y = i * scale + translateY;
			g2D.drawLine(translateX, y, screenWidth - translateX + 20, y);
			g2D.drawString(String.valueOf((graphHeight / scale) - i), translateX - 25, y + 10);
		}
	}
}
