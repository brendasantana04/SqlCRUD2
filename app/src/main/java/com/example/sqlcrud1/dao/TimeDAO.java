package com.example.sqlcrud1.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import com.example.sqlcrud1.model.Time;

import java.util.ArrayList;
import java.util.List;

/*
 *@author:<Brenda>
 *@ra:<1110482313042>
 */

public class TimeDAO implements ICRUDDao<Time> {
    private SQLiteDatabase db;
    private SQLiteHelper dbHelper;

    public TimeDAO(Context context) {
        dbHelper = new SQLiteHelper(context);
        db = dbHelper.getWritableDatabase();
    }

    public TimeDAO() {

    }

    @Override
    public void insert(Time time) throws SQLException {
        ContentValues values = new ContentValues();
        values.put("nome", time.getNome());
        values.put("cidade", time.getCidade());

        long result = db.insert("times", null, values);
        if (result == -1) {
            throw new SQLException("Erro ao inserir time.");
        }
    }

    @Override
    public int update(Time time) throws SQLException {
        ContentValues values = new ContentValues();
        values.put("nome", time.getNome());
        values.put("cidade", time.getCidade());

        int rowsAffected = db.update("times", values, "codigo = ?", new String[]{String.valueOf(time.getCodigo())});
        if (rowsAffected == 0) {
            throw new SQLException("Erro ao atualizar time.");
        }
        return rowsAffected;
    }

    @Override
    public void delete(Time time) throws SQLException {
        int rowsAffected = db.delete("times", "codigo = ?", new String[]{String.valueOf(time.getCodigo())});
        if (rowsAffected == 0) {
            throw new SQLException("Erro ao excluir time.");
        }
    }

    @Override
    public Time findOne(Time time) throws SQLException {
        Cursor cursor = db.query("times", null, "codigo = ?", new String[]{String.valueOf(time.getCodigo())}, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            Time foundTime = cursorToTime(cursor);
            cursor.close();
            return foundTime;
        }
        throw new SQLException("Time não encontrado.");
    }

    @Override
    public List<Time> findAll() throws SQLException {
        List<Time> times = new ArrayList<>();
        Cursor cursor = db.query("times", null, null, null, null, null, null);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                times.add(cursorToTime(cursor));
            }
            cursor.close();
        } else {
            throw new SQLException("Erro ao buscar times.");
        }
        return times;
    }

    private Time cursorToTime(Cursor cursor) {
        Time time = new Time();
        int codigoColumnIndex = cursor.getColumnIndex("codigo");
        int nomeColumnIndex = cursor.getColumnIndex("nome");
        int cidadeColumnIndex = cursor.getColumnIndex("cidade");

        if (codigoColumnIndex >= 0) {
            time.setCodigo(cursor.getInt(codigoColumnIndex));
        }
        if (nomeColumnIndex >= 0) {
            time.setNome(cursor.getString(nomeColumnIndex));
        }
        if (cidadeColumnIndex >= 0) {
            time.setCidade(cursor.getString(cidadeColumnIndex));
        }

        return time;
    }


    public void fechar() {
        db.close();
    }
}
