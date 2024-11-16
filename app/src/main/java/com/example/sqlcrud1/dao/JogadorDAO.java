package com.example.sqlcrud1.dao;

import static java.security.AccessController.getContext;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import com.example.sqlcrud1.model.Jogador;
import com.example.sqlcrud1.model.Time;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class JogadorDAO implements ICRUDDao<Jogador> {
    private SQLiteDatabase db;
    private SQLiteHelper dbHelper;

    public JogadorDAO(Context context) {
        dbHelper = new SQLiteHelper(context);
        db = dbHelper.getWritableDatabase();
    }

    @Override
    public void insert(Jogador jogador) throws SQLException {
        ContentValues values = new ContentValues();
        values.put("nome", jogador.getNome());
        values.put("dataNasc", jogador.getDataNasc().toString());
        values.put("altura", jogador.getAltura());
        values.put("peso", jogador.getPeso());
        values.put("time_id", jogador.getTime().getCodigo());

        long result = db.insert("jogadores", null, values);
        if (result == -1) {
            throw new SQLException("Erro ao inserir jogador.");
        }
    }

    @Override
    public int update(Jogador jogador) throws SQLException {
        ContentValues values = new ContentValues();
        values.put("nome", jogador.getNome());
        values.put("dataNasc", jogador.getDataNasc().toString());
        values.put("altura", jogador.getAltura());
        values.put("peso", jogador.getPeso());
        values.put("time_id", jogador.getTime().getCodigo());

        int rowsAffected = db.update("jogadores", values, "id = ?", new String[]{String.valueOf(jogador.getId())});
        if (rowsAffected == 0) {
            throw new SQLException("Erro ao atualizar jogador.");
        }
        return rowsAffected;
    }

    @Override
    public void delete(Jogador jogador) throws SQLException {
        int rowsAffected = db.delete("jogadores", "id = ?", new String[]{String.valueOf(jogador.getId())});
        if (rowsAffected == 0) {
            throw new SQLException("Erro ao excluir jogador.");
        }
    }

    @Override
    public Jogador findOne(Jogador jogador) throws SQLException {
        Cursor cursor = db.query("jogadores", null, "id = ?", new String[]{String.valueOf(jogador.getId())}, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            Jogador foundJogador = cursorToJogador(cursor);
            cursor.close();
            return foundJogador;
        }
        throw new SQLException("Jogador não encontrado.");
    }

    @Override
    public List<Jogador> findAll() throws SQLException {
        List<Jogador> jogadores = new ArrayList<>();
        Cursor cursor = db.query("jogadores", null, null, null, null, null, null);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                jogadores.add(cursorToJogador(cursor));
            }
            cursor.close();
        } else {
            throw new SQLException("Erro ao buscar jogadores.");
        }
        return jogadores;
    }

    private Jogador cursorToJogador(Cursor cursor) {
        Jogador jogador = new Jogador();

        int idIndex = cursor.getColumnIndex("id");
        int nomeIndex = cursor.getColumnIndex("nome");
        int dataNascIndex = cursor.getColumnIndex("dataNasc");
        int alturaIndex = cursor.getColumnIndex("altura");
        int pesoIndex = cursor.getColumnIndex("peso");
        int timeIdIndex = cursor.getColumnIndex("time_id");

        if (idIndex == -1 || nomeIndex == -1 || dataNascIndex == -1 || alturaIndex == -1 || pesoIndex == -1 || timeIdIndex == -1) {
            throw new SQLException("Colunas não encontradas no cursor.");
        }

        jogador.setId(cursor.getInt(idIndex));
        jogador.setNome(cursor.getString(nomeIndex));
        jogador.setDataNasc(LocalDate.parse(cursor.getString(dataNascIndex), DateTimeFormatter.ISO_DATE));
        jogador.setAltura(cursor.getFloat(alturaIndex));
        jogador.setPeso(cursor.getFloat(pesoIndex));

        int timeId = cursor.getInt(timeIdIndex);
        TimeDAO timeDAO = new TimeDAO();
        Time time = timeDAO.findOne(new Time(timeId));

        jogador.setTime(time);

        return jogador;
    }

    public void fechar() {
        db.close();
    }
}
