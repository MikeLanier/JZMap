import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;

class JZMapCellInfoDialog extends Stage
{
	private JZMapCell _previewCell = null;
	private HBox _pathPane = new HBox();
	private HBox _roomControls = new HBox();
	private CheckBox _room = new CheckBox("Room");
	private CheckBox _path = new CheckBox("Path");
	private CheckBox _northBox = new CheckBox("North");
	private CheckBox _southBox = new CheckBox("South");
	private CheckBox _eastBox = new CheckBox("East");
	private CheckBox _westBox = new CheckBox("West");
	private CheckBox _northeastBox = new CheckBox("Northeast");
	private CheckBox _northwestBox = new CheckBox("Northwest");
	private CheckBox _southeastBox = new CheckBox("Southeast");
	private CheckBox _southwestBox = new CheckBox("Southwest");
	private CheckBox _upBox = new CheckBox("Up");
	private CheckBox _downBox = new CheckBox("Down");

	JZMapCellInfoDialog(JZMapCell cell)
	{
		setOnCloseRequest(new EventHandler<WindowEvent>()
		{
			public void handle(WindowEvent we)
			{
				System.out.println("Stage is closing");
				if(_previewCell != null)
				{
					cell.copy(_previewCell);
				}
			}
		});

		initStyle(StageStyle.UTILITY);

		// Cell Info Dialog
		//      Display
		//      Cell type
		//          Room
		//          Path
		//          undefined
		//      Path enter/exit
		//          north, south, east, west, northeast, northwest, southeast, southwest, up, down
		//          one way, two way
		//      Room
		//          Name
		//          Description
		//          Stuff in room

		Canvas preview = new Canvas(200,200);
		_previewCell = new JZMapCell(preview, 0, 0);
		_previewCell.copy(cell);

		// a vertical pane for the controls of the dialog
		VBox pane = new VBox();
		pane.setPrefSize(450,400);

		{
			// first set of controls, cell type.  Label on the left
			// radio buttons on the right.  Create an HBox to contain
			// these. Add it to the VBox
			HBox cellType = new HBox();
			pane.getChildren().add(cellType);

			{
				// create the label and add it to the HBox
				Label cellTypeLabel = new Label("Cell type:");
				cellType.getChildren().add(cellTypeLabel);
				cellTypeLabel.setPadding(new Insets(12, 12, 12, 12));

				// create a VBox for the cell type radio buttons. One for room and
				// one for path, stack one above the other. Add it to the HBox
				VBox cellTypeOptions = new VBox();
				cellTypeOptions.setPadding(new Insets(12, 12, 12, 12));
				cellType.getChildren().add(cellTypeOptions);

				{
					// create a radio button for the room option and add it to the VBox
					cellTypeOptions.getChildren().add(_room);
					_room.setOnMouseClicked(new EventHandler<MouseEvent>()
					{
						@Override
						public void handle(MouseEvent event)
						{
							_pathPane.setVisible(true);
							_roomControls.setVisible(true);
							_path.setSelected(false);
							_previewCell.type(JZMapCell.Type.room);
							_previewCell.Update(60, 60);
						}
					});

					// create a radio button for the path option and add it to the VBox
					cellTypeOptions.getChildren().add(_path);
					_path.setOnMouseClicked(new EventHandler<MouseEvent>()
					{
						@Override
						public void handle(MouseEvent event)
						{
							_pathPane.setVisible(true);
							_roomControls.setVisible(false);
							_room.setSelected(false);
							_previewCell.type(JZMapCell.Type.path);
							_previewCell.Update(60, 60);
						}
					});
				}

				cellType.getChildren().add(preview);
				_previewCell.Update(60, 60);
			}

			// controls for defineing the paths in and out of the cell
			pane.getChildren().add(_pathPane);

			{
				// label for this set of controls
				Label pathPaneLabel = new Label("Paths enter/exit");
				pathPaneLabel.setPadding(new Insets(12, 12, 12, 12));
				_pathPane.getChildren().add(pathPaneLabel);

				// check boxes for the different paths; north, south, east, west, northeast, northwest,
				// southeast, southwest, up and down
				VBox paths = new VBox();
				paths.setPadding(new Insets(12, 12, 12, 12));
				_pathPane.getChildren().add(paths);

				{
					paths.getChildren().add(_northBox);
					_northBox.setOnMouseClicked(new EventHandler<MouseEvent>()
					{
						@Override
						public void handle(MouseEvent event)
						{
							_previewCell.ToggleExit(JZMapCell.Exit.north, _northBox.isSelected());
							_previewCell.Update(60, 60);
						}
					});

					paths.getChildren().add(_southBox);
					_southBox.setOnMouseClicked(new EventHandler<MouseEvent>()
					{
						@Override
						public void handle(MouseEvent event)
						{
							_previewCell.ToggleExit(JZMapCell.Exit.south, _southBox.isSelected());
							_previewCell.Update(60, 60);
						}
					});

					paths.getChildren().add(_eastBox);
					_eastBox.setOnMouseClicked(new EventHandler<MouseEvent>()
					{
						@Override
						public void handle(MouseEvent event)
						{
							_previewCell.ToggleExit(JZMapCell.Exit.east, _eastBox.isSelected());
							_previewCell.Update(60, 60);
						}
					});

					paths.getChildren().add(_westBox);
					_westBox.setOnMouseClicked(new EventHandler<MouseEvent>()
					{
						@Override
						public void handle(MouseEvent event)
						{
							_previewCell.ToggleExit(JZMapCell.Exit.west, _westBox.isSelected());
							_previewCell.Update(60, 60);
						}
					});

					paths.getChildren().add(_northeastBox);
					_northeastBox.setOnMouseClicked(new EventHandler<MouseEvent>()
					{
						@Override
						public void handle(MouseEvent event)
						{
							_previewCell.ToggleExit(JZMapCell.Exit.northeast, _northeastBox.isSelected());
							_previewCell.Update(60, 60);
						}
					});

					paths.getChildren().add(_northwestBox);
					_northwestBox.setOnMouseClicked(new EventHandler<MouseEvent>()
					{
						@Override
						public void handle(MouseEvent event)
						{
							_previewCell.ToggleExit(JZMapCell.Exit.northwest, _northwestBox.isSelected());
							_previewCell.Update(60, 60);
						}
					});

					paths.getChildren().add(_southeastBox);
					_southeastBox.setOnMouseClicked(new EventHandler<MouseEvent>()
					{
						@Override
						public void handle(MouseEvent event)
						{
							_previewCell.ToggleExit(JZMapCell.Exit.southeast, _southeastBox.isSelected());
							_previewCell.Update(60, 60);
						}
					});

					paths.getChildren().add(_southwestBox);
					_southwestBox.setOnMouseClicked(new EventHandler<MouseEvent>()
					{
						@Override
						public void handle(MouseEvent event)
						{
							_previewCell.ToggleExit(JZMapCell.Exit.southwest, _southwestBox.isSelected());
							_previewCell.Update(60, 60);
						}
					});

					paths.getChildren().add(_upBox);
					_upBox.setOnMouseClicked(new EventHandler<MouseEvent>()
					{
						@Override
						public void handle(MouseEvent event)
						{
							_previewCell.ToggleExit(JZMapCell.Exit.up, _upBox.isSelected());
							_previewCell.Update(60, 60);
						}
					});

					paths.getChildren().add(_downBox);
					_downBox.setOnMouseClicked(new EventHandler<MouseEvent>()
					{
						@Override
						public void handle(MouseEvent event)
						{
							_previewCell.ToggleExit(JZMapCell.Exit.down, _downBox.isSelected());
							_previewCell.Update(60, 60);
						}
					});
				}

				// check boxes to define if a path is one way or two way
				VBox oneway = new VBox();
				oneway.setPadding(new Insets(12, 12, 12, 12));
				_pathPane.getChildren().add(oneway);

				{
					CheckBox northOneWay = new CheckBox("One Way");
					oneway.getChildren().add(northOneWay);

					CheckBox southOneWay = new CheckBox("One Way");
					oneway.getChildren().add(southOneWay);

					CheckBox eastOneWay = new CheckBox("One Way");
					oneway.getChildren().add(eastOneWay);

					CheckBox westOneWay = new CheckBox("One Way");
					oneway.getChildren().add(westOneWay);

					CheckBox northeastOneWay = new CheckBox("One Way");
					oneway.getChildren().add(northeastOneWay);

					CheckBox northwestOneWay = new CheckBox("One Way");
					oneway.getChildren().add(northwestOneWay);

					CheckBox southeastOneWay = new CheckBox("One Way");
					oneway.getChildren().add(southeastOneWay);

					CheckBox southwestOneWay = new CheckBox("One Way");
					oneway.getChildren().add(southwestOneWay);

					CheckBox upOneWay = new CheckBox("One Way");
					oneway.getChildren().add(upOneWay);

					CheckBox downOneWay = new CheckBox("One Way");
					oneway.getChildren().add(downOneWay);
				}
			}

			// controls for the room info
			pane.getChildren().add(_roomControls);

			{
				// label for the room info controls
				Label roomControlsLabel = new Label("Room");
				roomControlsLabel.setPadding(new Insets(12, 12, 12, 12));
				_roomControls.getChildren().add(roomControlsLabel);

				// controls for room title, description and inventory
				VBox roomInfo = new VBox();
				roomInfo.setPadding(new Insets(12, 12, 12, 12));
				_roomControls.getChildren().add(roomInfo);

				{
					TextArea title = new TextArea();
					title.setPrefHeight(18);
					title.setPrefWidth(300);
					roomInfo.getChildren().add(title);

					TextArea desc = new TextArea();
					desc.setPrefHeight(100);
					desc.setPrefWidth(300);
					roomInfo.getChildren().add(desc);

					ComboBox inventory = new ComboBox();
					inventory.setPrefWidth(300);
					roomInfo.getChildren().add(inventory);
				}
			}

			HBox buttons = new HBox();
			buttons.setPadding(new Insets(12, 12, 12, 12));
			pane.getChildren().add(buttons);

			{
				Button save = new Button("Save");
				buttons.getChildren().add(save);

				Button cancel = new Button("Cancel");
				buttons.getChildren().add(cancel);
			}
		}

		_pathPane.setVisible(false);
		_roomControls.setVisible(false);
		if(_previewCell.type() == JZMapCell.Type.path)
		{
			_path.setSelected(true);
			_pathPane.setVisible(true);
		}

		if(_previewCell.type() == JZMapCell.Type.room)
		{
			_room.setSelected(true);
			_pathPane.setVisible(true);
			_roomControls.setVisible(true);
		}

		if(_previewCell.hasExit(JZMapCell.Exit.north))	_northBox.setSelected(true);
		if(_previewCell.hasExit(JZMapCell.Exit.south))	_southBox.setSelected(true);
		if(_previewCell.hasExit(JZMapCell.Exit.east))	_eastBox.setSelected(true);
		if(_previewCell.hasExit(JZMapCell.Exit.west))	_westBox.setSelected(true);
		if(_previewCell.hasExit(JZMapCell.Exit.northeast))	_northeastBox.setSelected(true);
		if(_previewCell.hasExit(JZMapCell.Exit.northwest))	_northwestBox.setSelected(true);
		if(_previewCell.hasExit(JZMapCell.Exit.southeast))	_southeastBox.setSelected(true);
		if(_previewCell.hasExit(JZMapCell.Exit.southwest))	_southwestBox.setSelected(true);
		if(_previewCell.hasExit(JZMapCell.Exit.up))		_upBox.setSelected(true);
		if(_previewCell.hasExit(JZMapCell.Exit.down))	_downBox.setSelected(true);

		Scene scene = new Scene(pane);
		setScene(scene);
	}
}
