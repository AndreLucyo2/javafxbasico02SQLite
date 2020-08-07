/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CONTROLLER;

import DAO.PessoaDao;
import DTO.PessoaDto;
import UTILIDADES.Aplicacao;
import VIEWMain.AlterarPessoa;
import VIEWMain.ListarPessoa;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.net.URL;
import java.util.List;
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
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
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
public class ListaPessoaController implements Initializable
{

    @FXML
    private TableView<PessoaDto> tblPessoa;

    @FXML
    private TableColumn<PessoaDto, Long> colId;

    @FXML
    private TableColumn<PessoaDto, String> colNome;

    @FXML
    private TableColumn<PessoaDto, String> colEmail;

    @FXML
    private TableColumn<PessoaDto, String> colFoto;

    @FXML
    private Button btDeletar;

    @FXML
    private Button btAtualizar;

    @FXML
    private Button btCancelar;

    @FXML
    private ImageView imgPessoa;

    @FXML
    private Button btAlterar;

    @FXML
    private Label lblID;

    @FXML
    private Label lblNome;

    @FXML
    private Label lblEmail;
    @FXML
    private Button btGerarPDF;
    @FXML
    private Button btPesquisar;
    @FXML
    private TextField txPesquisa;

    private PessoaDto pessoaSelected;

    private ObservableList<PessoaDto> pessoasPesquisa = FXCollections.observableArrayList();

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

	tblPessoa.getSelectionModel().selectedItemProperty().addListener(new ChangeListener()
	{
	    @Override
	    public void changed(ObservableValue observable, Object oldValue, Object newValue)
	    {

		pessoaSelected = (PessoaDto) newValue;

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
	    tblPessoa.setItems(atualizaObsLista());

	    imgPessoa.setImage(null);

	    Aplicacao app = new Aplicacao();
	    imgPessoa.setImage(app.setImageSemFoto());

	});

	btAtualizar.setOnKeyPressed((KeyEvent e) ->
	{
	    if (e.getCode() == KeyCode.ENTER)
	    {
		tblPessoa.setItems(atualizaObsLista());

		imgPessoa.setImage(null);

		Aplicacao app = new Aplicacao();
		imgPessoa.setImage(app.setImageSemFoto());
	    }
	});

	btAlterar.setOnMouseClicked((MouseEvent e) ->
	{

	    if (pessoaSelected != null)
	    {

		AlterarPessoa tela = new AlterarPessoa(pessoaSelected);

		try
		{
		    tela.start(new Stage());
		}
		catch (Exception ex)
		{
		    Logger.getLogger(LogInController.class.getName()).log(Level.SEVERE, null, ex);
		}

		tblPessoa.setItems(atualizaObsLista());
		tblPessoa.refresh();

	    }
	    else
	    {

		Alert alert = new Alert(Alert.AlertType.WARNING);
		alert.setTitle("Atenção!");
		alert.setHeaderText("Selecione um item.");
		alert.showAndWait();
	    }

	});

	btAlterar.setOnKeyPressed((KeyEvent e) ->
	{
	    if (e.getCode() == KeyCode.ENTER)
	    {

		AlterarPessoa tela = new AlterarPessoa(pessoaSelected);

		try
		{
		    tela.start(new Stage());
		}
		catch (Exception ex)
		{
		    Logger.getLogger(LogInController.class.getName()).log(Level.SEVERE, null, ex);
		}

		tblPessoa.setItems(atualizaObsLista());
		tblPessoa.refresh();

	    }
	    else
	    {

		Alert alert = new Alert(Alert.AlertType.WARNING);
		alert.setTitle("Atenção!");
		alert.setHeaderText("Selecione um item.");
		alert.showAndWait();
	    }
	});

	btGerarPDF.setOnMouseClicked((MouseEvent e) ->
	{
	    gerarPDF();
	});

	btGerarPDF.setOnKeyPressed((KeyEvent e) ->
	{
	    if (e.getCode() == KeyCode.ENTER)
	    {
		gerarPDF();
	    }
	});

	txPesquisa.setOnKeyReleased((KeyEvent e) ->
	{
	    tblPessoa.setItems(consultaObsLista());
	});

	btPesquisar.setOnMouseClicked((MouseEvent e) ->
	{
	    tblPessoa.setItems(consultaObsLista());
	});

	btPesquisar.setOnKeyPressed((KeyEvent e) ->
	{
	    if (e.getCode() == KeyCode.ENTER)
	    {
		tblPessoa.setItems(consultaObsLista());
	    }
	});
    }

    private void inicializaTabela()
    {
	colId.setCellValueFactory(new PropertyValueFactory("id"));
	colNome.setCellValueFactory(new PropertyValueFactory("nome"));
	colEmail.setCellValueFactory(new PropertyValueFactory("email"));
	colFoto.setCellValueFactory(new PropertyValueFactory("foto"));

	tblPessoa.setItems(atualizaObsLista());
    }

    /**
     * Faz o select no banco e cria um observableArrayList Retorna uma lista
     * completa
     *
     * @return
     */
    private ObservableList<PessoaDto> atualizaObsLista()
    {
	PessoaDao pessoaDao = new PessoaDao();

	pessoasPesquisa = FXCollections.observableArrayList(pessoaDao.selecAll());

	return pessoasPesquisa;
    }

    /**
     *
     * @return
     */
    private ObservableList<PessoaDto> consultaObsLista()
    {

	ObservableList<PessoaDto> pessoasPesquisaTemp = FXCollections.observableArrayList();

	for (PessoaDto pessoaDto : pessoasPesquisa)
	{

	    if (pessoaDto.getNome().toLowerCase().contains(txPesquisa.getText().toLowerCase()))
	    {
		pessoasPesquisaTemp.add(pessoaDto);
	    }
	}

	return pessoasPesquisaTemp;

    }

    /**
     * Carrega os dados do item senecionado:
     */
    private void mostrarDetalhes()
    {

	if (pessoaSelected != null)
	{
	    lblID.setText(pessoaSelected.getId() + "");
	    lblNome.setText(pessoaSelected.getNome());
	    lblEmail.setText(pessoaSelected.getEmail());

	    if (pessoaSelected.getFoto() != null && pessoaSelected.getFoto() != "")
	    {

		Aplicacao app = new Aplicacao();
		imgPessoa.setImage(app.setImageSemFoto());

	    }
	    else
	    {

		Aplicacao app = new Aplicacao();
		imgPessoa.setImage(app.setImageSemFoto());
	    }

	}
	else
	{
	    lblID.setText("");
	    lblNome.setText("");
	    lblEmail.setText("");

	}

    }

    private void deletarItem()
    {

	if (pessoaSelected != null)
	{
	    PessoaDao pessoa = new PessoaDao();
	    pessoa.delete(pessoaSelected);

	    tblPessoa.setItems(atualizaObsLista());

	    Alert alert = new Alert(Alert.AlertType.INFORMATION);
	    alert.setTitle("Sucesso!");
	    alert.setHeaderText("Registro deletado com sucesso.");
	    alert.setContentText("");
	    alert.showAndWait();
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

	ListarPessoa.getStage().close();
    }

    public static void abrirTela()
    {

	ListarPessoa tela = new ListarPessoa();

	try
	{
	    tela.start(new Stage());
	}
	catch (Exception ex)
	{
	    Logger.getLogger(LogInController.class.getName()).log(Level.SEVERE, null, ex);
	}
    }

    public void gerarPDF()
    {

	String pasta = selecionarPastaPDF();

	Document documento = new Document();

	try
	{

	    PdfWriter.getInstance(documento, new FileOutputStream(pasta));

	    documento.open();

	    List<PessoaDto> pessoas = new PessoaDao().selecAll();

	    for (int i = 0; i < pessoas.size(); i++)
	    {
		documento.add(new Paragraph("-----------------------------------------------------------------------"));
		documento.add(new Paragraph("ID    : " + pessoas.get(i).getId()));
		documento.add(new Paragraph("Nome  : " + pessoas.get(i).getNome()));
		documento.add(new Paragraph("Email : " + pessoas.get(i).getEmail()));
		documento.add(new Paragraph("Foto  : " + pessoas.get(i).getFoto()));
		documento.add(new Paragraph(""));
	    }

	    documento.close();

	    Alert alert = new Alert(Alert.AlertType.INFORMATION);
	    alert.setHeaderText("PDF Gerado com Sucesso. na PAsta:\n" + pasta);
	    alert.setContentText("");
	    alert.showAndWait();

	}
	catch (DocumentException ex)//Exceções de documento
	{
	    Logger.getLogger(ListaPessoaController.class.getName()).log(Level.SEVERE, null, ex);
	}
	catch (FileNotFoundException ex)
	{
	    Logger.getLogger(ListaPessoaController.class.getName()).log(Level.SEVERE, null, ex);
	}

    }

    private String selecionarPastaPDF()
    {

	FileChooser arquivo = new FileChooser();
	arquivo.getExtensionFilters().add(new FileChooser.ExtensionFilter("PDF", "*.pdf"));
	File file = arquivo.showSaveDialog(new Stage());

	if (file != null)
	{

	    return file.getAbsolutePath();

	}
	else
	{
	    Alert alert = new Alert(Alert.AlertType.WARNING);
	    alert.setHeaderText("Pdf Será Salvo na pasta padrao!");
	    alert.showAndWait();

	    return "C:/Test/PDF/x.pdf";
	}

    }

}
