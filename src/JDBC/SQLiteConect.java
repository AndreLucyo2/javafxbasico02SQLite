/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package JDBC;

import UTILIDADES.Aplicacao;
import java.sql.Connection;
import java.sql.DriverManager;

/**
 *
 * @author Andre
 */
public class SQLiteConect
{

    private static String nomeBanco = "BD01.db";
    private static String nomePasta = "CFG/BD";

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

	    String url = "jdbc:sqlite:" + definirArquivo();

	    Connection conexao = DriverManager.getConnection(url);

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
     *
     * @return
     */
    private static String definirArquivo()
    {
	try
	{

	    Aplicacao.criaPastaNaRaiz(nomePasta);

	    String pastaApp = new Aplicacao().getPastaAplicacao();
	    String arquivo = pastaApp + "/" + nomePasta + "/" + nomeBanco;

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
