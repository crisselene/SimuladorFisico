package simulator.model;

import org.json.JSONObject;

import simulator.misc.Vector2D;

public class Body {
	protected String id;
	protected double m;//masa
	protected Vector2D v;//velocidad
	protected Vector2D f;//fuerza
	protected Vector2D p;//posicion
	
	public Body(String id, double m, Vector2D v, Vector2D p) {
		super();
		this.id = id;
		this.m = m;
		this.v = v;
		this.f = new Vector2D(0.0,0.0);
		this.p = p;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Body other = (Body) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}



	public String getId() {
		return id;
	}

	public double getMass() {
		return m;
	}

	public Vector2D getVelocity() {
		return v;
	}

	public Vector2D getForce() {
		return f;
	}

	public Vector2D getPosition() {
		return p;
	}
	
	void addForce(Vector2D f) {
		this.f.plus(f);
	}
	
	void resetForce() {
		this.f = new Vector2D(0,0);
	}
	
	void move(double t) {
		Vector2D a;
		if(m!=0) {
		a= f.scale(1/m);
		}
		else {
			a = new Vector2D(0,0);
		}
		Vector2D vt= v.scale(t);
		Vector2D at= a.scale(Math.pow(t,2)*1/2);
		this.p= p.plus(vt).plus(at);
		this.v= v.plus(a.scale(t));
	}
	
	public JSONObject getState() {
		JSONObject jo1 = new JSONObject();
		
		jo1.put("id", id);
		jo1.put("m", m);
		jo1.put("v", v);
		jo1.put("f", f);
		jo1.put("p", p);
		return jo1;
	}
	
	@Override 
	public String toString() {
		return getState().toString();
	}
}
