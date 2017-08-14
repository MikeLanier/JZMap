import javafx.event.EventHandler;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TouchEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

import java.io.*;
import java.util.ArrayList;

class JZMap extends HBox
{
	private Canvas mapCanvas = null;
	private double scale = 1.0;
	private double cellRadius = 60.0;
	private double canvasHeight = 300;
	private double canvasWidth = 300;
	private double canvasBorder = 10.0;
	private double cellCountX = 3;
	private double cellCountY = 3;

	private ArrayList<JZMapCell> cells = new ArrayList<JZMapCell>();

	private void _save() throws IOException
	{
		System.out.println("_save");
		try
		{
			// Create file
			FileWriter fstream = new FileWriter("src\\file.zmap", false);
			BufferedWriter out = new BufferedWriter(fstream);
			out.write("JZMap");
			out.newLine();
			int n = cells.size();
			out.write(Integer.toString(n));	out.newLine();

			for(JZMapCell cell: cells)
			{
				cell.save(out);
			}

			out.write("0");
			out.newLine();
			out.close();
		}
		catch (Exception e)
		{
			//Catch exception if any
			System.err.println("Error: " + e.getMessage());
		}
	}

	private void _load() throws IOException
	{
		System.out.println("_load");
		try
		{
			cells.clear();

			// Create file
			FileReader fstream = new FileReader("src\\file.zmap");
			BufferedReader in = new BufferedReader(fstream);
			String line = in.readLine();
			line = in.readLine();
			Integer n = new Integer(line);

			for(int i=0; i<n.intValue(); i++)
			{
				JZMapCell cell = new JZMapCell(mapCanvas,0,0);
				cell.load(in);
				cells.add(cell);
			}

			Update();

			in.close();
		}
		catch (Exception e)
		{
			//Catch exception if any
			System.err.println("Error: " + e.getMessage());
		}
	}

	public JZMap(int width, int height, double _scale)
	{
		System.out.println("JZMap");
		VBox controls = null;
		ScrollPane mapPane = null;
		scale = _scale;

		// create a VBox for the option controls and add it to the layout
		controls = new VBox();
		getChildren().add(controls);

		// add controls to the control region of the layout
		Button load = new Button("Load");
		controls.getChildren().add(load);
		load.setOnMouseClicked(new EventHandler<MouseEvent>()
		{
			@Override
			public void handle(MouseEvent event)
			{
				try
				{
					_load();
				}
				catch(FileNotFoundException e)
				{
					System.out.println("FileNotFoundException");
				}
				catch(IOException e)
				{
					System.out.println("IOException");
				}
			}
		});

		Button save = new Button("Save");
		controls.getChildren().add(save);
		save.setOnMouseClicked(new EventHandler<MouseEvent>()
		{
			@Override
			public void handle(MouseEvent event)
			{
				try
				{
					_save();
				}
				catch(FileNotFoundException e)
				{
					System.out.println("FileNotFoundException");
				}
				catch(IOException e)
				{
					System.out.println("IOException");
				}
			}
		});

		Button zoomin = new Button("+");
		controls.getChildren().add(zoomin);
		zoomin.setOnMouseClicked(new EventHandler<MouseEvent>()
		{
			@Override
			public void handle(MouseEvent event)
			{
				System.out.println("zoomin");
				GraphicsContext gc = mapCanvas.getGraphicsContext2D();
				gc.setFill(Color.WHITE);
				gc.fillRect(0, 0, canvasWidth, canvasHeight);

				scale += 0.1;
				Update();
			}
		});

		Button zoomout = new Button("-");
		controls.getChildren().add(zoomout);
		zoomout.setOnMouseClicked(new EventHandler<MouseEvent>()
		{
			@Override
			public void handle(MouseEvent event)
			{
				System.out.println("zoomout");
				GraphicsContext gc = mapCanvas.getGraphicsContext2D();
				gc.setFill(Color.WHITE);
				gc.fillRect(0, 0, canvasWidth, canvasHeight);

				scale -= 0.1;
				Update();
			}
		});

		// create a canvas for the map
		mapCanvas = new Canvas(canvasWidth, canvasHeight );

		// create a scroll pane to contain the map with scroll bars and add it to the layout
		mapPane = new ScrollPane();
		getChildren().add(mapPane);

		// set the width and height of the display area of the map.  reduce the width by the width
		// of the controls region of the layout
		double controlsWidth = controls.getWidth();
		mapPane.setPrefSize(width-(int)controlsWidth,height);
		mapPane.setContent(mapCanvas);

		JZMapCell cell = null;

//		cell = new JZMapCell(mapCanvas, -2, -2);	cells.add(cell);
//		cell = new JZMapCell(mapCanvas, 0, -2);			cells.add(cell);
//		cell = new JZMapCell(mapCanvas, 2, -2);			cells.add(cell);
//
//		cell = new JZMapCell(mapCanvas, -2, 0);			cells.add(cell);
		cell = new JZMapCell(mapCanvas, 0, 0);				cells.add(cell);
//		cell = new JZMapCell(mapCanvas, 2, 0);				cells.add(cell);
//
//		cell = new JZMapCell(mapCanvas, -2, 2);			cells.add(cell);
//		cell = new JZMapCell(mapCanvas, 0, 2);				cells.add(cell);
//		cell = new JZMapCell(mapCanvas, 2, 2);				cells.add(cell);
//
//		cell = new JZMapCell(mapCanvas, -1, -1);			cells.add(cell);
//		cell = new JZMapCell(mapCanvas, 1, -1);			cells.add(cell);
//		cell = new JZMapCell(mapCanvas, -1, 1);			cells.add(cell);
//		cell = new JZMapCell(mapCanvas, 1, 1);			cells.add(cell);

		GraphicsContext gc = mapCanvas.getGraphicsContext2D();
		gc.setFill(Color.AQUA);
		gc.setStroke(Color.MAGENTA);
		gc.setLineWidth(2);
		gc.strokeLine(0, 0, canvasWidth, 0);
		gc.strokeLine(canvasWidth, 0, canvasWidth, canvasHeight);
		gc.strokeLine(canvasWidth, canvasHeight, 0, canvasHeight);
		gc.strokeLine(0, canvasHeight, 0, 0);

		// mouse and touch events for the map
		mapPane.setOnMouseClicked((e) -> {OnMouseClicked(e);});
		mapPane.setOnMouseMoved((e) -> {OnMouseMoved(e);});
		mapPane.setOnMouseEntered((e) -> {OnMouseEntered(e);});
		mapPane.setOnMouseExited((e) -> {OnMouseExited(e);});
		mapPane.setOnMousePressed((e) -> {OnMousePressed(e);});
		mapPane.setOnMouseReleased((e) -> {OnMouseReleased(e);});

		mapPane.setOnTouchMoved((e) -> {OnTouchMoved(e);});
		mapPane.setOnTouchPressed((e) -> {OnTouchPressed(e);});
		mapPane.setOnTouchReleased((e) -> {OnTouchReleased(e);});

		Update();
	}

	void Update()
	{
		System.out.println("Update");
		// get cell index range
		int cellIndexMinX = 0xffff;
		int cellIndexMinY = 0xffff;
		int cellIndexMaxX = -0xffff;
		int cellIndexMaxY = -0xffff;

		int n = cells.size();
		System.out.println("cell count: " + n);
		for(int i=0; i<n; i++)
		{
			JZMapCell cell = cells.get(i);
			if(cell.xIndex() > cellIndexMaxX)	cellIndexMaxX = cell.xIndex();
			if(cell.xIndex() < cellIndexMinX)	cellIndexMinX = cell.xIndex();
			if(cell.yIndex() > cellIndexMaxY)	cellIndexMaxY = cell.yIndex();
			if(cell.yIndex() < cellIndexMinY)	cellIndexMinY = cell.yIndex();
		}

		int cellCountX = cellIndexMaxX - cellIndexMinX + 2;
		int cellCountY = cellIndexMaxY - cellIndexMinY + 2;
		System.out.println("count: " + cellCountX + ", " + cellCountY);

		System.out.println("cellCount: " + cellCountX + ", " + cellCountY);

		canvasWidth = cellRadius * cellCountX * scale + canvasBorder;
		canvasHeight = cellRadius * cellCountY * scale + canvasBorder;

		System.out.println("canvas size: " + canvasWidth + ", " + canvasHeight);

		mapCanvas.setWidth(canvasWidth);
		mapCanvas.setHeight(canvasHeight);

		double xOffset = 0;
		double yOffset = 0;
		int r = cellCountX/2;
		if((r & 0x01) == 0 ) {
			xOffset = -cellRadius;
		}
		r = cellCountY/2;
		if((r & 0x01) == 0 ) {
			yOffset = -cellRadius;
		}

		GraphicsContext gc = mapCanvas.getGraphicsContext2D();
		gc.setFill(Color.WHITE);
		gc.fillRect(0, 0, canvasWidth, canvasHeight);

		gc.setFill(Color.AQUA);
		gc.setStroke(Color.MAGENTA);
		gc.setLineWidth(2);
		gc.strokeLine(0, 0, canvasWidth, 0);
		gc.strokeLine(canvasWidth, 0, canvasWidth, canvasHeight);
		gc.strokeLine(canvasWidth, canvasHeight, 0, canvasHeight);
		gc.strokeLine(0, canvasHeight, 0, 0);

		for(int i=0; i<cells.size(); i++)
		{
			JZMapCell cell = cells.get(i);
			if(cell != null)
			{
//				System.out.println("   cell: " + cell.xIndex() + ", " + cell.yIndex());
				cell.Update( xOffset+canvasWidth/2, yOffset+canvasHeight/2 ); // x, y, w, h );
			}
		}
	}

	void SetWidth(double width)
	{
		System.out.println("SetWidth(" + width + ")");
		setWidth(width);
	}

	void SetHeight(double height)
	{
		System.out.println("SetWidth(" + height + ")");
		setHeight(height);
	}

	public void OnTouchMoved(TouchEvent value)
	{
		//System.out.println("OnTouchMoved");
	}

	public void OnTouchPressed(TouchEvent value)
	{
		System.out.println("OnTouchPressed");
	}

	public void OnTouchReleased(TouchEvent value)
	{
		System.out.println("OnTouchReleased");
	}

	private JZMapCell find(int x, int y)
	{
		for(int i=0; i<cells.size(); i++)
		{
			JZMapCell cell = cells.get(i);
			if(cell != null)
			{
				if(x == cell.xIndex() && y == cell.yIndex())
					return cell;
			}
		}

		return null;
	}

	private boolean _dialogOpen = false;
	public void OnMouseClicked(MouseEvent value)
	{
		System.out.println("OnMouseClicked");
		double x = value.getX();
		double y = value.getY();

		JZMapCell selected = null;
		for(int i=0; i<cells.size(); i++)
		{

			JZMapCell cell = cells.get(i);
			if(cell.isHit(x,y) && !cell.Selected() == true)
			{
				cell.Selected(true);
				selected = cell;
			}
			else
				cell.Selected(false);
		}

		if(selected != null)
		{
			int[][] stuff = {
					{ -2, -2 },	{ -2, 0 },	{ -2, 2 },
					{ 0, -2 },				{ 0, 2 },
					{ 2, -2 },	{ 2, 0 },	{ 2, 2 },
					{ -1, -1 },				{ 1, -1 },
					{ -1, 1 },				{ 1, 1 },
			};
			int n = stuff.length;
			for(int i=0; i<n; i++)
			{
				if (find(selected.xIndex()+stuff[i][0], selected.yIndex()+stuff[i][1]) == null)
				{
					JZMapCell cell = new JZMapCell(mapCanvas, selected.xIndex()+stuff[i][0], selected.yIndex()+stuff[i][1]);
					cells.add(cell);
				}
			}

			Update();
			_dialogOpen = true;
			JZMapCellInfoDialog dialog = new JZMapCellInfoDialog(selected);
			dialog.showAndWait();
			_dialogOpen = false;

			Update();
		}
	}

	public void OnMouseMoved(MouseEvent value)
	{
		if(_dialogOpen) return;

//		System.out.println("OnMouseMoved");
		double x = value.getX();
		double y = value.getY();

		for(int i=0; i<cells.size(); i++)
		{
			JZMapCell cell = cells.get(i);
			if(cell.isHit(x,y)) {
				if(!cell.Hovered()) {
					System.out.println("isHit: " + cell.xIndex() + ", " + cell.yIndex());
					cell.Hovered(true);
					cell.Update();
				}
			}
			else {
				if(cell.Hovered()) {
					System.out.println("isHit: " + cell.xIndex() + ", " + cell.yIndex());
					cell.Hovered(false);
					cell.Update();
				}
			}
		}
	}

	public void OnMouseEntered(MouseEvent value)
	{
		System.out.println("OnMouseEntered");
	}

	public void OnMouseExited(MouseEvent value)
	{
		System.out.println("OnMouseExited");
	}

	public void OnMousePressed(MouseEvent value)
	{
		System.out.println("OnMousePressed");
	}

	public void OnMouseReleased(MouseEvent value)
	{
		System.out.println("OnMouseReleased");
	}
}
