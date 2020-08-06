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

    //o CONTRUTOR ja define e conexao
    public EmpresaDao()
    {
        //Cria e define e conexao com o banco Postgre:
        this.connection = new SQLiteConect().getConnection();                   

    }

    public boolean insert(EmpresaDto empresa)
    {

        try
        {
            //Ver Montar ssql com parametro : https://www.guj.com.br/t/consulta-com-parametros-em-java/77331        
            //String sql = "INSERT INTO usuario(usuario,senha) VALUES('" + usuario.getUsuario() + "','" + usuario.getSenha() + "'); ";
            //Cria Query para receber os parametros:
            String sql = "INSERT INTO empresa(nome,cpfcnpj,foto) VALUES(?,?,?);";

            //Prepara a Conexao com o banco, informano uma Query sql definida:
            PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, empresa.getNome());
            statement.setString(2, empresa.getCpfCnpj());
	    statement.setString(3, empresa.getFoto());

            //Executa a Query no banco:
            statement.execute();

            //Pega i ID gerado:
            ResultSet resultSet = statement.getGeneratedKeys();

            //Ao gravar ja retorna o ID gerado:
            if (resultSet.next())
            {
                int id = resultSet.getInt("id");
                empresa.setId(id);
            }

            //Fechar a conexão com banco:
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
            //Ver Montar ssql com parametro : https://www.guj.com.br/t/consulta-com-parametros-em-java/77331        
            //String sql = "INSERT INTO usuario(usuario,senha) VALUES('" + usuario.getUsuario() + "','" + usuario.getSenha() + "'); ";
            //Cria Query para receber os parametros:
            String sql = "UPDATE empresa SET nome = ?, cpfcnpj = ?, foto = ? WHERE id = ?;";

            //Prepara a Conexao com o banco, informano uma Query sql definida:
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, empresa.getNome());
            statement.setString(2, empresa.getCpfCnpj());
	    statement.setString(3, empresa.getFoto());
            statement.setLong(4, empresa.getId());

            //Executa a Query no banco:
            statement.execute();

            //Fechar a conexão com banco:
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
            //se ID for maior que zero , ja tem cadastro, neste daso sera um update
            update(empresa);
        }
        else
        {
            //caso contrario esta criando um novo usuario
            insert(empresa);
        }

    }

    public boolean delete(EmpresaDto empresa)
    {
        try
        {
            //Ver Montar ssql com parametro : https://www.guj.com.br/t/consulta-com-parametros-em-java/77331        
            //String sql = "INSERT INTO usuario(usuario,senha) VALUES('" + usuario.getUsuario() + "','" + usuario.getSenha() + "'); ";
            //Cria Query para receber os parametros:
            String sql = "DELETE FROM empresa WHERE id = ?;";

            //Prepara a Conexao com o banco, informano uma Query sql definida:
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setLong(1, empresa.getId());

            //Executa a Query no banco:
            statement.execute();

            //Fechar a conexão com banco:
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
            //executa a consulta conforma a query:
            statement.execute();
            ResultSet resultSet = statement.getResultSet();

            ArrayList<EmpresaDto> empresas = new ArrayList<>();

            //retorna varias linhas faz iteração em cada linha retornada do select:        
            while (resultSet.next())// resultSet.next() ja testa se retornou pelo menos uma linha.
            {
                int id = resultSet.getInt("id");
                String nome = resultSet.getString("nome");
                String cpfcnpj = resultSet.getString("cpfcnpj");
		String foto = resultSet.getString("foto");

                EmpresaDto empresa= new EmpresaDto(id, nome, cpfcnpj,foto);
                empresas.add(empresa);//Adiciona cada objeto no arrayList de objetos
            }

            //Fechar a conexão com banco:
            statement.close();
            connection.close();

            //Retorna a lista de objetos retornados do banco:
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

            //Prepara a Conexao com o banco, informano uma Query sql:
            PreparedStatement statement = connection.prepareStatement(sql);

            return getList(statement);

        }
        catch (SQLException ex)
        {
            //Printa o erro no promp
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

            //Prepara a Conexao com o banco, informano uma Query sql:
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setLong(1, empresa.getId());
            
            return getList(statement).get(0);//retorna somente 1 objeto

        }
        catch (SQLException ex)
        {
            //Printa o erro no promp
            ex.printStackTrace();

            Logger.getLogger(EmpresaDao.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }

    }

}
