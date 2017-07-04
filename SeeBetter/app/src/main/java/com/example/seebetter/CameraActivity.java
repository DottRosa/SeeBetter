package com.example.seebetter;

import android.Manifest;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.TextureView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * @author DottRosa (marco1rosa@gmail.com)
 *
 * Main activity che gestisce l'interfaccia principale
 */
public class CameraActivity extends AppCompatActivity implements FiltersFragment.selectedFilter, DaltonizeFragment.selectedDaltonize {
    //Permesso per la fotocamera
    private static final int REQUEST_CAMERA_PERMISSION = 101;

    //Renderer che si occupa del rendering dei frame
    private CameraRenderer renderer;

    //TextureView che mostra quello che viene ripreso con la fotocamera
    private TextureView textureView;

    //Filtro di default
    private int currentFilter = R.id.filter_1;

    //Bottone per l'apertura del menu dei filtri
    private ImageButton filters;

    /**
     * TextView che mostra il filtro applicato
     */
    private TextView filterType;
    /**
     * Oggetto che permette di gestire il menu dei filtri
     */
    private FiltersFragment filtersMenu;
    /**
     * Oggetto che permette di gestire il menu della daltonizzazione
     */
    private DaltonizeFragment daltonizeMenu;
    /**
     * Oggetto utile per la gestione dei fragment
     */
    private android.app.FragmentTransaction fragmentTransaction;

    /* TODO Cambiare il nome degli id dei filtri */




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Rimuovo la barra delle notifiche
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //Rimuovo la barra del titolo
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_fullscreen);

        //Creo il fragment dei filtri
        FragmentManager fragmentManager = getFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        filtersMenu = new FiltersFragment();
        fragmentTransaction.add(R.id.fragment_container, filtersMenu);
        fragmentTransaction.hide(filtersMenu).commit(); //Inizialmente nascosto

        //Creo il fragment della daltonizzazione
        fragmentManager = getFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        daltonizeMenu = new DaltonizeFragment();
        fragmentTransaction.add(R.id.fragment_container, daltonizeMenu);
        fragmentTransaction.hide(daltonizeMenu).commit(); //Inizialmente nascosto

        //Prelevo bottoni e textView dell'interfaccia
        filterType = (TextView)findViewById(R.id.filterType);   //Mostra il tipo di daltonismo
        filters = (ImageButton)findViewById(R.id.filters);

        //Avvio il manager che si occupa di gestire le preferenze
        SettingsManager.startManager(this);

        //Ascoltatore per la pressione del pulsante dei filtri
        filters.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                //Apre e chiude il menu dei filtri
                changeFragmentVisibility(filtersMenu);
                //Chiudo il menu della daltonizzazione se visibile
                if(daltonizeMenu.isVisible()){
                    changeFragmentVisibility(daltonizeMenu);
                }
            }
        });

        //Ascoltatore per la pressione del pulsante della daltonizzazione
        ImageButton daltonize = (ImageButton)findViewById(R.id.daltonize);
        daltonize.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                //Apre e chiude il menu della daltonizzazione
                changeFragmentVisibility(daltonizeMenu);
                //Chiudo il menu dei filtri se visibile
                if(filtersMenu.isVisible()){
                    changeFragmentVisibility(filtersMenu);
                }
            }
        });

        //Ascoltatore che permette di scattare foto e di mostrare all'utente la buona riuscita dell'operazione
        ImageButton takePicture = (ImageButton)findViewById(R.id.aperture);
        takePicture.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                //Se la foto è stata scattata con successo
                if(capture()){
                    //Mostro il messaggio di successo
                    Toast.makeText(CameraActivity.this, getString(R.string.success_photo_toast_message), Toast.LENGTH_SHORT).show();
                }
            }
        });

        //Ascoltatore che apre la pagina delle preferenze
        ImageButton settings = (ImageButton)findViewById(R.id.settings);
        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(CameraActivity.this, SettingsActivity.class);
                CameraActivity.this.startActivity(myIntent);
                /** TODO Per colpa del fragment perde i frame */
            }
        });

        //Ascoltatore che apre la pagina delle info
        ImageButton info = (ImageButton)findViewById(R.id.info);
        info.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent myIntent = new Intent(CameraActivity.this, InfoActivity.class);
                CameraActivity.this.startActivity(myIntent);
            }
        });


        //Controllo i permessi per la fotocamera
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA)) {
                Toast.makeText(this, "Camera access is required.", Toast.LENGTH_SHORT).show();

            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA},
                        REQUEST_CAMERA_PERMISSION);
            }

        } else {
            setupCameraPreviewView();
        }
    }


    /**
     * Ogni volta che questa activity viene fatta ripartire, controlla se le impostazioni sono state
     * cambiate.
     */
    @Override
    protected void onResume() {
        super.onResume();
        SettingsManager.updateSettings();
    }

    /**
     * Gestisce la comparsa e scomparsa di un fragment
     * @param frag il fragment da far sparire/comparire
     */
    public void changeFragmentVisibility(Fragment frag){
        FragmentManager fragmentManager = getFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        if(!frag.isVisible()){
            fragmentTransaction.show(frag).commit();

        } else {
            fragmentTransaction.hide(frag).commit();
        }
    }


    /**
     * Metodo che permette la comunicazione con il fragment responsabile del menu dei filtri.
     * Riceve un id relativo al filtro da applicare e nasconde il fragment.
     * @param filterId l'id del filtro da applicare
     */
    @Override
    public void getFilterFromMenu(int filterId) {
        changeFragmentVisibility(filtersMenu);  //Chiudo il menu dei filtri
        renderer.setSelectedFilter(filterId);   //Applico il filtro scelto
        setFilterType(filterId);    //Setto la scritta nella parte superiore dello schermo
        currentFilter = filterId;   //Imposto il filtro corrente come quello selezionato

        //In base al filtro cambio l'icona del pulsante dei filtri
        switch (filterId) {
            case Filters.NORMAL_SIMULATION_ID: {
                filters.setImageResource(R.drawable.ic_filters);
            }
            break;
            case Filters.PROTANOPE_SIMULATION_ID: {
                filters.setImageResource(R.drawable.ic_filter_protanopia);
            }
            break;
            case Filters.DEUTERANOPE_SIMULATION_ID: {
                filters.setImageResource(R.drawable.ic_filter_deuteranopia);
            }
            break;
            case Filters.TRITANOPE_SIMULATION_ID: {
                filters.setImageResource(R.drawable.ic_filter_tritanopia);
            }
            break;
        }
    }

    /**
     * Metodo che permette la comunicazione con il fragment responsabile del menu dei filtri.
     * Riceve un id relativo al filtro da applicare e nasconde il fragment.
     * @param filterId l'id del filtro da applicare
     */
    @Override
    public void getDaltonizeFromMenu(int filterId) {
        changeFragmentVisibility(daltonizeMenu);  //Chiudo il menu dei filtri
        renderer.setSelectedFilter(filterId);   //Applico il filtro scelto
        setFilterType(filterId);    //Setto la scritta nella parte superiore dello schermo
        currentFilter = filterId;   //Imposto il filtro corrente come quello selezionato
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CAMERA_PERMISSION: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    setupCameraPreviewView();
                }
            }
        }
    }


    /**
     * Setta il testo relativo al tipo di filtro che si sta utilizzando. Il testo si trova
     * nella parte superiore dello schermo.
     * @param id l'id del filtro utilizzato
     */
    private void setFilterType(int id){
        switch (id){
            case Filters.NORMAL_SIMULATION_ID :
                filterType.setText(R.string.normal_filter_type_name);
                break;
            case Filters.PROTANOPE_SIMULATION_ID :
                filterType.setText(R.string.protanope_filter_type_name);
                break;
            case Filters.DEUTERANOPE_SIMULATION_ID :
                filterType.setText(R.string.deuteranope_filter_type_name);
                break;
            case Filters.TRITANOPE_SIMULATION_ID :
                filterType.setText(R.string.tritanope_filter_type_name);
                break;
            case Filters.PROTANOPE_DALTONIZATION_ID :
                filterType.setText(R.string.protanope_daltonize_type_name);
                break;
            case Filters.DEUTERANOPE_DALTONIZATION_ID :
                filterType.setText(R.string.deuteranope_daltonize_type_name);
                break;
            case Filters.TRITANOPE_DALTONIZATION_ID :
                filterType.setText(R.string.tritanope_daltonize_type_name);
                break;
        }
    }


    /**
     * Imposta la renderizzazione e applica un ascoltatore per la pressione nella textureView
     */
    void setupCameraPreviewView() {
        renderer = new CameraRenderer(this);
        textureView = (TextureView) findViewById(R.id.textureview);
        assert textureView != null;
        textureView.setSurfaceTextureListener(renderer);

        //Ascoltatore che recepisce la pressione e il rilascio della textureView
        textureView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                switch (event.getAction()) {

                    case MotionEvent.ACTION_DOWN:   //Se si tiene premuto sullo schermo

                        switch (currentFilter){
                            case Filters.PROTANOPE_DALTONIZATION_ID :
                                filterOnTouch(renderer, Filters.PROTANOPE_SIMULATION_ID);
                                break;

                            case Filters.DEUTERANOPE_DALTONIZATION_ID :
                                filterOnTouch(renderer, Filters.DEUTERANOPE_SIMULATION_ID);
                                break;

                            case Filters.TRITANOPE_DALTONIZATION_ID :
                                filterOnTouch(renderer, Filters.TRITANOPE_SIMULATION_ID);
                                break;
                            default :   //Nel caso in cui il filtro corrente sia una simulazione e non una daltonizzazione
                                if(currentFilter != Filters.NORMAL_SIMULATION_ID){
                                    filterOnTouch(renderer, Filters.NORMAL_SIMULATION_ID);
                                }
                        }

                        //Se il menu della simulazione è aperto, lo chiudo
                        if(filtersMenu.isVisible()){
                            changeFragmentVisibility(filtersMenu);
                        }

                        //Se il menu della daltonizzazione è aperto, lo chiudo
                        if(daltonizeMenu.isVisible()){
                            changeFragmentVisibility(daltonizeMenu);
                        }
                        break;

                    case MotionEvent.ACTION_UP:
                    case MotionEvent.ACTION_CANCEL: //Quando smetto di premere lo schermo

                        //Se il filtro corrente non è quello normale, reimposto il filtro precedentemente scelto
                        if(currentFilter != Filters.NORMAL_SIMULATION_ID) {
                            renderer.setSelectedFilter(currentFilter);
                            setFilterType(currentFilter);
                        }
                        break;
                }
                return true;
            }
        });

        textureView.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                renderer.onSurfaceTextureSizeChanged(null, v.getWidth(), v.getHeight());
            }
        });
    }


    /**
     * Applica il filtro richiesto quando si preme in un punto sullo schermo se nelle preferenze
     * è stata spuntata la checkbox corrispondere al tipo di filtro da visualizzare
     * @param renderer il renderizzatore
     * @param filterID l'id del filtro da applicare
     */
    private void filterOnTouch(CameraRenderer renderer, int filterID){
        //Se è stato scelto di visualizzare un filtro
        if(SettingsManager.currentFingerFilter) {
            renderer.setSelectedFilter(filterID);
            setFilterType(filterID);
        } else {
            //Mostro la visione normale
            renderer.setSelectedFilter(Filters.NORMAL_SIMULATION_ID);
            setFilterType(Filters.NORMAL_SIMULATION_ID);
        }
    }

    /**
     * Crea il nome della foto che verrà salvata in memoria. Il nome tiene conto del filtro applicato
     * più i dati relativi alla data corrente.
     * @param format il formato in cui salvare l'immagine
     * @return il nome del file da salvare
     */
    private String createPhotoName(String format){
        String filterName = (String)filterType.getText();
        Date date = new Date();
        SimpleDateFormat today = new SimpleDateFormat("yyyy-MMMM-dd_hhmmss", Locale.ITALIAN);
        String timeString = today.format(date);
        return filterName+"_"+timeString+"."+format;
    }

    /**
     * Permette di salvare in memoria il frame corrente
     * @return true se il salvataggio è avvenuto correttamente, false altrimenti
     */
    private boolean capture() {
        //Il path in cui salvare la foto
        String path = Environment.getExternalStorageDirectory().toString();
        File dir = new File(path, "/PICTURES/SeeBetter/");
        //Se la directory non esiste, la crea
        if (!dir.isDirectory()) {
            dir.mkdirs();
        }

        //Il file da salvare
        File file = new File(dir, createPhotoName("jpg"));

        //Ottengo il bitmap del frame
        Bitmap bitmap = textureView.getBitmap();
        OutputStream outputStream = null;

        try {
            outputStream = new FileOutputStream(file);
            //Salvo l'immagine in JPEG con la massima risoluzione
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
            outputStream.flush();
            outputStream.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;

        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
