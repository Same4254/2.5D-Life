package Entity.FreeMoving.AI.Living.Viewer;

import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JFrame;
import javax.swing.JPanel;

import Entity.FreeMoving.Human;
import Main.Handler;

public class Viewer extends JPanel {
	private static final long serialVersionUID = 1L;

	private JFrame frame;
	private BarGraph barGraph;
	
	public Viewer(Handler handler, Human human) {
		frame = new JFrame("Character Viewer");
		frame.setSize(400, 500);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		frame.add(this);
		frame.setVisible(true);
		
		barGraph = new BarGraph(new DataSeries("Hunger", () -> {
			return human.getNeedManager().getHunger().getValue();
		}));
	}
	
	public void update() {
		barGraph.update();
	}
	
	public void render() {
		repaint();
	}
	
	@Override
	public void paintComponent(Graphics g) {
		Graphics2D g2D = (Graphics2D) g;
		g2D.clearRect(0, 0, frame.getWidth(), frame.getHeight());
		
		barGraph.render(g2D);
	}
}
