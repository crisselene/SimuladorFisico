package simulator.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;

import simulator.control.Controller;

public class MainWindow extends JFrame {
	// ...
	Controller _ctrl;

	public MainWindow(Controller ctrl) {
		super("Physics Simulator");
		_ctrl = ctrl;
		initGUI();
	}

	private void initGUI() {
		JPanel mainPanel = new JPanel(new BorderLayout());
		this.setContentPane(mainPanel);
		//mainPanel.setLayout(new BoxLayout(mainPanel,BoxLayout.Y_AXIS));
		
		
		JPanel menu = new ControlPanel(_ctrl, this);
		//menu.setPreferredSize(new Dimension(800,30));
		mainPanel.add(menu,BorderLayout.PAGE_START);
		BodiesTable bodies = new BodiesTable(_ctrl, new BodiesTableModel(_ctrl));
		bodies.setPreferredSize(new Dimension(800,300));
		bodies.setMaximumSize(new Dimension(Integer.MAX_VALUE,300));
		
		Viewer viewer = new Viewer(_ctrl);
		JPanel contentPanel = new JPanel();
		contentPanel.setLayout(new BoxLayout(contentPanel,BoxLayout.Y_AXIS));
		contentPanel.add(bodies);
		contentPanel.add(viewer);
		mainPanel.add(contentPanel,BorderLayout.CENTER);
		StatusBar status = new StatusBar(_ctrl);
		mainPanel.add(status,BorderLayout.PAGE_END);
		this.setMinimumSize(new Dimension(700,900));
		// TODO complete this method to build the GUI
		// ..
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		//this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setVisible(true);
	}
	// other private/protected methods
	// ...
}
