package production;

import javax.swing.JDialog;
import javax.swing.JFrame;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.JTextPane;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

/**
 * 
 * @author  Wei Gui
 *
 */

public class Visualizer_Ext {

	private JFrame frame;
	private JTextPane textPane;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
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
		frame.setBounds(100, 100, 450, 349);
		frame.setTitle("Warehouse System Extension");
		java.awt.Point offsetpoint= (java.awt.Point)Production.getV().getFrame().getLocation().clone();
		offsetpoint.translate(Production.getV().getFrame().getWidth()+10,0);
		frame.setLocation(offsetpoint);
		frame.setResizable(false);
		frame.setAlwaysOnTop(true);
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
					int answer=JOptionPane.showConfirmDialog(frame, "Restart with Default Settings?","Restart",JOptionPane.YES_NO_CANCEL_OPTION,JOptionPane.INFORMATION_MESSAGE);
					if(answer==0) Production.restart();
					if(answer==1) {
						Production.getV().getFrame().setAlwaysOnTop(false);
						String inputLimit = JOptionPane.showInputDialog("Please input tick Limit(Unit:ticks)"); 
						String inputUnittime = JOptionPane.showInputDialog("Please input tick unit time(Unit:ms)"); 
						if(inputLimit!=null&&inputUnittime!=null) {
							try{
							Production.restart(Integer.parseInt(inputLimit),Integer.parseInt(inputUnittime));
							Production.getV().getFrame().setAlwaysOnTop(false);
							} catch(Exception e1) {
								JOptionPane.showMessageDialog(null, e1.getClass().getName()+" "+e1.getMessage()+" Restart Cancelled.", "Error", JOptionPane.ERROR_MESSAGE); 
								Production.getV().getFrame().setAlwaysOnTop(false);
							}
						}
					}
				} catch (Throwable e1) {
					e1.printStackTrace();
				}
			}
		});
		mnNewMenu.add(mntmNewMenuItem);
		
		JMenuItem mntmNewMenuItem_2 = new JMenuItem("Exit Demo");
		mntmNewMenuItem_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		mnNewMenu.add(mntmNewMenuItem_2);
		
		JMenu mnNewMenu_1 = new JMenu("Controllers");
		menuBar.add(mnNewMenu_1);
		
		JMenuItem mntmNewMenuItem_1 = new JMenuItem("OrderController");
		mntmNewMenuItem_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				OrderGUI dialog=new OrderGUI();
				dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
				dialog.setVisible(true);
				Production.setOG(dialog);
			}
		});
		mnNewMenu_1.add(mntmNewMenuItem_1);
		
		JLabel lblOveralldebugoutput = new JLabel("Overall_Debug_Output");
		lblOveralldebugoutput.setBounds(10, 24, 213, 14);
		frame.getContentPane().add(lblOveralldebugoutput);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 43, 424, 218);
		frame.getContentPane().add(scrollPane);
		
		textPane = new JTextPane();
		textPane.setEditable(false);
		scrollPane.setViewportView(textPane);
		
		JLabel lblCurrentDemo = new JLabel("Current Demo");
		lblCurrentDemo.setBounds(10, 265, 130, 14);
		frame.getContentPane().add(lblCurrentDemo);
		
		textField = new JTextField();
		textField.setEditable(false);
		textField.setBounds(55, 290, 57, 20);
		frame.getContentPane().add(textField);
		textField.setColumns(10);
		
		JLabel lblTick = new JLabel("tick:");
		lblTick.setBounds(20, 293, 46, 14);
		frame.getContentPane().add(lblTick);
		
		JLabel lblMaxtick = new JLabel("maxtick:");
		lblMaxtick.setBounds(122, 293, 58, 14);
		frame.getContentPane().add(lblMaxtick);
		
		textField_1 = new JTextField();
		textField_1.setEditable(false);
		textField_1.setBounds(179, 290, 65, 20);
		frame.getContentPane().add(textField_1);
		textField_1.setColumns(10);
		
		JLabel lblUnittime = new JLabel("UnitTime:");
		lblUnittime.setBounds(254, 293, 73, 14);
		frame.getContentPane().add(lblUnittime);
		
		textField_2 = new JTextField();
		textField_2.setEditable(false);
		textField_2.setBounds(314, 290, 86, 20);
		frame.getContentPane().add(textField_2);
		textField_2.setColumns(10);
		textField_1.setText(Integer.toString(Production.getM().getLimit()));
		textField_2.setText(Integer.toString(Production.getM().getUnitTime()));
	}
	public void callfinalize() throws Throwable {
		Production.getV().setext(null);
		frame.dispose();
		this.finalize();
	}
	public JTextField getTextField() {
		return textField;
	}
	public JTextField getTextField_1() {
		return textField_1;
	}
	public JTextField getTextField_2() {
		return textField_2;
	}
	public JTextPane getTextPane() {
		return textPane;
	}
}
