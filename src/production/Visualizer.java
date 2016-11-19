package production;

import java.awt.Color;
<<<<<<< HEAD
import java.awt.Graphics;

import javax.swing.JFrame;
import javax.swing.JPanel;

import production.Report;
import production.Tickable;
import production.Floor;

public class Visualizer extends JFrame implements Tickable{

	public static final int WIDTH = 700, HEIGHT = 500;						// unit: pixel
	public static final String TITLE = "Warehouse System";
	
	private Floor F;
	private class Screen extends JPanel{
		
		private final int width = F.width * F.gridSize;
		private final int height = F.height * F.gridSize;
		
		private Screen(){
			this.setBackground(Color.BLUE);
			this.setBounds(10, 10, width, height);
			this.setVisible(true);
			
		}

		@Override
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			for(Point warehouseObject: MockFloor.FLOOR_LOCATIONS.values()) {
				if( warehouseObject.getName() == "ROBOT") {
					g.fillOval(warehouseObject.getX()*100, warehouseObject.getY()*100, 10, 10);
					g.setColor(Color.GREEN);
					System.out.println("found robot");
				}
				g.drawRect(warehouseObject.getX()*100, warehouseObject.getY()*100, 10, 10);
				g.setColor(Color.BLACK);
			}
			for(Point beltObject: MockFloor.PACKERBELT) {
				g.drawRect(beltObject.getX()*100, beltObject.getY()*100, 10, 10);
				g.setColor(Color.BLACK);
			}
			for(Point beltObject: MockFloor.PICKERBELT) {
				g.drawRect(beltObject.getX()*100, beltObject.getY()*100, 10, 10);
				g.setColor(Color.BLACK);
			}
			
		}
		
	}

	public Visualizer(Floor F){
		this.F = F;
		initialize();
	}
	
=======

import javax.swing.JFrame;
import javax.swing.JPanel;

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
>>>>>>> branch 'master' of https://github.com/dhuang12138/CS2820.git
	
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
