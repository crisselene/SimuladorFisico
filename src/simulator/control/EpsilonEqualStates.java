package simulator.control;

import java.util.List;

import org.json.JSONObject;

import simulator.model.Body;

public class EpsilonEqualStates implements StateComparator {
	
	double eps;

	public EpsilonEqualStates(double eps) {
		super();
		this.eps = eps;
	}



	@Override
	public boolean equal(JSONObject s1, JSONObject s2) {
		if(s1.getDouble("time") == s2.getDouble("time")) {
			s1.get("bodies");
			//for (Body bodies : iterable) {
				
			//}
		}
		return false;
	}

}
