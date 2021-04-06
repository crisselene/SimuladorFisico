package simulator.control;

import java.io.InputStream;
import java.io.OutputStream;

import simulator.factories.Factory;
import simulator.model.Body;
import simulator.model.PhysicsSimulator;

//herencia controller??
public class Controller_state extends Controller{

	public Controller_state(PhysicsSimulator _sim, Factory<Body> _bodiesFactory) {
		super(_sim, _bodiesFactory);	
		// TODO Auto-generated constructor stub
	}

	@Override
	public void run(int _steps, OutputStream os,InputStream in, StateComparator stateCmp) {
		
		
	}


}
