package simulator.factories;

import org.json.JSONArray;
import org.json.JSONObject;

import simulator.misc.Vector2D;
import simulator.model.ForceLaws;
import simulator.model.MovingTowardsFixedPoint;

public class MovingTowardsFixedPointBuilder extends Builder<ForceLaws> {

	public MovingTowardsFixedPointBuilder() {
		super("mtcp", "MovingTowardsFixedPointBuilder");
		// TODO Auto-generated constructor stub
	}
	@Override
	protected ForceLaws createTheInstance(JSONObject jo) {
		// TODO Auto-generated method stub
		int array[] = new int[2];
		

		//revisar si pasarle algo al coinstructor
		//Hay que cambiarle el (direccion por C) en el constructor de MovingTowardsFP
		JSONArray ja = jo.getJSONArray("c");
		if(ja != null) {
			for(int i = 0; i < ja.length(); i++) {
				array[i] = ja.getInt(i);
			}
		}
		Vector2D c = jo.has("c") ? new Vector2D(array[0], array[1]) : new Vector2D(0,0);
		
		double g = jo.has("g") ? jo.getDouble("g") : 9.81;
		
		return new MovingTowardsFixedPoint(c, g);
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
