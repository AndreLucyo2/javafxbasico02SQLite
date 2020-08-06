/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UTILIDADES;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLDecoder;
import javafx.scene.image.Image;
import javax.swing.JOptionPane;

/**
 *
 * @author Andre
 */
public class Aplicacao
{

    /**
     * Retorna o caminho onde a aplicação está sendo executada
     *
     * @return caminho do arquivo .jar
     */
    public String getPastaAplicacao()
    {
	//Retorna o diretorio completo desta classe:
	String url = getClass().getResource(getClass().getSimpleName() + ".class").getPath();

	//Converte em File para ter acesso a  metodo getPatch	
	File diretorio = new File(url).getParentFile();

	String path = null;
	if (diretorio.getPath().contains(".jar"))
	{
	    path = findJarParentPath(diretorio);
	}
	else
	{
	    path = diretorio.getPath();
	}

	try
	{
	    return URLDecoder.decode(path, "UTF-8");
	}
	catch (UnsupportedEncodingException e)
	{
	    return path.replace("%20", " ");
	}
    }

    /**
     * Retorna o caminho da aplicação ( arquivo .jar )
     */
    private String findJarParentPath(File jarFile)
    {
	//Faz um laço buscando o arquivo .jar:
	while (jarFile.getPath().contains(".jar"))
	{
	    jarFile = jarFile.getParentFile();
	}

	return jarFile.getPath().substring(6);
    }

    /**
     * Cria a pasta informada na raiz da aplicaão, Para subpastas informar :
     * Pasta/Pasta
     *
     * @param String pasta
     * @return
     */
    public static boolean criaPastaNaRaiz(String pasta)
    {
	try
	{
	    //----------------------------------------------------------------------
	    //Definir a pasta Raiz da aplicação: onde esta o arquivo .jar
	    //----------------------------------------------------------------------
	    String pastaJAR = new Aplicacao().getPastaAplicacao();

	    //----------------------------------------------------------------------
	    //VALIDA SE A PASTA EXISTE:
	    //----------------------------------------------------------------------
	    String patchBD = pastaJAR + "/" + pasta;
	    File diretorio = new File(patchBD);
	    if (!diretorio.exists())
	    {
		//cria pastas e subpasta: 
		diretorio.mkdirs();

		//Msg Prompt:
		System.out.println(">>>>>>> PASTAS CIADAS: " + diretorio.getAbsolutePath());

		return true;
	    }
	    else
	    {
		//Msg Prompt:
		System.out.println(">>>>>>> Pasta Ja existe!");
		return false;
	    }

	}
	catch (Exception ex)
	{
	    System.out.println("Erro! Criar Pastas!" + ex.getMessage() + "\n");
	    throw new RuntimeException(ex);
	}

    }

    /**
     * Pegar uma iagem que esta dentro do .jar
     *
     * @param camihoImagem
     * @return
     */
    public Image getImageJAR(String camihoImagem)
    {
	//Use o metodo getResource para ler recursos de um arquivo JAR. 
	//recupera imagens de um arquivo JAR
	ClassLoader cl = this.getClass().getClassLoader();

	JOptionPane.showMessageDialog(null, camihoImagem);

	JOptionPane.showMessageDialog(null, cl.getResource(camihoImagem).getPath());

	// Create icons
	Image img = new Image(cl.getResource(camihoImagem).getPath());

	return img;
    }

    //Pegar recurso dentro do .jar https://docs.oracle.com/javase/tutorial/deployment/webstart/retrievingResources.html	
    //Retorna uma imagem:
    public Image setImageSemFoto()
    {
	//Use o metodo getResource para ler recursos de um arquivo JAR. 
	//recupera imagens de um arquivo JAR
	ClassLoader cl = this.getClass().getClassLoader();

	
	
	URL url = cl.getResource("IMG/sem-foto-teste.jpg");
	Image img = new Image(url.getHost());
	
	JOptionPane.showMessageDialog(null,url);

	return img;
    }
}
