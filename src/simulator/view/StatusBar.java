package simulator.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JLabel;
import javax.swing.JPanel;

import simulator.control.Controller;
import simulator.model.Body;
import simulator.model.SimulatorObserver;

public class StatusBar extends JPanel implements SimulatorObserver {
	// ...
	private JLabel _currTime; // for current time
	private JLabel _currLaws; // for gravity laws
	private JLabel _numOfBodies; // for number of bodies
	private String timeS = "Time: ";
	private String bodiesS = "Bodies: ";
	private String lawsS = "Laws: ";

	StatusBar(Controller ctrl) {
		initGUI();
		ctrl.addObserver(this);
		
		
	}

	private void initGUI() {
		
		this.setLayout(new FlowLayout(FlowLayout.LEFT));
		this.setBorder(BorderFactory.createBevelBorder(1));
		// TODO complete the code to build the tool bar
		_currTime = new JLabel(timeS);
		add(_currTime);
		
		this.add(Box.createRigidArea(new Dimension(20, 0)));
		
		_numOfBodies = new JLabel(bodiesS);
		add(_numOfBodies);
		
		this.add(Box.createRigidArea(new Dimension(20, 0)));
		
		_currLaws = new JLabel(lawsS);
		add(_currLaws);
		
		this.setVisible(true);
	}
	// other private/protected methods
	// ...
	// SimulatorObserver methods
	// ...

	@Override
	public void onRegister(List<Body> bodies, double time, double dt, String fLawsDes) {
		_currTime.setText(timeS + time);
		_numOfBodies.setText(bodiesS + bodies.size());
		_currLaws.setText(lawsS + fLawsDes);
		
	}

	@Override
	public void onReset(List<Body> bodies, double time, double dt, String fLawsDesc) {
		_currTime.setText(timeS + time);
		_numOfBodies.setText(bodiesS + bodies.size());
		_currLaws.setText(lawsS + fLawsDesc);
		
	}

	@Override
	public void onBodyAdded(List<Body> bodies, Body b) {
		_numOfBodies.setText(bodiesS + bodies.size());
		
	}

	@Override
	public void onAdvance(List<Body> bodies, double time) {
		_currTime.setText(timeS + time);
		
	}

	@Override
	public void onDeltaTimeChanged(double dt) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onForceLawsChanged(String fLawsDesc) {
		_currLaws.setText(lawsS + fLawsDesc);
		
	}
}

