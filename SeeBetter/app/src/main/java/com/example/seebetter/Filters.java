package com.example.seebetter;

/**
 * @author DottRosa (marco1rosa@gmail.com)
 *
 * Classe che provvede a fornire tutti gli ID e gli shader utili all'applicazione
 */
public class Filters{

    //ID RELATIVO AL FILTRO UTILIZZATO
    //Id della visione normale
    static final int NORMAL_SIMULATION_ID = R.id.filter_1;

    //Id della simulazione protanope
    static final int PROTANOPE_SIMULATION_ID = R.id.filter_2;

    //Id della simulazione deuteranope
    static final int DEUTERANOPE_SIMULATION_ID = R.id.filter_3;

    //Id della simulazione tritanope
    static final int TRITANOPE_SIMULATION_ID = R.id.filter_4;

    //Id della daltonizzazione protanope
    static final int PROTANOPE_DALTONIZATION_ID = R.id.daltonize_p;

    //Id della daltonizzazione deuteranope
    static final int DEUTERANOPE_DALTONIZATION_ID = R.id.daltonize_d;

    //Id della daltonizzazione tritanope
    static final int TRITANOPE_DALTONIZATION_ID = R.id.daltonize_t;



    //SHADER PER I VERTICI DI OGNI FILTRO
    //Shader per i vertici
    public static final int VERTEX = R.raw.vertext;



    //SHADER PER LA VISIONE NORMALE
    //Shader per la visione normale
    public static final int NORMAL_SHADER = R.raw.normal;



    //SHADER PER LA SIMULAZIONE
    //Shader per la simulazione protanope
    public static final int PROTANOPE_SIMULATION_SHADER = R.raw.protanopia;

    //Shader per la simulazione deuteranope
    public static final int DEUTERANOPE_SIMULATION_SHADER = R.raw.deuteranopia;

    //Shader per la simulazione tritanope
    public static final int TRITANOPE_SIMULATION_SHADER = R.raw.tritanopia;



    //SHADER PER LA DALTONIZZAZIONE PROTANOPE
    //Shader per la simulazione protanope
    public static final int PROTANOPE_COLOR_DALTONIZATION_SHADER = R.raw.d_protanopia_color;

    //Shader per la simulazione protanope
    public static final int PROTANOPE_EDGE_SHADER = R.raw.e_protanopia;

    //Shader per la simulazione protanope
    public static final int PROTANOPE_BOTH_DALTONIZATION_SHADER = R.raw.d_protanopia_both;



    //SHADER PER LA DALTONIZZAZIONE DEUTERANOPE
    //Shader per la simulazione protanope
    public static final int DEUTERANOPE_COLOR_DALTONIZATION_SHADER = R.raw.d_deuteranopia_color;

    //Shader per la simulazione protanope
    public static final int DEUTERANOPE_EDGE_SHADER = R.raw.e_deuteranopia;

    //Shader per la simulazione protanope
    public static final int DEUTERANOPE_BOTH_DALTONIZATION_SHADER = R.raw.d_deuteranopia_both;



    //SHADER PER LA DALTONIZZAZIONE TRITANOPE
    //Shader per la simulazione protanope
    public static final int TRITANOPE_COLOR_DALTONIZATION_SHADER = R.raw.d_tritanopia_color;

    //Shader per la simulazione protanope
    public static final int TRITANOPE_EDGE_SHADER = R.raw.e_tritanopia;

    //Shader per la simulazione protanope
    public static final int TRITANOPE_BOTH_DALTONIZATION_SHADER = R.raw.d_tritanopia_both;
}
