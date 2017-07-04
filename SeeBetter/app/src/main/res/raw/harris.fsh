#extension GL_OES_standard_derivatives : enable
precision mediump float;

uniform vec3                iResolution;
uniform float               iGlobalTime;
uniform sampler2D           iChannel0;
varying vec2                texCoord;

float SOGLIA = 0.02;
float HARRIS_K = 0.06;

void mainImage(out vec4 fragColor, in vec2 fragCoord){
    vec3 img = texture2D(iChannel0, fragCoord).rgb;

    if(fragCoord.x > 0.0){

        float gray = length(img.rgb);

        float ResS = 200.;
        float ResT = 200.;

        vec2 stp0 = vec2(1./ResS, 0.);  //Angolo in basso a sx
        vec2 st0p = vec2(0., 1./ResT);  //Angolo in alto a sx
        vec2 stpp = vec2(1./ResS, 1./ResT); //Angolo in basso a dx
        vec2 stpm = vec2(1./ResS, -1./ResT);    //angolo in alto a dx

        float i00 = length(texture2D(iChannel0, fragCoord).rgb);
        float im1m1 = length(texture2D(iChannel0, fragCoord-stpp).rgb);
        float ip1p1 = length(texture2D(iChannel0, fragCoord+stpp).rgb);
        float im1p1 = length(texture2D(iChannel0, fragCoord-stpm).rgb);
        float ip1m1 = length(texture2D(iChannel0, fragCoord+stpm).rgb);
        float im10 = length(texture2D(iChannel0, fragCoord-stp0).rgb);
        float ip10 = length(texture2D(iChannel0, fragCoord+stp0).rgb);
        float i0m1 = length(texture2D(iChannel0, fragCoord-st0p).rgb);
        float i0p1 = length(texture2D(iChannel0, fragCoord+st0p).rgb);


        vec2 dF00 = vec2(dFdx(gray), dFdy(gray));
        vec2 dF11 = vec2(dFdx(ip1p1), dFdy(ip1p1));
        vec2 dFn1n1 = vec2(dFdx(im1m1), dFdy(im1m1));
        vec2 dFn11 = vec2(dFdx(im1p1), dFdy(im1p1));
        vec2 dF1n1 = vec2(dFdx(ip1m1), dFdy(ip1m1));
        vec2 dFn10 = vec2(dFdx(im10), dFdy(im10));
        vec2 dF10 = vec2(dFdx(ip10), dFdy(ip10));
        vec2 dF0n1 = vec2(dFdx(i0m1), dFdy(i0m1));
        vec2 dF01 = vec2(dFdx(i0p1), dFdy(i0p1));

        float prova = pow(2.,2.);

        float Sx_2 = pow(dF00.x, 2.) + pow(dF11.x, 2.) +pow(dFn1n1.x, 2.) +pow(dFn11.x, 2.) +pow(dF1n1.x, 2.) +pow(dFn10.x, 2.) +pow(dF10.x, 2.) +pow(dF0n1.x, 2.) +pow(dF01.x, 2.);
        float Sy_2 = pow(dF00.y, 2.) + pow(dF11.y, 2.) +pow(dFn1n1.y, 2.) +pow(dFn11.y, 2.) +pow(dF1n1.y, 2.) +pow(dFn10.y, 2.) +pow(dF10.y, 2.) +pow(dF0n1.y, 2.) +pow(dF01.y, 2.);
        float Sxy = dF00.x*dF00.y + dF11.x*dF11.y + dFn1n1.x*dFn1n1.y + dFn11.x*dFn11.y + dF1n1.x*dF1n1.y + dFn10.x*dFn10.y + dF10.x*dF10.y + dF0n1.x*dF0n1.y + dF01.x*dF01.y;

        //mat3 m = mat3(
        //    im1p1, im10, im1m1,
        //    i0p1, i00, i0m1,
        //    ip1p1, ip10, ip1m1
        //);

        mat2 m = mat2(
            Sx_2, Sxy,
            Sxy, Sy_2
        );


        float det = Sx_2*Sy_2 - pow(Sxy, 2.0);

        float traccia = HARRIS_K * pow(Sx_2+Sy_2, 2.0);

        float ris = det-traccia;

        if(ris > SOGLIA){
            fragColor = vec4(1.0, 0.0, 0.0, 1.0);
        } else {
            fragColor = vec4(img, 1.0);
        }

    } else {

    fragColor = vec4(img, 1.0);

    }
}

void main() {
	mainImage(gl_FragColor, texCoord);
}