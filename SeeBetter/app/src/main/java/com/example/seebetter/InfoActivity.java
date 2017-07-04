package com.example.seebetter;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import java.util.ArrayList;

/**
 * @author DottRosa (marco1rosa@gmail.com)
 *
 * Activity della lista di info
 */

public class InfoActivity extends AppCompatActivity {

    //Lista che conterrà tutte le voci del menu delle info
    final ArrayList<String> stringArrayList = new ArrayList<>();

    /**
     * Genera il menu delle info con tutte le voci
     * @param savedInstanceState l'istanza dell'activity chiamante
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Rimuovo la barra delle notifiche
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //Rimuovo la barra del titolo
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.info_list_layout);
        setTitle("Terminologia");

        //Aggiungo alla lista le voci del menu
        stringArrayList.add(getString(R.string.info_trichromatic_title));
        stringArrayList.add(getString(R.string.info_protanopia_title) + " (" + getString(R.string.info_protanopia_subtitle) + ")");
        stringArrayList.add(getString(R.string.info_deuteranopia_title) + " (" + getString(R.string.info_deuteranopia_subtitle) + ")");
        stringArrayList.add(getString(R.string.info_tritanopia_title) + " (" + getString(R.string.info_tritanopia_subtitle) + ")");
        stringArrayList.add(getString(R.string.info_daltonize_title) + " (" + getString(R.string.info_daltonize_subtitle) + ")");

        //Prendo la lista dal layout
        ListView infoList = (ListView)findViewById(R.id.info_list);

        //Popolo la lista
        final ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, stringArrayList);
        infoList.setAdapter(adapter);


        //Ascoltatore che rileva la selezione di una voce del menu
        infoList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getBaseContext(), DescriptionActivity.class);
                intent.putExtra("ITEM_LIST_POSITION", i);   //Passo all'activity successiva la voce del menu selezionata
                startActivity(intent);  //Faccio partire la nuova activity
            }
        });


    }

}
