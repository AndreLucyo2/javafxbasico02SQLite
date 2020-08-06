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

	//usa a tecla ENTER:
	btCancelar.setOnKeyPressed((KeyEvent e) ->
	{
	    if (e.getCode() == KeyCode.ENTER)
	    {
		fecharTela();
		HomeController.abrirTela();
	    }
	});

	//----------------------------------------------------------------
	tblEmpresa.getSelectionModel().selectedItemProperty().addListener(new ChangeListener()
	{
	    //Executa ao selecionar um item na tabela:
	    @Override
	    public void changed(ObservableValue observable, Object oldValue, Object newValue)
	    {
		//Pegar a empresa selecionadoa:
		empresaSelected = (EmpresaDto) newValue;

		mostrarDetalhes();
	    }
	});

	//----------------------------------------------------------------
	btDeletar.setOnMouseClicked((MouseEvent e) ->
	{
	    deletarItem();
	});

	//usa a tecla ENTER:
	btDeletar.setOnKeyPressed((KeyEvent e) ->
	{
	    if (e.getCode() == KeyCode.ENTER)
	    {
		deletarItem();
	    }
	});

	//----------------------------------------------------------------
	btAtualizar.setOnMouseClicked((MouseEvent e) ->
	{
	    tblEmpresa.setItems(atualizaObsLista());

	});

	//usa a tecla ENTER:
	btAtualizar.setOnKeyPressed((KeyEvent e) ->
	{
	    if (e.getCode() == KeyCode.ENTER)
	    {
		tblEmpresa.setItems(atualizaObsLista());
	    }
	});

	//----------------------------------------------------------------
	btAlterar.setOnMouseClicked((MouseEvent e) ->
	{
	    alterarItem();

	});

	//usa a tecla ENTER:
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
	//Verificar se foi selecionado algum item:
	if (empresaSelected != null)
	{
	    lblID.setText(empresaSelected.getId() + "");//0 + "" ja converte em string
	    lblNome.setText(empresaSelected.getNome());
	    lblCpfCnpj.setText(empresaSelected.getCpfCnpj());

	    //Valida se a foto for null:
	    if (empresaSelected.getFoto() != null)
	    {
		//Carrega a foto do banco na tela:
		imgEmpresa.setImage(new Image("file:///" + empresaSelected.getFoto()));
	    }
	    else
	    {
		//String caminhoFoto = "H:/Noot_DELL/Dev_Software/WorkSpaceJAVA/ws-NetBeans/javafxbasico02/src/IMG/sem-foto.jpg";

		String caminhoFoto = "@../IMG/sem-foto.jpg";
		imgEmpresa.setImage(new Image("file:///" + caminhoFoto));
	    }

	}
	else
	{
	    lblID.setText("");
	    lblNome.setText("");
	    lblCpfCnpj.setText("");
	    //imgPessoa.setImage(new Image(""));
	}

    }

    private void alterarItem()
    {
	//Valida se foi selecionado um item:
	if (empresaSelected != null)
	{
	    //Instancio a tela passa a empresa para ser alterada:
	    AlterarEmpresa tela = new AlterarEmpresa(empresaSelected);

	    try
	    {
		tela.start(new Stage());
	    }
	    catch (Exception ex)
	    {
		Logger.getLogger(LogInController.class.getName()).log(Level.SEVERE, null, ex);
	    }

	    //Atualizar a tabela:
	    tblEmpresa.setItems(atualizaObsLista());

	}
	else
	{
	    //Cria uma tela de mensagem:
	    Alert alert = new Alert(Alert.AlertType.WARNING);
	    alert.setTitle("Atenção!");
	    alert.setHeaderText("Selecione um item.");
	    alert.showAndWait();
	}
    }

    private void deletarItem()
    {
	//Verificar se foi selecionado algum item:
	if (empresaSelected != null)
	{
	    Alert alert1 = new Alert(Alert.AlertType.CONFIRMATION);
	    alert1.setHeaderText("Deseja realmente deletar o registro [ " + empresaSelected.getId() + " ] ? ");
	    alert1.showAndWait();
	    
	    //Teste:
	    System.out.println(alert1.getResult().getText());

	    if (alert1.getResult().getText().equals("OK"))
	    {
		EmpresaDao empresaDao = new EmpresaDao();
		empresaDao.delete(empresaSelected);

		//Atualizar a tabela:
		tblEmpresa.setItems(atualizaObsLista());

		//Cria uma tela de mensagem:
		Alert alert = new Alert(Alert.AlertType.INFORMATION);
		alert.setTitle("Sucesso!");
		alert.setHeaderText("Registro deletado com sucesso.");
		alert.setContentText("");
		alert.showAndWait();
	    }

	}
	else
	{
	    //Cria uma tela de mensagem:
	    Alert alert = new Alert(Alert.AlertType.WARNING);
	    alert.setTitle("Atenção!");
	    alert.setHeaderText("Selecione um item.");
	    alert.setContentText("");
	    alert.showAndWait();
	}

    }

    public static void fecharTela()
    {
	//Fechar a tela:
	ListarEmpresa.getStage().close();
    }

    public static void abrirTela()
    {
	//Instancio a tela desejada:
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
