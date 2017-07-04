package com.example.seebetter;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.ListPreference;
import android.preference.PreferenceFragment;

/**
 * @author DottRosa (marco1rosa@gmail.com)
 *
 * Il fragment che contiene le preferenze e le gestisce
 */

public class SettingsFragment extends PreferenceFragment implements SharedPreferences.OnSharedPreferenceChangeListener {

    //Chiave della preferenza relativa al metodo di daltonizzazione
    private final static String CORRECTION_METHOD_KEY = "correction_method";
    //Chiave della preferenza relativa al filtro da applicare premendo sullo schermo durante la daltonizzazione
    private final static String FINGER_FILTER__KEY = "finger_filter";
    //La lista dei metodi di correzione
    private ListPreference correctionMethod;
    //La checkbox per il filtro da applicare premendo sullo schermo durante la daltonizzazione
    private CheckBoxPreference fingerFilter;


    /**
     * Istanzia le variabili necessarie al funzionamento delle impostazioni
     * @param savedInstanceState lo stato dell'istanza
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addPreferencesFromResource(R.xml.preferences);  //L'xml delle preferenze

        correctionMethod = (ListPreference)findPreference(CORRECTION_METHOD_KEY);
        fingerFilter = (CheckBoxPreference)findPreference(FINGER_FILTER__KEY);

        //Setto come summary la voce selezionata
        correctionMethod.setSummary(correctionMethod.getEntry());
    }


    /**
     * Ascoltatore che viene invocato quando le impostazioni cambiano
     * @param sharedPreferences la preferenza
     * @param key la chiave della preferenza
     */
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences,
                                          String key) {

        //Se è stato cambiato il metodo di correzione
        if (key.equals("correction_method")) {
            correctionMethod.setSummary(correctionMethod.getEntry());
        }

        //Se è stata modificata la spunta alla checkbox
        if (key.equals("finger_filter")) {
            if(fingerFilter.isChecked()){
                fingerFilter.setSummary(R.string.finger_filter_summary_true);   //Checkbox attivata
            } else {
                fingerFilter.setSummary(R.string.finger_filter_summary_false);  //Checkbox disattivata
            }
        }


    }

    @Override
    public void onResume() {
        super.onResume();
        getPreferenceScreen().getSharedPreferences()
                .registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        getPreferenceScreen().getSharedPreferences()
                .unregisterOnSharedPreferenceChangeListener(this);
    }
}
