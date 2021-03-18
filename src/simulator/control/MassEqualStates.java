package simulator.control;

import org.json.JSONObject;

public abstract class MassEqualStates implements StateComparator{
	
	 @Override
	 public boolean equal(JSONObject s1, JSONObject s2) {
		 if(s1.getDouble("time") == s2.getDouble("time")){
			 
		 }
		return false;
	 }
		
}
