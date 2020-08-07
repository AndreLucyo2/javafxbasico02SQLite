/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UTILIDADES;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Andre
 */
public class SqlUtil
{

    /**
     * Executa query no banco
     *
     * @param sql
     * @param conexao
     * @return
     */
    public static boolean executaSQL(String sql, Connection conexao)
    {
	try
	{
	    PreparedStatement statement = conexao.prepareStatement(sql);

	    statement.execute();

	    statement.close();
	    conexao.close();

	    return true;

	}
	catch (SQLException ex)
	{
	    Logger.getLogger(SqlUtil.class.getName()).log(Level.SEVERE, null, ex);
	    return false;
	}
    }
}
