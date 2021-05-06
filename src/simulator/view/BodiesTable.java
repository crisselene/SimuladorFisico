package simulator.view;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.TitledBorder;

import simulator.control.Controller;
import javax.swing.JTable;

public class BodiesTable extends JPanel {
	
	private static final long serialVersionUID = 1L;
	private JTable table;
	
	BodiesTable(Controller ctrl, BodiesTableModel bodiesTableModel) {
		setLayout(new BorderLayout());
		setBorder(BorderFactory.createTitledBorder(
		BorderFactory.createLineBorder(Color.black, 2),
		"Bodies",
		TitledBorder.LEFT, TitledBorder.TOP));
		
		table = new JTable(bodiesTableModel);
	    JScrollPane scrollPane = new JScrollPane(table);
		add(table, BorderLayout.CENTER);
		add(scrollPane);
		// TODO complete
		}
}
