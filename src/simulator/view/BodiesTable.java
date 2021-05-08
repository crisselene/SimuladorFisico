package simulator.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

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
		this.setPreferredSize(new Dimension(700, 200));
		table = new JTable(bodiesTableModel);
		table.setGridColor(Color.WHITE);
	    JScrollPane scrollPane = new JScrollPane(table);
	    this.setMaximumSize(new Dimension(700,200));
		//this.add(table, BorderLayout.CENTER);
		this.add(scrollPane);
		// TODO complete
		}
}
