package com.example.sqlcrud1.controller;

import com.example.sqlcrud1.dao.TimeDAO;
import com.example.sqlcrud1.model.Time;

import java.sql.SQLException;
import java.util.List;

public class TimeController {
    private TimeDAO timeDAO;

    public TimeController(android.content.Context context) {
        timeDAO = new TimeDAO(context);
    }

    public void inserirTime(int codigo, String nome, String cidade) throws SQLException {
        Time time = new Time(codigo);
        time.setNome(nome);
        time.setCidade(cidade);
        timeDAO.insert(time);
    }

    public void atualizarTime(int codigo, String nome, String cidade) throws SQLException {
        Time time = new Time(codigo);
        time.setCodigo(codigo);
        time.setNome(nome);
        time.setCidade(cidade);
        timeDAO.update(time);
    }

    public void deletarTime(int codigo) throws SQLException {
        Time time = new Time(codigo);
        time.setCodigo(codigo);
        timeDAO.delete(time);
    }

    public Time buscarTime(int codigo) throws SQLException {
        Time time = new Time(codigo);
        time.setCodigo(codigo);
        return timeDAO.findOne(time);
    }

    public List<Time> listarTodosTimes() throws SQLException {
        return timeDAO.findAll();
    }
}
