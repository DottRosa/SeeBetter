package com.example.seebetter.Filter;

import android.content.Context;
import android.opengl.GLES20;

import com.example.seebetter.Filters;
import com.example.seebetter.MyGLUtils;
import com.example.seebetter.R;
import com.example.seebetter.SettingsManager;

/**
 * @author DottRosa (marco1rosa@gmail.com)
 *
 * Filtro di daltonizzazione protanope
 */

public class DaltonizeProtanope extends CameraFilter implements DaltonizeInterface{
    //L'id del filtro da applicare
    private int program;

    /**
     * Costruttore che imposta l'id del filtro in base alla scelta compiuta nel menu di daltonizzazione
     * @param context il contesto di CameraFilter
     */
    public DaltonizeProtanope(Context context) {
        super(context);

        //Imposto il filtro in base alla scelta dell'utente
        switch (SettingsManager.currentMethod) {
            case COLOR: //Filtro di modifica del colore
                program = MyGLUtils.buildProgram(context, Filters.VERTEX, Filters.PROTANOPE_COLOR_DALTONIZATION_SHADER);
                break;

            case EDGE:  //Filtro della edge detection
                program = MyGLUtils.buildProgram(context, Filters.VERTEX, Filters.PROTANOPE_EDGE_SHADER);
                break;

            case BOTH:  //Filtro che unisce colore ed edge detection
                program = MyGLUtils.buildProgram(context, Filters.VERTEX, Filters.PROTANOPE_BOTH_DALTONIZATION_SHADER);
                break;
        }
    }

    /**
     * Applica il filtro su tutto il frame
     * @param cameraTexId l'id della camera
     * @param canvasWidth larghezza della canvas
     * @param canvasHeight altezza della canvas
     */
    @Override
    public void onDraw(int cameraTexId, int canvasWidth, int canvasHeight) {
        setupShaderInputs(program,
                new int[]{canvasWidth, canvasHeight},
                new int[]{cameraTexId},
                new int[][]{});
        GLES20.glDrawArrays(GLES20.GL_TRIANGLE_STRIP, 0, 4);
    }
}
