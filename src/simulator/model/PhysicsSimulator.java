package simulator.model;

import java.util.List;

import org.json.JSONObject;

public class PhysicsSimulator {

	private double tiempo; //tiempo real de un paso.
	private ForceLaws ley;
	private List<Body> bs;
	private double tiempoActual = 0.0;
	
	public PhysicsSimulator(double tiempo, ForceLaws ley) throws IllegalArgumentException {
		if(tiempo > 0)
			this.tiempo = tiempo;
		else throw new IllegalArgumentException();
		this.ley = ley;
	}
	
	public void advance() {
		for (Body body : bs) {
			body.resetForce();
		}
		ley.apply(bs);
		for(Body body : bs) {
			body.move(tiempo);
		}
		tiempoActual += tiempo;
	}
	
	public void addBody(Body b) {
		for (Body body : bs) {
			if(body.getId().equals(b.id))
				throw new IllegalArgumentException();
		}
		bs.add(b);
	}
	
	public JSONObject getState() {
		//Asegurarse de lo de la key (null) esta bien y si no como hacerlo.
		JSONObject jo1 = new JSONObject();
		JSONObject joAux = new JSONObject();
		
		jo1.put("time", tiempoActual);
		for(Body body: bs) {
			joAux.put(null, body.getState());
		}
		jo1.put("bodies", joAux);
		return jo1;
	}
	
	public String toString() {
		return getState().toString();
	}
}
