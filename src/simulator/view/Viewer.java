package simulator.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.border.TitledBorder;

import simulator.control.Controller;
import simulator.misc.Vector2D;
import simulator.model.Body;
import simulator.model.SimulatorObserver;

public class Viewer extends JComponent implements SimulatorObserver {
	// ...
	private int _centerX;
	private int _centerY;
	private double _scale;
	private List<Body> _bodies;
	private boolean _showHelp;
	private boolean _showVectors;

	Viewer(Controller ctrl) {
		initGUI();
		ctrl.addObserver(this);
	}

	private void initGUI() {
	// TODO add border with title
		setLayout(new BorderLayout());
		setBorder(BorderFactory.createTitledBorder(
		BorderFactory.createLineBorder(Color.black, 2),
		"Viewer",
		TitledBorder.LEFT, TitledBorder.TOP));
		setBackground(Color.WHITE);
	_bodies = new ArrayList<>();
	_scale = 1.0;
	_showHelp = true;
	_showVectors = true;
	addKeyListener(new KeyListener() {
	// ...
	@Override
	public void keyPressed(KeyEvent e) {
				switch (e.getKeyChar()) {
				case '-':
					_scale = _scale * 1.1;
					repaint();
					break;
				case '+':
					_scale = Math.max(1000.0, _scale / 1.1);
					repaint();
					break;
				case '=':
					autoScale();
					repaint();
					break;
				case 'h':
					_showHelp = !_showHelp;
					repaint();
					break;
				case 'v':
					_showVectors = !_showVectors;
					repaint();
					break;
				default:
				}
			}
	

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
		});
	addMouseListener(new MouseListener() {
	// ...
	public void mouseEntered(MouseEvent e) {
	requestFocus();
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	});
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		// use �gr� to draw not �g� --- it gives nicer results
		Graphics2D gr = (Graphics2D) g;
		
		gr.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		gr.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		// calculate the center
		_centerX = getWidth() / 2;
		_centerY = getHeight() / 2;
		// TODO draw a cross at center
		gr.setColor(Color.RED);
		gr.drawLine(_centerX - 10, _centerY, _centerX + 10, _centerY);
		gr.drawLine(_centerX, _centerY - 10, _centerX, _centerY + 10);
		// TODO draw bodies (with vectors if _showVectors is true)
		
		for(Body b : _bodies) {
			//bolas azules
			int x = _centerX + (int) (b.getPosition().getX() / _scale);
			int y = _centerY -  (int) (b.getPosition().getY() / _scale);
			gr.setColor(Color.BLUE);
			gr.fillOval(x-5, y-5, 10, 10);
			gr.setColor(Color.BLACK);
			gr.drawString(b.getId(), x , y-5);
			
			if(_showVectors) {
				//velocidad
				Vector2D velocidadV = b.getVelocity().direction().scale(20);
				Vector2D fuerzaV = b.getForce().direction().scale(20);
				
				int x2 = x + (int) velocidadV.getX();
				int y2 = y - (int) velocidadV.getY();
				int x1 = x + (int) fuerzaV.getX();
				int y1 = y - (int) fuerzaV.getY();
				
				drawLineWithArrow(gr, x, y, x2, y2, 5, 5, Color.GREEN, Color.GREEN);
				drawLineWithArrow(gr, x, y, x1, y1, 5, 5,  Color.RED, Color.RED);
				
				gr.setColor(Color.BLUE);
				gr.fillOval(x-5, y-5, 10, 10);
				gr.setColor(Color.BLACK);
				gr.drawString(b.getId(), x , y-5);
			}
		}
		// TODO draw help if _showHelp is true
		if(_showHelp) {
			gr.setColor(Color.RED);
			gr.drawString("h:toggle help, v:toggle vectors, +: zoom-in, -: zoom-out, =: fit", 20, 30);
			gr.drawString("Scaling ratio: " + _scale, 20, 48);
		}
		
	}

	// other private/protected methods
	// ...
	private void autoScale() {
		double max = 1.0;
		for (Body b : _bodies) {
			Vector2D p = b.getPosition();
			max = Math.max(max, Math.abs(p.getX()));
			max = Math.max(max, Math.abs(p.getY()));
		}
		double size = Math.max(1.0, Math.min(getWidth(), getHeight()));
		_scale = max > size ? 4.0 * max / size : 1.0;
	}
	

	// This method draws a line from (x1,y1) to (x2,y2) with an arrow.
	// The arrow is of height h and width w.
	// The last two arguments are the colors of the arrow and the line
	private void drawLineWithArrow(//
			Graphics g, //
			int x1, int y1, //
			int x2, int y2, //
			int w, int h, //
			Color lineColor, Color arrowColor) {
		int dx = x2 - x1, dy = y2 - y1;
		double D = Math.sqrt(dx * dx + dy * dy);
		double xm = D - w, xn = xm, ym = h, yn = -h, x;
		double sin = dy / D, cos = dx / D;
		x = xm * cos - ym * sin + x1;
		ym = xm * sin + ym * cos + y1;
		xm = x;
		x = xn * cos - yn * sin + x1;
		yn = xn * sin + yn * cos + y1;
		xn = x;
		int[] xpoints = { x2, (int) xm, (int) xn };
		int[] ypoints = { y2, (int) ym, (int) yn };
		g.setColor(lineColor);
		g.drawLine(x1, y1, x2, y2);
		g.setColor(arrowColor);
		g.fillPolygon(xpoints, ypoints, 3);
	}
	// SimulatorObserver methods
	// ...

	@Override
	public void onRegister(List<Body> bodies, double time, double dt, String fLawsDes) {
		_bodies = bodies;
		autoScale();
		repaint();
	}

	@Override
	public void onReset(List<Body> bodies, double time, double dt, String fLawsDesc) {
		_bodies = bodies;
		autoScale();
		repaint();
		
	}

	@Override
	public void onBodyAdded(List<Body> bodies, Body b) {
		_bodies = bodies;
		autoScale();
		repaint();
		
	}

	@Override
	public void onAdvance(List<Body> bodies, double time) {
		_bodies = bodies;
		repaint();	
	}

	@Override
	public void onDeltaTimeChanged(double dt) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onForceLawsChanged(String fLawsDesc) {
		// TODO Auto-generated method stub
		
	}
}
