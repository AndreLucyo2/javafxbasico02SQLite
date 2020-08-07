/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CONTROLLER;

import DAO.PessoaDao;
import entidades.PessoaDto;
import viewMain.AlterarPessoa;
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

    private String caminhoFoto = "H:/Noot_DELL/Dev_Software/WorkSpaceJAVA/ws-NetBeans/javafxbasico02/src/IMG/sem-foto.jpg";

    private static PessoaDto pessoaIn;

    public static PessoaDto getPessoaIn()
    {
	return pessoaIn;
    }

    public static void setPessoaIn(PessoaDto pessoaIn)
    {
	AlterarPessoaController.pessoaIn = pessoaIn;
    }

    @FXML
    private Label lblID;

    @Override
    public void initialize(URL url, ResourceBundle rb)
    {

	carregarPessoa();

	btCancelar.setOnMouseClicked((MouseEvent e) ->
	{
	    fecharTela();
	});

	btCancelar.setOnKeyPressed((KeyEvent e) ->
	{
	    if (e.getCode() == KeyCode.ENTER)
	    {
		fecharTela();
	    }
	});

	btAtualizar.setOnMouseClicked((MouseEvent e) ->
	{
	    atualizarPessoa();
	    fecharTela();
	});

	btAtualizar.setOnKeyPressed((KeyEvent e) ->
	{
	    if (e.getCode() == KeyCode.ENTER)
	    {
		atualizarPessoa();
		fecharTela();
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

	    imgFoto.setImage(new Image("file:///" + file.getAbsolutePath()));//Retorna o caminho absoluto do arquivo 

	    caminhoFoto = file.getAbsolutePath();
	}
	else
	{

	    imgFoto.setImage(new Image("file:///" + caminhoFoto));

	    Alert alert = new Alert(Alert.AlertType.ERROR);
	    alert.setHeaderText("Imagem não selecionada!");
	    alert.showAndWait();
	}
    }

    public void carregarPessoa()
    {

	lblID.setText(pessoaIn.getId() + "");
	txNome.setText(pessoaIn.getNome());
	txemail.setText(pessoaIn.getEmail());
	txSenha.setText(pessoaIn.getSenha());
	txConfSenha.setText(pessoaIn.getSenha());

	caminhoFoto = pessoaIn.getFoto();

	imgFoto.setImage(new Image("file:///" + pessoaIn.getFoto()));

    }

    public void atualizarPessoa()
    {

	lblAlertConfSenha.setVisible(true);

	if (txSenha.getText().equals(txConfSenha.getText()))
	{

	    PessoaDto pessoaDto = new PessoaDto();
	    pessoaDto.setId(pessoaIn.getId());
	    pessoaDto.setNome(txNome.getText());
	    pessoaDto.setEmail(txemail.getText());
	    pessoaDto.setSenha(txSenha.getText());
	    pessoaDto.setFoto(caminhoFoto);

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

	AlterarPessoa tela = new AlterarPessoa(null);

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
