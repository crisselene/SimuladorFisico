package simulator.model;

import java.util.List;

import simulator.misc.Vector2D;

public class MovingTowardsFixedPoint implements ForceLaws {

	Vector2D centro; //direccion
	double g;//acerelacion fija, i.e gravedad
	
	
	public MovingTowardsFixedPoint(Vector2D direction, double acceleration) {
		super();
		this.centro = direction;
		this.g = acceleration;
	}

	@Override
	public void apply(List<Body> bs) {
		//dada una aceleracion fija(g) y una direccion la aceleracion es (-g* direccion)
		//la fuerza del cuerpo entonces es igual a masa por la aceleracion obtenida
		for (Body body : bs) {
			Vector2D acelera_direction = centro.scale(-g);
			body.f = acelera_direction.scale(body.m);
		}
	}

	@Override
	public String toString() {
		return "MovingTowardsFixedPoint [direction=" + centro + ", g=" + g + "]";
	}
	
	
	
}
