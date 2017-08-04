import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.ArcType;
import javafx.stage.Stage;

public class Main extends Application
{

    @Override
    public void start(Stage primaryStage) throws Exception
    {
    	System.out.println("start");
        JZMap canvas = new JZMap(1000,800,1.0 );

        StackPane pane = new StackPane();
        pane.getChildren().add(canvas);

        Scene scene = new Scene(pane);

        // add listeners for when the window is resized.  Tell the map the new size
		scene.widthProperty().addListener(new ChangeListener<Number>()
        {
			@Override
            public void changed(ObservableValue<? extends Number> observableValue, Number oldSceneWidth, Number newSceneWidth)
            {
                canvas.SetWidth(newSceneWidth.doubleValue());
				System.out.println("Width: " + newSceneWidth);
			}
		});

		scene.heightProperty().addListener(new ChangeListener<Number>()
        {
			@Override
            public void changed(ObservableValue<? extends Number> observableValue, Number oldSceneHeight, Number newSceneHeight)
            {
                canvas.SetHeight(newSceneHeight.doubleValue());
				System.out.println("Height: " + newSceneHeight);
			}
		});

        primaryStage.setScene(scene);
        primaryStage.setTitle("JZMap");
        primaryStage.show();
    }


    public static void main(String[] args)
	{
		System.out.println("Main");
        launch(args);
    }
}
