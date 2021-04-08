package simulator.control;

import org.json.JSONArray;
import org.json.JSONObject;

import simulator.misc.Vector2D;


public class EpsilonEqualStates implements StateComparator {
	
	double eps;

	public EpsilonEqualStates(double eps) {
		super();
		this.eps = eps;
	}



	@Override
	public boolean equal(JSONObject s1, JSONObject s2) {
		 boolean cuerpos_iguales = false; 
		 //mismo time
		 if(s1.getDouble("time") == s2.getDouble("time")){
			//listas de cuerpos de los dos json
			JSONArray bodiesS1 = s1.getJSONArray("bodies");
			JSONArray bodiesS2 = s2.getJSONArray("bodies");
			//si tienen la misma longitud recorremos la lista
			if(bodiesS1.length() == bodiesS2.length()) {
				for (int i = 0; i <bodiesS1.length(); i++) {
					JSONObject uno = bodiesS1.getJSONObject(i);
					JSONObject dos = bodiesS1.getJSONObject(i);
					//que el id sea igual
					if(dos.getString("id").equals(uno.getString("id")) &&
						Math.abs(dos.getDouble("m")-uno.getDouble("m"))<=eps)  {
					//vectores para comparar la posicion 
					Vector2D p1 = generateVector2D(uno.getJSONArray("p"));
					Vector2D p2 = generateVector2D(dos.getJSONArray("p"));
					double distancia = p1.distanceTo(p2);
					//vector velocidad
					Vector2D v1 = generateVector2D(uno.getJSONArray("v")); 
					Vector2D v2 = generateVector2D(dos.getJSONArray("v"));
					double distanciaV = v1.distanceTo(v2);
					//vector fuerza
					Vector2D f1 = generateVector2D(uno.getJSONArray("f")); 
					Vector2D f2 = generateVector2D(dos.getJSONArray("f"));
					double distanciaF = f1.distanceTo(f2);
					
					if(igual_modulo(distancia)) {
						if(igual_modulo(distanciaV)) {
							if(igual_modulo(distanciaF)) {
								cuerpos_iguales= true;
							}
						}
					}
					}else {
						cuerpos_iguales = false;
						return cuerpos_iguales;
					}		
				}	
			}
			return cuerpos_iguales; 
		 }
		return cuerpos_iguales;
	 }
	
	//genera los vectores2D
	private Vector2D generateVector2D(JSONArray ja) {
		Vector2D vector = new Vector2D(ja.getDouble(0),ja.getDouble(1));
		return vector;
	}

	//comprueba si tienen el mismo modulo
	private boolean igual_modulo(double a) {
		return (a <= eps);
		
	}

}
