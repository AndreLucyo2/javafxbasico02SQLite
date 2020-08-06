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
public class EmpresaDto
{

    private int id;
    private String nome;
    private String cpfCnpj;
    private String foto;//Salva so caminho da foto

    public EmpresaDto()
    {
    }

    public EmpresaDto(String nome, String cpfCnpj, String foto)
    {
	this.nome = nome;
	this.cpfCnpj = cpfCnpj;
	this.foto = foto;
    }

    public EmpresaDto(int id, String nome, String cpfCnpj, String foto)
    {
	this.id = id;
	this.nome = nome;
	this.cpfCnpj = cpfCnpj;
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

    public String getCpfCnpj()
    {
	return cpfCnpj;
    }

    public void setCpfCnpj(String cpfCnpj)
    {
	this.cpfCnpj = cpfCnpj;
    }

    public String getFoto()
    {
	return foto;
    }

    public void setFoto(String foto)
    {
	this.foto = foto;
    }

    //Teste: retorna uma string
    public String mostraEmpresa()
    {
	return getId() + " | " + getNome() + " | " + getCpfCnpj() + " | " + getFoto();
    }
}
