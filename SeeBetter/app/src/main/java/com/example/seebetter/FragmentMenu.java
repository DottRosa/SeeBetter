package com.example.seebetter;

import android.widget.RelativeLayout;

/**
 * @author DottRosa (marco1rosa@gmail.com)
 *
 * Interfaccia relativa ai fragment utilizzati come menu
 */

interface FragmentMenu {

    /**
     * Disattiva l'item corrente nel menu e attiva quello passato come parametro
     * @param rl il relative layout da rendere attivo
     */
    void activateMenuItem(RelativeLayout rl);


    /**
     * Comunica all'activity principale il filtro da applicare ed evidenzia la voce selezionata
     * @param rl la voce del menu selezionata
     * @param id l'id del filtro da applicare
     */
    void selectedFilterComunication(RelativeLayout rl, int id);

}
