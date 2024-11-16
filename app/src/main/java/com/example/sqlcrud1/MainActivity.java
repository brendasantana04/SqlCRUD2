package com.example.sqlcrud1;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import com.example.sqlcrud1.dao.JogadorDAO;
import com.example.sqlcrud1.dao.TimeDAO;
import com.example.sqlcrud1.ui.JogadorFragment;
import com.example.sqlcrud1.ui.TimeFragment;
import com.google.android.material.appbar.MaterialToolbar;

/*
 *@author:<Brenda>
 *@ra:<1110482313042>
 */

public class MainActivity extends AppCompatActivity {

    private JogadorDAO jogadorDao;
    private TimeDAO timeDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // iniciando daos
        jogadorDao = new JogadorDAO(this);
        timeDao = new TimeDAO(this);

        MaterialToolbar toolbar = findViewById(R.id.topAppBar);
        toolbar.setOnMenuItemClickListener(item -> {
            Fragment fragment = null;

            if (item.getItemId() == R.id.menu_jogador) {
                fragment = new JogadorFragment(jogadorDao);
            } else if (item.getItemId() == R.id.menu_time) {
                fragment = new TimeFragment(timeDao);
            }

            if (fragment != null) {
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, fragment)
                        .commit();
            }

            return true;
        });
    }
}
