package simulator.factories;

import org.json.JSONArray;
import org.json.JSONObject;

import simulator.misc.Vector2D;
import simulator.model.ForceLaws;
import simulator.model.MovingTowardsFixedPoint;

public class MovingTowardsFixedPointBuilder extends Builder<ForceLaws> {

	@Override
	protected ForceLaws createTheInstance(JSONObject jo) {
		// TODO Auto-generated method stub
		int array[] = new int[2];
		
		this._TypeTag = jo.getString("type");
		if(this._TypeTag.equals("mtcp")) {
			//revisar si pasarle algo al coinstructor
			//Hay que cambiarle el (direccion por C) en el constructor de MovingTowardsFP
			JSONArray ja = jo.getJSONObject("data").getJSONArray("c");
			if(ja != null) {
				for(int i = 0; i < ja.length(); i++) {
					array[i] = ja.getInt(i);
				}
			}
			Vector2D c = new Vector2D(array[0], array[1]);
			
			double g = jo.getJSONObject("data").getDouble("g");
			
			return new MovingTowardsFixedPoint(c, g);
		}
		else return null;
	}
	@Override
	protected JSONObject createData() {
		JSONObject jo = new JSONObject();
		
		JSONArray c = new JSONArray();
		c.put(0, "positionX");
		c.put(1, "positionY");
		jo.put("p", c);
		
		jo.put("g", "gravitation");
		
		return jo;
	}
}
