package simulator.model;

import simulator.misc.Vector2D;

public class MassLossingBody extends Body{

	private double lossFactor; //numero entre 0 y 1. Factor de perdida de masa
	private double lossFrequency; //tiempo en segundos despues del cual el objecto pierde masa. Positivo
	private double contador;
	
	public MassLossingBody(String id, double m, Vector2D v, Vector2D f, Vector2D p, double lossFactor, double lossFrequency) {
		super(id, m, v, f, p);
		// TODO Auto-generated constructor stub
		this.lossFactor = lossFactor;
		this.lossFrequency = lossFrequency;
		contador = 0.0;
	}
	
//	@Override
//	void move(double t) {
//		Vector2D a;
//		if(m!=0) {
//		a= f.scale(1/m);
//		}
//		else {
//			a = new Vector2D(0,0);
//		}
//		Vector2D vt= v.scale(t);
//		Vector2D at= a.scale(Math.pow(t,2)*1/2);
//		this.p= p.plus(vt).plus(at);
//		this.v= v.plus(a.scale(t));
//		if(lossFrequency != 0.0) {
//			m = m*(1 - lossFactor);
//			contador += t; 
//		}
//	}
	
	void move(double t) {
		super.move(t);
		
		//Losing mass if needed
		contador += t;
		if (contador >= lossFrequency) {
			contador = 0.0;
			m = m * (1 - lossFactor);
		}
	}

}
