package production;

import java.awt.BorderLayout;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

@SuppressWarnings("serial")
public class OrderGUI extends JDialog {
	/**
	 * 
	 * @author  Wei Gui
	 *
	 */
	private final JPanel contentPanel = new JPanel();
	private JTable table;
	private JTable table_1;
	private JScrollPane scrollPane;
	private JScrollPane scrollPane_1;

	public OrderGUI() {
		setTitle("OrderController");
		setBounds(100, 100, 574, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		java.awt.Point offsetpoint= (java.awt.Point)Production.getV().getFrame().getLocation().clone();
		offsetpoint.translate(0,-310);
		this.setLocation(offsetpoint);
		this.setResizable(false);
		this.setAlwaysOnTop(true);
		contentPanel.setLayout(null);
		
		scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(10, 11, 413, 116);
		contentPanel.add(scrollPane_1);
		
		table = new JTable();
		scrollPane_1.setViewportView(table);
		table.setRowSelectionAllowed(false);
		table.setModel(new DefaultTableModel(
			new Object[][] {
				{"Order name(address)", "Items"},
			},
			new String[] {
				"Order name(address)", "Items"
			}
		));
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 138, 413, 116);
		contentPanel.add(scrollPane);
		
		table_1 = new JTable();
		scrollPane.setViewportView(table_1);
		table_1.setRowSelectionAllowed(false);
		table_1.setModel(new DefaultTableModel(
				new Object[][] {
					{"Order name(address)", "Items"},
				},
				new String[] {
					"Order name(address)", "Items"
				}
			));
		
		JButton btnNewButton = new JButton("<html>Randomly Gen a<br />new Order &<br /> add it into the queue</html>");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Production.getO().rd();
			}
		});
		btnNewButton.setBounds(433, 14, 125, 240);
		contentPanel.add(btnNewButton);
	}

	public JTable getTable() {
		return table;
	}

	public JTable getTable_1() {
		return table_1;
	}
}
