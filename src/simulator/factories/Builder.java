package simulator.factories;

import org.json.JSONException;
import org.json.JSONObject;


public abstract class Builder<T> {

	String _TypeTag; //tipo del objeto a construir
	String _Desc; //descripci�n del objeto
	public Builder(String type, String desc) {
		this._TypeTag = type;
		this._Desc = desc;
	}
	public T createInstance(JSONObject info) {
		T inst = null;
		try {
			if(_TypeTag != null && _TypeTag.equals(info.getString("type")))
				inst = createTheInstance(info.getJSONObject("data"));
		}catch(JSONException JSONe) {
			throw new IllegalArgumentException(JSONe);
		}
		return inst;
	}
	//abstracto porque no sabemos que es T
	protected abstract T createTheInstance(JSONObject jsonObject);
	
	public JSONObject getBuilderInfo() {
		JSONObject info = new JSONObject();
		info.put("type", _TypeTag);
		info.put("data", createData());//data particular para distintos T
		info.put("desc", _Desc);
		return info;
	}
	
	//no poner nada en data, se sobreescribir� dependiendo de T
	protected JSONObject createData() {
		return new JSONObject();
	}
	
}
