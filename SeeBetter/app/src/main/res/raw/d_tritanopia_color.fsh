precision mediump float;

uniform vec3                iResolution;
uniform float               iGlobalTime;
uniform sampler2D           iChannel0;
varying vec2                texCoord;

//Matrice di conversione da RGB a LMS (le righe rappresentano le colonne)
mat3 RGBtoLMS = mat3(
   17.8824, 43.5161, 4.1193,
   3.4557, 27.1554, 3.8671,
   0.02996, 0.18431, 1.4670
);

//Matrice di conversione da LMS a RGB (le righe rappresentano le colonne)
mat3 LMStoRGB = mat3(
   0.0809, -0.1305, 0.1167,
   -0.0102, 0.0540, -0.1136,
   -0.0003, -0.0041, 0.6935
);

mat3 toTRITAN = mat3(
    0.95451, -0.00447, -0.01251,
    -0.04719, 0.96543, 0.07312,
    2.74872, 0.88835, -0.01161
);

mat3 DALTONIZE = mat3(
    1.0, 0.0, 0.7,
    0.0, 1.0, 0.7,
    0.0, 0.0, 0.0
);

void mainImage(out vec4 fragColor, in vec2 fragCoord ){

    vec3 tex = texture2D( iChannel0, fragCoord.xy).rgb;     //Estraggo il colore rgb del pixel

    vec3 realRGB = tex.rgb*255.0;   //Passo da [0,1] a [0,255]

    vec3 LMS = realRGB*RGBtoLMS;    //Trovo il rispettivo colore nello spazio LMS

    vec3 newLMS = toTRITAN*LMS;     //Simulo la protanopia

    vec3 RGB = newLMS*LMStoRGB;             //Converto da LMS a RGB

    vec3 difference = RGB-realRGB;  //Calcolo la differenza tra l'rgb trovato e quello di partenza

    vec3 RRGGBB = difference*DALTONIZE; //Trovo i valori per la correzione dell'immagine

    vec3 finalRGB = vec3(RRGGBB.r+RGB.r, RRGGBB.g+RGB.g, RRGGBB.b+RGB.b);

    if(finalRGB.r > 255.0){
        finalRGB.r = 255.0;
    }
    if(finalRGB.g > 255.0){
        finalRGB.g = 255.0;
    }
    if(finalRGB.b > 255.0){
        finalRGB.b = 255.0;
    }

    fragColor = vec4(finalRGB/255.0, 1.0);        //Passo il colore calcolato riportandolo al range [0,1]
}

void main() {
	mainImage(gl_FragColor, texCoord);
}