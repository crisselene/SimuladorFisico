package simulator.model;

import java.util.List;

public class PhysicsSimulator {

	private double tiempo; //tiempo real de un paso.
	private ForceLaws ley;
	private List<Body> bs;
	private double tiempoActual = 0.0;
	
	public PhysicsSimulator(double tiempo, ForceLaws ley) {
		this.tiempo = tiempo;
		this.ley = ley;
	}
	
	private void resetForce() {
		for (Body body : bs) {
			body.resetForce();
		}
	}
	
	private void apply() {
		ley.apply(bs);
	}
	
	private void move() {
		for(Body body : bs) {
			body.move(tiempo);
		}
	}
	
	public void advance() {
		resetForce();
		apply();
		move();
		tiempoActual += tiempo;
	}
	
}
