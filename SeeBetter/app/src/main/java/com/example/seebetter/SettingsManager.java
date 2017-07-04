package com.example.seebetter;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * @author DottRosa (marco1rosa@gmail.com)
 *
 * Activity che avvia i settings
 */

public class SettingsManager {
    //Permette di accedere a tutte le preferenze
    private static SharedPreferences prefs;
    //Nome della lista di preferenze per il metodo di daltonizzazione
    private final static String PREFS_DALTONIZE_NAME = "correction_method";
    //Nome della checkbox per attivare i filtri daltonici o meno alla pressione sullo schermo durante la daltonizzazione
    private final static String PREFS_FINGER_FILTER_NAME = "finger_filter";
    //Metodo di default per il metodo di daltonizzazione (in questo caso solo il colore di default)
    private final static String DEFAULT_METHOD = "0";
    //La checkbox è sputanta di default
    private final static boolean DEFAULT_FINGER_FILTER = true;
    //Il metodo di daltonizzazione corrente
    public static int currentMethod;
    //Indica se la checkbox è spuntata o meno
    public static boolean currentFingerFilter;

    /**
     * Avvia il manager, accedendo alle preferenze in base al contesto
     * @param context il contesto dell'activity che invoca il metodo
     */
    public static void startManager(Context context){
        prefs = PreferenceManager.getDefaultSharedPreferences(context);
        currentMethod = 0;
        currentFingerFilter = true;
    }

    /**
     * Aggiorna i valori relativi alle voci del menu se queste vengono modificate
     */
    public static void updateSettings(){
        //Il metodo di daltonizzazione
        int method = Integer.parseInt(prefs.getString(PREFS_DALTONIZE_NAME,DEFAULT_METHOD));

        if(method != currentMethod) {
            currentMethod = method;
        }

        //La spunta della checkbox
        boolean finger = prefs.getBoolean(PREFS_FINGER_FILTER_NAME,DEFAULT_FINGER_FILTER);
        if(finger != currentFingerFilter) {
            currentFingerFilter = !currentFingerFilter;
        }
    }


}
