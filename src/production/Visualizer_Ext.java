package production;

import javax.swing.JFrame;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

/**
 * 
 * @author dihuang, Wei Gui
 *
 */

public class Visualizer_Ext {

	private JFrame frame;
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
	public Visualizer_Ext() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
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
		java.awt.Point offsetpoint= (java.awt.Point)Production.getV().getFrame().getLocation().clone();
		offsetpoint.translate(Production.getV().getFrame().getWidth()+10,0);
		frame.setLocation(offsetpoint);
		frame.setResizable(false);
		frame.getContentPane().setLayout(null);
		
		JMenuBar menuBar = new JMenuBar();
		menuBar.setBounds(0, 0, 444, 21);
		frame.getContentPane().add(menuBar);
		
		JMenu mnNewMenu = new JMenu("Demo");
		menuBar.add(mnNewMenu);
		
		JMenuItem mntmNewMenuItem = new JMenuItem("Restart Demo");
		mntmNewMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					Production.restart();
				} catch (Throwable e1) {
					e1.printStackTrace();
				}
			}
		});
		mnNewMenu.add(mntmNewMenuItem);
		
		JMenu mnNewMenu_1 = new JMenu("Controllers");
		menuBar.add(mnNewMenu_1);
		
		JMenuItem mntmNewMenuItem_1 = new JMenuItem("OrderController");
		mnNewMenu_1.add(mntmNewMenuItem_1);
	}
	public void callfinalize() throws Throwable {
		Production.getV().setext(null);
		frame.dispose();
		this.finalize();
	}
}
