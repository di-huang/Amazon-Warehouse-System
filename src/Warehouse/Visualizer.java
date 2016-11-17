package warehouse_system.visualizer;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JFrame;
import javax.swing.JPanel;

import warehouse_system.Report;
import warehouse_system.Tickable;
import warehouse_system.floor.Floor;

public class Visualizer extends JFrame implements Tickable{

	public static final int WIDTH = 665, HEIGHT = 415;						// unit: pixel
	public static final String TITLE = "Warehouse System";
	
	private Floor F;
	private class Screen extends JPanel{
		
		private final int width = F.width * F.gridSize;
		private final int height = F.height * F.gridSize;
		
		private Screen(){
			this.setBackground(Color.BLUE);
			this.setBounds(30, 50, width, height);
			this.setVisible(true);
		}

//		@Override
//		protected void paintComponent(Graphics g) {
//			super.paintComponent(g);
//			g.drawLine(0, F.gridSize, width, F.gridSize);
//		}
		
	}

	public Visualizer(Floor F){
		this.F = F;
		initialize();
	}
	
	private void initialize(){
		this.setTitle(TITLE);
		this.setResizable(false);
		this.getContentPane().setBackground(Color.BLACK);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		this.setSize(WIDTH, HEIGHT);
        	this.setLocationRelativeTo(null);
        
		this.setLayout(null);
		Screen screen = new Screen();
		this.add(screen);
		this.setVisible(true);
	}

	@Override
	public void tick(int clk) {
		
	}
	
}
