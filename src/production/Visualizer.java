package production;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GraphicsEnvironment;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;

/**
 * 
 * @author dihuang, Wei Gui
 *
 */
public class Visualizer implements Tickable{
	public static final String TITLE = "Warehouse System";
	private Screen screen;
	private Master M;
	private Visualizer_Ext ext=null;
	private JFrame f = new JFrame("Warehouse System");
	public JFrame getFrame() {
		return f;
	}
	public Visualizer_Ext getext() {
		return ext;
	}
	public void setext(Visualizer_Ext set) {
		ext=set;
	}
	public Visualizer(){
		createAndShowGUI();
	}
	public void givenMaster(Master M){
		this.M = M;
	}
	private void createAndShowGUI(){
		f.setResizable(false);
		f.setLocationRelativeTo(null);
		java.awt.Point tempp=GraphicsEnvironment.getLocalGraphicsEnvironment().getCenterPoint();
		tempp.translate(-Floor.width/2, -Floor.height/2);
		f.setLocation(tempp);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		screen = new Screen();
		JButton start_jb = new JButton("Start");
		start_jb.addMouseListener(new MouseAdapter(){
			@Override
			public void mousePressed(MouseEvent e) {
				super.mousePressed(e);
				M.start();
			}
		});
		JButton stop_jb = new JButton("Stop");
		stop_jb.addMouseListener(new MouseAdapter(){
			@Override
			public void mousePressed(MouseEvent e) {
				super.mousePressed(e);
				M.stop();
			}
		});
		JButton ext_jb = new JButton("Extension");
		ext_jb.addMouseListener(new MouseAdapter(){
			@Override
			public void mousePressed(MouseEvent e) {
				super.mousePressed(e);
				try {
					extPressed();
				} catch (Throwable e1) {
					e1.printStackTrace();
				}
			}
		});
		screen.add(start_jb);
		screen.add(stop_jb);
		screen.add(ext_jb);
		f.add(screen);
		f.pack();
		f.setAlwaysOnTop(true);
		f.setVisible(true);
		redirectSystemStreams();
	}
	/**
	 * @author dihuang
	 * Screen sub-component of Visualizer which shows the graphical simulation
	 */
	@SuppressWarnings("serial")
	private class Screen extends JPanel{
		final int offset = 50;
		final int gridSize = Floor.gridSize;
		final int width = Floor.width;
		final int height = Floor.height;

		String debug = "";
		Screen(){
			addMouseListener(new MouseAdapter(){
				@Override
				public void mousePressed(MouseEvent e) {
					int x = (e.getX()-offset)/gridSize;
					int y = (e.getY()-offset)/gridSize;
					String temp = "("+x + "," + y+")";
					if(x < 0 || x > 5 || y < 0 || y > 5 || debug.equals(temp)) return;
					debug = temp;
					System.out.println("++++++++++++++++++++++++++++++++");
					System.out.println("Debug: this coordinate is " + debug);
					System.out.println("++++++++++++++++++++++++++++++++");
				}

			});
		}
		@Override
		public Dimension getPreferredSize() {
			return new Dimension(offset*2+width, offset*2+height);
		}
		@Override
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			// warehouse: 60 * 60
			g.drawLine(offset, offset, width+offset, offset);
			g.drawLine(offset, offset, offset, height+offset);
			g.drawLine(width+offset, offset, width+offset, height+offset);
			g.drawLine(offset, height+offset, width+offset, height+offset);
			for(int i = 1; i < height/gridSize; i++){
				g.drawLine(offset, offset+gridSize*i, width+offset, offset+gridSize*i);
			}
			for(int i = 1; i < width/gridSize; i++){
				g.drawLine(offset+gridSize*i, offset, offset+gridSize*i, height+offset);
			}

			// belt
			paintBelt(0, 5, g);
			paintBelt(0, 4, g);
			paintBelt(0, 3, g);
			paintBelt(0, 2, g);
			paintBelt(0, 1, g);
			paintBelt(0, 0, g);

			// picker and packer stations
			g.setColor(Color.GREEN);
			int xPicker = Floor.PICKER.getX();
			int yPicker = Floor.PICKER.getY();
			int xPacker = Floor.PACKER.getX();
			int yPacker = Floor.PACKER.getY();
			g.fillRect(xPicker*gridSize+offset, yPicker*gridSize+offset, gridSize, gridSize);
			g.fillRect(xPacker*gridSize+offset, yPacker*gridSize+offset, gridSize, gridSize);

			// chargers
			g.setColor(Color.RED);
			int xCharger = Floor.CHARGER.getX();
			int yCharger = Floor.CHARGER.getY();
			g.fillRect(xCharger*gridSize+offset, yCharger*gridSize+offset, gridSize, gridSize);
			xCharger = Floor.CHARGER_2.getX();
			yCharger = Floor.CHARGER_2.getY();
			g.fillRect(xCharger*gridSize+offset, yCharger*gridSize+offset, gridSize, gridSize);

			// Shelf
			for(Shelf s : Floor.SHELVES){
				paintShelf(s.getPos(),g);
			}

			// Robot
			for(Robot r:RobotScheduler.robots) {
			paintRobot(r, g);
			}

			// Bin
			for(Bin b : Belt.getBelt1()){
				paintBin(b.getPos(),g);
			}
			// Parcel
			for(Parcel p : Belt.getBelt2()){
				paintParcel(p.getPos(),g);
			}
		}
		/**
		 * paint all objects
		 * @param (x,y), g
		 */
		void paintBelt(int x, int y, Graphics g){
			g.setColor(Color.BLACK);
			int X = offset + x*gridSize;
			int Y = offset + y*gridSize;
			for(int i = 1; i < gridSize/5; i++){
				int yt = Y + i*5;
				g.drawLine(X, yt, X+gridSize, yt);
			}
		}
		void paintRobot(Robot r, Graphics g){
			Point pos = r.getPOS();
			if(r.carrying()){
				g.setColor(Color.BLACK);
			}else{
				g.setColor(Color.BLUE);
			}
			int xRobot = pos.getX();
			int yRobot = pos.getY();
			g.fillRect(xRobot*gridSize+offset+10, yRobot*gridSize+offset+10, gridSize-20, gridSize-20);
		}
		void paintShelf(Point pos, Graphics g){
			g.setColor(Color.WHITE);
			int xShelf = pos.getX();
			int yShelf = pos.getY();
			g.fillRect(xShelf*gridSize+offset, yShelf*gridSize+offset, gridSize, gridSize);
			g.setColor(Color.BLACK);
			g.drawRect(xShelf*gridSize+offset, yShelf*gridSize+offset, gridSize, gridSize);
			g.drawLine(xShelf*gridSize+offset, yShelf*gridSize+offset, xShelf*gridSize+offset+gridSize, yShelf*gridSize+offset+gridSize);
			g.drawLine(xShelf*gridSize+offset, yShelf*gridSize+offset+gridSize, xShelf*gridSize+offset+gridSize, yShelf*gridSize+offset);
		}
		void paintBin(Point pos, Graphics g){
			g.setColor(Color.ORANGE);
			int X = pos.getX();
			int Y = pos.getY();
			g.fillRect(X*gridSize+offset, Y*gridSize+offset, gridSize, gridSize);
		}
		void paintParcel(Point pos, Graphics g){
			g.setColor(Color.GRAY);
			int X = pos.getX();
			int Y = pos.getY();
			g.fillRect(X*gridSize+offset, Y*gridSize+offset, gridSize, gridSize);
		}
	}
	@Override
	public void tick(int clk) {
		screen.repaint();
	}
	@Override
	public boolean suspend(int suspticks, int currtick) {
		return false;
	}
	private void extPressed() throws Throwable {
		if(ext!=null) {
			ext.getFrame().setVisible(false);
			ext.callfinalize();
			ext=null;
		} else {
			ext=new Visualizer_Ext();
			ext.getFrame().setVisible(true);
		}
	}
	private void updateTextPane(final String text) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				if(ext!=null) {
					Document doc = ext.getTextPane().getDocument();
					try {
						doc.insertString(doc.getLength(), text, null);
					} catch (BadLocationException e) {
						throw new RuntimeException(e);
					}
					ext.getTextPane().setCaretPosition(doc.getLength() - 1);
				}
			}
		});
	}
	private void redirectSystemStreams() {
		OutputStream out = new OutputStream() {
			@Override
			public void write(final int b) throws IOException {
				updateTextPane(String.valueOf((char) b));
			}

			@Override
			public void write(byte[] b, int off, int len) throws IOException {
				updateTextPane(new String(b, off, len));
			}

			@Override
			public void write(byte[] b) throws IOException {
				write(b, 0, b.length);
			}
		};

		System.setOut(new PrintStream(out, true));
		System.setErr(new PrintStream(out, true));
	}
}
