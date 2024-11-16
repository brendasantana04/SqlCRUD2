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
import com.example.sqlcrud1.dao.ICRUDDao;
import com.example.sqlcrud1.model.Jogador;
import com.example.sqlcrud1.model.Time;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

/*
 *@author:<Brenda>
 *@ra:<1110482313042>
 */

public class JogadorFragment extends Fragment {

    private EditText etIdJogador, etNomeJogador, etDataNascJogador, etAlturaJogador, etPesoJogador, etTimeJogador;
    private TextView tvResultadoJogador;
    private ICRUDDao<Jogador> jogadorDao;  // Aqui você passará a implementação de ICRUDDao

    public JogadorFragment() {}

    public JogadorFragment(ICRUDDao<Jogador> jogadorDao) {
        this.jogadorDao = jogadorDao;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_jogador, container, false);

        etIdJogador = view.findViewById(R.id.etIdJogador);
        etNomeJogador = view.findViewById(R.id.etNomeJogador);
        etDataNascJogador = view.findViewById(R.id.etDataNascJogador);
        etAlturaJogador = view.findViewById(R.id.etAlturaJogador);
        etPesoJogador = view.findViewById(R.id.etPesoJogador);
        etTimeJogador = view.findViewById(R.id.etTimeJogador);
        tvResultadoJogador = view.findViewById(R.id.tvResultadoJogador);

        Button btnInsert = view.findViewById(R.id.btnInsertJogador);
        Button btnUpdate = view.findViewById(R.id.btnUpdateJogador);
        Button btnDelete = view.findViewById(R.id.btnDeleteJogador);
        Button btnFindOne = view.findViewById(R.id.btnFindOneJogador);
        Button btnListAll = view.findViewById(R.id.btnListAllJogador);

        btnInsert.setOnClickListener(v -> insertJogador());
        btnUpdate.setOnClickListener(v -> updateJogador());
        btnDelete.setOnClickListener(v -> deleteJogador());
        btnFindOne.setOnClickListener(v -> findOneJogador());
        btnListAll.setOnClickListener(v -> listAllJogador());

        return view;
    }

    private void insertJogador() {
        Jogador jogador = new Jogador();
        jogador.setNome(etNomeJogador.getText().toString());
        jogador.setDataNasc(LocalDate.parse(etDataNascJogador.getText().toString())); // Converte de String para LocalDate
        jogador.setAltura(Float.parseFloat(etAlturaJogador.getText().toString()));
        jogador.setPeso(Float.parseFloat(etPesoJogador.getText().toString()));

        Time time = new Time();
        time.setCodigo(Integer.parseInt(etTimeJogador.getText().toString()));
        jogador.setTime(time);

        try {
            jogadorDao.insert(jogador);
            tvResultadoJogador.setText("Jogador inserido com sucesso!");
        } catch (SQLException e) {
            tvResultadoJogador.setText("Erro ao inserir: " + e.getMessage());
        }
    }

    private void updateJogador() {
        Jogador jogador = new Jogador();
        jogador.setId(Integer.parseInt(etIdJogador.getText().toString()));
        jogador.setNome(etNomeJogador.getText().toString());
        jogador.setDataNasc(LocalDate.parse(etDataNascJogador.getText().toString()));
        jogador.setAltura(Float.parseFloat(etAlturaJogador.getText().toString()));
        jogador.setPeso(Float.parseFloat(etPesoJogador.getText().toString()));

        Time time = new Time();
        time.setCodigo(Integer.parseInt(etTimeJogador.getText().toString()));
        jogador.setTime(time);

        try {
            int rowsUpdated = jogadorDao.update(jogador);
            tvResultadoJogador.setText(rowsUpdated + " jogador(es) atualizado(s) com sucesso!");
        } catch (SQLException e) {
            tvResultadoJogador.setText("Erro ao atualizar: " + e.getMessage());
        }
    }

    private void deleteJogador() {
        Jogador jogador = new Jogador();
        jogador.setId(Integer.parseInt(etIdJogador.getText().toString()));

        try {
            jogadorDao.delete(jogador);
            tvResultadoJogador.setText("Jogador deletado com sucesso!");
        } catch (SQLException e) {
            tvResultadoJogador.setText("Erro ao deletar: " + e.getMessage());
        }
    }

    private void findOneJogador() {
        Jogador jogador = new Jogador();
        jogador.setId(Integer.parseInt(etIdJogador.getText().toString()));

        try {
            Jogador foundJogador = jogadorDao.findOne(jogador);
            tvResultadoJogador.setText(foundJogador != null ? foundJogador.toString() : "Jogador não encontrado.");
        } catch (SQLException e) {
            tvResultadoJogador.setText("Erro ao buscar: " + e.getMessage());
        }
    }

    private void listAllJogador() {
        try {
            List<Jogador> jogadores = jogadorDao.findAll();
            StringBuilder result = new StringBuilder("Lista de Jogadores:\n");
            for (Jogador jogador : jogadores) {
                result.append(jogador.toString()).append("\n");
            }
            tvResultadoJogador.setText(result.toString());
        } catch (SQLException e) {
            tvResultadoJogador.setText("Erro ao listar: " + e.getMessage());
        }
    }
}
