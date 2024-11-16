package com.example.sqlcrud1.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SQLiteHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "crud.db";
    private static final int DATABASE_VERSION = 1;

    public SQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // criando tabelas
        String createTimesTable = "CREATE TABLE times (" +
                "codigo INTEGER PRIMARY KEY AUTOINCREMENT," +
                "nome TEXT NOT NULL," +
                "cidade TEXT NOT NULL);";

        String createJogadoresTable = "CREATE TABLE jogadores (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "nome TEXT NOT NULL," +
                "dataNasc TEXT NOT NULL," +
                "altura REAL NOT NULL," +
                "peso REAL NOT NULL," +
                "time_id INTEGER," +
                "FOREIGN KEY(time_id) REFERENCES times(codigo));";

        db.execSQL(createTimesTable);
        db.execSQL(createJogadoresTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS jogadores");
        db.execSQL("DROP TABLE IF EXISTS times");
        onCreate(db);
    }
}
