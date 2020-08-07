/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package VIEWMain;

import CONTROLLER.AlterarPessoaController;
import DTO.PessoaDto;
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
public class AlterarPessoa extends Application
{
    
    private static Stage stage;

    
    public AlterarPessoa(PessoaDto pessoa)
    {
	AlterarPessoaController.setPessoaIn(pessoa);
    }  
    
    
    @Override
    public void start(Stage tela) throws Exception
    {
	
	Parent root = FXMLLoader.load(getClass().getResource("/VIEW/FXMLAlterarPessoa.fxml"));

	
	Scene scene = new Scene(root);

	
	scene.getStylesheets().add("/VIEW/MyStyles.css");

	
	tela.setScene(scene);
	tela.setTitle("Alterar de Pessoa");

	
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
	AlterarPessoa.stage = stage;
    }

}
