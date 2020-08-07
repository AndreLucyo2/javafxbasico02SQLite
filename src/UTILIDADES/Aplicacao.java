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
	
	String url = getClass().getResource(getClass().getSimpleName() + ".class").getPath();

	
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
	    
	    
	    
	    String pastaJAR = new Aplicacao().getPastaAplicacao();

	    
	    
	    
	    String patchBD = pastaJAR + "/" + pasta;
	    File diretorio = new File(patchBD);
	    if (!diretorio.exists())
	    {
		
		diretorio.mkdirs();

		
		System.out.println(">>>>>>> PASTAS CIADAS: " + diretorio.getAbsolutePath());

		return true;
	    }
	    else
	    {
		
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
	
	
	ClassLoader cl = this.getClass().getClassLoader();

	JOptionPane.showMessageDialog(null, camihoImagem);

	JOptionPane.showMessageDialog(null, cl.getResource(camihoImagem).getPath());

	
	Image img = new Image(cl.getResource(camihoImagem).getPath());

	return img;
    }

    
    
    public Image setImageSemFoto()
    {
	
	
	ClassLoader cl = this.getClass().getClassLoader();

	
	
	URL url = cl.getResource("IMG/sem-foto-teste.jpg");
	Image img = new Image(url.getHost());
	
	JOptionPane.showMessageDialog(null,url);

	return img;
    }
}
