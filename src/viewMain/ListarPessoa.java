/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package viewMain;

import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author Andre
 */
public class ListarPessoa extends Application
{

    private static Stage stage;

    @Override
    public void start(Stage tela) throws Exception
    {

	Parent root = FXMLLoader.load(getClass().getResource("/viewFxml/FXMLListarPessoa.fxml"));

	Scene scene = new Scene(root);

	scene.getStylesheets().add("/viewFxml/MyStyles.css");

	tela.setScene(scene);
	tela.setTitle("Listar Pessoas");

	tela.show();

	setStage(tela);

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
	ListarPessoa.stage = stage;
    }

}
