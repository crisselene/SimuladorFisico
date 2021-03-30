package simulator.factories;

import org.json.JSONArray;
import org.json.JSONObject;

import simulator.control.EpsilonEqualStates;
import simulator.control.StateComparator;

public class EpsilonEqualStatesBuilder extends Builder<StateComparator> {

	@Override
	protected StateComparator createTheInstance(JSONObject jo) {
		// TODO Auto-generated method stub
		this._TypeTag = jo.getString("type");
		if(this._TypeTag.equals("epseq")) {
			double eps = jo.getJSONObject("data").getDouble("eps");
			return new EpsilonEqualStates(eps);
		}
		else return null;
	}
	
	@Override
	protected JSONObject createData() {
		JSONObject jo = new JSONObject();
		
		jo.put("eps", "epsilon");
		
		return jo;
	}
}
