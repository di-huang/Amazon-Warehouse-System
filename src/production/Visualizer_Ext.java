package production;

import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JButton;

public class Visualizer_Ext {

	private JFrame frame;
	private Visualizer mast;

	/**
	 * Launch the application.
	 
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Visualizer_Ext window = new Visualizer_Ext();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}*/

	/**
	 * Create the application.
	 */
	public JFrame getFrame() {
		return frame;
	}
	public Visualizer_Ext(Visualizer Master) {
		mast=Master;
		initialize(Master);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize(Visualizer Master) {
		frame = new JFrame();
		frame.addComponentListener(new ComponentAdapter() {
			@Override
			public void componentHidden(ComponentEvent e) {
				try {
					callfinalize();
				} catch (Throwable e1) {
					e1.printStackTrace();
				}
			}
		});
		frame.setBounds(100, 100, 450, 300);
		frame.setTitle("Warehouse System Extension");
		java.awt.Point offsetpoint= (java.awt.Point)Master.getFrame().getLocation().clone();
		offsetpoint.translate(Master.getFrame().getWidth()+10,0);
		frame.setLocation(offsetpoint);
		frame.setResizable(false);
		frame.getContentPane().setLayout(null);
		
		JButton btnNewButton = new JButton("New button");
		btnNewButton.setBounds(284, 188, 89, 23);
		frame.getContentPane().add(btnNewButton);
	}
	public void callfinalize() throws Throwable {
		mast.setext(null);
		this.finalize();
	}
}
