/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package viewMain;

import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 *
 * @author Andre
 */
public class Login extends Application
{

    private static Stage stage;

    @Override
    public void start(Stage stage) throws Exception
    {

	Parent root = FXMLLoader.load(getClass().getResource("/viewFxml/FXMLLogIn.fxml"));

	Scene scene = new Scene(root);

	scene.getStylesheets().add("/viewFxml/MyStyles.css");

	stage.setScene(scene);
	stage.setTitle("Login");

	stage.show();

	setStage(stage);

	stage.setOnCloseRequest(new EventHandler<WindowEvent>()
	{
	    @Override
	    public void handle(WindowEvent event)
	    {
		event.consume();

		stage.close();
		Platform.exit();
		System.exit(0);
	    }
	});

    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)
    {

	launch(args);

    }

    public static Stage getStage()
    {
	return stage;
    }

    public static void setStage(Stage stage)
    {
	Login.stage = stage;
    }

}
