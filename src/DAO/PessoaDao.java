/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import DTO.PessoaDto;
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

    //o CONTRUTOR ja define e conexao
    public PessoaDao()
    {
        //Cria e define e conexao com o banco Postgre:
        this.connection = new SQLiteConect().getConnection();
    }

    public boolean insert(PessoaDto pessoa)
    {
        try
        {
            //Ver Montar ssql com parametro : https://www.guj.com.br/t/consulta-com-parametros-em-java/77331        
            //String sql = "INSERT INTO usuario(usuario,senha) VALUES('" + usuario.getUsuario() + "','" + usuario.getSenha() + "'); ";
            //Cria Query para receber os parametros:
            String sql = "INSERT INTO pessoa(nome,email,senha, foto) VALUES(?,?,?,?);";

            //Prepara a Conexao com o banco, informano uma Query sql definida:
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, pessoa.getNome());
            statement.setString(2, pessoa.getEmail());
            statement.setString(3, pessoa.getSenha());
	    statement.setString(4, pessoa.getFoto());

            //Executa a Query no banco:
            statement.execute();

            //Fechar a conexão com banco:
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
            //Ver Montar ssql com parametro : https://www.guj.com.br/t/consulta-com-parametros-em-java/77331        
            //String sql = "INSERT INTO usuario(usuario,senha) VALUES('" + usuario.getUsuario() + "','" + usuario.getSenha() + "'); ";
            //Cria Query para receber os parametros:
            String sql = "UPDATE pessoa SET nome = ?, email = ?, senha = ?, foto = ? WHERE id = ?;";

            //Prepara a Conexao com o banco, informano uma Query sql definida:
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, pessoa.getNome());
            statement.setString(2, pessoa.getEmail());
            statement.setString(3, pessoa.getSenha());
	    statement.setString(4, pessoa.getFoto());
            statement.setLong(5, pessoa.getId());

            //Executa a Query no banco:
            statement.execute();

            //Fechar a conexão com banco:
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
            //se ID for maior que zero , ja tem cadastro, neste daso sera um update
            update(pessoa);
        }
        else
        {
            //caso contrario esta criando um novo usuario
            insert(pessoa);
        }

    }

    public boolean delete(PessoaDto pessoa)
    {
        try
        {
            //Ver Montar ssql com parametro : https://www.guj.com.br/t/consulta-com-parametros-em-java/77331        
            //String sql = "INSERT INTO usuario(usuario,senha) VALUES('" + usuario.getUsuario() + "','" + usuario.getSenha() + "'); ";
            //Cria Query para receber os parametros:
            String sql = "DELETE FROM pessoa WHERE id = ?;";

            //Prepara a Conexao com o banco, informano uma Query sql definida:
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setLong(1, pessoa.getId());

            //Executa a Query no banco:
            statement.execute();

            //Fechar a conexão com banco:
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
            //executa a consulta conforma a query:
            statement.execute();
            ResultSet resultSet = statement.getResultSet();

            ArrayList<PessoaDto> pessoas = new ArrayList<>();

            //retorna varias linhas faz iteração em cada linha retornada do select:        
            while (resultSet.next())// resultSet.next() ja testa se retornou pelo menos uma linha.
            {
                int id = resultSet.getInt("id");
                String nome = resultSet.getString("nome");
                String email = resultSet.getString("email");
                String senha = resultSet.getString("senha");
		String foto = resultSet.getString("foto");

                PessoaDto pessoa = new PessoaDto(id, nome, email, senha,foto);
                pessoas.add(pessoa);//Adiciona cada objeto no arrayList de objetos
            }

            //Fechar a conexão com banco:
            statement.close();
            connection.close();

            //Retorna a lista de objetos retornados do banco:
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

            //Prepara a Conexao com o banco, informano uma Query sql:
            PreparedStatement statement = connection.prepareStatement(sql);

            return getList(statement);

        }
        catch (SQLException ex)
        {
            //Printa o erro no promp
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

            //Prepara a Conexao com o banco, informano uma Query sql:
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setLong(1, pessoa.getId());

            return getList(statement).get(0);//retorna somente 1 objeto

        }
        catch (SQLException ex)
        {
            //Printa o erro no promp
            ex.printStackTrace();

            Logger.getLogger(PessoaDao.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }

    }

    /**
     * Teste: Verifica se o usuario informado na tela de logIn existe no banco
     * @param pessoa
     */
    public boolean validaLogin(PessoaDto pessoa)
    {
        try
        {
            String sql = "SELECT * FROM pessoa WHERE email = ? AND senha = ?";

            //Prepara a Conexao com o banco, informano uma Query sql:
            PreparedStatement statement = connection.prepareStatement(sql);     
            statement.setString(1, pessoa.getEmail());
            statement.setString(2, pessoa.getSenha());

            statement.execute();

            //Pega o resultado que retornou do banco:
            ResultSet resultSet = statement.getResultSet();

            //Enquanto resultSet tiver um proximo, retornar true caso contrario false: 
            return resultSet.next();

        }
        catch (SQLException ex)
        {
            //Printa o erro no promp
            ex.printStackTrace();

            Logger.getLogger(PessoaDao.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }

    }

}
