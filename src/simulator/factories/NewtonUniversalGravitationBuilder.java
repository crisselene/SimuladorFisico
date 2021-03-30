package simulator.factories;

import org.json.JSONArray;
import org.json.JSONObject;

import simulator.model.ForceLaws;
import simulator.model.NewtonUniversalGravitation;

public class NewtonUniversalGravitationBuilder extends Builder<ForceLaws>{

	@Override
	protected ForceLaws createTheInstance(JSONObject jo) {
		// TODO Auto-generated method stub
		this._TypeTag = jo.getString("type");
		if(this._TypeTag.equals("nlug")) {
			return new NewtonUniversalGravitation();
		}
		else return null;
	}
	
	@Override
	protected JSONObject createData() {
		JSONObject jo = new JSONObject();
		
		jo.put("G", "gravitation");
		
		return jo;
	}
}
