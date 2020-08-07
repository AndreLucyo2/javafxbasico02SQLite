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

  
    private String caminhoFoto = "H:/Noot_DELL/Dev_Software/WorkSpaceJAVA/ws-NetBeans/javafxbasico02/src/IMG/sem-foto.jpg";

    private static EmpresaDto empresaIn;

    public static EmpresaDto getEmpresaIn()
    {
	return empresaIn;
    }

    public static void setEmpresaIn(EmpresaDto empresaIn)
    {
	AlterarEmpresaController.empresaIn = empresaIn;
    }
  
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
	
	carregarEmpresa();

	
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
	    atualizarEmpresa();
	    fecharTela();
	});


	btAtualizar.setOnKeyPressed((KeyEvent e) ->
	{
	    if (e.getCode() == KeyCode.ENTER)
	    {
		atualizarEmpresa();
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


    public void carregarEmpresa()
    {
	
	lblID.setText(empresaIn.getId() + "");
	txNome.setText(empresaIn.getNome());
	txCpCnpj.setText(empresaIn.getCpfCnpj());


	caminhoFoto = empresaIn.getFoto();

	imgFoto.setImage(new Image("file:///" + empresaIn.getFoto()));

    }


    public void atualizarEmpresa()
    {

	EmpresaDto empresaDto = new EmpresaDto();
	empresaDto.setId(empresaIn.getId());
	empresaDto.setNome(txNome.getText());
	empresaDto.setCpfCnpj(txCpCnpj.getText());
	empresaDto.setFoto(caminhoFoto);


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
	
	AlterarEmpresa tela = new AlterarEmpresa(null);

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
