package com.example.sqlcrud1.model;

/*
 *@author:<Brenda>
 *@ra:<1110482313042>
 */

public class Time {
    private int codigo;
    private String nome;
    private String cidade;


    public Time() {
    }

    public Time(int codigo) {
        this.codigo = codigo;
    }
    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    @Override
    public String toString() {
        return "Time{codigo=" + codigo + ", nome='" + nome + "', cidade='" + cidade + "'}";
    }
}
