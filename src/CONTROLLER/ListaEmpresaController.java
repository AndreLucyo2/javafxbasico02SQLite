/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CONTROLLER;

import DAO.EmpresaDao;
import DTO.EmpresaDto;
import VIEWMain.AlterarEmpresa;
import VIEWMain.ListarEmpresa;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Andre
 */
public class ListaEmpresaController implements Initializable
{

    @FXML
    private TableView<EmpresaDto> tblEmpresa;

    @FXML
    private TableColumn<EmpresaDto, Long> colId;

    @FXML
    private TableColumn<EmpresaDto, String> colNome;

    @FXML
    private TableColumn<EmpresaDto, String> colCpfCnpj;

    @FXML
    private Button btDeletar;

    @FXML
    private Button btAtualizar;

    @FXML
    private Button btCancelar;

    @FXML
    private ImageView imgEmpresa;

    @FXML
    private Button btAlterar;

    @FXML
    private Label lblID;

    @FXML
    private Label lblNome;

    @FXML
    private Label lblCpfCnpj;

    private EmpresaDto empresaSelected;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
	inicializaTabela();

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

	
	tblEmpresa.getSelectionModel().selectedItemProperty().addListener(new ChangeListener()
	{
	    
	    @Override
	    public void changed(ObservableValue observable, Object oldValue, Object newValue)
	    {
		
		empresaSelected = (EmpresaDto) newValue;

		mostrarDetalhes();
	    }
	});

	
	btDeletar.setOnMouseClicked((MouseEvent e) ->
	{
	    deletarItem();
	});

	
	btDeletar.setOnKeyPressed((KeyEvent e) ->
	{
	    if (e.getCode() == KeyCode.ENTER)
	    {
		deletarItem();
	    }
	});

	
	btAtualizar.setOnMouseClicked((MouseEvent e) ->
	{
	    tblEmpresa.setItems(atualizaObsLista());

	});

	
	btAtualizar.setOnKeyPressed((KeyEvent e) ->
	{
	    if (e.getCode() == KeyCode.ENTER)
	    {
		tblEmpresa.setItems(atualizaObsLista());
	    }
	});

	
	btAlterar.setOnMouseClicked((MouseEvent e) ->
	{
	    alterarItem();

	});

	
	btAlterar.setOnKeyPressed((KeyEvent e) ->
	{
	    if (e.getCode() == KeyCode.ENTER)
	    {
		alterarItem();
	    }
	});

    }

    private void inicializaTabela()
    {
	colId.setCellValueFactory(new PropertyValueFactory("id"));
	colNome.setCellValueFactory(new PropertyValueFactory("nome"));
	colCpfCnpj.setCellValueFactory(new PropertyValueFactory("cpfCnpj"));
	tblEmpresa.setItems(atualizaObsLista());
    }

    private ObservableList<EmpresaDto> atualizaObsLista()
    {
	EmpresaDao empresa = new EmpresaDao();
	return FXCollections.observableArrayList(empresa.selecAll());
    }

    /**
     * Carrega os dados do item senecionado:
     */
    private void mostrarDetalhes()
    {
	
	if (empresaSelected != null)
	{
	    lblID.setText(empresaSelected.getId() + "");
	    lblNome.setText(empresaSelected.getNome());
	    lblCpfCnpj.setText(empresaSelected.getCpfCnpj());

	    
	    if (empresaSelected.getFoto() != null)
	    {
		
		imgEmpresa.setImage(new Image("file:///" + empresaSelected.getFoto()));
	    }
	    else
	    {
		

		String caminhoFoto = "@../IMG/sem-foto.jpg";
		imgEmpresa.setImage(new Image("file:///" + caminhoFoto));
	    }

	}
	else
	{
	    lblID.setText("");
	    lblNome.setText("");
	    lblCpfCnpj.setText("");
	    
	}

    }

    private void alterarItem()
    {
	
	if (empresaSelected != null)
	{
	    
	    AlterarEmpresa tela = new AlterarEmpresa(empresaSelected);

	    try
	    {
		tela.start(new Stage());
	    }
	    catch (Exception ex)
	    {
		Logger.getLogger(LogInController.class.getName()).log(Level.SEVERE, null, ex);
	    }

	    
	    tblEmpresa.setItems(atualizaObsLista());

	}
	else
	{
	    
	    Alert alert = new Alert(Alert.AlertType.WARNING);
	    alert.setTitle("Atenção!");
	    alert.setHeaderText("Selecione um item.");
	    alert.showAndWait();
	}
    }

    private void deletarItem()
    {
	
	if (empresaSelected != null)
	{
	    Alert alert1 = new Alert(Alert.AlertType.CONFIRMATION);
	    alert1.setHeaderText("Deseja realmente deletar o registro [ " + empresaSelected.getId() + " ] ? ");
	    alert1.showAndWait();
	    
	    
	    System.out.println(alert1.getResult().getText());

	    if (alert1.getResult().getText().equals("OK"))
	    {
		EmpresaDao empresaDao = new EmpresaDao();
		empresaDao.delete(empresaSelected);

		
		tblEmpresa.setItems(atualizaObsLista());

		
		Alert alert = new Alert(Alert.AlertType.INFORMATION);
		alert.setTitle("Sucesso!");
		alert.setHeaderText("Registro deletado com sucesso.");
		alert.setContentText("");
		alert.showAndWait();
	    }

	}
	else
	{
	    
	    Alert alert = new Alert(Alert.AlertType.WARNING);
	    alert.setTitle("Atenção!");
	    alert.setHeaderText("Selecione um item.");
	    alert.setContentText("");
	    alert.showAndWait();
	}

    }

    public static void fecharTela()
    {
	
	ListarEmpresa.getStage().close();
    }

    public static void abrirTela()
    {
	
	ListarEmpresa tela = new ListarEmpresa();

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
