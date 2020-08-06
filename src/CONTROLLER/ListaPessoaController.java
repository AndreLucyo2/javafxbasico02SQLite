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

    //Guardar item selecionado:
    private PessoaDto pessoaSelected;

    //Guarda o resultado da pesquisa:
    private ObservableList<PessoaDto> pessoasPesquisa = FXCollections.observableArrayList();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
	//Inicaliza e parametriza a tabela:
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
	//Executa ao selecionar um item na tabela:
	tblPessoa.getSelectionModel().selectedItemProperty().addListener(new ChangeListener()
	{
	    @Override
	    public void changed(ObservableValue observable, Object oldValue, Object newValue)
	    {
		//Pegar a pessoa selecionadoa: 
		//Melhor fazer uma metodo que busque no banco pelo ID, e depois carregue a pessoaSelected
		pessoaSelected = (PessoaDto) newValue;

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
	    tblPessoa.setItems(atualizaObsLista());

	    imgPessoa.setImage(null);
	    
	    //Seta imagem SemFoto:
	    Aplicacao app = new Aplicacao();
	    imgPessoa.setImage(app.setImageSemFoto());

	});

	//usa a tecla ENTER:
	btAtualizar.setOnKeyPressed((KeyEvent e) ->
	{
	    if (e.getCode() == KeyCode.ENTER)
	    {
		tblPessoa.setItems(atualizaObsLista());

		imgPessoa.setImage(null);
		
		//Seta imagem SemFoto:
		Aplicacao app = new Aplicacao();
		imgPessoa.setImage(app.setImageSemFoto());
	    }
	});

	//----------------------------------------------------------------
	btAlterar.setOnMouseClicked((MouseEvent e) ->
	{
	    //https://youtu.be/rvhM6eJY6VU?list=PLA1W9ojL1mVwql7mYQrMqHJ7GEb9xEg8q&t=477

	    //Valida se foi selecionado um item:
	    if (pessoaSelected != null)
	    {
		//Instancio a tela passa a pessoa para ser alterada:
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
		//Cria uma tela de mensagem:
		Alert alert = new Alert(Alert.AlertType.WARNING);
		alert.setTitle("Atenção!");
		alert.setHeaderText("Selecione um item.");
		alert.showAndWait();
	    }

	});

	//usa a tecla ENTER:
	btAlterar.setOnKeyPressed((KeyEvent e) ->
	{
	    if (e.getCode() == KeyCode.ENTER)
	    {

		//Instancio a tela passa a pessoa para ser alterada:
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
		//Cria uma tela de mensagem:
		Alert alert = new Alert(Alert.AlertType.WARNING);
		alert.setTitle("Atenção!");
		alert.setHeaderText("Selecione um item.");
		alert.showAndWait();
	    }
	});

	//----------------------------------------------------------------
	btGerarPDF.setOnMouseClicked((MouseEvent e) ->
	{
	    gerarPDF();
	});

	//usa a tecla ENTER:
	btGerarPDF.setOnKeyPressed((KeyEvent e) ->
	{
	    if (e.getCode() == KeyCode.ENTER)
	    {
		gerarPDF();
	    }
	});

	//----------------------------------------------------------------
	//ja faz a atualiza a tabela ao digitar:
	txPesquisa.setOnKeyReleased((KeyEvent e) ->
	{
	    tblPessoa.setItems(consultaObsLista());
	});

	btPesquisar.setOnMouseClicked((MouseEvent e) ->
	{
	    tblPessoa.setItems(consultaObsLista());
	});

	//usa a tecla ENTER:
	btPesquisar.setOnKeyPressed((KeyEvent e) ->
	{
	    if (e.getCode() == KeyCode.ENTER)
	    {
		tblPessoa.setItems(consultaObsLista());
	    }
	});
    }

    //Inicialisa e formata a tabela:
    private void inicializaTabela()
    {
	colId.setCellValueFactory(new PropertyValueFactory("id"));
	colNome.setCellValueFactory(new PropertyValueFactory("nome"));
	colEmail.setCellValueFactory(new PropertyValueFactory("email"));
	colFoto.setCellValueFactory(new PropertyValueFactory("foto"));

	//Carregar uma consulta na tabela:
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

	//Teste de pesquisa:Preenche uma listagem
	pessoasPesquisa = FXCollections.observableArrayList(pessoaDao.selecAll());

	//Retorna a litsa resultado da consulta:
	return pessoasPesquisa;
    }

    /**
     * Ajusta a tela com a list da pesquisa: Trabalha os dados em memoria
     * ref.:https://youtu.be/AziM0vwbQIo?list=PLA1W9ojL1mVwql7mYQrMqHJ7GEb9xEg8q&t=650
     *
     * @return
     */
    private ObservableList<PessoaDto> consultaObsLista()
    {
	//Lista vazia para receber dados filtrados:
	ObservableList<PessoaDto> pessoasPesquisaTemp = FXCollections.observableArrayList();

	//1° Forma : Teste Lambda:
        //ObservableList<PessoaDto> result = (ObservableList<PessoaDto>) pessoasPesquisaTemp.stream().filter(x -> x.getNome().toLowerCase().contains(txPesquisa.getText().toLowerCase()));
	//return result;
	
	//2° Forma : Teste ForEach:
	//Iterar na lista de pessoas aplicando o filtro "Que Contem..."
	for (PessoaDto pessoaDto : pessoasPesquisa)
	{
	    //Pega o texto do campo, poe tudp em munuscula retorna o que contem o textto da pqesquisa
	    if (pessoaDto.getNome().toLowerCase().contains(txPesquisa.getText().toLowerCase()))
	    {
		pessoasPesquisaTemp.add(pessoaDto);
	    }
	}
	
	//Retorna a litsa resultado do filro:
	return pessoasPesquisaTemp;
	
	
    }

    /**
     * Carrega os dados do item senecionado:
     */
    private void mostrarDetalhes()
    {
	//Verificar se foi selecionado algum item:
	if (pessoaSelected != null)
	{
	    lblID.setText(pessoaSelected.getId() + "");//0 + "" ja converte em string
	    lblNome.setText(pessoaSelected.getNome());
	    lblEmail.setText(pessoaSelected.getEmail());

	    //Valida se a foto for null:
	    if (pessoaSelected.getFoto() != null && pessoaSelected.getFoto() != "")
	    {
		//Carrega a foto do banco na tela:
		//imgPessoa.setImage(new Image("file:///" + pessoaSelected.getFoto()));

		//teste:
		//Seta imagem SemFoto:
		Aplicacao app = new Aplicacao();
		imgPessoa.setImage(app.setImageSemFoto());

	    }
	    else
	    {
		//Seta imagem SemFoto:
		Aplicacao app = new Aplicacao();
		imgPessoa.setImage(app.setImageSemFoto());
	    }

	}
	else
	{
	    lblID.setText("");
	    lblNome.setText("");
	    lblEmail.setText("");
	    //imgPessoa.setImage(new Image(""));
	}

    }

    private void deletarItem()
    {
	//Verificar se foi selecionado algum item:
	if (pessoaSelected != null)
	{
	    PessoaDao pessoa = new PessoaDao();
	    pessoa.delete(pessoaSelected);

	    //Atualizar a tabela:
	    tblPessoa.setItems(atualizaObsLista());

	    //Cria uma tela de mensagem:
	    Alert alert = new Alert(Alert.AlertType.INFORMATION);
	    alert.setTitle("Sucesso!");
	    alert.setHeaderText("Registro deletado com sucesso.");
	    alert.setContentText("");
	    alert.showAndWait();
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
	ListarPessoa.getStage().close();
    }

    public static void abrirTela()
    {
	//Instancio a tela desejada:
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

    //=============================================================================================
    //MANIPULAÇÃO DE DOCUMENTO E EXPORTAÇÃO DE PDF
    //Link para baixa a Biblioteca: 
    // https://mvnrepository.com/artifact/com.itextpdf/itextpdf/5.5.13.1
    //=============================================================================================
    public void gerarPDF()
    {
	//definir onde deve ser salvo:
	String pasta = selecionarPastaPDF();

	//Cria um documento:
	Document documento = new Document();

	try
	{
	    //Excritor do PDF:
	    PdfWriter.getInstance(documento, new FileOutputStream(pasta));//PODE DAR ERRO DE ACESSO, pode ser utilizado o usuario publico C:/Users/Public/TsteGerais

	    //-----------------------------------------------------------------------------------
	    //ESCREVER CONTEUDO:
	    //-----------------------------------------------------------------------------------
	    //Abre o cocumento:
	    documento.open();

	    //Pegar uma lista de objetos:
	    List<PessoaDto> pessoas = new PessoaDao().selecAll();
	    //Adiciona informações no odcumento:
	    for (int i = 0; i < pessoas.size(); i++)
	    {
		documento.add(new Paragraph("-----------------------------------------------------------------------"));
		documento.add(new Paragraph("ID    : " + pessoas.get(i).getId()));
		documento.add(new Paragraph("Nome  : " + pessoas.get(i).getNome()));
		documento.add(new Paragraph("Email : " + pessoas.get(i).getEmail()));
		documento.add(new Paragraph("Foto  : " + pessoas.get(i).getFoto()));
		documento.add(new Paragraph(""));
	    }

	    //Fecha a libera o documento:
	    documento.close();
	    //-----------------------------------------------------------------------------------

	    //Cria uma tela de mensagem:
	    Alert alert = new Alert(Alert.AlertType.INFORMATION);
	    alert.setHeaderText("PDF Gerado com Sucesso. na PAsta:\n" + pasta);
	    alert.setContentText("");
	    alert.showAndWait();

	}
	catch (DocumentException ex)//Exceções de documento
	{
	    Logger.getLogger(ListaPessoaController.class.getName()).log(Level.SEVERE, null, ex);
	}
	catch (FileNotFoundException ex)//exeções de arquivo
	{
	    Logger.getLogger(ListaPessoaController.class.getName()).log(Level.SEVERE, null, ex);
	}

    }

    //Retorna o caminho de onde quer savar:
    private String selecionarPastaPDF()
    {
	//Criar janela para selecionar arquivo:
	FileChooser arquivo = new FileChooser();
	arquivo.getExtensionFilters().add(new FileChooser.ExtensionFilter("PDF", "*.pdf"));//Filtro de seleão
	File file = arquivo.showSaveDialog(new Stage());

	//Valida se foi selecionado algum arquivo:
	if (file != null)
	{

	    //Pega o caminho completo com o nome arquivo ja definido:
	    return file.getAbsolutePath();

	}
	else
	{
	    Alert alert = new Alert(Alert.AlertType.WARNING);
	    alert.setHeaderText("Pdf Será Salvo na pasta padrao!");
	    alert.showAndWait();

	    //Definições pasta default:    
	    return "C:/Test/PDF/x.pdf";
	}

    }

}
