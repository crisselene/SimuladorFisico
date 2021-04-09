package simulator.control;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import simulator.factories.Factory;
import simulator.model.Body;
import simulator.model.PhysicsSimulator;

public class Controller {
	PhysicsSimulator _sim;
	Factory<Body> _bodiesFactory;
	
	public Controller(PhysicsSimulator _sim, Factory<Body> _bodiesFactory) {
		super();
		this._sim = _sim;
		this._bodiesFactory = _bodiesFactory;
	}

	//cojo el fichero de entrada, lo leo y proceso os cuerpos
	//este metodo lo compaerten todas las clases
	public void loadBodies(InputStream in) {
		JSONObject jsonInput = new JSONObject(new JSONTokener(in));
		JSONArray bodies = jsonInput.getJSONArray("bodies");
		for (int i = 0; i < bodies.length(); i++) {
			_sim.addBody(_bodiesFactory.createInstance(bodies.getJSONObject(i)));
		}
	}

	public void run(int _steps, OutputStream os ,InputStream expOut, StateComparator cmp) throws DifferentStructs_Exception {
		JSONObject expOutJO = null;
		
		if(expOut!=null) expOutJO = new JSONObject(new JSONTokener(expOut));
		
		if(os == null) {
			os = new OutputStream() {
				@Override
				public void write(int b) throws IOException {}
			};
		}
		
		//abrir json
		PrintStream p = new PrintStream(os);
		p.println("{");
		p.println("\"states\": [");
		
		
		JSONObject currState = null;
		JSONObject expState = null;
		
		
		for (int i = 0; i < _steps; i++) {
			//comparaci�n de los estados iniciales
			currState = _sim.getState();
			p.print(currState+"\n"); //lo vamos imprimiendo en distintas l�neas
			p.print(","); //los separamos por comas para que sea legible
			//si le hesmos pasado un expected json
			if(expOutJO != null) {
				expState = expOutJO.getJSONArray("states").getJSONObject(i);
				if(!cmp.equal(expState,currState)) throw new DifferentStructs_Exception("Las estructuras son distintas ",currState, expState, _steps); //se comparan los dos estados
				
			}
			_sim.advance();
		}
		currState = _sim.getState();
		p.println(currState);//el �ltimo 
		//si aqu� te da siempre error puede ser error de coma flotante
		//comprobar metiendo los json generados al viewer
				
		
		//cerrar json
		p.println("]");
		p.println("}");
	}
	
	
}


