/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DTO;




/**
 *
 * @author Andre
 */
public class PessoaDto
{

    private int id;
    private String nome;
    private String email;
    private String senha;
    private String foto;

    public PessoaDto()
    {
    }

    public PessoaDto(String email, String senha)
    {
	this.email = email;
	this.senha = senha;
    }

    public PessoaDto(String nome, String email, String senha, String foto)
    {
	this.nome = nome;
	this.email = email;
	this.senha = senha;
	this.foto = foto;
    }

    public PessoaDto(int id, String nome, String email, String senha, String foto)
    {
	this.id = id;
	this.nome = nome;
	this.email = email;
	this.senha = senha;
	this.foto = foto;
    }

    public int getId()
    {
	return id;
    }

    public void setId(int id)
    {
	this.id = id;
    }

    public String getNome()
    {
	return nome;
    }

    public void setNome(String nome)
    {
	this.nome = nome;
    }

    public String getEmail()
    {
	return email;
    }

    public void setEmail(String email)
    {
	this.email = email;
    }

    public String getSenha()
    {
	return senha;
    }

    public void setSenha(String senha)
    {
	this.senha = senha;
    }

    public String getFoto()
    {
	return foto;
    }

    public void setFoto(String foto)
    {
	this.foto = foto;
    }

    
    public String mostraPessoa()
    {
	return getId() + " | " + getNome() + " | " + getEmail() + " | " + getSenha()+ " | " + getFoto();
    }

}
