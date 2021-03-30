package simulator.factories;

import org.json.JSONObject;

import simulator.control.MassEqualStates;
import simulator.control.StateComparator;

public class MassEqualStatesBuilder extends Builder<StateComparator> {

	@Override
	protected StateComparator createTheInstance(JSONObject jo) {
		// TODO Auto-generated method stub
		this._TypeTag = jo.getString("type");
		if(this._TypeTag.equals("masseq")) {
			return new MassEqualStates();
		}
		else return null;
	}

}
