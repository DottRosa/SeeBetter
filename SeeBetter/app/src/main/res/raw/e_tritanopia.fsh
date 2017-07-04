#extension GL_OES_standard_derivatives : enable
precision mediump float;

uniform vec3                iResolution;
uniform float               iGlobalTime;
uniform sampler2D           iChannel0;
varying vec2                texCoord;


float threshold = 0.2;


void mainImage(out vec4 fragColor, in vec2 fragCoord ){

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
        fragColor = vec4(0.0, 1.0, 1.0, 1.0);
    } else {
        fragColor = vec4(center, 1.0);
    }
}

void main() {
	mainImage(gl_FragColor, texCoord);
}