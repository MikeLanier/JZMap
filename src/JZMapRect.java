public class JZMapRect
{
	double left = 0;
	double top = 0;
	double right = 0;
	double bottom = 0;

	public JZMapRect()
	{
		left = 0;
		top = 0;
		right = 0;
		bottom = 0;
	}

	public JZMapRect(double l, double t, double r, double b)
	{
		left = l;
		top = t;
		right = r;
		bottom = b;
	}

	public JZMapRect(JZMapPoint p, JZMapSize s)
	{
		left = p.x;
		top = p.y;
		right = p.x + s.width;
		bottom = p.y + s.height;
	}

	public String toString()
	{
		String s = left + ", " + top + ", " + right + ", " + bottom;
		return s;
	}

	public Boolean isPtInRect(double x, double y)
	{
		if( x >= left && x <= right)
			if( y >= top && y <= bottom)
				return true;

		return false;
	}

	public JZMapPoint CenterPoint()
	{
		JZMapPoint p = new JZMapPoint();
		p.x = left + (right-left)/2;
		p.y = top + (bottom-top)/2;
		return p;
	}
}
