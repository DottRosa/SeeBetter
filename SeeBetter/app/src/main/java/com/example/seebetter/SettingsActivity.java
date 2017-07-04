package com.example.seebetter;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;

/**
 * @author DottRosa (marco1rosa@gmail.com)
 *
 * Activity che avvia i settings
 */

public class SettingsActivity extends AppCompatActivity {

    /**
     * Avvia la pagina delle preferenze
     * @param savedInstanceState l'istanza dell'activity chiamante
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_layout);

        //Rimuovo la barra delle notifiche
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setTitle("Preferenze");

        //Avvio il fragment delle impostazioni
        getFragmentManager().beginTransaction()
                .replace(R.id.preferences_container, new SettingsFragment())
                .commit();
    }
}
