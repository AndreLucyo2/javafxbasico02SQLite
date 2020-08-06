/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package VIEWMain;

import DAO.PessoaDao;
import DTO.PessoaDto;
import JDBC.AtuBanco;
import JDBC.SQLiteConect;
import UTILIDADES.Aplicacao;
import java.io.File;
import java.io.IOException;
import java.util.List;
import javax.swing.JOptionPane;

//iTexJava: https://sourceforge.net/projects/itext/
// -- https://mvnrepository.com/artifact/com.itextpdf/itextpdf
/**
 *
 * @author Andre
 */
public class TesteBancoDeDados
{

    public static void main(String[] args)
    {
	TesteBancoDeDados tt = new TesteBancoDeDados();
	tt.teste();

	//Use o metodo getResource para ler recursos de um arquivo JAR. 
	//recupera imagens de um arquivo JAR
	//---------------------------------------------------------------------
	//Teste de Insert com for:
	//---------------------------------------------------------------------
	//for (int i = 0; i < 10; i++)
	//{
	//    //Teste de Inseer Postgre:
	//    testeInsert(i);
	//    
	//    System.out.println("inset: "+i);
	//}
	//---------------------------------------------------------------------
	//Teste de Banco:
	//---------------------------------------------------------------------
	//testeDelete();
	//testeUpdate();
	//testeSelect();
    }

    public void teste()
    {
	ClassLoader cl = this.getClass().getClassLoader();

	JOptionPane.showMessageDialog(null, cl.getResource("IMG/sem-foto-teste.jpg").getFile());

    }

    public String testeBuscacaminhoJar_04()
    {
	//Defino o camiho do .jar
	String caminho = getClass().getResource(getClass().getSimpleName() + ".class").getPath();
	String jarFilePath = caminho.substring(caminho.indexOf(":") + 1, caminho.indexOf("!"));

	//Converte em File para ter acesso a  metodo getPatch	
	File diretorio = new File(jarFilePath).getParentFile();

	//Faz um laço buscando o arquivo .jar:
	while (diretorio.getPath().contains(".jar"))
	{
	    diretorio = diretorio.getParentFile();
	}

	//Mostro o texto retornado
	msgCX(diretorio.getPath());

	System.out.println(jarFilePath);

	return diretorio.getPath();
    }

    private static void testeBuscacaminhoJar_03() throws IOException
    {
	File dir = new File(".");
	msgCX(dir.getCanonicalPath());

	System.out.println(dir);
    }

    private static void testeBuscacaminhoJar_02()
    {
	String dir = System.getProperty("user.dir");
	msgCX(dir);

	System.out.println(dir);
    }

    private static void testeBuscacaminhoJar_01()
    {

	Aplicacao caminho = new Aplicacao();
	String pastaApp = caminho.getPastaAplicacao();
	msgCX(pastaApp);

	System.out.println(pastaApp);

    }

    private static void msgCX(String mensagem)
    {
	JOptionPane.showMessageDialog(null, "Texto Retornado:\n" + mensagem);

    }

    private static void testeCriaBanco()
    {
	//---------------------------------------------------------------------
	//Testa a conexão e Criar Banco:
	//---------------------------------------------------------------------
	new SQLiteConect().getConnection();

	//---------------------------------------------------------------------
	//Validar se o arquivo do banco Existe:
	//---------------------------------------------------------------------
	if (true)
	{
	    //Criar as tabelas:
	    AtuBanco.criarTablePEssoa();
	    AtuBanco.criarTableEmpresa();

	    //Inserir usuário Admim:
	    String sql = "INSERT INTO pessoa(nome,email,senha) VALUES('Andre','admin','123');";
	    new AtuBanco().executaSQL(sql);

	}
	else
	{
	    System.out.println("Banco de dados nao encontrado!.");
	}
    }

    //-------------------------------------------------------------
    private static void testeInsert(int i)
    {
	PessoaDto pessoa1 = new PessoaDto();
	pessoa1.setNome("Pessoa - " + i);
	pessoa1.setEmail("teset@teste.com");
	pessoa1.setSenha("123");
	pessoa1.setFoto("");

	PessoaDao pessoaDao = new PessoaDao();

	if (pessoaDao.insert(pessoa1))
	{
	    System.out.println("Registro adicionado: " + pessoa1.getId());
	}
	else
	{
	    System.out.println("Não adicionado");
	}
    }

    private static void testeUpdate()
    {
	//Pode carregar um objeto assim:
	PessoaDto pessoa1 = new PessoaDto("Teste", "teste@teste.com", "123", "");
	pessoa1.setId(11);

	PessoaDao pessoaDao = new PessoaDao();
	if (pessoaDao.update(pessoa1))
	{
	    System.out.println("Registro Atualizado");
	}
	else
	{
	    System.out.println("Não Atualizado");
	}

    }

    private static void testeDelete()
    {
	int idTeste = 10;

	//Pode carregar um objeto assim:
	PessoaDto pessoa1 = new PessoaDto();
	pessoa1.setId(idTeste);

	PessoaDao pessoaDao = new PessoaDao();
	if (pessoaDao.delete(pessoa1))
	{
	    System.out.println("Registro Deletado");
	}
	else
	{
	    System.out.println("Não deletado");
	}

    }

    private static void testeSelect()
    {
	PessoaDao pessoaDao = new PessoaDao();
	List<PessoaDto> pessoas = pessoaDao.selecAll();

	if (pessoas != null)
	{
	    for (int x = 0; x < pessoas.size(); x++)
	    {
		//Printa a lista:
		System.out.println(pessoas.get(x).mostraPessoa());
		System.out.println("--------------------------------------");
	    }
	}
	else
	{
	    System.out.println("Não deletado");
	}

    }

}
