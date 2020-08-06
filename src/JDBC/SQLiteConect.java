/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package JDBC;

import UTILIDADES.Aplicacao;
import java.sql.Connection;
import java.sql.DriverManager;

/* 
    Driver :SQLite https://bitbucket.org/xerial/sqlite-jdbc/downloads/
    ou : SQLite  https://mvnrepository.com/artifact/org.xerial/sqlite-jdbc    
    Pagina oficial: https://www.sqlite.org/index.html
    ref.: https://www.sqlitetutorial.net/sqlite-java/sqlite-jdbc-driver/

-------------------------------------------------------
    Sintaxe SQLite: https://www.sqlitetutorial.net/sqlite-create-table/
    Gerenciador do BD : https://sqlitebrowser.org/
    Ref. : https://www.devmedia.com.br/sqlite-o-pequeno-notavel/7249
    ref.: https://receitasdecodigo.com.br/java/usando-sqlite-em-java

    //ref: https://www.it-swarm.dev/pt/c%23/senha-proteger-um-banco-de-dados-sqlite.-e-possivel/967014684/
 */
/**
 *
 * @author Andre
 */
public class SQLiteConect
{

    //----------------------------------------------------------------------   
    //Dados da String SQLite Conection:
    //----------------------------------------------------------------------   
    private static String nomeBanco = "BD01.db";
    private static String nomePasta = "CFG/BD";

    //----------------------------------------------------------------------   
    //Pegar o ptch do banco
    //----------------------------------------------------------------------   
    private static String arquivoBD;
    public static String getArquivoBD()
    {
	arquivoBD = definirArquivo();
	return arquivoBD;
    }

    /**
     * Retorna uma conexão com o banco SQLite
     *
     * @return uma Connection
     */
    public Connection getConnection()
    {
	try
	{
	    //----------------------------------------------------------------------           
	    //Definir o ptch do banco
	    //----------------------------------------------------------------------
	    String url = "jdbc:sqlite:" + definirArquivo();

	    //----------------------------------------------------------------------
	    //Configurar os dados da conexao e cria o banco caso nao existir
	    //----------------------------------------------------------------------
	    Connection conexao = DriverManager.getConnection(url);

	    //----------------------------------------------------------------------
	    //Retorna um conexao:
	    //----------------------------------------------------------------------
	    return conexao;
	}
	catch (Exception ex)
	{
	    System.out.println("Erro! conexão nao foi criada!\n" + ex.getMessage());
	    throw new RuntimeException(ex);
	}
    }

    /**
     * Monta patch do banco de dados: [ sqlite_database_file_path ]
     * @return
     */
    private static String definirArquivo()
    {
	try
	{
	    //----------------------------------------------------------------------           
	    //Pego a pasta Raiz da aplicação .jar:
	    //----------------------------------------------------------------------
	    Aplicacao.criaPastaNaRaiz(nomePasta);
	    
	    //----------------------------------------------------------------------           
	    //Monta patch do banco de dados: [ sqlite_database_file_path ]
	    //----------------------------------------------------------------------
	    String pastaApp = new Aplicacao().getPastaAplicacao();
	    String arquivo = pastaApp+"/"+nomePasta+ "/" + nomeBanco;

	    //Msg Prompt de teste:
	    System.out.println(">>>>>>> ARQUIVO BD definida: " + arquivo + "\n");

	    
	    return arquivo;

	}
	catch (Exception ex)
	{
	    System.out.println("Erro! Criar Pasta BD SQLite!" + ex.getMessage() + "\n");
	    throw new RuntimeException(ex);
	}

    }

}
