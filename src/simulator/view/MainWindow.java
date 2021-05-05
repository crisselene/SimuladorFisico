package simulator.view;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JPanel;

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
		JPanel menu = new ControlPanel(_ctrl);
		mainPanel.add(menu,BorderLayout.NORTH);
		this.setMinimumSize(new Dimension(600,400));
		// TODO complete this method to build the GUI
		// ..
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setVisible(true);
	}
	// other private/protected methods
	// ...
}
