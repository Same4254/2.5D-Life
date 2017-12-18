package Entity.FreeMoving.AI.Living.Viewer;

import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JFrame;
import javax.swing.JPanel;

import Entity.FreeMoving.Player;
import Main.Handler;

public class Viewer extends JPanel {
	private static final long serialVersionUID = 1L;

	private JFrame frame;
	private BarGraph barGraph;
	
	public Viewer(Handler handler) {
		frame = new JFrame("Character Viewer");
		frame.setSize(400, 500);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		frame.add(this);
		frame.setVisible(true);
		
		Player player = handler.getWorld().getPlayer();
		
		barGraph = new BarGraph(new DataSeries("Hunger", () -> {
			return player.getNeedManager().getHunger().getValue();
		}));
	}
	
	@Override
	public void paintComponent(Graphics g) {
		Graphics2D g2D = (Graphics2D) g;
		g2D.clearRect(0, 0, frame.getWidth(), frame.getHeight());
		
		barGraph.update();
		barGraph.render(g2D);
	}
}
