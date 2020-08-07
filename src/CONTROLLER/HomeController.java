/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CONTROLLER;

import DAO.EmpresaDao;
import DAO.PessoaDao;
import DTO.EmpresaDto;
import DTO.PessoaDto;
import VIEWMain.Home;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Andre
 */
public class HomeController implements Initializable
{

    
    
    @FXML
    private Button btCadastrarPessoa;

    @FXML
    private Button btCadastrarEmpresa;

    @FXML
    private Button btListarPessoa;

    @FXML
    private Button btListarEmpresa;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        
        btCadastrarPessoa.setOnMouseClicked((MouseEvent e) ->
        {
            fecharTela();
            CadPessoaController.abrirTela();
        });

        
        btCadastrarPessoa.setOnKeyPressed((KeyEvent e) ->
        {
            if (e.getCode() == KeyCode.ENTER)
            {
                fecharTela();
                CadPessoaController.abrirTela();
            }
        });

        
        btCadastrarEmpresa.setOnMouseClicked((MouseEvent e) ->
        {
            fecharTela();
            CadEmpresaController.abrirTela();
        });

        
        btCadastrarEmpresa.setOnKeyPressed((KeyEvent e) ->
        {
            if (e.getCode() == KeyCode.ENTER)
            {
                fecharTela();
                CadEmpresaController.abrirTela();
            }
        });

        
        btListarPessoa.setOnMouseClicked((MouseEvent e) ->
        {
            fecharTela();
            ListaPessoaController.abrirTela();
        });

        
        btListarPessoa.setOnKeyPressed((KeyEvent e) ->
        {
            if (e.getCode() == KeyCode.ENTER)
            {
                fecharTela();
                ListaPessoaController.abrirTela();
            }
        });

        
        btListarEmpresa.setOnMouseClicked((MouseEvent e) ->
        {
            fecharTela();
            ListaEmpresaController.abrirTela();
        });

        
        btListarEmpresa.setOnKeyPressed((KeyEvent e) ->
        {
            if (e.getCode() == KeyCode.ENTER)
            {
                fecharTela();
                ListaEmpresaController.abrirTela();
            }
        });

    }

    private static void listarPessoa()
    {
        System.out.println("Listando pessoas: ");

        PessoaDao pessoaDao = new PessoaDao();
        List<PessoaDto> pessoas = pessoaDao.selecAll();

        if (pessoas != null)
        {
            for (int x = 0; x < pessoas.size(); x++)
            {
                
                System.out.println(pessoas.get(x).mostraPessoa());
                System.out.println("--------------------------------------");
            }
        }
        else
        {
            System.out.println("Não Selecionado");
        }

    }

    private static void listarEmpresa()
    {
        System.out.println("Listando pessoas: ");

        EmpresaDao empresaDao = new EmpresaDao();
        List<EmpresaDto> empresas = empresaDao.selecAll();

        if (empresas != null)
        {
            for (int x = 0; x < empresas.size(); x++)
            {
                
                System.out.println(empresas.get(x).mostraEmpresa());
                System.out.println("--------------------------------------");
            }
        }
        else
        {
            System.out.println("Não Selecionado");
        }

    }

    public static void fecharTela()
    {
        
        Home.getStage().close();
    }

    public static void abrirTela()
    {
        
        Home tela = new Home();

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
