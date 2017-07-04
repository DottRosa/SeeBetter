package com.example.seebetter;

import android.app.Fragment;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * @author DottRosa (marco1rosa@gmail.com)
 *
 * Fragment per il menu di daltonizzazione
 */
public class DaltonizeFragment extends Fragment implements FragmentMenu{
    //Voce del menu attivata dall'utente
    private RelativeLayout activeMenuItem;


    /**
     * Interfaccia che permette la comunicazione con l'activity che
     * istanzia questo fragment.
     */
    interface selectedDaltonize{

        /**
         * Il metodo prende la tipologia di filtro e la passa all'activity
         * che ha richiamato questo fragment
         * @param daltonizeId l'id del filtro da applicare
         */
        void getDaltonizeFromMenu(int daltonizeId);
    }

    //L'interfaccia che permetterà di passare i dati
    selectedDaltonize sd;


    /**
     * Gestisce il menu di daltonizzazione
     * @param inflater per il layout del fragment
     * @param container il contenitore
     * @param savedInstanceState l'istanza dell'activity precedente
     * @return il fragment per il menu di daltonizzazione
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        //Estrapolo il fragment in base al layout e al contenitore
        View frag = inflater.inflate(R.layout.daltonize_fragment_layout, container, false);

        //Prendo i relative layout relativi ai bottoni
        final RelativeLayout daltonP = (RelativeLayout)frag.findViewById(R.id.dp_box);
        final RelativeLayout daltonD = (RelativeLayout)frag.findViewById(R.id.dd_box);
        final RelativeLayout daltonT = (RelativeLayout)frag.findViewById(R.id.dt_box);


        //Ascoltatore per la pressione del pulsante di daltonizzazione per protanopia
        daltonP.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                selectedFilterComunication(daltonP, Filters.PROTANOPE_DALTONIZATION_ID);
            }
        });


        //Ascoltatore per la pressione del pulsante di daltonizzazione per deuteranopia
        daltonD.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                selectedFilterComunication(daltonD, Filters.DEUTERANOPE_DALTONIZATION_ID);
            }
        });


        //Ascoltatore per la pressione del pulsante di daltonizzazione per tritanopia
        daltonT.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                selectedFilterComunication(daltonT, Filters.TRITANOPE_DALTONIZATION_ID);
            }
        });

        return frag;
    }


    /**
     * Disattiva l'item corrente nel menu e attiva quello passato come parametro
     * @param rl il relative layout da rendere attivo
     */
    @Override
    public void activateMenuItem(RelativeLayout rl){
        if(activeMenuItem != null){
            //Disattivo la voce attiva
            activeMenuItem.setBackgroundColor(Color.parseColor("#AA000000"));
            ((TextView)activeMenuItem.getChildAt(1)).setTextColor(Color.parseColor("#FFFFFF"));
        }
        activeMenuItem = rl;    //Imposto il nuovo elemento attivo
        //Attivo la nuova voce
        rl.setBackgroundColor(Color.parseColor("#FFFFFF"));
        ((TextView)rl.getChildAt(1)).setTextColor(Color.parseColor("#000000"));
    }


    /**
     * Comunica all'activity principale il filtro da applicare ed evidenzia la voce selezionata
     * @param rl la voce del menu selezionata
     * @param id l'id del filtro da applicare
     */
    @Override
    public void selectedFilterComunication(RelativeLayout rl, int id) {
        activateMenuItem(rl);
        sd = (selectedDaltonize)getActivity();
        sd.getDaltonizeFromMenu(id);
    }
}
