import javafx.scene.shape.Polyline;

import java.awt.*;
import java.awt.geom.Path2D;
import java.awt.geom.Point2D;

public class JZMapPolygon
{
	static java.awt.Polygon _frame = new java.awt.Polygon();
	java.awt.Polygon _border = new java.awt.Polygon();
	static java.awt.Polygon _room = new java.awt.Polygon();

	public JZMapPolygon()
	{
	}

	private void BuildPolygon( java.awt.Polygon polygon, int radius, int xCenter, int yCenter, boolean octagon )
	{
		System.out.println("BuildPolygon");

		int d = radius * 2;
		int d3 = d / 3;
		int d6 = d / 6;

		if(octagon) {
			polygon.addPoint(xCenter - d6, yCenter - radius);
			polygon.addPoint(xCenter + d6, yCenter - radius);
			polygon.addPoint(xCenter + radius, yCenter - d6);
			polygon.addPoint(xCenter + radius, yCenter + d6);
			polygon.addPoint(xCenter + d6, yCenter + radius);
			polygon.addPoint(xCenter - d6, yCenter + radius);
			polygon.addPoint(xCenter - radius, yCenter + d6);
			polygon.addPoint(xCenter - radius, yCenter - d6);
			polygon.addPoint(xCenter - d6, yCenter - radius);
		}
		else {
			polygon.addPoint(xCenter, yCenter - radius);
			polygon.addPoint(xCenter + radius, yCenter);
			polygon.addPoint(xCenter, yCenter + radius);
			polygon.addPoint(xCenter - radius, yCenter);
			polygon.addPoint(xCenter, yCenter - radius);
		}
	}
	
	private void BuildPolygons( int radius, int xCenter, int yCenter, boolean octagon )
	{
		System.out.println("BuildPolygons");
		BuildPolygon( _frame, radius, xCenter, yCenter, octagon );
		BuildPolygon( _border, radius-3, xCenter, yCenter, octagon );
		BuildPolygon( _room, radius-12, xCenter, yCenter, octagon );
	}

	public JZMapPolygon(int radius, int xCenter, int yCenter, boolean octagon)
	{
		System.out.println("JZMapPolygon");
		BuildPolygons(radius, xCenter, yCenter, octagon);
	}

	public String toString()
	{
		String s = "_frame, _border, _room"; //left + ", " + top + ", " + right + ", " + bottom;
		return s;
	}

	public Boolean isPtInRect(double x, double y)
	{
		return _frame.contains(x, y);
	}
}
