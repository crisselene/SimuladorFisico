package simulator.model;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

public class PhysicsSimulator {

	private double dt; //tiempo real de un paso.
	private ForceLaws ley;
	private List<Body> bs;
	private double tiempo; //tiempo acumulado
	
	public PhysicsSimulator(double dt, ForceLaws ley) throws IllegalArgumentException {
		if(dt > 0)
			this.dt = dt;
		else throw new IllegalArgumentException();
		
		if(ley != null)
			this.ley = ley;
		else throw new IllegalArgumentException();
		
		bs = new ArrayList<Body>();
		tiempo = 0.0;
	}
	
	public void advance() {
		for (Body body : bs) {
			body.resetForce();
		}
		ley.apply(bs);
		for(Body body : bs) {
			body.move(dt);
		}
		tiempo += dt;
	}
	
	public void addBody(Body b) {
		if(bs.contains(b)) {
			throw new IllegalArgumentException();
		}
			bs.add(b);
	}
	
	public JSONObject getState() {
		//Asegurarse de lo de la key (null) esta bien y si no como hacerlo.
		JSONObject jo1 = new JSONObject();
		JSONArray joAux = new JSONArray();
		
		jo1.put("time", tiempo);
		for(Body body: bs) {
			joAux.put(body.getState());
		}
		jo1.put("bodies", joAux);
		return jo1;
	}
	
	public String toString() {
		return getState().toString();
	}
}
