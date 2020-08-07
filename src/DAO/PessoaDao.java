/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import entidades.PessoaDto;
import JDBC.SQLiteConect;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Andre
 */
public class PessoaDao
{

    private final Connection connection;

    public PessoaDao()
    {

	this.connection = new SQLiteConect().getConnection();
    }

    public boolean insert(PessoaDto pessoa)
    {
	try
	{

	    String sql = "INSERT INTO pessoa(nome,email,senha, foto) VALUES(?,?,?,?);";

	    PreparedStatement statement = connection.prepareStatement(sql);
	    statement.setString(1, pessoa.getNome());
	    statement.setString(2, pessoa.getEmail());
	    statement.setString(3, pessoa.getSenha());
	    statement.setString(4, pessoa.getFoto());

	    statement.execute();

	    statement.close();
	    connection.close();

	    return true;
	}
	catch (SQLException ex)
	{
	    Logger.getLogger(PessoaDao.class.getName()).log(Level.SEVERE, null, ex);
	    return false;
	}
    }

    public boolean update(PessoaDto pessoa)
    {
	try
	{

	    String sql = "UPDATE pessoa SET nome = ?, email = ?, senha = ?, foto = ? WHERE id = ?;";

	    PreparedStatement statement = connection.prepareStatement(sql);
	    statement.setString(1, pessoa.getNome());
	    statement.setString(2, pessoa.getEmail());
	    statement.setString(3, pessoa.getSenha());
	    statement.setString(4, pessoa.getFoto());
	    statement.setLong(5, pessoa.getId());

	    statement.execute();

	    statement.close();
	    connection.close();

	    return true;
	}
	catch (SQLException ex)
	{
	    Logger.getLogger(PessoaDao.class.getName()).log(Level.SEVERE, null, ex);
	    return false;
	}

    }

    public void insertOrUpdate(PessoaDto pessoa) throws SQLException
    {
	if (pessoa.getId() > 0)
	{

	    update(pessoa);
	}
	else
	{

	    insert(pessoa);
	}

    }

    public boolean delete(PessoaDto pessoa)
    {
	try
	{

	    String sql = "DELETE FROM pessoa WHERE id = ?;";

	    PreparedStatement statement = connection.prepareStatement(sql);
	    statement.setLong(1, pessoa.getId());

	    statement.execute();

	    statement.close();
	    connection.close();

	    return true;
	}
	catch (SQLException ex)
	{
	    Logger.getLogger(PessoaDao.class.getName()).log(Level.SEVERE, null, ex);
	    return false;
	}

    }

    /**
     * Executa a consulta e passa para o arrayList:
     */
    private ArrayList<PessoaDto> getList(PreparedStatement statement)
    {
	try
	{

	    statement.execute();
	    ResultSet resultSet = statement.getResultSet();

	    ArrayList<PessoaDto> pessoas = new ArrayList<>();

	    while (resultSet.next())
	    {
		int id = resultSet.getInt("id");
		String nome = resultSet.getString("nome");
		String email = resultSet.getString("email");
		String senha = resultSet.getString("senha");
		String foto = resultSet.getString("foto");

		PessoaDto pessoa = new PessoaDto(id, nome, email, senha, foto);
		pessoas.add(pessoa);
	    }

	    statement.close();
	    connection.close();

	    return pessoas;
	}
	catch (SQLException ex)
	{
	    Logger.getLogger(PessoaDao.class.getName()).log(Level.SEVERE, null, ex);
	    return null;
	}
    }

    /**
     *
     * @return
     */
    public ArrayList<PessoaDto> selecAll()
    {
	try
	{
	    String sql = "SELECT * FROM pessoa ORDER BY id";

	    PreparedStatement statement = connection.prepareStatement(sql);

	    return getList(statement);

	}
	catch (SQLException ex)
	{

	    ex.printStackTrace();

	    Logger.getLogger(PessoaDao.class.getName()).log(Level.SEVERE, null, ex);
	    return null;
	}
    }

    public PessoaDto selectPorID(PessoaDto pessoa)
    {
	try
	{
	    String sql = "SELECT * FROM pessoa WHERE ID = ?";

	    PreparedStatement statement = connection.prepareStatement(sql);
	    statement.setLong(1, pessoa.getId());

	    return getList(statement).get(0);

	}
	catch (SQLException ex)
	{

	    ex.printStackTrace();

	    Logger.getLogger(PessoaDao.class.getName()).log(Level.SEVERE, null, ex);
	    return null;
	}

    }

    /**
     * Teste: Verifica se o usuario informado na tela de logIn existe no banco
     *
     * @param pessoa
     */
    public boolean validaLogin(PessoaDto pessoa)
    {
	try
	{
	    String sql = "SELECT * FROM pessoa WHERE email = ? AND senha = ?";

	    PreparedStatement statement = connection.prepareStatement(sql);
	    statement.setString(1, pessoa.getEmail());
	    statement.setString(2, pessoa.getSenha());

	    statement.execute();

	    ResultSet resultSet = statement.getResultSet();

	    return resultSet.next();

	}
	catch (SQLException ex)
	{

	    ex.printStackTrace();

	    Logger.getLogger(PessoaDao.class.getName()).log(Level.SEVERE, null, ex);
	    return false;
	}

    }

}
