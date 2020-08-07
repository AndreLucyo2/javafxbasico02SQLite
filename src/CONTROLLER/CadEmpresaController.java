/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CONTROLLER;

import DAO.EmpresaDao;
import entidades.EmpresaDto;
import viewMain.CadEmpresa;
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

    @Override
    public void initialize(URL url, ResourceBundle rb)
    {

	btCancelar.setOnMouseClicked((MouseEvent e) ->
	{
	    fecharTela();
	    HomeController.abrirTela();
	});

	btCancelar.setOnKeyPressed((KeyEvent e) ->
	{
	    if (e.getCode() == KeyCode.ENTER)
	    {
		fecharTela();
		HomeController.abrirTela();
	    }
	});

	btCadastrar.setOnMouseClicked((MouseEvent e) ->
	{
	    cadastrarEmpresa();
	});

	btCadastrar.setOnKeyPressed((KeyEvent e) ->
	{
	    if (e.getCode() == KeyCode.ENTER)
	    {
		cadastrarEmpresa();
	    }
	});

	imgFoto.setOnMouseClicked((MouseEvent e) ->
	{
	    selecionarFoto();
	});

    }

    private void selecionarFoto()
    {

	FileChooser arquivo = new FileChooser();
	arquivo.getExtensionFilters().add(new ExtensionFilter("Imagens", "*.jpg", "*.png", "*.bmp"));
	File file = arquivo.showOpenDialog(new Stage());

	if (file != null)
	{

	    imgFoto.setImage(new Image("file:///" + file.getAbsolutePath()));
	    caminhoFoto = file.getAbsolutePath();
	}
	else
	{
	    Alert alert = new Alert(AlertType.ERROR);
	    alert.setHeaderText("Imagem não selecionada!");
	    alert.showAndWait();

	    caminhoFoto = "";
	}

    }

    public void cadastrarEmpresa()
    {

	String nome = txNome.getText();
	String cpfcnj = txCpCnpj.getText();
	String foto = caminhoFoto;

	EmpresaDto empresa = new EmpresaDto(nome, cpfcnj, foto);

	EmpresaDao empresaDao = new EmpresaDao();

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
