package com.example.sqlcrud1.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import com.example.sqlcrud1.R;
import com.example.sqlcrud1.controller.ICRUDDao;
import com.example.sqlcrud1.model.Time;

import java.sql.SQLException;
import java.util.List;

/*
 *@author:<Brenda>
 *@ra:<1110482313042>
 */

public class TimeFragment extends Fragment {

    private EditText etCodigoTime, etNomeTime, etCidadeTime;
    private TextView tvResultadoTime;
    private ICRUDDao<Time> timeDao;

    public TimeFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_time, container, false);

        etCodigoTime = view.findViewById(R.id.etCodigoTime);
        etNomeTime = view.findViewById(R.id.etNomeTime);
        etCidadeTime = view.findViewById(R.id.etCidadeTime);
        tvResultadoTime = view.findViewById(R.id.tvResultadoTime);

        Button btnInsert = view.findViewById(R.id.btnInsertTime);
        Button btnUpdate = view.findViewById(R.id.btnUpdateTime);
        Button btnDelete = view.findViewById(R.id.btnDeleteTime);
        Button btnFindOne = view.findViewById(R.id.btnFindOneTime);
        Button btnListAll = view.findViewById(R.id.btnListAllTime);

        btnInsert.setOnClickListener(v -> insertTime());
        btnUpdate.setOnClickListener(v -> updateTime());
        btnDelete.setOnClickListener(v -> deleteTime());
        btnFindOne.setOnClickListener(v -> findOneTime());
        btnListAll.setOnClickListener(v -> listAllTime());

        return view;
    }

    private void insertTime() {
        Time time = new Time();
        time.setNome(etNomeTime.getText().toString());
        time.setCidade(etCidadeTime.getText().toString());
        try {
            timeDao.insert(time);
            tvResultadoTime.setText("Time inserido com sucesso!");
        } catch (SQLException e) {
            tvResultadoTime.setText("Erro ao inserir: " + e.getMessage());
        }
    }

    private void updateTime() {
        Time time = new Time();
        time.setCodigo(Integer.parseInt(etCodigoTime.getText().toString()));
        time.setNome(etNomeTime.getText().toString());
        time.setCidade(etCidadeTime.getText().toString());
        try {
            int rowsUpdated = timeDao.update(time);
            tvResultadoTime.setText(rowsUpdated + " time(s) atualizado(s) com sucesso!");
        } catch (SQLException e) {
            tvResultadoTime.setText("Erro ao atualizar: " + e.getMessage());
        }
    }

    private void deleteTime() {
        Time time = new Time();
        time.setCodigo(Integer.parseInt(etCodigoTime.getText().toString()));
        try {
            timeDao.delete(time);
            tvResultadoTime.setText("Time deletado com sucesso!");
        } catch (SQLException e) {
            tvResultadoTime.setText("Erro ao deletar: " + e.getMessage());
        }
    }

    private void findOneTime() {
        Time time = new Time();
        time.setCodigo(Integer.parseInt(etCodigoTime.getText().toString()));
        try {
            Time foundTime = timeDao.findOne(time);
            tvResultadoTime.setText(foundTime != null ? foundTime.toString() : "Time não encontrado.");
        } catch (SQLException e) {
            tvResultadoTime.setText("Erro ao buscar: " + e.getMessage());
        }
    }

    private void listAllTime() {
        try {
            List<Time> times = timeDao.findAll();
            StringBuilder result = new StringBuilder("Lista de Times:\n");
            for (Time time : times) {
                result.append(time.toString()).append("\n");
            }
            tvResultadoTime.setText(result.toString());
        } catch (SQLException e) {
            tvResultadoTime.setText("Erro ao listar: " + e.getMessage());
        }
    }
}

