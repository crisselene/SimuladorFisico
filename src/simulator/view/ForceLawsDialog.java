package simulator.view;


import javax.swing.JDialog;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JTable;


public class ForceLawsDialog extends JDialog {
	private JLabel info;
	private JTable table;

	public ForceLawsDialog() {
		super();
		
		initGUI();
	}

	private void initGUI() {
		getContentPane().setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
		
		info = new JLabel("Select a force law and provide values for the parameters in the value column"
				+ " (default values are used for the parameters with no value) ");
		getContentPane().add(info);
		
		table = new JTable();
		getContentPane().add(table);
		this.setVisible(true);
		
	}
	
}
