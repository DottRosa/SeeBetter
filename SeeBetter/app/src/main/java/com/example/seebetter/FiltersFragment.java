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
 * Fragment per il menu di simulazione
 */
public class FiltersFragment extends Fragment implements FragmentMenu{
    //Voce del menu attivata dall'utente
    private RelativeLayout activeMenuItem;


    /**
     * Interfaccia che permette la comunicazione con l'activity che
     * istanzia questo fragment.
     */
    interface selectedFilter{

        /**
         * Il metodo prende la tipologia di filtro e la passa all'activity
         * che ha richiamato questo fragment
         * @param filterId l'id del filtro da applicare
         */
        void getFilterFromMenu(int filterId);
    }

    //L'interfaccia che permetterà di passare i dati
    selectedFilter sf;


    /**
     * Gestisce il menu di simulazione
     * @param inflater per il layout del fragment
     * @param container il contenitore
     * @param savedInstanceState l'istanza dell'activity precedente
     * @return il fragment per il menu di daltonizzazione
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        //Il frammento che verrà usato come view
        View frag = inflater.inflate(R.layout.filters_fragment_layout, container, false);

        //Box che rappresentano le voci del menu dei filtri
        final RelativeLayout filterN = (RelativeLayout)frag.findViewById(R.id.n_box);
        final RelativeLayout filterP = (RelativeLayout)frag.findViewById(R.id.p_box);
        final RelativeLayout filterD = (RelativeLayout)frag.findViewById(R.id.d_box);
        final RelativeLayout filterT = (RelativeLayout)frag.findViewById(R.id.t_box);

        //Attivo la voce del menu principale, cioè la visione normale
        activateMenuItem(filterN);


        //Ascoltatore per la voce relativa al filtro normale
        filterN.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                selectedFilterComunication(filterN, Filters.NORMAL_SIMULATION_ID);
            }
        });

        //Ascoltatore per la voce relativa al filtro protanope
        filterP.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                selectedFilterComunication(filterP, Filters.PROTANOPE_SIMULATION_ID);
            }
        });

        //Ascoltatore per la voce relativa al filtro deuteranope
        filterD.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                selectedFilterComunication(filterD, Filters.DEUTERANOPE_SIMULATION_ID);
            }
        });

        //Ascoltatore per la voce relativa al filtro tritanope
        filterT.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                selectedFilterComunication(filterT, Filters.TRITANOPE_SIMULATION_ID);
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
        sf = (selectedFilter) getActivity();
        sf.getFilterFromMenu(id);
    }
}
