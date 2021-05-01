package simulator.view;

import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import java.awt.*;
import java.awt.event.ActionListener;
import simulator.control.Controller;
import simulator.model.Body;
import simulator.model.SimulatorObserver;
import javax.swing.JLabel;


public class ControlPanel extends JPanel implements SimulatorObserver {
	// ...
	private Controller _ctrl;
	private boolean _stopped;
	private JButton btnFileSelector;
	private JButton btnRun;
	private JButton btnPhysics;
	private JButton btnStop;
	private JTextField textField;
	private JSpinner spinner;

	ControlPanel(Controller ctrl) {
		_ctrl = ctrl;
		_stopped = true;
		initGUI();
		_ctrl.addObserver(this);
		}
	
	

	private void initGUI() {
		setSize(600, 200);//**** cambiar al ancho de la ventana la primer componente
		setLayout(null);
		setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		btnFileSelector = new JButton("");
		btnFileSelector.setToolTipText("Select a File");
		btnFileSelector.setIcon(new ImageIcon("C:\\Users\\Cris\\Desktop\\ARCHIVOS COMPLU\\workspace_tpII\\simuladorFisico\\resources\\icons\\open.png"));
		add(btnFileSelector);
		
		
		btnPhysics = new JButton("");
		btnPhysics.setIcon(new ImageIcon("C:\\Users\\Cris\\Desktop\\ARCHIVOS COMPLU\\workspace_tpII\\simuladorFisico\\resources\\icons\\physics.png"));
		add(btnPhysics);
		
		btnRun = new JButton("");
		btnRun.setIcon(new ImageIcon("C:\\Users\\Cris\\Desktop\\ARCHIVOS COMPLU\\workspace_tpII\\simuladorFisico\\resources\\icons\\run.png"));
		add(btnRun);
		
		btnStop = new JButton("");
		btnStop.setIcon(new ImageIcon("C:\\Users\\Cris\\Desktop\\ARCHIVOS COMPLU\\workspace_tpII\\simuladorFisico\\resources\\icons\\stop.png"));
		add(btnStop);
		
		JLabel lblNewLabel = new JLabel("Steps:");
		add(lblNewLabel);
		
		spinner = new JSpinner();
		add(spinner);
		
		JLabel lblNewLabel_1 = new JLabel("Delta-Time:");
		add(lblNewLabel_1);
		
		textField = new JTextField();
		add(textField);
		textField.setColumns(10);
		
		
		/*JFileChooser fileChoose = new JFileChooser();
		//botones
		JButton buttonFile = this.createButton( 10, 10, 10, 10,"../../src/resources/icons/open.png","select a file");
		ActionListener FileButtonListener = new ActionListener() {
			@Override
			public void actionPerformed(java.awt.event.ActionEvent e) {
				//if(e.getSource() == this.load) {
					
				//}
				
			}
		};
		buttonFile.addActionListener(FileButtonListener);
		
		JButton botonLaws = this.createButton( 10, 20, 10, 10,"src/resources/icons/physics.png","select a force Law");
		ActionListener LawsButtonListener = new ActionListener() {
			@Override
			public void actionPerformed(java.awt.event.ActionEvent e) {
				//meter lo que hace el fileButton
				
			}
		};
		botonLaws.addActionListener(LawsButtonListener);
		
		JButton resetButtons = this.createButton( 10, 10, 10, 10,"src/resources/icons/run.png","run and reset all the buttons");
		ActionListener resetButtonListener = new ActionListener() {
			@Override
			public void actionPerformed(java.awt.event.ActionEvent e) {
				//meter lo que hace el fileButton
				
			}
		};
		resetButtons.addActionListener(resetButtonListener);
		
		JButton stopButton = this.createButton( 10, 10, 10, 10,"src/resources/icons/stop.png","stop the execution");
		ActionListener stopButtonListener = new ActionListener() {
			@Override
			public void actionPerformed(java.awt.event.ActionEvent e) {
				//meter lo que hace el fileButton
				
			}
		};
		stopButton.addActionListener(stopButtonListener);
		
		JButton exitButton = this.createButton( 10, 10, 10, 10,"src/resources/icons/exit.png","exit");
		ActionListener exitButtonListener = new ActionListener() {
			@Override
			public void actionPerformed(java.awt.event.ActionEvent e) {
				//meter lo que hace el fileButton
				
			}
		};
		exitButton.addActionListener(exitButtonListener);
		
		JSpinner steps =new JSpinner();
		JTextField deltaTime = new JTextField();
	}

	
	private JButton createButton( int x, int y, int w, int h, String path,String description){
		
		JButton button = new JButton();
		button.setBounds(208, 5, 33, 9);
		button.setIcon(new ImageIcon("path"));
		button.setToolTipText(description);
		this.add(button);//***********?
		return button;
		*/
	}
	// other private/protected methods
	// ...
	private void run_sim(int n) {
		if (n > 0 && !_stopped) {
			try {
				_ctrl.run(1);
			} catch (Exception e) {
				// TODO show the error in a dialog box
				// TODO enable all buttons
				_stopped = true;
				return;
			}
			SwingUtilities.invokeLater(new Runnable() {
				@Override
				public void run() {
					run_sim(n - 1);
				}
			});
		} else {
			_stopped = true;
			// TODO enable all buttons
		}
	}
	// SimulatorObserver methods
	// ...

	@Override
	public void onRegister(List<Body> bodies, double time, double dt, String fLawsDes) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onReset(List<Body> bodies, double time, double dt, String fLawsDesc) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onBodyAdded(List<Body> bodies, Body b) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onAdvance(List<Body> bodies, double time) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onDeltaTimeChanged(double dt) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onForceLawsChanged(String fLawsDesc) {
		// TODO Auto-generated method stub
		
	}
	
}
