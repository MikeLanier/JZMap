import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;

class JZMapCell
{
	@SuppressWarnings("FieldCanBeLocal")

	// The x/y index of the cell on the map.  The indices will be even for an octagon and odd for a diamond.
	//    Both indices will be even or odd.  You cannot have one even and the other odd
	private int  _xIndex;
	private int  _yIndex;

	int xIndex() { return _xIndex; }
	int yIndex() { return _yIndex; }

	public enum Exit
	{
		north,
		northeast,
		east,
		southeast,
		south,
		southwest,
		west,
		northwest,
		up,
		down,
	}

	public enum Type
	{
		undefined,
		room,
		path,
	}

	// initialize a cell as undefined.  When displayed they will be white with a dashed border
	private Type _type = Type.undefined;
	Type type() { return _type; }
	void type(Type t) { _type = t; }

	// defined the exits for the cell.  None to begin with.  Octagons can exit N, W, E, W, NE, NW, SE, SW,
	// up or down. Diamonds can only exit NE, NW, SE, SW.
	private ArrayList<Exit> exits = new ArrayList<>();
	void ToggleExit(Exit exit, Boolean on)
	{
		for(int i=0; i<exits.size(); i++)
		{
			if(exits.get(i) == exit)
			{
				if(!on)
				{
					exits.remove(i);
				}

				return;
			}
		}

		if(on)
		{
			exits.add(exit);
		}
	}

	// returns true if the cell as an exit in the given direction
	Boolean hasExit(Exit exit)
	{
		for(Exit e: exits)
		{
			if(e == exit)
			{
				return true;
			}
		}

		return false;
	}

	private JZMapPolygon _polygon = null;
	private static int _radius = 60;

	private Boolean _bHovered = false;
	private Boolean _bSelected = false;

	void copy(JZMapCell c)
	{
//		_xIndex = c._xIndex;
//		_yIndex = c._yIndex;
		_radius = c._radius;
		_type = c._type;
		int xCenter = _radius * _xIndex;
		int yCenter = _radius * _yIndex;
		boolean octagon = (_xIndex & 1) != 0 ? false : true;
		_polygon = new JZMapPolygon( _radius, xCenter, yCenter, octagon );

		for(int i=0; i<c.exits.size(); i++ )
		{
			exits.add(c.exits.get(i));
		}
	}

	Boolean Hovered()
	{
		return _bHovered;
	}

	void Hovered(Boolean hovered)
	{
		if(_bHovered != hovered)
		{
			_bHovered = hovered;
			//Update();
		}
	}

	Boolean Selected() { return _bSelected; }
	void Selected(Boolean selected)
	{
		if(_bSelected != selected)
		{
			_bSelected = selected;
			//Update();
		}
	}

	private Canvas m_parent = null;

//	public JZMapCell()
//	{
//	}

	JZMapCell(Canvas parent, int xIndex, int yIndex)
	{
//		System.out.println("JZMapCell");
		_xIndex = xIndex;
		_yIndex = yIndex;
		_radius = 60;
		int xCenter = _radius * _xIndex;
		int yCenter = _radius * _yIndex;
		boolean octagon = (xIndex & 1) != 0 ? false : true;

		_polygon = new JZMapPolygon( _radius, xCenter, yCenter, octagon );
		m_parent = parent;
	}

	static double _mapCenterX = 0;
	static double _mapCenterY = 0;
	Boolean isHit(double x, double y)
	{
		double xt = x-_mapCenterX;
		double yt = y-_mapCenterY;
//		System.out.println(_polygon);
//		System.out.println("isHit(" + xIndex() + ", " + yIndex() + ") : " + xt + ", " + yt);
		return _polygon.isPtInRect(x-_mapCenterX,y-_mapCenterY);
	}

	private void UpdatePaths(GraphicsContext gc, JZMapPolygon range)
	{
		gc.setLineDashes(0);
		gc.setLineWidth(2);
		gc.setFill(Color.WHITE);
		gc.setStroke(Color.BLACK);

		for(Exit exit: exits)
		{
//			if(exit == Exit.north)
//			{
//				gc.strokeLine(range.CenterPoint().x, range.top, range.CenterPoint().x, range.CenterPoint().y);
//			}
//			else if(exit == Exit.south)
//			{
//				gc.strokeLine(range.CenterPoint().x, range.bottom, range.CenterPoint().x, range.CenterPoint().y);
//			}
//			else if(exit == Exit.east)
//			{
//				gc.strokeLine(range.right, range.CenterPoint().y, range.CenterPoint().x, range.CenterPoint().y);
//			}
//			else if(exit == Exit.west)
//			{
//				gc.strokeLine(range.left, range.CenterPoint().y, range.CenterPoint().x, range.CenterPoint().y);
//			}
//			else if(exit == Exit.northwest)
//			{
//				gc.strokeLine(range.left, range.top, range.CenterPoint().x, range.CenterPoint().y);
//			}
//			else if(exit == Exit.northeast)
//			{
//				gc.strokeLine(range.right, range.top, range.CenterPoint().x, range.CenterPoint().y);
//			}
//			else if(exit == Exit.southwest)
//			{
//				gc.strokeLine(range.left, range.bottom, range.CenterPoint().x, range.CenterPoint().y);
//			}
//			else if(exit == Exit.southeast)
//			{
//				gc.strokeLine(range.right, range.bottom, range.CenterPoint().x, range.CenterPoint().y);
//			}
		}
	}

	void Update()
	{
		Update( _mapCenterX, _mapCenterY );
	}

	void Update( double mapCenterX, double mapCenterY )
	{
//		System.out.println("JZMapCell::Update");
		_mapCenterX = mapCenterX;
		_mapCenterY = mapCenterX;

		GraphicsContext gc = m_parent.getGraphicsContext2D();
		gc.setFill(Color.AQUA);

		if(_type == Type.room)
		{
			gc.setLineDashes(0);
			gc.setLineWidth(2);

			int offset = 20;
			gc.setFill(Color.GREEN);
//			gc.fillRect(xOrigin,yOrigin,_cellWidth,_cellHeight);

//			UpdatePaths(gc,_range);

			gc.setFill(Color.WHITE);
			gc.setStroke(Color.BLACK);
//			gc.fillRect(xOrigin+offset,yOrigin+offset,_cellWidth-offset*2,_cellHeight-offset*2);
//			gc.strokeRect(xOrigin+offset,yOrigin+offset,_cellWidth-offset*2,_cellHeight-offset*2);
			int n = _polygon._frame.npoints;
			double[] xpoints = new double[9];
			double[] ypoints = new double[9];
			for(int i=0; i<n; i++) {
				xpoints[i] = mapCenterX + (double)_polygon._frame.xpoints[i];
				ypoints[i] = mapCenterY + (double)_polygon._frame.ypoints[i];
			}

			gc.fillPolygon( xpoints, ypoints, n );
		}

		else if(_type == Type.path)
		{
			gc.setLineDashes(0);
			gc.setFill(Color.GREEN);
			//gc.fillRect(xOrigin,yOrigin,_cellWidth,_cellHeight);
			int n = _polygon._frame.npoints;
			double[] xpoints = new double[9];
			double[] ypoints = new double[9];
			for(int i=0; i<n; i++) {
				xpoints[i] = mapCenterX + (double)_polygon._frame.xpoints[i];
				ypoints[i] = mapCenterY + (double)_polygon._frame.ypoints[i];
			}

			gc.fillPolygon( xpoints, ypoints, n );

//			UpdatePaths(gc,_range);
		}

		else
		{
			if(_bSelected)
				gc.setStroke(Color.RED);
			else if(_bHovered)
				gc.setStroke(Color.ORANGE);
			else
				gc.setStroke(Color.BLACK);

			gc.setLineDashes(6, 6);
			gc.setLineWidth(1);

			int n = _polygon._border.npoints;
			double[] xpoints = new double[9];
			double[] ypoints = new double[9];
			for(int i=0; i<n; i++) {
				xpoints[i] = mapCenterX + (double)_polygon._border.xpoints[i];
				ypoints[i] = mapCenterY + (double)_polygon._border.ypoints[i];
			}

			gc.strokePolygon( xpoints, ypoints, n );
		}
	}

	void save(BufferedWriter out) throws IOException
	{
		try
		{
			out.write("1,");
			out.write(Integer.toString(_xIndex)+",");
			out.write(Integer.toString(_yIndex)+",");
			out.write(_type.toString()+",");

			int n = exits.size();
			out.write(Integer.toString(n)+",");
			for(Exit exit: exits)
			{
				out.write(exit.toString()+",");
			}

			out.newLine();
		}
		catch (Exception e)
		{
			//Catch exception if any
			System.err.println("Error: " + e.getMessage());
		}
	}

	void load(BufferedReader in) throws IOException
	{
		try
		{
			String line = in.readLine();
			String[] items = line.split(",");

			_xIndex = new Integer(items[1]);
			_yIndex = new Integer(items[2]);

			switch(items[3]) {
				case "room":
					_type = Type.room;
					break;
				case "path":
					_type = Type.path;
					break;
				default:
					_type = Type.undefined;
					break;
			}

			int n = Integer.parseInt(items[4]);
			for(int i=0; i<n; i++)
			{
				Exit exit = Exit.down;
				if(items[5+i].equals("north"))	exit = Exit.north;
				if(items[5+i].equals("south"))	exit = Exit.south;
				if(items[5+i].equals("east"))	exit = Exit.east;
				if(items[5+i].equals("west"))	exit = Exit.west;
				if(items[5+i].equals("northeast"))	exit = Exit.northeast;
				if(items[5+i].equals("northwest"))	exit = Exit.northwest;
				if(items[5+i].equals("southeast"))	exit = Exit.southeast;
				if(items[5+i].equals("southwest"))	exit = Exit.southwest;
				if(items[5+i].equals("up"))	exit = Exit.up;
				if(items[5+i].equals("down"))	exit = Exit.down;

				exits.add(exit);
			}
		}
		catch (Exception e)
		{
			//Catch exception if any
			System.err.println("Error: " + e.getMessage());
		}
	}
}
