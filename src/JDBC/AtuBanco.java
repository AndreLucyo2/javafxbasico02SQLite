/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package JDBC;

import DAO.EmpresaDao;
import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author Andre
 */
public class AtuBanco
{

    private final Connection connection;

    public AtuBanco()
    {

	this.connection = new SQLiteConect().getConnection();

    }

    public static boolean criarTabelas()
    {
	String bd = SQLiteConect.getArquivoBD();
	File diretorio = new File(bd).getParentFile();

	if (diretorio.exists())
	{

	    createTables();
	    return true;
	}
	else
	{
	    JOptionPane.showMessageDialog(null, "Erro ao Criar as Tabelas!", "Aviso", JOptionPane.ERROR_MESSAGE);
	    return false;
	}

    }

    private static void createTables()
    {

	criarTablePEssoa();
	criarTableEmpresa();
    }

    public boolean executaSQL(String sql)
    {
	try
	{
	    PreparedStatement statement = connection.prepareStatement(sql);

	    statement.execute();

	    statement.close();
	    connection.close();

	    return true;

	}
	catch (SQLException ex)
	{
	    Logger.getLogger(EmpresaDao.class.getName()).log(Level.SEVERE, null, ex);
	    return false;
	}
    }

    public static void criarTablePEssoa()
    {
	String sql = "CREATE TABLE IF NOT EXISTS pessoa( "
		+ " id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,"
		+ " nome VARCHAR(200) NOT NULL,"
		+ " email VARCHAR(200) NOT NULL,"
		+ " senha VARCHAR(20) NOT NULL,"
		+ " foto VARCHAR(200) "
		+ ")";

	if (new AtuBanco().executaSQL(sql))
	{
	    System.out.println(">>>>>>> Tabela pessoa Criado com sucesso");
	}

    }

    public static void criarTableEmpresa()
    {
	String sql = "CREATE TABLE IF NOT EXISTS empresa( "
		+ " id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,"
		+ " nome VARCHAR(200) NOT NULL,"
		+ " cpfCnpj VARCHAR(200) NOT NULL,"
		+ " foto VARCHAR(200) "
		+ ")";

	if (new AtuBanco().executaSQL(sql))
	{
	    System.out.println(">>>>>>> Tabela empresa Criado com sucesso");
	}

    }

}
