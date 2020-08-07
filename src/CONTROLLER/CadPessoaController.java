/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CONTROLLER;

import DAO.PessoaDao;
import DTO.PessoaDto;
import VIEWMain.CadPessoa;
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
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Andre
 */
public class CadPessoaController implements Initializable
{

    @FXML
    private TextField txNome;

    @FXML
    private TextField txemail;

    @FXML
    private PasswordField txSenha;

    @FXML
    private PasswordField txConfSenha;

    @FXML
    private Label lblAlertConfSenha;

    @FXML
    private Button btCancelar;

    @FXML
    private Button btCadastrar;

    @FXML
    private ImageView imgFoto;

    
    private String caminhoFoto = "H:/Noot_DELL/Dev_Software/WorkSpaceJAVA/ws-NetBeans/javafxbasico02/src/IMG/sem-foto.jpg";

    /**
     * Initializes the controller class.
     */
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
	    cadastrarPessoa();
	    fecharTela();
	    HomeController.abrirTela();
	});

	
	btCadastrar.setOnKeyPressed((KeyEvent e) ->
	{
	    if (e.getCode() == KeyCode.ENTER)
	    {
		cadastrarPessoa();
		fecharTela();
		HomeController.abrirTela();
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
	arquivo.getExtensionFilters().add(new FileChooser.ExtensionFilter("Imagens", "*.jpg", "*.png", "*.bmp"));
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

	}

    }

    public void cadastrarPessoa()
    {
	
	String nome = txNome.getText();
	String email = txemail.getText();
	String senha = txSenha.getText();
	String confirmaSenha = txConfSenha.getText();
	String foto = caminhoFoto;

	
	lblAlertConfSenha.setVisible(false);

	
	if (senha.equals(confirmaSenha))
	{
	    
	    PessoaDto pessoa = new PessoaDto(nome, email, senha, foto);

	    
	    PessoaDao pessoaDao = new PessoaDao();

	    
	    if (pessoaDao.insert(pessoa))
	    {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setHeaderText(pessoa.getNome().toUpperCase() + "\nAdicionado com sucesso");
		alert.showAndWait();
	    }
	    else
	    {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setHeaderText("Erro ao cadastrar!");
		alert.showAndWait();
	    }

	}
	else
	{
	    Alert alert = new Alert(AlertType.ERROR);
	    alert.setHeaderText("As senhas não coincidem!");
	    alert.showAndWait();

	}

    }

    public static void fecharTela()
    {
	CadPessoa.getStage().close();
    }

    public static void abrirTela()
    {
	
	CadPessoa tela = new CadPessoa();

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
