package simulator.control;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import simulator.misc.Vector2D;
import simulator.model.Body;

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
					Vector2D p1 = new Vector2D(uno.getJSONArray("p").getDouble(0),uno.getJSONArray("p").getDouble(1)); //"x" e "y"
					Vector2D p2 = new Vector2D(dos.getJSONArray("p").getDouble(0),dos.getJSONArray("p").getDouble(1));
					double distancia = p1.distanceTo(p2);
					//vector velocidad
					Vector2D v1 = new Vector2D(uno.getJSONArray("v").getDouble(0),uno.getJSONArray("v").getDouble(1)); 
					Vector2D v2 = new Vector2D(dos.getJSONArray("v").getDouble(0),dos.getJSONArray("v").getDouble(1));
					double distanciaV = v1.distanceTo(v2);
					//vector fuerza
					Vector2D f1 = new Vector2D(uno.getJSONArray("f").getDouble(0),uno.getJSONArray("f").getDouble(1)); 
					Vector2D f2 = new Vector2D(dos.getJSONArray("f").getDouble(0),dos.getJSONArray("f").getDouble(1));
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


	//comprueba si tienen el mismo modulo
	private boolean igual_modulo(double a) {
		return (a <= eps);
		
	}

}
