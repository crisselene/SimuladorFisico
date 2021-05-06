package simulator.view;

import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.SwingUtilities;

import java.awt.*;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import simulator.control.Controller;
import simulator.model.Body;
import simulator.model.SimulatorObserver;
import javax.swing.JLabel;
import javax.swing.JOptionPane;


public class ControlPanel extends JPanel implements SimulatorObserver {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// ...
	private Controller _ctrl;
	private boolean _stopped;
	private JButton btnFileSelector;
	private JButton btnRun;
	private JButton btnPhysics;
	private JButton btnStop;
	private JButton exitButton;
	private JTextField textField;
	private JSpinner spinner;
	private ForceLawsDialog fldialog;
	//FIle chooser
	private JFileChooser fileChoose;
	private Container parent;

	ControlPanel(Controller ctrl) {
		_ctrl = ctrl;
		_stopped = true;
		initGUI();
		_ctrl.addObserver(this);
	  parent = this.getParent();
		}
	
	

	private void initGUI() {
		JToolBar toolBar = new JToolBar();
		


		//setLayout(new FlowLayout());
		

		fileChoose =  new JFileChooser();
		fileChoose.setCurrentDirectory(new File("./resources/examples"));
		this.setVisible(true); 
		
		
		toolBar.setSize(700,50);
		/*setSize(600, 50);//**** cambiar al ancho de la ventana la primer componente
		this.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 10));
		this.add(Box.createHorizontalGlue());*/

		//botones
		btnFileSelector = this.createButton( 10, 10, 10, 10,"./resources/icons/open.png","select a file");
		btnPhysics = this.createButton( 10, 20, 10, 10,"./resources/icons/physics.png","select a force Law");
		btnRun = this.createButton( 10, 10, 10, 10,"./resources/icons/run.png","run and reset all the buttons");
		btnStop = this.createButton( 10, 10, 10, 10,"./resources/icons/stop.png","stop the execution");
		toolBar.add(btnFileSelector);
		toolBar.add(Box.createRigidArea(new Dimension(5, 0)));
		toolBar.add(btnPhysics);
		toolBar.add(Box.createRigidArea(new Dimension(5, 0)));
		toolBar.add(btnRun);
		toolBar.add(btnStop);
		//JLabels
		
				JLabel lblNewLabel = new JLabel("Steps:");
				toolBar.add(lblNewLabel);
				
				toolBar.add(Box.createRigidArea(new Dimension(5, 0)));
				
				spinner = new JSpinner();
				spinner.add(Box.createRigidArea(new Dimension(0,20)));
				JComponent field = ((JSpinner.DefaultEditor) spinner.getEditor());
				field.setPreferredSize( new Dimension(40, 0));
				toolBar.add(spinner);
				
				toolBar.add(Box.createRigidArea(new Dimension(5, 0)));
				
				JLabel lblNewLabel_1 = new JLabel("Delta-Time:");
				toolBar.add(lblNewLabel_1);
				
				toolBar.add(Box.createRigidArea(new Dimension(5, 0)));
				
				textField = new JTextField();
				toolBar.add(textField);
				textField.setColumns(10);
				
				toolBar.add(Box.createRigidArea(new Dimension(150, 0)));
		//exit Button
		exitButton = this.createButton( 10, 10, 10, 10,"./resources/icons/exit.png","exit");
		this.add(toolBar, BorderLayout.PAGE_START);
		toolBar.add(exitButton);
		toolBar.add(Box.createGlue());
		
		
		//action listener botones:
		ActionListener FileButtonListener = new ActionListener() {
			@Override
			public void actionPerformed(java.awt.event.ActionEvent e) {
				int v = fileChoose.showOpenDialog(parent);
				if (v==JFileChooser.APPROVE_OPTION){ 
					File file = fileChoose.getSelectedFile();
					try {
						_ctrl.reset();
						_ctrl.loadBodies(new FileInputStream(file));
					} catch (FileNotFoundException e1) {
						// SI FALLA CARGAR LOS CUERPOS MOSTRAR DIALOG MESSAGE ERROR
						
					}
				 }
				
			}
		};
		btnFileSelector.addActionListener(FileButtonListener);
		

		ActionListener LawsButtonListener = new ActionListener() {
			@Override
			public void actionPerformed(java.awt.event.ActionEvent e) {
				
				fldialog = new ForceLawsDialog(_ctrl);
				
				/*"Select a force law and provide values for the parameters in the value column "
				 + "(default values are used for the parameters with no value)."*/
			}
		};
		btnPhysics.addActionListener(LawsButtonListener);
		
		
		ActionListener resetButtonListener = new ActionListener() {
			@Override
			public void actionPerformed(java.awt.event.ActionEvent e) {
				btnPhysics.setEnabled(false);
				btnFileSelector.setEnabled(false);
				exitButton.setEnabled(false);
				_stopped = false;
				try {
				double delta = Double.parseDouble(textField.getText());
				_ctrl.setDeltaTime(delta);
				}catch (Exception e1) {
					JOptionPane.showMessageDialog(new JFrame(),
		                    "Introduzca un valor válido para delta",
		                    "Error",
		                    JOptionPane.ERROR_MESSAGE);
				}
				
				int spin = (int) (spinner.getValue());
				run_sim(spin);
			}
		};
		btnRun.addActionListener(resetButtonListener);
		

		ActionListener stopButtonListener = new ActionListener() {
			@Override
			public void actionPerformed(java.awt.event.ActionEvent e) {
				_stopped = true;
				
			}
		};
		btnStop.addActionListener(stopButtonListener);
		

		ActionListener exitButtonListener = new ActionListener() {
			@Override
			public void actionPerformed(java.awt.event.ActionEvent e) {
				quit();
				
			}

			private void quit() {
				int n = JOptionPane.showOptionDialog(new JFrame(),
						 "Are sure you want to quit?", "Quit",
						 JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null,
						 null, null);
						 if (n == 0) {System.exit(0); }
			}
		};
		exitButton.addActionListener(exitButtonListener);
		
	}

	
	private JButton createButton( int x, int y, int w, int h, String path,String description){
		
		JButton button = new JButton();
		button.setBounds(208, 5, 33, 9);
		button.setIcon(new ImageIcon(path));
		button.setToolTipText(description);
		//this.add(button);
		return button;
		
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
				btnPhysics.setEnabled(true);
				btnFileSelector.setEnabled(true);
				exitButton.setEnabled(true);
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
			btnPhysics.setEnabled(true);
			btnFileSelector.setEnabled(true);
			exitButton.setEnabled(true);
			
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
