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

//Matrice di conversione per la protanopia
mat3 toDEUTAN = mat3(
    1.42319, 0.67558, 0.00267,
    -0.88995, -0.42203, -0.00504,
    1.77557, 2.82788, 0.99914
);

//Matrice di daltonizzazione per protanopia
mat3 DALTONIZE = mat3(
    1.0, 0.5, 0.0,
    0.0, 0.0, 0.0,
    0.0, 0.5, 1.0
);

float threshold = 0.2;

void mainImage(out vec4 fragColor, in vec2 fragCoord ){

    vec3 tex = texture2D( iChannel0, fragCoord.xy).rgb;     //Estraggo il colore rgb del pixel

    vec3 realRGB = tex.rgb*255.0;   //Passo da [0,1] a [0,255]

    vec3 LMS = realRGB*RGBtoLMS;    //Trovo il rispettivo colore nello spazio LMS

    vec3 newLMS = toDEUTAN*LMS;     //Simulo la protanopia

    vec3 RGB = newLMS*LMStoRGB;             //Converto da LMS a RGB

    vec3 difference = RGB-realRGB;  //Calcolo la differenza tra l'rgb trovato e quello di partenza

    vec3 RRGGBB = difference*DALTONIZE; //Trovo i valori per correggere l'rgb trovato

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

    //Calcolo gli edge

    float ResS = 1080.;
    float ResT = 1920.;

    float X = 1./ResS;
    float Y = 1./ResT;

    vec3 topLeft = texture2D(iChannel0, fragCoord + vec2(-X,Y)).rgb;
    vec3 top = texture2D(iChannel0, fragCoord + vec2(0,Y)).rgb;
    vec3 topRight = texture2D(iChannel0, fragCoord + vec2(X,Y)).rgb;
    vec3 left = texture2D(iChannel0, fragCoord + vec2(-X,0)).rgb;
    vec3 center = texture2D(iChannel0, fragCoord).rgb;
    vec3 right = texture2D(iChannel0, fragCoord + vec2(X,0)).rgb;
    vec3 bottomLeft = texture2D(iChannel0, fragCoord + vec2(-X,-Y)).rgb;
    vec3 bottom = texture2D(iChannel0, fragCoord + vec2(0,-Y)).rgb;
    vec3 bottomRight = texture2D(iChannel0, fragCoord + vec2(X,-Y)).rgb;

    float pixelX = length(topLeft) - length(topRight) + 2.0*length(left) - 2.0*length(right) + length(bottomLeft) - length(bottomRight);
    float pixelY = length(topLeft) + 2.0*length(top) + length(topRight) - length(bottomLeft) - 2.0*length(bottom) - length(bottomRight);

    float val = sqrt((pixelX*pixelX)+(pixelY*pixelY));

    if(val > threshold){
        fragColor = vec4(0.0, 0.0, 1.0, 1.0);
    } else {
        fragColor = vec4(finalRGB/255.0, 1.0);        //Passo il colore calcolato riportandolo al range [0,1]
    }
}

void main() {
	mainImage(gl_FragColor, texCoord);
}