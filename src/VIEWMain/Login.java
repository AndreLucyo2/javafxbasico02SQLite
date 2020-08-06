/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package VIEWMain;

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
	// 1- Lê/Carrega o FXML - Passar o caminho completo do FXML
	Parent root = FXMLLoader.load(getClass().getResource("/VIEW/FXMLLogIn.fxml"));

	// 2 - Coloca o FXML em uma Scene
	Scene scene = new Scene(root);

	//CSS de Styles:
	scene.getStylesheets().add("/VIEW/MyStyles.css");

	// 3 - Coloca a Scene em uma janela
	stage.setScene(scene);
	stage.setTitle("Login");

	// 4 - Abre a janela:
	stage.show();

	// 5 - Coloca a janela pronta em um novo Stage
	setStage(stage);

	//Clicar no X techar:
	stage.setOnCloseRequest(new EventHandler<WindowEvent>()
	{
	    @Override
	    public void handle(WindowEvent event)
	    {
		event.consume();

		// código a ser executado ao fechar o sistema.
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
