/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CONTROLLER;

import DAO.EmpresaDao;
import DTO.EmpresaDto;
import VIEWMain.CadEmpresa;
import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Andre
 */
public class CadEmpresaController implements Initializable
{

    @FXML
    private TextField txNome;

    @FXML
    private TextField txCpCnpj;

    @FXML
    private Button btCancelar;

    @FXML
    private Button btCadastrar;

    @FXML
    private ImageView imgFoto;

    private String caminhoFoto;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
	//================================================================================================
	btCancelar.setOnMouseClicked((MouseEvent e) ->
	{
	    fecharTela();
	    HomeController.abrirTela();
	});

	//usa a tecla ENTER:
	btCancelar.setOnKeyPressed((KeyEvent e) ->
	{
	    if (e.getCode() == KeyCode.ENTER)
	    {
		fecharTela();
		HomeController.abrirTela();
	    }
	});

	//================================================================================================
	btCadastrar.setOnMouseClicked((MouseEvent e) ->
	{
	    cadastrarEmpresa();
	});

	//usa a tecla ENTER:
	btCadastrar.setOnKeyPressed((KeyEvent e) ->
	{
	    if (e.getCode() == KeyCode.ENTER)
	    {
		cadastrarEmpresa();
	    }
	});

	//================================================================================================
	imgFoto.setOnMouseClicked((MouseEvent e) ->
	{
	    selecionarFoto();
	});

    }

    private void selecionarFoto()
    {
	//Criar janela para selecionar arquivo:
	FileChooser arquivo = new FileChooser();
	arquivo.getExtensionFilters().add(new ExtensionFilter("Imagens", "*.jpg", "*.png", "*.bmp"));//Filtro de seleão
	File file = arquivo.showOpenDialog(new Stage());//Retorna um File

	//Valida se foi selecionado algum arquivo:
	if (file != null)
	{
	    //Passa a imagem para o objeto Image:
	    imgFoto.setImage(new Image("file:///" + file.getAbsolutePath()));//Retorna o caminho absoluto do arquivo 
	    caminhoFoto = file.getAbsolutePath();//Guarda o caminho do arquivo
	}
	else
	{
	    Alert alert = new Alert(AlertType.ERROR);
	    alert.setHeaderText("Imagem não selecionada!");
	    alert.showAndWait();

	    //Limpa o caminho:
	    caminhoFoto = "";
	}

    }

    public void cadastrarEmpresa()
    {
	//Pegar os valores da tela
	String nome = txNome.getText();
	String cpfcnj = txCpCnpj.getText();
	String foto = caminhoFoto;

	//Cria o DTO:
	EmpresaDto empresa = new EmpresaDto(nome, cpfcnj, foto);

	//Cria o DAO
	EmpresaDao empresaDao = new EmpresaDao();

	//Valida se Cadastrou:
	if (empresaDao.insert(empresa))
	{
	    Alert alert = new Alert(AlertType.CONFIRMATION);
	    alert.setHeaderText("Registro adicionado: " + empresa.getId());

	    fecharTela();

	    alert.showAndWait();

	    HomeController.abrirTela();
	}
	else
	{
	    Alert alert = new Alert(AlertType.ERROR);
	    alert.setHeaderText("Erro ao cadastrar!");
	    alert.showAndWait();
	}

    }

    public static void fecharTela()
    {
	CadEmpresa.getStage().close();
    }

    public static void abrirTela()
    {
	//Instancio a tela desejada:
	CadEmpresa tela = new CadEmpresa();

	try
	{
	    tela.start(new Stage());
	}
	catch (Exception ex)
	{
	    Logger.getLogger(LogInController.class.getName()).log(Level.SEVERE, null, ex);
	}
    }

}
