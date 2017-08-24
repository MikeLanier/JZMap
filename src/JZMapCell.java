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

	private Type _type = Type.undefined;
	Type type() { return _type; }
	void type(Type t) { _type = t; }

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

	private JZMapRect _range = new JZMapRect();

	private static double	_cellWidth = 100;
	private static double	_cellHeight = 100;

	private Boolean _bHovered = false;
	private Boolean _bSelected = false;

	void copy(JZMapCell c)
	{
		_xIndex = c._xIndex;
		_yIndex = c._yIndex;
		_type = c._type;

		for(int i=0; i<c.exits.size(); i++ )
		{
			exits.add(c.exits.get(i));
		}
	}

	void Hovered(Boolean hovered)
	{
		if(_bHovered != hovered)
		{
			_bHovered = hovered;
			Update();
		}
	}

	Boolean Selected() { return _bSelected; }
	void Selected(Boolean selected)
	{
		if(_bSelected != selected)
		{
			_bSelected = selected;
			Update();
		}
	}

	private Canvas m_parent = null;

//	public JZMapCell()
//	{
//		_xIndex = 0;
//		_yIndex = 0;
//		m_parent = null;
//	}

	JZMapCell(Canvas parent, int xIndex, int yIndex)
	{
//		System.out.println("JZMapCell");
		_xIndex = xIndex;
		_yIndex = yIndex;
		m_parent = parent;
		_cellWidth = 100;
		_cellHeight = 100;
	}

	Boolean isHit(double x, double y)
	{
		return _range.isPtInRect(x,y);
	}

	private void UpdatePaths(GraphicsContext gc, JZMapRect range)
	{
		gc.setLineDashes(0);
		gc.setLineWidth(2);
		gc.setFill(Color.WHITE);
		gc.setStroke(Color.BLACK);

		for(Exit exit: exits)
		{
			if(exit == Exit.north)
			{
				gc.strokeLine(range.CenterPoint().x, range.top, range.CenterPoint().x, range.CenterPoint().y);
			}
			else if(exit == Exit.south)
			{
				gc.strokeLine(range.CenterPoint().x, range.bottom, range.CenterPoint().x, range.CenterPoint().y);
			}
			else if(exit == Exit.east)
			{
				gc.strokeLine(range.right, range.CenterPoint().y, range.CenterPoint().x, range.CenterPoint().y);
			}
			else if(exit == Exit.west)
			{
				gc.strokeLine(range.left, range.CenterPoint().y, range.CenterPoint().x, range.CenterPoint().y);
			}
			else if(exit == Exit.northwest)
			{
				gc.strokeLine(range.left, range.top, range.CenterPoint().x, range.CenterPoint().y);
			}
			else if(exit == Exit.northeast)
			{
				gc.strokeLine(range.right, range.top, range.CenterPoint().x, range.CenterPoint().y);
			}
			else if(exit == Exit.southwest)
			{
				gc.strokeLine(range.left, range.bottom, range.CenterPoint().x, range.CenterPoint().y);
			}
			else if(exit == Exit.southeast)
			{
				gc.strokeLine(range.right, range.bottom, range.CenterPoint().x, range.CenterPoint().y);
			}
		}
	}

//	void Update(int xIndex, int yIndex) {
//		if (m_parent != null) {
//			double width = m_parent.getWidth();
//			double height = m_parent.getHeight();
//
//			double xCenter = width / 2;
//			double yCenter = height / 2;
//
//			double xOrigin = xCenter - _cellWidth / 2 + xIndex * _cellWidth;
//			double yOrigin = yCenter - _cellHeight / 2 + yIndex * _cellHeight;
//
//			Update( xOrigin, yOrigin, _cellWidth, _cellHeight );
//		}
//	}

	private void Update()
	{
		Update( _range.left, _range.top, _cellWidth, _cellHeight);
	}

	void Update( double xOrigin, double yOrigin, double width, double height )
	{
//		System.out.println("Update: " + xOrigin + ", " + yOrigin + ", " + width + ", " + height);
		{
			_cellWidth = width;
			_cellHeight = height;

			_range = new JZMapRect(new JZMapPoint(xOrigin,yOrigin), new JZMapSize(_cellWidth,_cellHeight));

			GraphicsContext gc = m_parent.getGraphicsContext2D();
			gc.setFill(Color.AQUA);

			if(_type == Type.room)
			{
				gc.setLineDashes(0);
				gc.setLineWidth(2);

				int offset = 20;
				gc.setFill(Color.GREEN);
				gc.fillRect(xOrigin,yOrigin,_cellWidth,_cellHeight);

				UpdatePaths(gc,_range);

				gc.setFill(Color.WHITE);
				gc.setStroke(Color.BLACK);
				gc.fillRect(xOrigin+offset,yOrigin+offset,_cellWidth-offset*2,_cellHeight-offset*2);
				gc.strokeRect(xOrigin+offset,yOrigin+offset,_cellWidth-offset*2,_cellHeight-offset*2);
			}

			else if(_type == Type.path)
			{
				gc.setLineDashes(0);
				gc.setFill(Color.GREEN);
				gc.fillRect(xOrigin,yOrigin,_cellWidth,_cellHeight);

				UpdatePaths(gc,_range);
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

				gc.strokeRoundRect(xOrigin + 10, yOrigin + 10, _cellWidth - 20, _cellHeight - 20, 5, 5);
			}
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
