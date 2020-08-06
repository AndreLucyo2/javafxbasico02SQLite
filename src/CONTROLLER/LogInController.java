/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CONTROLLER;

import DAO.PessoaDao;
import DTO.PessoaDto;
import JDBC.AtuBanco;
import VIEWMain.Login;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javax.swing.JOptionPane;

/**
 * FXML Controller class
 *
 * @author Andre
 */
public class LogInController implements Initializable
{
    public LogInController()
    {
	//Execuar o Atubanco ao logar:
	if (AtuBanco.criarTabelas())
	{
	    //Mensagem de confirmação:
	    JOptionPane.showMessageDialog(null, "Banco Criado com Suceso!", "Aviso", JOptionPane.INFORMATION_MESSAGE);
	}
	
    }

    @FXML
    private TextField txEmail;

    @FXML
    private PasswordField txSenha;

    @FXML
    private Button btEntrar;

    @FXML
    private Button btSair;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
	//================================================================================================
	btEntrar.setOnMouseClicked((MouseEvent e) ->
	{
	    logar();
	});

	//usa a tecla ENTER:
	btEntrar.setOnKeyPressed((KeyEvent e) ->
	{
	    if (e.getCode() == KeyCode.ENTER)
	    {
		logar();
	    }

	});

	//usa a tecla ENTER depois de digitar a senha:
	txSenha.setOnKeyPressed((KeyEvent e) ->
	{
	    if (e.getCode() == KeyCode.ENTER)
	    {
		logar();
	    }

	});

	//================================================================================================
	//Sair com clique do mouse
	btSair.setOnMouseClicked((MouseEvent e) ->
	{
	    System.out.println("Saí com clique");
	    fecharTela();
	});

	//usa a tecla ENTER ou ESC:
	btSair.setOnKeyPressed((KeyEvent e) ->
	{
	    if (e.getCode() == KeyCode.ENTER || e.getCode() == KeyCode.ESCAPE)
	    {
		System.out.println("Saí com tecla enter");
		fecharTela();
	    }

	});

    }

    /**
     * Valida caso for acesso do admin
     *
     * @return
     */
    public boolean isAdminAcess()
    {
	String admin = "admin";
	String snhAdm = "123";
	boolean acessoAdm = false;
	if (txEmail.getText().equals(admin) && txSenha.getText().equals(snhAdm))
	{
	    acessoAdm = true;
	}
	else
	{
	    acessoAdm = false;
	}
	return acessoAdm;
    }

    public void logar()
    {
	//Pega os dados da tela e passa para o objeto:
	PessoaDto pessoa = new PessoaDto(txEmail.getText(), txSenha.getText());

	//Valida no banco:
	if (!pessoa.getEmail().equals("") && !pessoa.getSenha().equals("") && new PessoaDao().validaLogin(pessoa) || isAdminAcess())
	{
	    //Fechar tela do login:
	    fecharTela();

	    //Abre tela home:
	    HomeController.abrirTela();
	}
	else
	{
	    //Cria uma tela de mensagem:
	    Alert alert = new Alert(AlertType.WARNING);
	    alert.setTitle("Usuário inválido!");
	    alert.setHeaderText("Erro Ao logar \nEmail ou senha incorreto.");
	    alert.setContentText("");
	    alert.show();
	}
    }

    public void fecharTela()
    {
	//Fechar a tela:
	Login.getStage().close();
    }

    public static void abrirTela()
    {
	//Instancio a tela desejada:
	Login tela = new Login();

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
