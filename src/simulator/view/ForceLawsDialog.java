package simulator.view;


import javax.swing.JDialog;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JComboBox;

import java.awt.BorderLayout;
import java.awt.Choice;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Iterator;
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
		//panel informacion
		info = new JLabel();
		info.setHorizontalAlignment(SwingConstants.CENTER);
		info.setAlignmentX(Component.CENTER_ALIGNMENT);
		info.setText( "<html><p>Select a force law and provide values for the parametes in the <b>Value column</b> (default values are used for parametes with no value).</p></html>");


		getContentPane().add(info);
		
		//tabla 
		
		//panel combo box
		JPanel panelFuerzas = new JPanel();
		
		JLabel forceLabel = new JLabel("Force Law: ");
		panelFuerzas.add(forceLabel);
		
		
		listForces = _ctrl.getForceLawsInfo();
		
		comboBox = new DefaultComboBoxModel<>();
		for ( JSONObject j : listForces) {
			comboBox.addElement(j.getString("desc"));
		}
		LawsTableModel model = new LawsTableModel(_ctrl);
		ActionListener comboBoxListener = new ActionListener() {
			@Override
			public void actionPerformed(java.awt.event.ActionEvent e) {
				//llama al modelo de la tabla para que cambie los datos
				JSONObject elegido = new JSONObject();
				for ( JSONObject f : listForces) {
					if(comboBox.getSelectedItem().equals(f.getString("desc"))) {
						elegido = f;
					}
				}
				model.updateTable(elegido);
		}
		};
		table = new LawsTable(_ctrl, model);
		add(table, BorderLayout.CENTER);
		
		JComboBox<String> comboBoxf = new JComboBox(comboBox);
		comboBoxf.addActionListener(comboBoxListener);
		panelFuerzas.add(comboBoxf);
		
		getContentPane().add(panelFuerzas);
		
		JPanel panelBotones = new JPanel();
		panelBotones.setLayout(new BoxLayout(panelBotones, BoxLayout.X_AXIS));
		JButton btnCancel = this.createButton( 20, 5, 5, 20,"./resources/icons/exit.png","exit");
		this.setVisible(true);
	}
	
private JButton createButton( int x, int y, int w, int h, String path,String description){
		
		JButton button = new JButton();
		button.setBounds(208, 5, 33, 9);
		button.setIcon(new ImageIcon(path));
		button.setToolTipText(description);
		//this.add(button);
		return button;
		
	}

	private class LawsTableModel extends AbstractTableModel {
		
		private static final long serialVersionUID = 1L;
		
		//private JSONObject elegido;
		private List<Row> data;
		private String columns[] = {"Key", "Value", "Description"};
		
		LawsTableModel(Controller ctrl) {
			data = new ArrayList<>();
		}
		
		private void updateTable(JSONObject elegido) {
			Iterator<String> keys = elegido.keys();
			
			while(keys.hasNext()) {
				String key = keys.next();
				data.add(new Row(key, "", elegido.getString(key)));
			}
			this.fireTableDataChanged();
		}
		
		@Override
		public int getRowCount() {
			if (this.data == null) return 0;
			else return this.data.size();
		}

		@Override
		public int getColumnCount() {
			return columns.length;
		}

		@Override
		public Object getValueAt(int rowIndex, int columnIndex) {
			switch(columnIndex) {
				case 0: return data.get(rowIndex).getKey();
				case 1: return data.get(rowIndex).getValue();
				case 2: return data.get(rowIndex).getDesc();
			}
			return "";
		}
		
		@Override
		public String getColumnName(int col) {		
			if (this.columns == null) return "";
			else return this.columns[col];
		}
		
		@Override
		public void setValueAt(Object value, int row, int col) {
			
		}
		
		public String[] createArrayKeys(List<JSONObject> keys, String desc, String type) {
			int i = 0;
			String array[] = new String[keys.size()];
			
			for(JSONObject j : keys) {
				array[i] = j.getString(desc) + j.getString(type);
				i++;
			}
			return array;
		}
	}
	
	private class LawsTable extends JPanel {
		
		private static final long serialVersionUID = 1L;
		
		private JTable tableL;
		LawsTable(Controller ctrl, LawsTableModel lawsTableModel) {
			setLayout(new BorderLayout());
		tableL = new JTable(lawsTableModel);
		JScrollPane scrollPane = new JScrollPane(tableL);
		this.add(scrollPane, BorderLayout.CENTER);
		//tableL.setVisible(true);
		}
	}
	
	private class Row{
		private String key;
		private String value;
		private String description;
		
		public Row(String key, String value, String description) {
			this.key = key;
			this.value = value;
			this.description = description;
		}
		public String getKey() { return key;}
		public String getValue() {return value;}
		public String getDesc() {return description;}
	}
}
