package simulator.model;

import java.util.List;

import simulator.misc.Vector2D;

public class NewtonUniversalGravitation implements ForceLaws {
	protected double G;
	
	public NewtonUniversalGravitation(double g) {
		super();
		this.G = g;
	}


	@Override
	public void apply(List<Body> bs) {
		for (Body iesimo : bs) {
				for (Body jesimo : bs) {
					
					if (!jesimo.equals(iesimo)) {
						Vector2D l = forceNewton(iesimo, jesimo);
						System.out.println(l.toString());
						iesimo.addForce(l);
					}
			}
		}
	}

	private Vector2D forceNewton(Body iesimo, Body jesimo) {
		Vector2D delta = jesimo.getPosition().minus(iesimo.getPosition());
	    double dist = delta.magnitude();
	    double magnitude = (dist > 0) ? (G * iesimo.getMass() * jesimo.getMass()) / (dist * dist) : 0.0;
	    return delta.direction().scale(magnitude);
	}
	

	@Override
	public String toString() {
		return "NewtonUniversalGravitation [G=" + G + "]";
	}
	
	
}
