package simulator.view;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import simulator.control.Controller;
import simulator.model.Body;
import simulator.model.SimulatorObserver;

public class BodiesTableModel extends AbstractTableModel implements SimulatorObserver {
	// ...
	private List<Body> _bodies;
	private String columns[] = {"Id", "Mass", "Position", "Velocity", "Force"};

	BodiesTableModel(Controller ctrl) {
		_bodies = new ArrayList<>();
		ctrl.addObserver(this);
	}

	@Override
	public int getRowCount() {
		return _bodies.size();
		// TODO complete
	}

	@Override
	public int getColumnCount() {
		return columns.length;
		// TODO complete
	}

	@Override
	public String getColumnName(int column) {
		String name = columns[column];
		return name;
		// TODO complete
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Body b = _bodies.get(rowIndex);
		
		if(columnIndex == 0) return b.getId();
		else if(columnIndex == 1) return b.getMass();
		else if(columnIndex == 2) return b.getPosition();
		else if(columnIndex == 3) return b.getVelocity();
		else return b.getForce();
	}
	// SimulatorObserver methods
	// ...

	@Override
	public void onRegister(List<Body> bodies, double time, double dt, String fLawsDes) {
		// TODO Auto-generated method stub
		_bodies = bodies;
		fireTableStructureChanged();
	}

	@Override
	public void onReset(List<Body> bodies, double time, double dt, String fLawsDesc) {
		// TODO Auto-generated method stub
		_bodies = bodies;
		fireTableStructureChanged();
	}

	@Override
	public void onBodyAdded(List<Body> bodies, Body b) {
		// TODO Auto-generated method stub
		_bodies = bodies;
		fireTableStructureChanged();
	}

	@Override
	public void onAdvance(List<Body> bodies, double time) {
		// TODO Auto-generated method stub
		_bodies = bodies;
		fireTableStructureChanged();
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
