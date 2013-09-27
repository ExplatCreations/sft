varying vec4 v_color;
varying vec2 v_texCoords;

uniform sampler2D u_texture;
uniform vec2 u_renderSize;
#define CLAMP(a) clamp(a, 0.0, 1.0)


void main() {
    vec4 inColor = texture2D(u_texture, gl_FragCoord.xy/(u_renderSize));
    float r = inColor.r;
    float g = inColor.g;
    float b = inColor.b;
    float a = inColor.a;
    float rOut = (r * 0.393) + (g * 0.769) + (b * 0.189);
    float gOut = (r * 0.349) + (g * 0.686) + (b * 0.168);
    float bOut = (r * 0.272) + (g * 0.534) + (b * 0.131);
    if (inColor.xyz == vec3(0.0, 0.0, 0.0)) {
        discard;
    } else {
        //gl_FragColor = vec4(1.0,0.3,0.0,1.0);
        gl_FragColor = vec4(CLAMP(rOut),
                            CLAMP(gOut),
                            CLAMP(bOut),
                            a);
    }
}
