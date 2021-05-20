package simulator.view;

import javax.swing.JDialog;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JComboBox;

import java.awt.BorderLayout;
import java.awt.Choice;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.SwingConstants;
import javax.swing.border.TitledBorder;
import javax.swing.table.AbstractTableModel;

import org.json.JSONArray;
import org.json.JSONObject;

import simulator.control.Controller;
import simulator.model.Body;
import simulator.model.ForceLaws;
import simulator.model.NewtonUniversalGravitation;
import simulator.model.SimulatorObserver;

public class ForceLawsDialog extends JDialog {
	private JLabel info;
	private LawsTable table;
	private DefaultComboBoxModel comboBox;
	private Controller _ctrl;
	private JSONObject forcelawsinfo;
	private List<JSONObject> listForces;

	public ForceLawsDialog(Controller ctrl, Frame parent) {
		super(parent, "", true);
		_ctrl = ctrl;
		initGUI();
		
	}

	@SuppressWarnings("unchecked")
	private void initGUI() {
		getContentPane().setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
		this.setSize(700, 500);
		// panel informacion
		info = new JLabel();
		info.setHorizontalAlignment(SwingConstants.CENTER);
		info.setAlignmentX(Component.CENTER_ALIGNMENT);
		info.setText(
				"<html><p>Select a force law and provide values for the parametes in the <b>Value column</b> (default values are used for parametes with no value).</p></html>");

		getContentPane().add(info);

		// tabla

		// panel combo box
		JPanel panelFuerzas = new JPanel();

		JLabel forceLabel = new JLabel("Force Law: ");
		panelFuerzas.add(forceLabel);

		listForces = _ctrl.getForceLawsInfo();

		comboBox = new DefaultComboBoxModel<>();
		
		for (JSONObject j : listForces) {
			comboBox.addElement(j.getString("desc"));
		}
		
		LawsTableModel model = new LawsTableModel(_ctrl);
		table = new LawsTable(_ctrl, model);
		add(table, BorderLayout.CENTER);
		JComboBox<String> comboBoxf = new JComboBox<>(comboBox);
		comboBoxf.setSelectedItem(0);
		JSONObject data = (JSONObject) listForces.get(0).get("data");
		model.updateTable(data);
		
		ActionListener comboBoxListener = new ActionListener() {
			@Override
			public void actionPerformed(java.awt.event.ActionEvent e) {
				// llama al modelo de la tabla para que cambie los datos
				//JSONObject elegido = new JSONObject();
				model.resetTabla();
			//	for (JSONObject f : listForces) {
				//	if (comboBox.getSelectedItem().equals(f.getString("desc"))) {
						// System.out.println(f);
						JSONObject f = listForces.get(comboBoxf.getSelectedIndex());
						JSONObject data = (JSONObject) f.get("data");
						model.updateTable(data);
						// System.out.println(data.getString(getName()));

	//				}
		//		}

			}
		};

		

		
		
		comboBoxf.addActionListener(comboBoxListener);
		panelFuerzas.add(comboBoxf);

		getContentPane().add(panelFuerzas);

		JPanel panelBotones = new JPanel();
		panelBotones.setLayout(new BoxLayout(panelBotones, BoxLayout.X_AXIS));
		
		JButton cancelButton = new JButton("Cancel");
		ActionListener cancelListener = new ActionListener() {
			@Override 
			public void actionPerformed(ActionEvent e) {
				//dispose();
				ForceLawsDialog.this.setVisible(false);
			}
		};
		cancelButton.addActionListener(cancelListener);
		JButton okButton = new JButton("OK");
		ActionListener okListener = new ActionListener() {
			@Override 
			public void actionPerformed(ActionEvent e) {
				JSONObject lawJSON = new JSONObject();
				String type = (listForces.get(comboBoxf.getSelectedIndex()).getString("type"));
				lawJSON.put("type",type);
				lawJSON.put("data", model.createLawData(type));
				
				lawJSON.put("desc", listForces.get(comboBoxf.getSelectedIndex()).getString("desc"));
				//System.out.println(lawJSON);
				_ctrl.setForceLaws(lawJSON);
				ForceLawsDialog.this.setVisible(false);
				//System.out.println(data);
				//_ctrl.setForceLaws(data);
				//JSONObject f = listForces.get(comboBoxf.getSelectedIndex());
				//System.out.println(f);
				//_ctrl.setForceLaws(f);
			}
		};
		okButton.addActionListener(okListener);
		panelBotones.add(cancelButton);
		panelBotones.add(okButton);
		
		getContentPane().add(panelBotones);
		
		this.setVisible(true);
	}
	
	private class LawsTableModel extends AbstractTableModel {

		private static final long serialVersionUID = 1L;

		// private JSONObject elegido;
		private List<Row> data;
		private String columns[] = { "Key", "Value", "Description" };

		LawsTableModel(Controller ctrl) {
			data = new ArrayList<>();
		}

		public void resetTabla() {
			data.clear();

		}

		private void updateTable(JSONObject jo) {
			for(String key: jo.keySet()) {
				data.add(new Row(key, "", jo.getString(key)));
			}
			/*Iterator<String> iter = jo.keys();
			while (iter.hasNext()) {
				String key = iter.next();
				String desc = jo.getString(key);
				data.add(new Row(key, "", desc));
			}*/

			this.fireTableStructureChanged();
		}

		@Override
		public int getRowCount() {
			if (this.data == null)
				return 0;
			else
				return this.data.size();
		}

		@Override
		public int getColumnCount() {
			return columns.length;
		}

		@Override
		public Object getValueAt(int rowIndex, int columnIndex) {
			switch (columnIndex) {
			case 0:
				return data.get(rowIndex).getKey();
			case 1:
				return data.get(rowIndex).getValue();
			case 2:
				return data.get(rowIndex).getDesc();
			}
			return "";
		}

		@Override
		public String getColumnName(int col) {
			if (this.columns == null)
				return "";
			else
				return this.columns[col];
		}
		
		

		@Override
		public void setValueAt(Object value, int row, int col) {
			Row r = data.get(row);
			data.set(row, new Row(r.getKey(), value.toString(), r.getDesc()));
			fireTableDataChanged();
		}
		
		@Override
		public boolean isCellEditable(int row, int col) {
			return col == 1;
		}
		
		public JSONObject createLawData(String type) {
			JSONObject data = new JSONObject();
			for (int i = 0; i < getRowCount(); i++) {
				if(getValueAt(i, 1) != "") {
					try {
						comprobarValidez(type, data);
					}catch(NumberFormatException n) {
						JOptionPane.showMessageDialog(null,"Introduzca número válido");
						break;
					}catch (Exception e) {
						JOptionPane.showMessageDialog(null,"Centro inválido. Se aplica [0.0,0.0]");
						break;
					}
					
		
				}
			}
			return data;
		}
		
		public JSONObject createLawData() {
			// recorrer filas y crear un String {clave:valor, clave:valor...}
			// y con esto crear el data
			JSONObject data = new JSONObject();
			for (int i = 0; i < getRowCount(); i++) {
				if(getValueAt(i, 1) != "") {
					try {
						//comprobarValidez(type, data);
					}catch(NumberFormatException n) {
						JOptionPane.showMessageDialog(null,"Introduzca número válido");
						break;
					}catch (Exception e) {
						JOptionPane.showMessageDialog(null,"Centro inválido. Se aplica [0.0,0.0]");
						break;
					}
					
		
				}
			}
			return data;
		}

		private void comprobarValidez(String type, JSONObject data) throws NumberFormatException, Exception {
			switch(type) {
			case "nlug": {
				if(getValueAt(0,0)!="") {
					data.put((String) getValueAt(0, 0), Double.parseDouble(getValueAt(0,1).toString()));
				}else {
					try {
					data.put((String) getValueAt(0, 0), Double.parseDouble(getValueAt(0,1).toString()));
					}catch (NumberFormatException e) {
						throw e;
					}
				}

			}break;
			case "mtfp": {
				
				//transformación  a dígito:
				JSONArray array = new JSONArray();
				String valor = getValueAt(0,1).toString();
				String valorC;
				//quitar los []
				valor = valor.replace("[", "");
				valor = valor.replace("]", "");
				String[] valorJ = valor.split(",");
				if(valorJ.length==2 || valor.length()==0) {
					for(int l = 0; l< valorJ.length; l ++)
			        {
			            valorC = valorJ[l];
			            if(!valorC.isEmpty()) {
			            	try {	
			            	 array.put(Double.parseDouble(String.valueOf(valorC)));
			            }catch (NumberFormatException n){
							throw n;
						}
			            }
			        }
				}else {
					throw new Exception();	
				}
			    
				
				data.put((String) getValueAt(0, 0), array);
				
				String gravity = getValueAt(1,1).toString();
				if(!gravity.isEmpty()) {
					data.put((String) getValueAt(1, 0), Double.parseDouble(getValueAt(1,1).toString()));

				}
			}break;
			case "ng":{
				
			}
			
			
				
			}
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
			// tableL.setVisible(true);
		}
	}

	private class Row {
		private String key;
		private String value;
		private String description;

		public Row(String key, String value, String description) {
			this.key = key;
			this.value = value;
			this.description = description;
		}

		public String getKey() {
			return key;
		}

		public String getValue() {
			return value;
		}

		public String getDesc() {
			return description;
		}
	}
}
