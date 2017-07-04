package com.example.seebetter.Filter;

import android.content.Context;
import android.opengl.GLES20;

import com.example.seebetter.Filters;
import com.example.seebetter.MyGLUtils;

/**
 * @author DottRosa (marco1rosa@gmail.com)
 *
 * Filtro di simulazione deuteranope
 */

public class DeuteranopeFilter extends CameraFilter{
    //L'id del filtro da applicare
    private int program;

    /**
     * Costruttore che imposta l'id del filtro in base alla scelta compiuta nel menu di simulazione
     * @param context il contesto di CameraFilter
     */
    public DeuteranopeFilter(Context context) {
        super(context);

        //Imposto il filtro deuteranope
        program = MyGLUtils.buildProgram(context, Filters.VERTEX, Filters.DEUTERANOPE_SIMULATION_SHADER);
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
