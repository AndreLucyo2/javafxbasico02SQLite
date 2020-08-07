/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package VIEWMain;

import CONTROLLER.AlterarEmpresaController;
import DTO.EmpresaDto;
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
public class AlterarEmpresa extends Application
{
    
    private static Stage stage;

    
    public AlterarEmpresa(EmpresaDto empresa)
    {
	AlterarEmpresaController.setEmpresaIn(empresa);
    }      
    
    @Override
    public void start(Stage tela) throws Exception
    {
	
	Parent root = FXMLLoader.load(getClass().getResource("/VIEW/FXMLAlterarEmpresa.fxml"));

	
	Scene scene = new Scene(root);

	
	scene.getStylesheets().add("/VIEW/MyStyles.css");

	
	tela.setScene(scene);
	tela.setTitle("Alterar de Empresa");

	
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
	AlterarEmpresa.stage = stage;
    }

}
