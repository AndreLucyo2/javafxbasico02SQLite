/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import DTO.EmpresaDto;
import JDBC.SQLiteConect;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Andre
 */
public class EmpresaDao
{

    private final Connection connection;

    public EmpresaDao()
    {

	this.connection = new SQLiteConect().getConnection();

    }

    public boolean insert(EmpresaDto empresa)
    {

	try
	{

	    String sql = "INSERT INTO empresa(nome,cpfcnpj,foto) VALUES(?,?,?);";

	    PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
	    statement.setString(1, empresa.getNome());
	    statement.setString(2, empresa.getCpfCnpj());
	    statement.setString(3, empresa.getFoto());

	    statement.execute();

	    ResultSet resultSet = statement.getGeneratedKeys();

	    if (resultSet.next())
	    {
		int id = resultSet.getInt("id");
		empresa.setId(id);
	    }

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

    public boolean update(EmpresaDto empresa)
    {
	try
	{

	    String sql = "UPDATE empresa SET nome = ?, cpfcnpj = ?, foto = ? WHERE id = ?;";

	    PreparedStatement statement = connection.prepareStatement(sql);
	    statement.setString(1, empresa.getNome());
	    statement.setString(2, empresa.getCpfCnpj());
	    statement.setString(3, empresa.getFoto());
	    statement.setLong(4, empresa.getId());

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

    public void insertOrUpdate(EmpresaDto empresa) throws SQLException
    {
	if (empresa.getId() > 0)
	{

	    update(empresa);
	}
	else
	{

	    insert(empresa);
	}

    }

    public boolean delete(EmpresaDto empresa)
    {
	try
	{

	    String sql = "DELETE FROM empresa WHERE id = ?;";

	    PreparedStatement statement = connection.prepareStatement(sql);
	    statement.setLong(1, empresa.getId());

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

    /**
     * Executa a consulta e passa para o arrayList:
     */
    private ArrayList<EmpresaDto> getList(PreparedStatement statement)
    {

	try
	{

	    statement.execute();
	    ResultSet resultSet = statement.getResultSet();

	    ArrayList<EmpresaDto> empresas = new ArrayList<>();

	    while (resultSet.next())
	    {
		int id = resultSet.getInt("id");
		String nome = resultSet.getString("nome");
		String cpfcnpj = resultSet.getString("cpfcnpj");
		String foto = resultSet.getString("foto");

		EmpresaDto empresa = new EmpresaDto(id, nome, cpfcnpj, foto);
		empresas.add(empresa);
	    }

	    statement.close();
	    connection.close();

	    return empresas;
	}
	catch (SQLException ex)
	{
	    Logger.getLogger(EmpresaDao.class.getName()).log(Level.SEVERE, null, ex);
	    return null;
	}
    }

    /**
     *
     * @return
     */
    public ArrayList<EmpresaDto> selecAll()
    {
	try
	{
	    String sql = "SELECT * FROM empresa ORDER BY id";

	    PreparedStatement statement = connection.prepareStatement(sql);

	    return getList(statement);

	}
	catch (SQLException ex)
	{

	    ex.printStackTrace();

	    Logger.getLogger(EmpresaDao.class.getName()).log(Level.SEVERE, null, ex);
	    return null;
	}
    }

    public EmpresaDto selectPorID(EmpresaDto empresa)
    {
	try
	{
	    String sql = "SELECT * FROM empresa WHERE ID = ?";

	    PreparedStatement statement = connection.prepareStatement(sql);
	    statement.setLong(1, empresa.getId());

	    return getList(statement).get(0);

	}
	catch (SQLException ex)
	{

	    ex.printStackTrace();

	    Logger.getLogger(EmpresaDao.class.getName()).log(Level.SEVERE, null, ex);
	    return null;
	}

    }

}
