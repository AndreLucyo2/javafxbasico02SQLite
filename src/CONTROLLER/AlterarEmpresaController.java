/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CONTROLLER;

import DAO.EmpresaDao;
import DTO.EmpresaDto;
import VIEWMain.AlterarEmpresa;
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
public class AlterarEmpresaController implements Initializable
{

    @FXML
    private ImageView imgFoto;

    @FXML
    private Label lblID;

    @FXML
    private TextField txNome;

    @FXML
    private TextField txCpCnpj;

    @FXML
    private Button btCancelar;

    @FXML
    private Button btAtualizar;

    //Caso nao selecionar foto:
    private String caminhoFoto = "H:/Noot_DELL/Dev_Software/WorkSpaceJAVA/ws-NetBeans/javafxbasico02/src/IMG/sem-foto.jpg";

    //======================== RECEBE OBJTO PARA SER ALTERADO ==============
    //Recebe uma empresa para alterar:
    private static EmpresaDto empresaIn;

    public static EmpresaDto getEmpresaIn()
    {
	return empresaIn;
    }

    public static void setEmpresaIn(EmpresaDto empresaIn)
    {
	AlterarEmpresaController.empresaIn = empresaIn;
    }
    //----------------------------------------------------------------------

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
	//ja inicializa com uma empresa carregada:
	carregarEmpresa();

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
	    atualizarEmpresa();
	    fecharTela();
	});

	//usa a tecla ENTER:
	btAtualizar.setOnKeyPressed((KeyEvent e) ->
	{
	    if (e.getCode() == KeyCode.ENTER)
	    {
		atualizarEmpresa();
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
    public void carregarEmpresa()
    {
	//Pegar os valores da tela
	lblID.setText(empresaIn.getId() + "");
	txNome.setText(empresaIn.getNome());
	txCpCnpj.setText(empresaIn.getCpfCnpj());

	//Guarda o caminho, caso nao alterar, grava o mesmo:
	caminhoFoto = empresaIn.getFoto();
	//Carrega a foto na tela:
	imgFoto.setImage(new Image("file:///" + empresaIn.getFoto()));

    }

    /**
     * Faz o update no banco:
     */
    public void atualizarEmpresa()
    {
	//Cria o DTO e pega os valores da tela
	EmpresaDto empresaDto = new EmpresaDto();
	empresaDto.setId(empresaIn.getId());
	empresaDto.setNome(txNome.getText());
	empresaDto.setCpfCnpj(txCpCnpj.getText());
	empresaDto.setFoto(caminhoFoto);

	//Cria o DAO e Valida se Cadastrou:
	EmpresaDao empresaDao = new EmpresaDao();
	if (empresaDao.update(empresaDto))
	{
	    Alert alert = new Alert(Alert.AlertType.INFORMATION);
	    alert.setHeaderText("Registro [ " + empresaDto.getId() + " ] Alterado com Sucesso: ");
	    fecharTela();
	    alert.showAndWait();
	}
	else
	{
	    Alert alert = new Alert(Alert.AlertType.WARNING);
	    alert.setHeaderText("Erro ao alterar!");
	    alert.showAndWait();
	}

    }

    public static void fecharTela()
    {
	AlterarEmpresa.getStage().close();
    }

    public static void abrirTela()
    {
	//Instancio a tela desejada:
	AlterarEmpresa tela = new AlterarEmpresa(null);

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
