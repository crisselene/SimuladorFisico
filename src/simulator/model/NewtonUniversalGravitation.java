package simulator.model;

import java.util.List;

import simulator.misc.Vector2D;

public class NewtonUniversalGravitation implements ForceLaws {
	protected double G;

	public NewtonUniversalGravitation() {
		super();
		G = 6.67E-11;
	}
	
	public NewtonUniversalGravitation(double g) {
		super();
		this.G = g;
	}


	@Override
	public void apply(List<Body> bs) {
		Vector2D fuerzaT = new Vector2D(0, 0);
		// obtenemos el primer cuerpo, iesimo
		for (int i = 0; i < bs.size(); i++) {
			Body iesimo = bs.get(i);
			// caso especial en el que la masa del cuerpo es 0
			if (iesimo.m == 0.0) {
				iesimo.v.scale(0); // velocidad a 0
				// la aceleración a 0 se hace en el move() de Body

			} else {
				// obtenemos segundo cuerpo, jesimo para aplicarle la ley a iesimo
				for (int j = 0; j < bs.size(); j++) {
					Body jesimo = bs.get(j);
					if (jesimo != iesimo) {

						// LEY NEWTON para obtener vector Fij
						Vector2D Fij = forceNewton(iesimo, jesimo);

						iesimo.f = Fij;
					}

				}
			}
		}
	}

	private Vector2D forceNewton(Body iesimo, Body jesimo) {
		// numerador ley Newton:
		double numerador = (iesimo.m * jesimo.m);
		// denominador ley Newton (ditancia al cuadrado):
		double denominador = jesimo.p.distanceTo(iesimo.p);
		Math.pow(denominador, 2);
		// multiplicar por G para obtener fij
		double fij = G * (numerador / denominador);

		// DIRECCION Fij
		Vector2D dij = jesimo.p.minus(iesimo.p);
		Vector2D Fij = dij.scale(fij);
		return Fij;
	}

	@Override
	public String toString() {
		return "NewtonUniversalGravitation [G=" + G + "]";
	}
	
	
}
