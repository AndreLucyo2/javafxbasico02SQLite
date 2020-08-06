/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CONTROLLER;

import DAO.PessoaDao;
import DTO.PessoaDto;
import VIEWMain.AlterarPessoa;
import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
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
public class AlterarPessoaController implements Initializable
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
    private Button btAtualizar;

    @FXML
    private ImageView imgFoto;

    //Caso nao selecionar foto:
    private String caminhoFoto = "H:/Noot_DELL/Dev_Software/WorkSpaceJAVA/ws-NetBeans/javafxbasico02/src/IMG/sem-foto.jpg";

    //======================== RECEBE OBJTO PARA SER ALTERADO ==============
    //Recebe uma pessoa para alterar:
    private static PessoaDto pessoaIn;

    public static PessoaDto getPessoaIn()
    {
	return pessoaIn;
    }

    public static void setPessoaIn(PessoaDto pessoaIn)
    {
	AlterarPessoaController.pessoaIn = pessoaIn;
    }
    //----------------------------------------------------------------------

    @FXML
    private Label lblID;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
	//ja inicializa com uma pessoa carregada:
	carregarPessoa();

	//================================================================================================
	btCancelar.setOnMouseClicked((MouseEvent e) ->
	{
	    fecharTela();
	});

	//usa a tecla ENTER:
	btCancelar.setOnKeyPressed((KeyEvent e) ->
	{
	    if (e.getCode() == KeyCode.ENTER)
	    {
		fecharTela();
	    }
	});

	//================================================================================================
	btAtualizar.setOnMouseClicked((MouseEvent e) ->
	{
	    atualizarPessoa();
	    fecharTela();
	});

	//usa a tecla ENTER:
	btAtualizar.setOnKeyPressed((KeyEvent e) ->
	{
	    if (e.getCode() == KeyCode.ENTER)
	    {
		atualizarPessoa();
		fecharTela();
	    }
	});

	//================================================================================================
	imgFoto.setOnMouseClicked((MouseEvent e) ->
	{
	    selecionarFoto();
	});
    }

    /**
     * Atualiza a foto na tela e o caminho no variavel
     */
    private void selecionarFoto()
    {
	//Criar janela para selecionar arquivo:
	FileChooser arquivo = new FileChooser();
	arquivo.getExtensionFilters().add(new FileChooser.ExtensionFilter("Imagens", "*.jpg", "*.png", "*.bmp"));//Filtro de seleão
	File file = arquivo.showOpenDialog(new Stage());//Retorna um File

	//Valida se foi selecionado algum arquivo:
	if (file != null)
	{
	    //Passa a imagem para o objeto Image da tela:
	    imgFoto.setImage(new Image("file:///" + file.getAbsolutePath()));//Retorna o caminho absoluto do arquivo 

	    //Guarda o caminho do arquivo para gravar no banco 
	    caminhoFoto = file.getAbsolutePath();
	}
	else
	{
	    //Mantem sem foto:
	    imgFoto.setImage(new Image("file:///" + caminhoFoto));
	    
	    //Alerta se não selecionar nada, mantem foto atual
	    Alert alert = new Alert(Alert.AlertType.ERROR);
	    alert.setHeaderText("Imagem não selecionada!");
	    alert.showAndWait();
	}
    }

    /**
     * Carrega os dados do objeto na tela:
     */
    public void carregarPessoa()
    {
	//Pegar os valores da tela
	lblID.setText(pessoaIn.getId() + "");
	txNome.setText(pessoaIn.getNome());
	txemail.setText(pessoaIn.getEmail());
	txSenha.setText(pessoaIn.getSenha());
	txConfSenha.setText(pessoaIn.getSenha());

	//Guarda o caminho, caso nao alterar, grava o mesmo:
	caminhoFoto = pessoaIn.getFoto();
	//Carrega a foto na tela:
	imgFoto.setImage(new Image("file:///" + pessoaIn.getFoto()));

    }

    /**
     * Faz o update no banco:
     */
    public void atualizarPessoa()
    {
	//Oculta Aviso de senha:
	lblAlertConfSenha.setVisible(true);

	//Confirmar Senha: BLL
	if (txSenha.getText().equals(txConfSenha.getText()))
	{
	    //Cria o DTO e pega os valores da tela
	    PessoaDto pessoaDto = new PessoaDto();	    
	    pessoaDto.setId(pessoaIn.getId());
	    pessoaDto.setNome(txNome.getText());
	    pessoaDto.setEmail(txemail.getText());
	    pessoaDto.setSenha(txSenha.getText());
	    pessoaDto.setFoto(caminhoFoto); 

	    //Cria o DAO e Valida se Cadastrou:
	    PessoaDao pessoaDao = new PessoaDao();	   
	    if (pessoaDao.update(pessoaDto))
	    {
		Alert alert = new Alert(Alert.AlertType.INFORMATION);
		alert.setHeaderText("Registro [ " + pessoaDto.getId() + " ] Alterado com Sucesso: ");
		fecharTela();
		alert.showAndWait();
	    }
	    else
	    {
		Alert alert = new Alert(Alert.AlertType.ERROR);
		alert.setHeaderText("Erro ao alterar!");
		alert.showAndWait();
	    }

	}
	else
	{
	    Alert alert = new Alert(Alert.AlertType.ERROR);
	    alert.setHeaderText("As senhas não coincidem!");
	    alert.showAndWait();

	    lblAlertConfSenha.setVisible(true);
	}

    }

    public static void fecharTela()
    {
	AlterarPessoa.getStage().close();
    }

    public static void abrirTela()
    {
	//Instancio a tela desejada:
	AlterarPessoa tela = new AlterarPessoa(null);

	try
	{
	    //Mostra a tela:
	    tela.start(new Stage());
	}
	catch (Exception ex)
	{
	    Logger.getLogger(LogInController.class.getName()).log(Level.SEVERE, null, ex);
	}
    }

}
