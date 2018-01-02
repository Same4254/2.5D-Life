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
	private BarGraph needGraph;
	private BarGraph skillGraph;
	
	public Viewer(Handler handler, Human human) {
		frame = new JFrame("Character Viewer");
		frame.setSize(400, 1000);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		needGraph = new BarGraph("Needs", 40, 0, new DataSeries("Hunger", () -> {
			return human.getNeedManager().getHunger().getValue();
		}), new DataSeries("Entertainment", () -> {
			return human.getNeedManager().getEntertainment().getValue();
		}), new DataSeries("Sleep", () -> {
			return human.getNeedManager().getSleep().getValue();
		}));
		
		skillGraph = new BarGraph("Skills", 40, 450, new DataSeries("Programming", () -> { 
			return human.getSkillManager().getProgrammingSkill().getValue();
		}), new DataSeries("Cooking", () -> {
			return human.getSkillManager().getCookingSkill().getValue();
		}));
		
		frame.add(this);
		frame.setVisible(true);
	}
	
	public void update() {
		needGraph.update();
		skillGraph.update();
	}
	
	public void render() {
		repaint();
	}
	
	@Override
	public void paintComponent(Graphics g) {
		Graphics2D g2D = (Graphics2D) g;
		g2D.clearRect(0, 0, frame.getWidth(), frame.getHeight());
		
		needGraph.render(g2D);
		skillGraph.render(g2D);
	}
}
