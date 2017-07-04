package com.example.seebetter;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

/**
 * @author DottRosa (marco1rosa@gmail.com)
 *
 * Activity che offre una spiegazione dettagliata della voce del menu info selezionata
 */

public class DescriptionActivity extends AppCompatActivity {

    //Indici che identificano le varie voci del menu
    private final static int NORMAL_ITEM = 0;
    private final static int PROTANOPIA_ITEM = 1;
    private final static int DEUTERANOPIA_ITEM = 2;
    private final static int TRITANOPIA_ITEM = 3;
    private final static int DALTONIZE_ITEM = 4;


    /**
     * Riceve la voce selezionata nell'activity precedente e imposta il testo in base ad essa
     * @param savedInstanceState l'istanza dell'activity chiamante
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Rimuovo la barra delle notifiche
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //Rimuovo la barra del titolo
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.description_layout);

        TextView title = (TextView)findViewById(R.id.description_title);
        TextView subtitle = (TextView)findViewById(R.id.description_subtitle);
        TextView text = (TextView)findViewById(R.id.description_text);

        //Prendo l'indice della voce selezionata, di default è 0
        int selectedItem = getIntent().getIntExtra("ITEM_LIST_POSITION", 0);

        //In base alla voce selezionata, imposto titolo, sottotitolo e testo
        switch (selectedItem){
            case NORMAL_ITEM:
                setTitle("Visione Tricromatica");
                title.setText(R.string.info_trichromatic_title);
                subtitle.setText(R.string.info_trichromatic_subtitle);
                text.setText(R.string.info_trichromatic_text);
                break;
            case PROTANOPIA_ITEM:
                setTitle("Protanopia");
                title.setText(R.string.info_protanopia_title);
                subtitle.setText(R.string.info_protanopia_subtitle);
                text.setText(R.string.info_protanopia_text);
                break;
            case DEUTERANOPIA_ITEM:
                setTitle("Deuteranopia");
                title.setText(R.string.info_deuteranopia_title);
                subtitle.setText(R.string.info_deuteranopia_subtitle);
                text.setText(R.string.info_deuteranopia_text);
                break;
            case TRITANOPIA_ITEM:
                setTitle("Tritanopia");
                title.setText(R.string.info_tritanopia_title);
                subtitle.setText(R.string.info_tritanopia_subtitle);
                text.setText(R.string.info_tritanopia_text);
                break;
            case DALTONIZE_ITEM:
                setTitle("Daltonizzazione");
                title.setText(R.string.info_daltonize_title);
                subtitle.setText(R.string.info_daltonize_subtitle);
                text.setText(R.string.info_daltonize_text);
                break;
        }

        /* TODO Migliorare la mostra del testo corretto in base alla selezione */


    }


}
