package simulator.factories;

import org.json.JSONArray;
import org.json.JSONObject;

import simulator.model.Body;
import simulator.misc.Vector2D;

public class BasicBodyBuilder extends Builder<Body> {

	@Override
	protected Body createTheInstance(JSONObject jo) {
		// TODO Auto-generated method stub
		double array[] = new double[2]; //recorrer los array de posicion, velocidad
		
		this._TypeTag = jo.getString("type");
		if(this._TypeTag.equals("basic")) {
			String id = jo.getJSONObject("data").getString("id");
			
			JSONArray ja = jo.getJSONObject("data").getJSONArray("p");
			if(ja != null) {
				for(int i = 0; i < ja.length(); i++) {
					array[i] = ja.getDouble(i);
				}
			}
			Vector2D p = new Vector2D(array[0], array[1]);
			
			ja = jo.getJSONObject("data").getJSONArray("v");
			if(ja != null) {
				for (int i = 0; i < ja.length(); i++) {
					array[i] = ja.getDouble(i);
				}
			}
			Vector2D v = new Vector2D(array[0], array[1]);
			
			double m = jo.getJSONObject("data").getDouble("m");
			
			return new Body(id, m, v, p);
		}
		else {
			return null;
		}
	}

	@Override
	protected JSONObject createData() {
		JSONObject jo = new JSONObject();
		
		jo.put("id", "identifier");
		
		JSONArray p = new JSONArray();
		p.put(0, "positionX");
		p.put(1, "positionY");
		jo.put("p", p);
		
		JSONArray v = new JSONArray();
		v.put(0, "velocityX");
		v.put(1, "volocityY");
		jo.put("v", v);
		
		jo.put("m", "mass");
		
		return jo;
	}
}
