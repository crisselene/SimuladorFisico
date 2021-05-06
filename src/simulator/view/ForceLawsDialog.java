package simulator.view;


import javax.swing.JDialog;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JComboBox;

import java.awt.BorderLayout;
import java.awt.Choice;
import java.awt.Color;
import java.awt.Component;
import java.util.ArrayList;
import java.util.List;

import javax.swing.SwingConstants;
import javax.swing.border.TitledBorder;
import javax.swing.table.AbstractTableModel;

import org.json.JSONObject;

import simulator.control.Controller;
import simulator.model.Body;
import simulator.model.ForceLaws;
import simulator.model.SimulatorObserver;


public class ForceLawsDialog extends JDialog {
	private JLabel info;
	private LawsTable table;
	private DefaultComboBoxModel comboBox;
	private Controller _ctrl;
	private JSONObject forcelawsinfo;
	private List<JSONObject> listForces;

	public ForceLawsDialog(Controller ctrl) {
		super();
		_ctrl = ctrl;
		initGUI();
	}
	

	private void initGUI() {
		getContentPane().setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
		this.setSize(700,500);
		info = new JLabel();
		info.setHorizontalAlignment(SwingConstants.CENTER);
		info.setAlignmentX(Component.CENTER_ALIGNMENT);
		info.setText("<html><p style=\"width:500px\">"+"Select a force law and provide values for the parameters in the value column"
				+ " (default values are used for the parameters with no value) "+"</p></html>");
		getContentPane().add(info);
		
		
		Object[] possibilities = {"Newton Laws of Universal Gravitation",
				"Moving Towards a Fixed Point","No Force"};
		
		
		//getContentPane().add(table);
		
		//lista de fuerzas
		listForces = _ctrl.getForceLawsInfo();
		comboBox = new DefaultComboBoxModel<>();
		for ( JSONObject j : listForces) {
			comboBox.addElement(j.getString("desc"));
		}
		table = new LawsTable(_ctrl, new LawsTableModel(_ctrl));
		add(table, BorderLayout.CENTER);
		JComboBox Comboboxf = new JComboBox(comboBox);
		getContentPane().add(Comboboxf);
		//comboBox.add(jo.desc);
		this.setVisible(true);
		
	}
	private class LawsTableModel extends AbstractTableModel {
		
		private static final long serialVersionUID = 1L;
		private List<ForceLaws> laws;

		LawsTableModel(Controller ctrl) {
			laws = new ArrayList<>();
		}
		@Override
		public int getRowCount() {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public int getColumnCount() {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public Object getValueAt(int rowIndex, int columnIndex) {
			// TODO Auto-generated method stub
			return null;
		}
	}
	
	private class LawsTable extends JPanel {
		private JTable tableL;
		LawsTable(Controller ctrl, LawsTableModel lawsTableModel) {
			setLayout(new BorderLayout());
		tableL = new JTable(lawsTableModel);
		this.add(tableL, BorderLayout.CENTER);
		tableL.setVisible(true);
		}
	}
}
