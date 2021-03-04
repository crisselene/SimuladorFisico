package simulator.model;

import java.util.List;

import simulator.misc.Vector2D;

public class NewtonUniversalGravitation implements ForceLaws {
double G;
	
	public NewtonUniversalGravitation(double g) {
		super();
		G = 6.67E-11;
	}


	@Override
	public void apply(List<Body> bs) {
		Vector2D fuerzaT = new Vector2D(0,0);
		for (Body body : bs) {
			fuerzaT.plus( body.getForce());
			//TODO: sacar cuerpo iesimo con geti
			//otro for
			//
			
			//for (Body bodies : bs) {
				
			//}
			
			
		}
	}
	
	
}
