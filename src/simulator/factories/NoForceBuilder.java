package simulator.factories;

import org.json.JSONObject;

import simulator.model.ForceLaws;
import simulator.model.NoForce;

public class NoForceBuilder extends Builder<ForceLaws>{

	@Override
	protected ForceLaws createTheInstance(JSONObject jo) {
		// TODO Auto-generated method stub
		this._TypeTag = jo.getString("type");
		if(this._TypeTag.equals("ng")){
			return new NoForce();
		}
		else return null;
	}

}
