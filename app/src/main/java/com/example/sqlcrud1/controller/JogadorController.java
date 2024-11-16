package com.example.sqlcrud1.controller;

import com.example.sqlcrud1.dao.JogadorDAO;
import com.example.sqlcrud1.model.Jogador;
import com.example.sqlcrud1.model.Time;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

/*
 *@author:<Brenda>
 *@ra:<1110482313042>
 */

public class JogadorController {
    private JogadorDAO jogadorDAO;

    public JogadorController(android.content.Context context) {
        jogadorDAO = new JogadorDAO(context);
    }

    public void inserirJogador(String nome, String dataNasc, float altura, float peso, int timeId) throws SQLException {
        Jogador jogador = new Jogador();
        jogador.setNome(nome);
        jogador.setDataNasc(converterStringParaLocalDate(dataNasc));
        jogador.setAltura(altura);
        jogador.setPeso(peso);

        Time time = new Time(timeId);
        time.setCodigo(timeId);
        jogador.setTime(time);

        jogadorDAO.insert(jogador);
    }

    public void atualizarJogador(int id, String nome, String dataNasc, float altura, float peso, int timeId) throws SQLException {
        Jogador jogador = new Jogador();
        jogador.setId(id);
        jogador.setNome(nome);
        jogador.setDataNasc(converterStringParaLocalDate(dataNasc));
        jogador.setAltura(altura);
        jogador.setPeso(peso);

        Time time = new Time(timeId);
        time.setCodigo(timeId);
        jogador.setTime(time);

        jogadorDAO.update(jogador);
    }

    public void deletarJogador(int id) throws SQLException {
        Jogador jogador = new Jogador();
        jogador.setId(id);
        jogadorDAO.delete(jogador);
    }

    public Jogador buscarJogador(int id) throws SQLException {
        Jogador jogador = new Jogador();
        jogador.setId(id);
        return jogadorDAO.findOne(jogador);
    }

    public List<Jogador> listarTodosJogadores() throws SQLException {
        return jogadorDAO.findAll();
    }

    public String converterLocalDateParaString(LocalDate dataNasc) {
        if (dataNasc != null) {
            return dataNasc.format(DateTimeFormatter.ISO_DATE);
        }
        return "";
    }

    public LocalDate converterStringParaLocalDate(String dataNasc) {
        if (dataNasc != null && !dataNasc.isEmpty()) {
            return LocalDate.parse(dataNasc, DateTimeFormatter.ISO_DATE);
        }
        return null;
    }
}
