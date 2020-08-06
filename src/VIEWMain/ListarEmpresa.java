/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package VIEWMain;

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
public class ListarEmpresa extends Application
{

    //Cria uma tela:
    private static Stage stage;

    @Override
    public void start(Stage tela) throws Exception
    {
	// 1- Lê/Carrega o FXML - Passar o caminho completo do FXML
	Parent root = FXMLLoader.load(getClass().getResource("/VIEW/FXMLListarEmpresa.fxml"));

	// 2 - Coloca o FXML em uma Scene
	Scene scene = new Scene(root);

	//CSS de Styles:
	scene.getStylesheets().add("/VIEW/MyStyles.css");

	// 3 - Coloca a Scene em uma janela
	tela.setScene(scene);
	tela.setTitle("Listar Empresa");

	// 4 - Abre a janela:
	tela.show();

	// 5 - Coloca a janela pronta em um novo Stage
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
	ListarEmpresa.stage = stage;
    }

}
